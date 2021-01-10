<template>
    <div style="width: 80%;margin: 0 auto;">
        <el-table
                :data="courseTable"
                style="width: 100%;">
            <el-table-column
                    label="编号"
                    prop="id"
                    width="100px"
            >
            </el-table-column>
            <el-table-column
                    label="课程名称"
                    prop="name">
            </el-table-column>
            <el-table-column
                    label="课任教师"
                    prop="teacher.username"
                    width="100px">
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
                            title="确定要删除课程吗？"
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
                :total="page.total"
                style="margin-top: 10px"
        >
        </el-pagination>
        <el-dialog
                title="课程信息"
                :visible.sync="dialogTableVisible"
                style="width: 800px;margin-left: auto;margin-right: auto"
        >
            <el-form v-model="courseInfo.change" style="width: 80%;margin: 0 auto">
                <el-form-item label="课程名称" label-width="80px">
                    <el-input
                            v-model="courseInfo.change.name"
                    >
                    </el-input>
                </el-form-item>
                <el-form-item label="任课老师" label-width="80px">
                    <el-select
                            v-model="courseInfo.change.teacher"
                            filterable
                            remote
                            reserve-keyword
                            value-key="username"
                            placeholder="请输入学号或姓名"
                            :remote-method="getTeacher"
                            :loading="loading">
                        <el-option
                                v-for="item in searchList"
                                :key=item.id
                                :label="item.username"
                                :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="课程小组" label-width="80px">
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
                <el-form-item label="课程成员" label-width="80px">
                    <el-popconfirm
                            confirm-button-text='保存'
                            cancel-button-text='不保存'
                            icon="el-icon-info"
                            icon-color="red"
                            title="即将离开页面，需要保存修改吗？"
                            style="float: left"
                            @confirm="saveAndGo('member')"
                            @cancel="justGo('member')"
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
    import CourseMember from "./CourseMember";

    export default {
        name: "CourseManage",
        components:{
        },
        data() {
            return {
                courseTable: [],
                search: '',
                page: {
                    currentPage: 1,
                    total: 0,
                    pageSize: 10,
                },
                courseInfo: {
                    origin: {},
                    change: {},

                },
                searchList:[],
                loading:false,
                dialogTableVisible: false,
            }
        },
        mounted() {
            let _this = this;
            _this.$axios.get("/searchCourse/" + _this.page.currentPage + "?kw=").then(resp => {
                _this.courseTable = resp.data.result;
                _this.page.total = resp.data.result2;
            })
        },
        methods: {
            handleEdit(index,course) {
                this.courseInfo.origin = JSON.parse(JSON.stringify(course));
                this.courseInfo.change = JSON.parse(JSON.stringify(course));
                this.searchList = [];
                this.searchList.push(course.teacher);
                this.dialogTableVisible = true
            },
            handleDelete(index,course) {
                let _this = this;
                _this.$axios.get("/admin/course/remove?courseId=" + course.id).then(resp => {
                    if(resp.data.code ===200){
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
                _this.$axios.get("/searchCourse/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.courseTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleCurrentChange() {
                let _this = this;
                _this.$axios.get("/searchCourse/" + _this.page.currentPage + "?kw=" + _this.search).then(resp => {
                    _this.courseTable = resp.data.result;
                    _this.page.total = resp.data.result2;
                })
            },
            handleUpdate() {
                let _this = this;
                _this.$axios.post("/admin/course/basic", _this.courseInfo.change).then(resp => {
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
            resetForm(){
                this.courseInfo.change = JSON.parse(JSON.stringify(this.courseInfo.origin));
                this.searchList = [];
                this.searchList.push(this.courseInfo.origin.teacher);
            },
            getTeacher(query){
                if (query !== '') {
                    this.loading = true;
                    this.searchList = [];
                    var _this = this;
                    _this.$axios.get('/searchUser?keyword=' + query).then(resp => {
                        console.log(resp.data.result);
                        if (resp && resp.status === 200) {
                            _this.searchList = resp.data.result
                        }
                    });
                    setTimeout(() => {
                        this.loading = false;
                    }, 1000);
                }
            },
            saveAndGo(type) {
                this.handleUpdate();
                this.justGo(type);
            },
            justGo(type) {
                var _this = this;
                if (type === "group") {
                    _this.$axios.get('/courseGroups?courseId=' + this.courseInfo.origin.id,).then(resp => {
                        if (resp && resp.status === 200) {
                            let tabInfo = {
                                tab: {
                                    name: "AdminGroup-course:" + this.courseInfo.origin.id,
                                    title: this.courseInfo.origin.name + "的小组",
                                    component: AdminGroup
                                },
                                data: resp.data.result
                            };
                            _this.dialogTableVisible = false;
                            _this.$emit("createTab", tabInfo)
                        }
                    });
                } else {
                    let tabInfo = {
                        tab: {
                            name: "CourseMember:" + this.courseInfo.origin.id,
                            title: this.courseInfo.origin.name + "的成员",
                            component: CourseMember
                        },
                        data: this.courseInfo.origin
                    };
                    _this.dialogTableVisible = false;
                    _this.$emit("createTab", tabInfo)
                }

            },
        }
    }
</script>

<style scoped>

</style>