<template>
    <div class="app-container">
        <h2 style="text-align: center">发布新课程</h2>
        <el-steps :active="active"
                  process-status="wait"
                  align-center
                  style="margin-
bottom: 40px;">
            <el-step title="填写课程基本信息" />
            <el-step title="创建课程大纲" />
            <el-step title="最终发布" />
        </el-steps>

        <div class="ccInfo">
            <img :src="publishCourseinfo.cover" />
            <div class="main">
                <h2>{{ publishCourseinfo.title }}</h2>
                <p class="gray">
                    <span>共{{ publishCourseinfo.lessonNum }}课时</span>
                </p>
                <p>
                    <span>所属分类：{{ publishCourseinfo.subjectLevelOne }} —
                        {{ publishCourseinfo.subjectLevelTwo }}</span>
                </p>
                <p>课程讲师：{{ publishCourseinfo.teacherName }}</p>
                <h3 class="red">￥{{ publishCourseinfo.price }}</h3>
            </div>
        </div>

        <el-form label-width="120px"
                 style="text-align: center">
            <el-form-item>
                <el-button @click="previous">返回修改</el-button>
                <el-button :disabled="saveBtnDisabled"
                           type="primary"
                           @click="publish">发布课程</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
// 引入调用 subject.js 文件
import course from "@/api/teacher/course";

export default {
    data() {
        return {
            active: 3,
            saveBtnDisabled: false,
            courseId: "",
            publishCourseinfo: {},
        };
    },
    created() {
        this.courseId = this.$route.params.id;
        this.getPublishCourseInfo();
    },
    methods: {
        getPublishCourseInfo() {
            course.getPublishCourseInfo(this.courseId).then((resp) => {
                this.publishCourseinfo = resp.data.course;
                console.log(this.publishCourseinfo);
            });
        },

        // 上一步
        previous() {
            this.$router.push({ path: `/course/chapter/${this.courseId}` });
        },

        // 最终发布
        publish() {
            this.$confirm("此操作将发布课程, 是否继续?", "提示", {
                // 设置 cancel 和 close 为不同的事件
                distinguishCancelAndClose: true,
                confirmButtonText: "确定",
                cancalButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    course.publishCourse(this.courseId).then((resp) => {
                        // 提示
                        this.$message({
                            message: "发布课程成功",
                            type: "success",
                        });

                        // 跳转列表页面
                        this.$router.push({ path: `/course/list` });
                    });
                })
                .catch((action) => {
                    if (action === "cancel") {
                        this.$message({
                            type: "info",
                            message: "取消发布",
                        });
                    } else if (action === "close") {
                        this.$message({
                            type: "info",
                            message: "已关闭",
                        });
                    }
                });
        },
    },
};
</script>

<style scoped>
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #ddd;
    margin-bottom: 40px;
    position: relative;
}
.ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
}
.ccInfo .main {
    margin-left: 520px;
}
.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}
.ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
}
</style>