<template>
    <div style="width: 80%;margin: 0 auto;">
        <el-table
                :data="userTable"
                style="width: 100%;">
            <el-table-column
                    label="编号"
                    prop="id"
                    width="100px"
            >
            </el-table-column>
            <el-table-column
                    label="学号"
                    prop="usernum"
                    width="150px"
            >
            </el-table-column>
            <el-table-column
                    label="姓名"
                    prop="username">
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
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">注销
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="page.currentPage"
                :page-size="page.pageSize"
                :total="page.total"
                style="margin-top: 10px"
        >
        </el-pagination>
        <el-dialog
                title="用户信息"
                :visible.sync="dialogTableVisible"
                style="width: 850px;margin-left: auto;margin-right: auto"
        >
            <el-form v-model="userInfo.change" style="width: 80%;margin: 0 auto">
                <el-form-item label="学号" label-width="50px">
                    <el-input
                            v-model="userInfo.change.usernum"
                    >

                    </el-input>
                </el-form-item>
                <el-form-item label="姓名" label-width="50px">
                    <el-input
                            v-model="userInfo.change.username"
                    >

                    </el-input>
                </el-form-item>
                <el-form-item label="权限" label-width="50px">
                    <el-radio label="student" border v-model="userInfo.changeRole.role1" size="small" style="margin: 0 12px 0 0;float: left">学生</el-radio>
                    <el-radio label="teacher" border v-model="userInfo.changeRole.role1" size="small" style="margin: 0;float: left">教师</el-radio>
                    <el-checkbox v-model="userInfo.changeRole.role2" label="管理员" border size="small" style="float: right;margin:0;"></el-checkbox>
                </el-form-item>
                <el-form-item label="密码" label-width="50px">
                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定要重置用户的密码吗？"
                            style="float: left"
                            @confirm="resetPassword"
                    >
                        <el-button slot="reference" type="danger">重置密码</el-button>
                    </el-popconfirm>
                </el-form-item>
                <el-form-item label="小组" label-width="50px">
                    <el-popconfirm
                            confirm-button-text='保存'
                            cancel-button-text='不保存'
                            icon="el-icon-info"
                            icon-color="red"
                            title="即将离开页面，需要保存修改吗？"
                            style="float: left"
                            @confirm="saveAndGo('group')"
                            @cancel="justGo('group')"
                    >
                        <el-button slot="reference">查看详情</el-button>
                    </el-popconfirm>
                </el-form-item>
                <el-form-item label="课程" label-width="50px">
                    <el-popconfirm
                            confirm-button-text='保存'
                            cancel-button-text='不保存'
                            icon="el-icon-info"
                            icon-color="red"
                            title="即将离开页面，需要保存修改吗？"
                            style="float: left"
                            @confirm="saveAndGo('course')"
                            @cancel="justGo('course')"
                    >
                        <el-button slot="reference">查看详情</el-button>
                    </el-popconfirm>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm">重置</el-button>
                <el-button type="primary" @click="handleUpdate">保存</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import AdminGroup from "./AdminGroup";
    import AdminCourse from "./AdminCourse";

    export default {
        name: "UserManage",
        data() {
            return {
                userTable: [],
                search: '',
                userInfo: {
                    origin: {},
                    change: {},
                    originRole: {
                        role1: 'student',
                        role2: false
                    },
                    changeRole: {
                        role1: 'student',
                        role2: false
                    },
                },
                dialogTableVisible: false,
                page: {
                    currentPage: 1,
                    total: 17,
                    pageSize: 10,
                },
            }
        },
        mounted() {
            let _this = this;
            _this.$axios.get("/searchUser/" + _this.page.currentPage + "?kw=").then(resp => {
                _this.userTable = resp.data.result;
                _this.page.total = resp.data.result2;
            })
        },
        methods: {
            handleEdit(index, user) {
                this.userInfo.origin = JSON.parse(JSON.stringify(user));
                this.userInfo.change = JSON.parse(JSON.stringify(user));
                this.userInfo.originRole.role1 = "student";
                this.userInfo.changeRole.role1 = "student";
                this.userInfo.originRole.role2 = false;
                this.userInfo.changeRole.role2 = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].role === "teacher") {
                        this.userInfo.originRole.role1 = "teacher";
                        this.userInfo.changeRole.role1 = "teacher"
                    } else if (user.roles[i].role === "admin") {
                        this.userInfo.originRole.role2 = true;
                        this.userInfo.changeRole.role2 = true
                    }

                }
                this.dialogTableVisible = true
            },
            handleDelete() {

            },
            handleSearch() {
                let _this = this;
                _this.page.currentPage = 1;
                _this.$axios.get("/searchUser/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.userTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleCurrentChange() {
                let _this = this;
                _this.$axios.get("/searchUser/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.userTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleUpdate() {
                let _this = this;
                let roles = [];
                if(_this.userInfo.changeRole.role1==="student"){
                    roles.push({
                        id: 1,
                        role: "student"
                    })
                }
                else{
                    roles.push({
                        id: 2,
                        role: "teacher"
                    })
                }
                if(_this.userInfo.changeRole.role2){
                    roles.push({
                        id: 3,
                        role: "admin"
                    })
                }
                _this.userInfo.change.roles = roles;
                _this.$axios.post("/admin/user/basic", _this.userInfo.change).then(resp => {
                    if (resp.data.code === 200) {
                        _this.$message({
                            type: 'success',
                            message: '保存成功!'
                        });
                        _this.handleCurrentChange();
                    }
                }).catch(resp => {
                    console.log(resp);
                    _this.$message({
                        type: 'error',
                        message: '未知错误!'
                    });
                })
            },
            resetForm() {
                this.userInfo.change = JSON.parse(JSON.stringify(this.userInfo.origin));
                this.userInfo.changeRole = JSON.parse(JSON.stringify(this.userInfo.originRole));
            },
            resetPassword() {
                let _this = this;
                _this.$axios.get("/admin/resetPassword?userId=" + _this.userInfo.origin.id).then(resp => {
                    if (resp.data.code === 200) {
                        _this.$message({
                            type: 'success',
                            message: '重置成功!'
                        });
                    }
                }).catch(resp => {
                    console.log(resp);
                    _this.$message({
                        type: 'error',
                        message: '未知错误!'
                    });
                })
            },
            saveAndGo(type) {
                this.handleUpdate();
                this.justGo(type);
            },
            justGo(type) {
                var _this = this;
                if (type === "group") {
                    _this.$axios.get('/userGroups?userId=' + this.userInfo.origin.id,).then(resp => {
                        console.log(resp.data);
                        if (resp && resp.status === 200) {
                            let tabInfo = {
                                tab: {
                                    name: "AdminGroup-user:" + this.userInfo.origin.id,
                                    title: this.userInfo.origin.username + "的小组",
                                    component: AdminGroup
                                },
                                data: resp.data.result
                            };
                            _this.dialogTableVisible = false;
                            _this.$emit("createTab", tabInfo)
                        }
                    });
                } else {
                    _this.$axios.get('/userCourses?userId=' + this.userInfo.origin.id,).then(resp => {
                        console.log(resp.data);
                        if (resp && resp.status === 200) {
                            let tabInfo = {
                                tab: {
                                    name: "AdminCourse-user:" + this.userInfo.origin.id,
                                    title: this.userInfo.origin.username + "的课程",
                                    component: AdminCourse
                                },
                                data: resp.data.result
                            };
                            _this.dialogTableVisible = false;
                            _this.$emit("createTab", tabInfo)
                        }
                    });
                }

            },
        }
    }
</script>

<style scoped>

</style>