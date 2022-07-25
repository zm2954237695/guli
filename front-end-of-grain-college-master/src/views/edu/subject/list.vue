<template>
    <div class="app-container">
        <el-input v-model="filterText"
                  placeholder="Filter keyword"
                  style="margin-bottom:30px;" />

        <el-tree ref="tree2"
                 :data="subjectList"
                 :props="defaultProps"
                 :filter-node-method="filterNode"
                 class="filter-tree"
                 default-expand-all />

    </div>
</template>

<script>
// 引入调用 subject.js 文件
import subject from "@/api/teacher/subject";

export default {
    data() {
        return {
            filterText: "",
            subjectList: [],
            defaultProps: {
                children: "children",
                label: "title",
            },
        };
    },
    watch: {
        filterText(val) {
            this.$refs.tree2.filter(val);
        },
    },
    created() {
        this.getAllSubject();
    },
    methods: {
        filterNode(value, data) {
            if (!value) return true;
            return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1;
        },

        getAllSubject() {
            subject
                .getAllSubject()
                .then((response) => {
                    // 请求成功
                    // response 接口返回数据
                    this.subjectList = response.data.list;
                })
                .catch((err) => {
                    // 请求失败
                    console.log(err);
                });
        },
    },
};
</script>

