<template>
    <div class="app-container">
        讲师列表

        <el-form :inline="true"
                 class="demo-form-inline"
                 style="margin-left: 20px; margin-top: 12px">
            <el-form-item>
                <el-input v-model="teacherQuery.name"
                          placeholder="讲师姓名" />
            </el-form-item>
            <el-form-item>
                <el-select v-model="teacherQuery.level"
                           placeholder="讲师头衔">
                    <el-option :value="1"
                               label="高级讲师" />
                    <el-option :value="2"
                               label="首席讲师" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-date-picker v-model="teacherQuery.begin"
                                placeholder="选择开始时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                default-time="00:00:00"
                                type="datetime" />
            </el-form-item>
            <el-form-item>
                <el-date-picker v-model="teacherQuery.end"
                                placeholder="选择截止时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                default-time="00:00:00"
                                type="datetime" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary"
                           @click="getList()">查询</el-button>
                <el-button type="default"
                           @click="resetData()">清空</el-button>
            </el-form-item>
        </el-form>

        <el-table v-loading="listLoading"
                  :data="list"
                  style="width: 100%"
                  element-loading-test="数据加载中"
                  border
                  fit
                  highlight-current-row>
            <el-table-column prop="date"
                             label="序号"
                             width="70"
                             align="center">
                <template slot-scope="scope">
                    <!-- 通过第几页计算是第几条 -->
                    {{ (current - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column label="名称"
                             width="80">
                <template slot-scope="scope">
                    {{ scope.row.name }}
                </template>
            </el-table-column>

            <el-table-column label="头衔"
                             width="80">
                <template slot-scope="scope">
                    {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
                </template>
            </el-table-column>

            <el-table-column prop="intro"
                             label="资历" />

            <el-table-column prop="gmtCreate"
                             label="添加时间"
                             width="160" />

            <el-table-column prop="sort"
                             label="排序"
                             width="60" />

            <el-table-column label="操作"
                             width="200"
                             align="center">
                <template slot-scope="scope">
                    <router-link :to="'/teacher/edit/' + scope.row.id">
                        <el-button type="primary"
                                   size="mini"
                                   icon="el-icon-edit">修改</el-button>
                    </router-link>
                    <el-button type="danger"
                               size="mini"
                               icon="el-icon-delete"
                               @click="removeTeacherById(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--分页组件-->
        <el-pagination :total="total"
                       :page-size="limit"
                       :current-page="current"
                       background
                       layout="prev, pager, next,total,jumper"
                       style="padding: 30px 0; text-align: center"
                       @current-change="getList" />
    </div>
</template>

<script>
// 引入调用teacher.js文件
import teacher from "@/api/teacher/teacher";

export default {
    // 写核心代码的位置
    // data: {},
    data() {
        return {
            // 定义变量和初始值
            list: null,
            // 当前页
            current: 1,
            // 每页记录数
            limit: 10,
            // 总记录数
            total: 0,
            // 条件封装对象
            teacherQuery: {},
            // 加载
            listLoading: true,
        };
    },
    created() {
        // 页面渲染之前执行，一般调用 methods 定义的方法
        this.getList();
    },
    methods: {
        // 创建具体的方法，调用teacher.js定义的方法
        // 讲师列表
        getList(current = 1) {
            this.current = current;
            teacher
                .getTeacherListPage(this.current, this.limit, this.teacherQuery)
                .then((response) => {
                    // 请求成功
                    // response 接口返回数据
                    this.listLoading = false;
                    this.list = response.data.records;
                    this.total = response.data.total;
                })
                .catch((err) => {
                    // 请求失败
                    console.log(err);
                });
        },
        // 清空
        resetData() {
            // 表单输入项数据清空
            this.teacherQuery = {};
            // 查询所有讲师数据
            this.getList();
        },
        // 删除
        removeTeacherById(id) {
            // 弹出框 https://element.eleme.cn/#/zh-CN/component/message-box
            this.$confirm("此操作将永久删除该讲师记录, 是否继续?", "提示", {
                // 设置 cancel 和 close 为不同的事件
                distinguishCancelAndClose: true,
                confirmButtonText: "确定",
                cancalButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    teacher
                        .removeTeacherById(id)
                        .then((response) => {
                            // 提示信息
                            this.$message({
                                type: "success",
                                message: "删除成功!",
                            });
                            // 刷新列表页面
                            this.getList();
                        })
                        .catch((err) => {
                            console.log(err);
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
    },
};
</script>