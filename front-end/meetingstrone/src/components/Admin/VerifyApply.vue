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
                    prop="applicant.username"
                    width="150px">
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
                style="margin-top: 10px"
                :page-size="page.pageSize"
                :total="page.total">
        </el-pagination>
        <el-dialog
                title="组队请求信息"
                :visible.sync="dialogTableVisible"
                style="width: 800px;margin: auto auto">
            <el-form label-position="left" class="demo-table-expand" v-model="applyInfo">
                <el-form-item>
                    <template slot="label" class="label">
                        <h3>标题</h3>
                    </template>
                    <span class="content">{{ applyInfo.title }}</span>
                </el-form-item>
                <el-form-item>
                    <template slot="label" class="label">
                        <h3>简介</h3>
                    </template>
                    <span class="content">{{ applyInfo.intro }}</span>
                </el-form-item>
                <el-form-item v-if="applyInfo.applicant">
                    <template slot="label" class="label">
                        <h3>发布人</h3>
                    </template>
                    <span class="content">{{ applyInfo.applicant.username }}</span>
                </el-form-item>
                <el-form-item>
                    <template slot="label" class="label">
                        <h3>类型</h3>
                    </template>
                    <span class="content">{{ applyType(applyInfo.type) }}</span>
                </el-form-item>
                <el-form-item v-if="applyInfo.group">
                    <template slot="label" class="label">
                        <h3>关联小组</h3>
                    </template>
                    <span class="content">{{ applyInfo.group.name }}</span>
                </el-form-item>
                <el-form-item v-if="applyInfo.course">
                    <template slot="label" class="label">
                        <h3>关联课程</h3>
                    </template>
                    <span class="content">{{ applyInfo.course.name }}</span>
                </el-form-item>
                <el-form-item>
                    <template slot="label" class="label">
                        <h3>标签</h3>
                    </template>
                    <span class="content">
                                <el-tag size="small" v-for="tag in applyInfo.tags" :key="tag" style="margin-left: 5px">
                                    {{ tag }}
                                </el-tag>
                            </span>
                </el-form-item>
                <el-form-item>
                    <template slot="label" class="label">
                        <h3>创建日期</h3>
                    </template>
                    <span class="content">{{ tranDate(applyInfo.crtDate) }}</span>
                </el-form-item>
                <el-form-item>
                    <template slot="label" class="label">
                        <h3>截止日期</h3>
                    </template>
                    <span class="content">{{ tranDate(applyInfo.ddlDate) }}</span>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="reject" type="danger">遣返</el-button>
                <el-button type="success" @click="permit">通过</el-button>
            </div>
        </el-dialog>
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
                    pageSize: 10,
                },
                applyInfo:{},
                dialogTableVisible:false,
            }
        },
        mounted() {
            let _this = this;
            _this.$axios.get("/searchApply" + "/0/" + _this.page.currentPage + "?kw=").then(resp => {
                _this.applyTable = resp.data.result;
                _this.page.total = resp.data.result2;
            })
        },
        computed:{
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
            tranDate: function () {
                return function (date) {
                    return this.$moment(date).format(
                        "YYYY-MM-DD HH:mm:ss"
                    );
                }
            },

        },
        methods: {
            handleEdit(index,apply) {
                this.applyInfo = apply;
                this.dialogTableVisible = true
            },
            handleDelete(index,apply) {

            },
            handleSearch() {
                let _this = this;
                _this.page.currentPage = 1;
                _this.$axios.get("/searchApply" + "/0/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.applyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleCurrentChange() {
                let _this = this;
                _this.$axios.get("/searchApply" + "/0/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.applyTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            reject(){
                let _this =this;
                _this.$axios.get("/admin/apply/state/2?applyId="+_this.applyInfo.change.id).then(resp=>{
                    if(resp.data.code === 200){
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        _this.handleCurrentChange()
                    }
                })
            },
            permit(){
                let _this =this;
                _this.$axios.get("/admin/apply/state/1?applyId="+_this.applyInfo.change.id).then(resp=>{
                    if(resp.data.code === 200){
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        _this.handleCurrentChange()
                    }
                })
            }
        }
    }
</script>

<style scoped>
    h3 {
        margin: 2px 0 2px 0;
        width: 80px;
    }

    .content {
        float: left;
        margin: 2px 0 2px 0;
    }

    .demo-table-expand .el-form-item {
        margin-left: 80px;
        margin-right: 0;
        margin-bottom: 0;
        width: 65%;
    }
</style>