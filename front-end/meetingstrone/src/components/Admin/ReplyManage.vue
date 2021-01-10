<template>
    <div style="width: 80%;margin: 0 auto;">
        <el-table
                :data="replyTable"
                style="width: 100%;">
            <el-table-column
                    label="编号"
                    prop="id"
                    width="100px"
            >
            </el-table-column>
            <el-table-column
                    label="发布者"
                    prop="responder.username">
            </el-table-column>
            <el-table-column
                    label="目标请求"
                    prop="apply.title">
            </el-table-column>
            <el-table-column
                    label="状态">
                <template slot-scope="scope">
                    <el-tag :type=replyState(scope.row.state) size="small" style="margin-right: 15px">
                        {{ replyState(scope.row.state + 3) }}
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
                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定要删除请求吗？"
                            style="margin-left: 10px"
                            @confirm="handleDelete(scope.$index, scope.row)"
                    >
                        <el-button
                                size="mini"
                                type="danger"
                                slot="reference">删除
                        </el-button>
                    </el-popconfirm>
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
        name: "ReplyManege",
        data() {
            return {
                replyTable: [],
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
            _this.$axios.get("/searchReply/"+_this.page.currentPage+"?kw=").then(resp=>{
                _this.replyTable = resp.data.result;
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
                _this.$axios.get("/searchReply/"+_this.page.currentPage+"?kw="+_this.search).then(resp=>{
                    _this.replyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleCurrentChange() {
                let _this = this;
                _this.$axios.get("/searchReply/"+_this.page.currentPage+"?kw="+_this.search).then(resp=>{
                    _this.replyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            replyState: function (state) {
                    switch (state) {
                        case 0:
                            return "warning";
                        case 1:
                            return "success";
                        case 2:
                            return "danger";
                        case 3:
                            return "待处理";
                        case 4:
                            return "已接收";
                        case 5:
                            return "被拒绝";
                        default:
                            return ""
                    }
            },
        }
    }
</script>

<style scoped>

</style>