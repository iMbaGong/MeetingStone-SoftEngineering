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
                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定要注销用户吗？"
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
                style="margin-top: 10px"
                :page-size="page.pageSize"
                :total="page.total">
        </el-pagination>

        <el-dialog
                title="组队请求信息"
                :visible.sync="dialogTableVisible"
                style="width: 800px;margin: auto auto"
        >
            <el-form :model="applyInfo.change" label-width="80px">
                <el-form-item label="请求标题" prop="title">
                    <el-input type="text" placeholder="请输入请求标题" v-model="applyInfo.change.title" maxlength="10"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="发布者" label-width="80px">
                    <el-select
                            v-model="applyInfo.change.applicant"
                            filterable
                            remote
                            reserve-keyword
                            style="width: 100%"
                            value-key="username"
                            placeholder="请输入学号或姓名"
                            @change="changeApplicant"
                            :remote-method="findUser"
                            :loading="loading">
                        <el-option
                                v-for="item in applicant"
                                :key=item.id
                                :label="item.username"
                                :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="请求类型" prop="type">
                    <el-select v-model="applyInfo.change.type" placeholder="招募队友or寻求组队" style="width: 100%">
                        <el-option label="招募队友/课内请求" :value="1"></el-option>
                        <el-option label="招募队友/课外请求" :value="2"></el-option>
                        <el-option label="寻求组队/课内请求" :value="3"></el-option>
                        <el-option label="寻求组队/课外请求" :value="4"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-show=isGroup label="绑定小组" prop="group">
                    <el-select
                            class="opt"
                            v-model="applyInfo.change.group"
                            filterable
                            placeholder="请输入关键词"
                            value-key="name"
                            style="width: 100%;">
                        <el-option
                                v-for="item in userGroups"
                                :key=item.id
                                :label="item.name"
                                :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-show=isCourse label="绑定课程" prop="course">
                    <el-select
                            class="opt"
                            v-model="applyInfo.change.course"
                            filterable
                            placeholder="请输入关键词"
                            value-key="name"
                            style="width: 100%;"
                    >
                        <el-option
                                v-for="item in userCourses"
                                :key=item.id
                                :label="item.name"
                                :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-show=!isCourse label="选择标签">
                    <div style="float: left">
                        <el-tag
                                :key="tag"
                                v-for="tag in applyInfo.change.tags"
                                closable
                                :disable-transitions="false"
                                @close="handleClose(tag)">
                            {{ tag }}
                        </el-tag>
                        <el-input
                                class="input-new-tag"
                                v-if="inputVisible"
                                v-model="inputValue"
                                ref="saveTagInput"
                                size="small"
                                @keyup.enter.native="handleInputConfirm"
                                @blur="handleInputConfirm"
                        >
                        </el-input>
                        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
                    </div>

                </el-form-item>
                <el-form-item label="请求简介" prop="intro">
                    <el-input type="textarea" v-model="applyInfo.change.intro" maxlength="64"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="创建日期">
                    <span class="content">{{ tranDate(applyInfo.change.crtDate) }}</span>
                </el-form-item>
                <el-form-item label="截止日期">
                    <el-date-picker
                            v-model="applyInfo.change.ddlDate"
                            type="datetime"
                            placeholder="选择日期时间"
                            :picker-options="pickerOptions"
                            style="width: 100%">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm">重置</el-button>
                <el-button @click="reject" type="danger">遣返</el-button>
                <el-button type="primary" @click="handleUpdate">保存</el-button>
            </div>
        </el-dialog>
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
                applyInfo: {
                    origin: {},
                    change: {},
                },
                inputVisible: false,
                inputValue: {},
                applicant:[],
                userGroups: [],
                userCourses: [],
                loading: false,
                dialogTableVisible: false,
                pickerOptions: {
                    shortcuts: [{
                        text: '一天',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一周',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', date);
                        }
                    }, {
                        text: '一个月',
                        onClick(picker) {
                            const date = new Date();
                            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7 * 30);
                            picker.$emit('pick', date);
                        }
                    }]
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
        computed: {
            applyState: function () {
                return function (state) {
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
                }
            },
            tranDate: function () {
                return function (date) {
                    return this.$moment(date).format(
                        "YYYY-MM-DD HH:mm:ss"
                    );
                }
            },
            isGroup: function () {
                return this.applyInfo.change.type === 1 || this.applyInfo.change.type === 2
            },
            isCourse: function () {
                return this.applyInfo.change.type === 1 || this.applyInfo.change.type === 3
            },
        },
        methods: {
            handleEdit(index, apply) {
                this.applyInfo.origin = JSON.parse(JSON.stringify(apply));
                this.applyInfo.change = JSON.parse(JSON.stringify(apply));
                this.userGroups = [];
                this.userCourses = [];
                this.applicant = [];
                this.applicant.push(this.applyInfo.change.applicant);
                if (this.applyInfo.change.course)
                    this.userCourses.push(this.applyInfo.change.course);
                if (this.applyInfo.change.group)
                    this.userGroups.push(this.applyInfo.change.group);
                this.dialogTableVisible = true
            },
            handleDelete(index, apply) {
                let _this = this;
                _this.$axios.get("/admin/apply/remove?applyId=" + apply.id).then(resp => {
                    if (resp.data.code === 200) {
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        _this.handleCurrentChange()
                    }
                })
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
            handleUpdate() {
                let _this =this;
                _this.$axios.post("/admin/apply/basic",_this.applyInfo.change).then(resp=>{
                     if(resp.data.code === 200){
                         _this.$message({
                             type: 'success',
                             message: '操作成功!'
                         });
                         _this.handleCurrentChange()
                     }
                })
            },
            showInput() {
                if (this.applyInfo.change.tags.length === 3) {
                    this.$message.error('最多3个标签');
                } else {
                    this.inputVisible = true;
                    this.$nextTick(function () {
                        this.$refs.saveTagInput.$refs.input.focus();
                    });
                }
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    this.applyInfo.change.tags.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            handleClose(tag) {
                this.applyInfo.change.tags.splice(this.applyInfo.change.tags.indexOf(tag), 1);
            },
            resetForm() {
                this.applyInfo.change = JSON.parse(JSON.stringify(this.applyInfo.origin));
                this.userGroups = [];
                this.userCourses = [];
                this.applicant = [];
                this.applicant.push(this.applyInfo.change.applicant);
                if (this.applyInfo.change.course)
                    this.userCourses.push(this.applyInfo.change.course);
                if (this.applyInfo.change.group)
                    this.userGroups.push(this.applyInfo.change.group);
            },
            reject() {
                let _this =this;
                _this.$axios.get("/admin/apply/reject?applyId="+_this.applyInfo.change.id).then(resp=>{
                    if(resp.data.code === 200){
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        _this.handleCurrentChange()
                    }
                })
            },
            findUser(query) {
                if (query !== '') {
                    this.loading = true;
                    this.applicant = [];
                    var _this = this;
                    _this.$axios.get('/searchUser?keyword=' + query).then(resp => {
                        if (resp && resp.status === 200) {
                            _this.applicant = resp.data.result
                        }
                    });
                    setTimeout(() => {
                        this.loading = false;
                    }, 1000);
                }
            },
            changeApplicant(){
                let _this = this;
                _this.$axios.get('/userGroups?userId=' + this.applyInfo.change.applicant.id,).then(resp => {
                    console.log(resp.data);
                    if (resp && resp.status === 200) {
                        _this.userGroups = resp.data.result
                    }
                });
                _this.$axios.get('/userCourses?userId=' + this.applyInfo.change.applicant.id,).then(resp => {
                    console.log(resp.data);
                    if (resp && resp.status === 200) {
                        _this.userCourses = resp.data.result
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .el-tag + .el-tag {
        margin-left: 10px;
    }

    .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
    }

    .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
    }
</style>