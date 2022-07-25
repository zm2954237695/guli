<template>
    <div class="app-container">

        <el-form label-width="120px">
            <el-form-item label="讲师名称">
                <el-input v-model="teacher.name" />
            </el-form-item>
            <el-form-item label="讲师排序">
                <el-input-number
                    v-model="teacher.sort"
                    controls-position="right"
                    :min="0"
                />
            </el-form-item>
            <el-form-item label="讲师头衔">
                <el-select
                    v-model="teacher.level"
                    clearable
                    placeholder="选择讲师头衔"
                >
                    <!--
        数据类型一定要和取出的json中的一致，否则没法回填
        因此，这里value使用动态绑定的值，保证其数据类型是number
        -->
                    <el-option
                        :value="1"
                        label="高级讲师"
                    />
                    <el-option
                        :value="2"
                        label="首席讲师"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="讲师资历">
                <el-input v-model="teacher.career" />
            </el-form-item>
            <el-form-item label="讲师简介">
                <el-input
                    v-model="teacher.intro"
                    :rows="10"
                    type="textarea"
                />
            </el-form-item>
            <!-- 讲师头像 -->
            <el-form-item label="讲师头像">
                <!-- 头衔缩略图 -->
                <pan-thumb :image="String(teacher.avatar)" />
                <!-- 文件上传按钮 -->
                <el-button
                    type="primary"
                    icon="el-icon-upload"
                    @click="imagecropperShow = true"
                >更换头像
                </el-button>
                <!--
                    v-show：是否显示上传组件
                    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                    :url：后台上传的url地址
                    @close：关闭上传组件
                    @crop-upload-success：上传成功后的回调 -->
                <image-cropper
                    v-show="imagecropperShow"
                    :width="300"
                    :height="300"
                    :key="imagecropperKey"
                    :url="BASE_API + '/eduoss/fileoss'"
                    field="file"
                    @close="close"
                    @crop-upload-success="cropSuccess"
                />
            </el-form-item>
            <el-form-item>
                <el-button
                    :disabled="saveBtnDisabled"
                    type="primary"
                    @click="saveOrUpdate"
                >保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>


<script>
// 引入调用teacher.js文件
import teacher from "@/api/teacher/teacher";
//引入头像组件
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";

export default {
    data() {
        return {
            teacher: {
                name: "",
                sort: 0,
                level: 1,
                career: "",
                intro: "",
                avatar: "",
            },
            // 保存按钮是否禁用 防止二次点击
            saveBtnDisabled: false,
            // 上传弹窗的组件是否显示
            imagecropperShow: false,
            // 获取dev.env.js的ip和端口号
            BASE_API: process.env.BASE_API,
            imagecropperKey: "0",
        };
    },
    created() {
        this.init();
    },
    watch: {
        // 加此监听的原因是，在更新页面有讲师的时候点击新增，讲师内容并没有消失，所以需要监听路由的变化，来判断是否需要重置内容
        // $route(to, from)固定写法，意思为监听路由变化
        $route(to, from) {
            console.log(to, from);
            // 路由变化方式，当路由发生变化，方法就执行
            console.log("watch $route");
            this.init();
        },
    },
    methods: {
        init() {
            // 判断路径里面是否有讲师id值
            if (this.$route.params && this.$route.params.id) {
                // 从路径中获取id值
                const id = this.$route.params.id;

                // 调用根据id查询的方法
                this.getTeacherById(id);
            } else {
                this.teacher = {};
            }
        },

        // 保存或更新
        saveOrUpdate() {
            this.saveBtnDisabled = true;

            if (this.teacher.id) {
                // 调用根据id查询的方法
                this.updateTeacher();
            } else {
                // 添加
                this.saveData();
            }
        },

        // 添加讲师
        saveData() {
            teacher.saveTeacher(this.teacher).then((response) => {
                // 提示信息-添加成功
                this.$message({
                    message: "添加讲师记录成功",
                    type: "success",
                });
                // 跳转到列表页面 路由跳转
                this.$router.push({
                    path: "/teacher/table",
                });
            });
        },

        // 根据 id 查询讲师信息
        getTeacherById(id) {
            teacher.getTeacherById(id).then((response) => {
                this.teacher = response.data.teacher;
            });
        },

        // 更新讲师
        updateTeacher() {
            teacher.updateTeacher(this.teacher).then((response) => {
                // 提示信息-更新成功
                this.$message({
                    message: "更新讲师记录成功",
                    type: "success",
                });
                // 跳转到列表页面 路由跳转
                this.$router.push({
                    path: "/teacher/table",
                });
            });
        },

        // 关闭上传弹框
        close() {
            this.imagecropperShow = false;

            // 上传组件初始化
            this.imagecropperKey = this.imagecropperKey + 1;
        },

        // 上传成功时执行的方法
        cropSuccess(data) {
            // 上传之后接口返回图片地址
            this.teacher.avatar = data.url;
            this.imagecropperShow = false;
            this.imagecropperKey = this.imagecropperKey + 1;
        },
    },
    components: {
        ImageCropper,
        PanThumb,
    },
};
</script>