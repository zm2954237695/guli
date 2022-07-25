<template>
    <div class="app-container">

        <!--多条件查询表单-->
        <el-form
            :inline="true"
            class="demo-form-inline"
            style="margin-left: 20px; margin-top: 12px"
        >
            <el-form-item label="课程名称">
                <el-input
                    v-model="courseQuery.title"
                    placeholder="请输入名称"
                ></el-input>
            </el-form-item>
            <el-form-item label="发布状态">
                <el-select
                    v-model="courseQuery.status"
                    placeholder="课程状态"
                >
                    <el-option
                        label="已发布"
                        :value="'Normal'"
                    ></el-option>
                    <el-option
                        label="未发布"
                        :value="'Draft'"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button
                    type="primary"
                    icon="el-icon-search"
                    @click="getCourseList()"
                >查询</el-button>
                <el-button
                    type="default"
                    @click="resetData()"
                >清空</el-button>
            </el-form-item>
        </el-form>

        <!--数据显示的表格-->
        <el-table
            :data="list"
            style="width: 100%"
            border
            fit
            highlight-current-row
            element-loading-text="数据加载中"
        >
            <el-table-column
                prop="date"
                label="序号"
                width="70"
                align="center"
            >
                <template slot-scope="scope">
                    {{ (current - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                prop="title"
                label="课程名称"
                width="300"
            >
            </el-table-column>
            <el-table-column
                label="发布状态"
                width="100"
            >
                <template slot-scope="scope">
                    {{ scope.row.status === "Normal" ? "已发布" : "未发布" }}
                </template>
            </el-table-column>
            <el-table-column
                prop="lessonNum"
                label="课时数"
                width="200"
            />
            <el-table-column
                prop="gmtCreate"
                label="添加时间"
                width="300"
            />
            <el-table-column
                prop="viewCount"
                label="浏览数量"
                width="200"
            />
            <el-table-column
                label="操作"
                align="center"
            >
                <template slot-scope="scope">
                    <router-link :to="'/course/info/' + scope.row.id">
                        <el-button
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                        >编辑课程基本信息</el-button>
                    </router-link>
                    <router-link :to="'/course/chapter/' + scope.row.id">
                        <el-button
                            type="info"
                            size="mini"
                            icon="el-icon-edit"
                        >编辑课程大纲</el-button>
                    </router-link>
                    <el-button
                        type="danger"
                        size="mini"
                        icon="el-icon-delete"
                        @click="removeCourseById(scope.row.id)"
                    >课程删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
            background
            layout="prev, pager, next,total,jumper"
            :total="total"
            :page-size="limit"
            style="padding: 30px 0; text-align: center"
            :current-page="current"
            @current-change="getCourseList"
        >
        </el-pagination>

    </div>
</template>

<script>
//引入要调用的方法，course.js文件
import course from "@/api/teacher/course.js";

export default {
    data() {
        return {
            list: null,
            current: 1,
            limit: 10,
            courseQuery: {},
            total: 0,
        };
    },
    created() {
        this.getCourseList();
    },
    methods: {
        getCourseList(current = 1) {
            this.current = current;
            course
                .pageCourseCondition(this.current, this.limit, this.courseQuery)
                .then((resp) => {
                    this.list = resp.data.records;
                    this.total = resp.data.total;
                })
                .catch((err) => {
                    console.log(err);
                });
        },

        // 重置
        resetData() {
            // 清空输入项

            this.courseQuery = {};
            // 重置内容
            this.getCourseList();
        },

        // 删除课程
        removeCourseById(id) {
            this.$confirm("此操作将删除课程, 是否继续?", "提示", {
                // 设置 cancel 和 close 为不同的事件
                distinguishCancelAndClose: true,
                confirmButtonText: "确定",
                cancalButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    course.removeCourseById(id).then(() => {
                        // 提示
                        this.$message({
                            message: "删除课程成功",
                            type: "success",
                        });

                        this.getCourseList();
                    });
                })
                .catch((action) => {
                    if (action === "cancel") {
                        this.$message({
                            type: "info",
                            message: "取消删除",
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

