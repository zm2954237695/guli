<template>
    <div class="app-container">
        <h2 style="text-align: center">发布新课程</h2>
        <el-steps
            :active="active"
            finish-status="success"
            align-center
            style="margin-bottom: 40px;"
        >
            <el-step title="填写课程基本信息"></el-step>
            <el-step title="创建课程大纲"></el-step>
            <el-step title="最终发布"></el-step>
        </el-steps>

        <el-form label-width="120px">
            <el-form-item label="课程标题">
                <el-input
                    v-model="courseInfo.title"
                    placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
                />
            </el-form-item>

            <!-- 所属分类 -->
            <el-form-item label="所属分类">
                <el-select
                    v-model="courseInfo.subjectParentId"
                    placeholder="一级分类"
                    @change="subjectLevelOneChanged"
                >
                    <el-option
                        v-for="subject in subjectOneLists"
                        :key="subject.id"
                        :label="subject.title"
                        :value="subject.id"
                    ></el-option>
                </el-select>
                <el-select
                    v-model="courseInfo.subjectId"
                    placeholder="二级分类"
                >
                    <el-option
                        v-for="subject in subjectTwoLists"
                        :key="subject.id"
                        :label="subject.title"
                        :value="subject.id"
                    ></el-option>
                </el-select>
            </el-form-item>
            <!--课程讲师-->
            <el-form-item label="课程讲师">
                <el-select
                    v-model="courseInfo.teacherId"
                    placeholder="请选择"
                >
                    <el-option
                        v-for="teacher in teacherLists"
                        :key="teacher.id"
                        :label="teacher.name"
                        :value="teacher.id"
                    ></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="总课时">
                <el-input-number
                    :min="0"
                    v-model="courseInfo.lessonNum"
                    controls-position="right"
                    placeholder="请填写课程的总课时数"
                />
            </el-form-item>

            <!-- 课程简介-->
            <el-form-item label="课程简介">
                <tinymce
                    :height="300"
                    v-model="courseInfo.description"
                />
            </el-form-item>

            <!-- 课程封面 -->
            <el-form-item label="课程封面">
                <el-upload
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    :action="BASE_API + '/eduoss/fileoss'"
                    class="avatar-uploader"
                >
                    <img
                        v-if="courseInfo.cover"
                        :src="courseInfo.cover"
                        class="avatar"
                    />
                    <i
                        v-else
                        class="el-icon-plus avatar-uploader-icon"
                    ></i>
                </el-upload>
            </el-form-item>

            <el-form-item label="课程价格">
                <el-input-number
                    :min="0"
                    v-model="courseInfo.price"
                    controls-position="right"
                    placeholder="免费课程请设置为0元"
                />
                元
            </el-form-item>

            <el-form-item style="text-align: center">
                <el-button
                    :disabled="saveBtnDisabled"
                    type="primary"
                    @click="next"
                >保存并下一步</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
// 引入调用 subject.js 文件
import course from "@/api/teacher/course";
// 讲师
import teacher from "@/api/teacher/teacher";
// 分类
import subject from "@/api/teacher/subject.js";
// 富文本
import Tinymce from "@/components/Tinymce";

export default {
    data() {
        return {
            active: 1,
            saveBtnDisabled: false,
            courseInfo: {
                title: "",
                subjectParentId: "",
                subjectId: "",
                teacherId: "",
                lessonNum: 0,
                description: "",
                cover: "",
                price: 0,
            },
            // 封装讲师列表 用于下拉所有讲师
            teacherLists: [],
            // 封装所以一级分类数据
            subjectOneLists: [],
            // 封装二级分类数据
            subjectTwoLists: [],
            BASE_API: process.env.BASE_API,
            courseId: "",
        };
    },
    created() {
        // 初始化
        this.init();
    },
    watch: {
        // 加此监听的原因是，在更新页面有数据的时候点击新增，数据并没有消失，所以需要监听路由的变化，来判断是否需要重置内容
        // $route(to, from)固定写法，意思为监听路由变化
        $route(to, from) {
            console.log(to, from);
            // 路由变化方式，当路由发生变化，方法就执行
            this.init();
        },
    },
    methods: {
        // 初始化方法
        init() {
            if (this.$route.params && this.$route.params.id) {
                this.courseId = this.$route.params.id;
                this.getCourseInfo();
            } else {
                this.getAllTeacher();
                this.getSubjectList();
                this.courseInfo = {};
            }
        },

        // 点击下一步进行保存
        next() {
            this.saveOrUpdate();
        },

        // 根据数据中是否有id，判断是新增还是修改
        saveOrUpdate() {
            if (this.courseInfo.id) {
                this.updateCourse();
            } else {
                this.addCourse();
            }
        },

        // 所有讲师
        getAllTeacher() {
            teacher.getAllTeacher().then((resp) => {
                this.teacherLists = resp.data.items;
            });
        },

        //课程分类
        getSubjectList() {
            subject.getAllSubject().then((resp) => {
                this.subjectOneLists = resp.data.list;
            });
        },

        // 一级分类改变时，二级分类变成一级分类的字段
        subjectLevelOneChanged(value) {
            for (let i = 0; i < this.subjectOneLists.length; i++) {
                if (this.subjectOneLists[i].id == value) {
                    this.subjectTwoLists = this.subjectOneLists[i].children;
                }
            }
        },

        // 上传成功后
        handleAvatarSuccess(resp) {
            this.courseInfo.cover = resp.data.url;
        },

        // 上传之前调用的方法
        beforeAvatarUpload(file) {
            // 判断大小、类型
            const isJPG = file.type === "image/jpeg";
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 2MB!");
            }
            return isJPG && isLt2M;
        },

        // 添加课程
        addCourse() {
            course.addCourseInfo(this.courseInfo).then((resp) => {
                // 成功提示
                this.$message({
                    message: "保存课程信息成功",
                    type: "success",
                });
                // 然后跳转下一页面
                this.$router.push({
                    path: "/course/chapter/" + resp.data.courseId,
                });
            });
        },

        // 更新课程
        updateCourse() {
            course.updateCourseInfo(this.courseInfo).then((resp) => {
                // 成功提示
                this.$message({
                    message: "保存课程信息成功",
                    type: "success",
                });
                // 然后跳转下一页面
                this.$router.push({
                    path: "/course/chapter/" + resp.data.courseId,
                });
            });
        },

        // 数据回显
        getCourseInfo() {
            course.getCourseById(this.courseId).then((resp) => {
                this.courseInfo = resp.data.course;
                // 获取对应的二级分类列表
                subject.getAllSubject().then((resp) => {
                    this.subjectOneLists = resp.data.list;
                    // 获取所有讲师接口
                    this.getAllTeacher();
                    if (this.courseInfo.subjectId) {
                        this.subjectOneLists.forEach((element) => {
                            if (
                                element.id === this.courseInfo.subjectParentId
                            ) {
                                this.subjectTwoLists = element.children;
                            }
                        });
                    }
                });
            });
        },
    },
    components: {
        Tinymce,
    },
};
</script>
<style>
.tinymce-container {
    line-height: 0px;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>