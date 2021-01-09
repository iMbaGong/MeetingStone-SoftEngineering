<template>
    <div style="width: 80%;margin: 0 auto;">
        <el-table
                :data="applyTable"
                style="width: 100%;">
            <el-table-column
                    label="编号"
                    prop="id"
                    width="100px"
            >
            </el-table-column>
            <el-table-column
                    label="标题"
                    prop="title">
            </el-table-column>
            <el-table-column
                    label="发布者"
                    prop="applicant.username">
            </el-table-column>
            <el-table-column
                    label="标签">
                <template slot-scope="scope">
                    <el-tag size="small" v-for="tag in scope.row.tags" :key="tag" style="margin-left: 5px">
                        {{ tag }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            @change="handleSearch"
                            size="mini"
                            placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">查看详情
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="page.currentPage"

                :page-size="page.pageSize"
                :total="page.total">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "VerifyApply",
        data() {
            return {
                applyTable: [],
                search: '',
                page: {
                    currentPage: 1,
                    total: 0,
                    pageSize:10,
                },
            }
        },
        mounted(){
            let _this = this;
            _this.$axios.get("/searchApply"+"/0/"+_this.page.currentPage+"?kw=").then(resp=>{
                _this.applyTable = resp.data.result;
                _this.page.total = resp.data.result2;
            })
        },
        methods: {
            handleEdit() {

            },
            handleDelete() {

            },
            handleSearch() {
                let _this = this;
                _this.page.currentPage=1;
                _this.$axios.get("/searchApply"+"/0/"+_this.page.currentPage+"?kw="+_this.search).then(resp=>{
                    _this.applyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleCurrentChange() {
                let _this = this;
                _this.$axios.get("/searchApply"+"/0/"+_this.page.currentPage+"?kw="+_this.search).then(resp=>{
                    _this.applyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            applyType: function () {
                return function (type) {
                    switch (type) {
                        case 1:
                            return "招募队友/课内请求";
                        case 2:
                            return "招募队友/课外请求";
                        case 3:
                            return "寻求组队/课内请求";
                        case 4:
                            return "寻求组队/课外请求";
                        default:
                            return ""
                    }
                }
            },
        }
    }
</script>

<style scoped>

</style>