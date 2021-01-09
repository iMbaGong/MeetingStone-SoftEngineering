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
                    label="状态"
                    width="100px"
            >
                <template slot-scope="scope">
                    <el-tag :type=applyState(scope.row.state) size="small" style="margin-right: 15px">
                        {{ applyState(scope.row.state + 3) }}
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
        name: "ApplyManage",
        data() {
            return {
                applyTable: [],
                search: '',
                page: {
                    currentPage: 1,
                    total: 0,
                    pageSize: 10,
                },
            }
        },
        mounted() {
            let _this = this;
            _this.$axios.get("/searchApply" + "/1/" + _this.page.currentPage + "?kw=").then(resp => {
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
                _this.page.currentPage = 1;
                _this.$axios.get("/searchApply" + "/1/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.applyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleCurrentChange() {
                let _this = this;
                _this.$axios.get("/searchApply" + "/1/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.applyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            applyState: function (state) {
                switch (state) {
                    case 0:
                        return "warning";
                    case 1:
                        return "success";
                    case 2:
                        return "danger";
                    case 3:
                        return "审核中";
                    case 4:
                        return "已发布";
                    case 5:
                        return "被遣返";
                    default:
                        return ""
                }

            },
        }
    }
</script>

<style scoped>

</style>