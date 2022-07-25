<template>

    <div class="app-container">

        <h2 style="text-align: center;">发布新课程</h2>

        <el-steps :active="2"
                  process-status="wait"
                  align-center
                  style="margin-bottom: 40px;">
            <el-step title="填写课程基本信息" />
            <el-step title="创建课程大纲" />
            <el-step title="最终发布" />
        </el-steps>

        <el-button type="text"
                   @click="openChapterDialog()">添加章节</el-button>

        <!-- 章节 -->
        <ul class="chanpterList">
            <li v-for="chapter in chapterList"
                :key="chapter.id">
                <p>
                    {{ chapter.title }}

                    <span class="acts">
                        <el-button style=""
                                   type="text"
                                   @click="openVideo(chapter.id)">添加小节</el-button>
                        <el-button style=""
                                   type="text"
                                   @click="openEditChatper(chapter.id)">编辑</el-button>
                        <el-button type="text"
                                   @click="removeChapter(chapter.id)">删除</el-button>
                    </span>
                </p>

                <!-- 视频 -->
                <ul class="chanpterList videoList">
                    <li v-for="video in chapter.children"
                        :key="video.id">
                        <p>{{ video.title }}

                            <span class="acts">

                                <el-button style=""
                                           type="text"
                                           @click="openEditVideo(video.id)">编辑</el-button>
                                <el-button type="text"
                                           @click="removeVideo(video.id)">删除</el-button>
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>
        <div style="text-align: center">
            <el-button @click="previous">上一步</el-button>
            <el-button :disabled="saveBtnDisabled"
                       type="primary"
                       @click="next">下一步</el-button>
        </div>

        <!-- 添加和修改章节表单 -->
        <el-dialog :visible.sync="dialogFormVisible"
                   title="添加章节">
            <el-form :model="chapter"
                     label-width="120px">
                <el-form-item label="章节标题">
                    <el-input v-model="chapter.title" />
                </el-form-item>
                <el-form-item label="章节排序">
                    <el-input-number v-model="chapter.sort"
                                     :min="0"
                                     controls-position="right" />
                </el-form-item>
            </el-form>
            <div slot="footer"
                 class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary"
                           @click="saveOrUpdate">确 定</el-button>
            </div>
        </el-dialog>

        <!--添加小节表单-->
        <!-- 添加和修改课时表单 -->
        <el-dialog :visible.sync="dialogVideoFormVisible"
                   title="添加课时">
            <el-form :model="video"
                     label-width="120px">
                <el-form-item label="课时标题">
                    <el-input v-model="video.title" />
                </el-form-item>
                <el-form-item label="课时排序">
                    <el-input-number v-model="video.sort"
                                     :min="0"
                                     controls-
                                     position="right" />
                </el-form-item>
                <el-form-item label="是否免费">
                    <el-radio-group v-model="video.isFree">
                        <el-radio :label="true">免费</el-radio>
                        <el-radio :label="false">默认</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="上传视频">
                    <el-upload :on-success="handleVodUploadSuccess"
                               :on-remove="handleVodRemove"
                               :before-remove="beforeVodRemove"
                               :on-exceed="handleUploadExceed"
                               :file-list="fileList"
                               :action="BASE_API + '/eduvod/video/uploadAliyunVideo'"
                               :limit="1"
                               class="upload-demo">
                        <el-button size="small"
                                   type="primary">上传视频</el-button>
                        <el-tooltip placement="right-end">
                            <div slot="content">
                                最大支持1G，<br />
                                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br />
                                SWF、TS、VOB、WMV、WEBM 等视频格式上传
                            </div>
                            <i class="el-icon-question" />
                        </el-tooltip>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer"
                 class="dialog-footer">
                <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
                <el-button :disabled="saveVideoBtnDisabled"
                           type="primary"
                           @click="saveOrUpdateVideo(video.id)">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import chapter from "@/api/teacher/chapter";
import video from "@/api/teacher/video";

export default {
    data() {
        return {
            active: 2,
            saveBtnDisabled: false,
            saveVideoBtnDisabled: false,
            courseId: "",
            chapterList: [],
            // 章节弹框是否显示
            dialogFormVisible: false,
            // 小节弹框是否显示
            dialogVideoFormVisible: false,
            chapter: {
                courseId: "",
                title: "",
                sort: 0,
            },
            video: {
                title: "",
                sort: 0,
                isFree: false,
                // 阿里云视频id
                videoSourceId: "",
                // 视频名称
                videoOriginalName: "",
            },
            // 上传文件列表
            fileList: [],
            // 接口API地址
            BASE_API: process.env.BASE_API,
            formLabelWidth: "120px",
        };
    },
    created() {
        if (this.$route.params && this.$route.params.id) {
            this.courseId = this.$route.params.id;
            this.getChapterVideo();
        }
    },
    methods: {
        //==============================上传视频====================================

        // 上传视频成功后调用的方法
        handleVodUploadSuccess(response, file, fileList) {
            // 上传视频id赋值
            this.video.videoSourceId = response.data.videoId;
            // 上传视频名称赋值
            this.video.videoOriginalName = file.name;
        },

        // 点击删除调用的方法
        beforeVodRemove(file, fileList) {
            console.log(fileList)
            return this.$confirm(`确定移除 ${file.name}？`);
        },

        // 确认删除调用的方法
        handleVodRemove(videoId) {
            video.removeAliyunVideo(this.video.videoSourceId)
            .then((response) => {
                this.$message({
                    type: "success",
                    message: "删除视频成功",
                });
                // 清空文件列表
                this.fileList = [];
                this.video.videoSourceId = "";
                this.video.videoOriginalName = "";
            })
        },

        // 文件超出个数限制时的钩子
        handleUploadExceed(files, fileList) {
            this.$message.warning("限制上传单个视频");
        },
        //==============================章节操作====================================

        openChapterDialog() {
            // 弹窗显示
            this.dialogFormVisible = true;
            // 内容清空
            this.chapter.id = "";
            this.chapter.title = "";
            this.chapter.sort = 0;
        },

        // 编辑
        openEditChatper(id) {
            // 弹框
            this.dialogFormVisible = true;
            // 数据回显
            chapter.getChapterInfo(id).then((resp) => {
                this.chapter = resp.data.chapter;
            });
        },

        // 删除章节
        removeChapter(id) {
            this.$confirm("此操作将永久删除该章节记录, 是否继续?", "提示", {
                // 设置 cancel 和 close 为不同的事件
                distinguishCancelAndClose: true,
                confirmButtonText: "确定",
                cancalButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    chapter.removeChapter(id).then(() => {
                        // 提示
                        this.$message({
                            message: "删除章节成功",
                            type: "success",
                        });
                        // 刷新数据
                        this.getChapterVideo();
                    });
                })
                .catch((action) => {
                    if (action === "cancel") {
                        this.$message({
                            type: "info",
                            message: "已取消删除",
                        });
                    } else if (action === "close") {
                        this.$message({
                            type: "info",
                            message: "已关闭",
                        });
                    }
                });
        },

        updateChapter() {
            chapter.updateChapter(this.chapter).then(() => {
                // 关弹框
                this.dialogFormVisible = false;
                // 提示
                this.$message({
                    message: "保存章节成功",
                    type: "success",
                });
                // 刷新数据
                this.getChapterVideo();
            });
        },

        addChapter() {
            this.chapter.courseId = this.courseId;
            chapter.addChapter(this.chapter).then(() => {
                // 关弹框
                this.dialogFormVisible = false;
                // 提示
                this.$message({
                    message: "保存章节成功",
                    type: "success",
                });
                // 刷新数据
                this.getChapterVideo();
            });
        },

        saveOrUpdate() {
            if (this.chapter.id) {
                this.updateChapter();
            } else {
                this.addChapter();
            }
        },

        // 根据课程id查询章节和小节
        getChapterVideo() {
            chapter.getAllChapterVideo(this.courseId).then((resp) => {
                this.chapterList = resp.data.allChapterVideo;
            });
        },

        //==============================小节操作====================================

        openVideo(id) {
            // 弹框
            this.dialogVideoFormVisible = true;
            // 设置章节ID
            this.video.chapterId = id;

            // 内容清空
            this.video.title = "";
            this.video.sort = 0;
            this.video.isFree = false;
            this.fileList = [];
        },

        addVideo() {
            video.addVideo(this.video).then((resp) => {
                // 关弹框
                this.dialogVideoFormVisible = false;
                // 提示
                this.$message({
                    message: "保存小节成功",
                    type: "success",
                });
                // 刷新数据
                this.getChapterVideo();
            });
        },

        updateVideo(id) {
            video.updateVideo(this.video).then((resp) => {
                // 关弹框
                this.dialogVideoFormVisible = false;
                // 提示
                this.$message({
                    message: "保存小节成功",
                    type: "success",
                });
                // 刷新数据
                this.getChapterVideo();
            });
        },

        // 编辑
        openEditVideo(id) {
            // 弹框
            this.dialogVideoFormVisible = true;
            // 数据回显
            video.getVideoInfo(id).then((resp) => {
                this.video = resp.data.video;
                if (resp.data.video.videoOriginalName && resp.data.video.videoSourceId) {
                    this.fileList = [{name: resp.data.video.videoOriginalName}];
                } else {
                    this.fileList = [];
                }
            });
        },

        removeVideo(id) {
            this.$confirm("此操作将永久删除该小节记录, 是否继续?", "提示", {
                // 设置 cancel 和 close 为不同的事件
                distinguishCancelAndClose: true,
                confirmButtonText: "确定",
                cancalButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    video.removeVideo(id).then(() => {
                        // 提示
                        this.$message({
                            message: "删除小节成功",
                            type: "success",
                        });
                        // 刷新数据
                        this.getChapterVideo();
                    });
                })
                .catch((action) => {
                    if (action === "cancel") {
                        this.$message({
                            type: "info",
                            message: "已取消删除",
                        });
                    } else if (action === "close") {
                        this.$message({
                            type: "info",
                            message: "已关闭",
                        });
                    }
                });
        },

        // 保存小节
        saveOrUpdateVideo() {
            // 设置课程id
            this.video.courseId = this.courseId;
            if (this.video.id) {
                this.updateVideo();
            } else {
                this.addVideo();
            }
        },

        //==============================上一步、下一步====================================

        previous() {
            this.$router.push({ path: `/course/info/${this.courseId}` });
        },
        next() {
            this.$router.push({ path: `/course/publish/${this.courseId}` });
        },
    },
};
</script>

<style scoped>
.chanpterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li {
    position: relative;
}
.chanpterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #ddd;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList {
    padding-left: 50px;
}
.videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #ddd;
}
</style>