<template>
    <div style="width: 100%;">
        <el-form label-position="left" class="demo-table-expand">
            <el-form-item>
                <template slot="label" class="label">
                    <h3>课程名称</h3>
                </template>
                <span class="content">{{ course.name }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>任课教师</h3>
                </template>
                <span class="content">{{ course.teacher.username }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>课程小组</h3>
                </template>
                <el-button size="small" class="content" @click="getGroups">点击查看</el-button>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>课程成员</h3>
                </template>
                <el-button size="small" class="content" @click="getMembers">点击查看</el-button>
            </el-form-item>
        </el-form>

        <el-dialog :visible.sync="courseMemberVisible" style="width: 800px">
            <template slot="title">
                <h3 style="width: max-content;">课程成员</h3>
            </template>
            <el-form>
                <el-form-item label="助教" v-if="course.assist">
                    {{ course.assist.usernum }} {{ course.assist.username }}
                    <el-button
                        type="primary"
                        style="float: right"
                        v-if="isTeacher($store.state.user.id)"
                        @click="removeAssist"
                    >移除
                    </el-button>
                </el-form-item>
                <el-form-item label="选择助教" v-if="isTeacher($store.state.user.id)&&!course.assist">
                    <el-select
                        class="opt"
                        v-model="assistSelect"
                        filterable
                        remote
                        reserve-keyword
                        placeholder="请输入学号或姓名"
                        :remote-method="getAssist"
                        :loading="loading">
                        <el-option
                            v-for="item in searchList"
                            :key=item.id
                            :label="item.username"
                            :value=item>
                        </el-option>
                    </el-select>
                    <el-button
                        type="primary"
                        style="margin-left: 5px"
                        @click="setAssist"
                    >确定
                    </el-button>
                </el-form-item>
            </el-form>
            <el-table :data="course.members">
                <el-table-column property="usernum" label="学号" width="150"></el-table-column>
                <el-table-column property="username" label="姓名"></el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog :visible.sync="courseGroupVisible" style="width: 1000px">
            <template slot="title">
                <h3 style="width: max-content;">课程小组</h3>
            </template>
            <el-table :data="course.groups" v-if="isTeacher($store.state.user.id)">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" class="demo-table-expand">
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>组名</h3>
                                </template>
                                <span class="content">{{ props.row.name }}</span>
                            </el-form-item>
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>组长</h3>
                                </template>
                                <span class="content">{{ props.row.leader.username }}</span>
                            </el-form-item>
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>简介</h3>
                                </template>
                                <span class="content">{{ props.row.info }}</span>
                            </el-form-item>
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>组员</h3>
                                </template>
                                <span class="content">{{ groupMember(props.row.members, props.row.leader.id) }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column property="name" label="组名" width="150"></el-table-column>
                <el-table-column property="leader.username" label="组长" width="100"></el-table-column>
                <el-table-column label="操作" align="right">
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="primary"
                            @click="editGroup(scope.row.id,scope.$index)"> 编辑
                        </el-button>
                        <el-popconfirm
                            confirm-button-text='确定'
                            @confirm="deleteGroup(scope.row.id,scope.$index)"
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除小组吗"
                            style="margin-left: 15px"
                        >
                            <el-button
                                size="mini"
                                type="danger"
                                slot="reference"> 删除
                            </el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <el-table :data="course.groups" v-if="!isTeacher($store.state.user.id)">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" class="demo-table-expand">
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>组名</h3>
                                </template>
                                <span class="content">{{ props.row.name }}</span>
                            </el-form-item>
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>组长</h3>
                                </template>
                                <span class="content">{{ props.row.leader.username }}</span>
                            </el-form-item>
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>简介</h3>
                                </template>
                                <span class="content">{{ props.row.info }}</span>
                            </el-form-item>
                            <el-form-item>
                                <template slot="label" class="label">
                                    <h3>组员</h3>
                                </template>
                                <span class="content">{{ groupMember(props.row.members, props.row.leader.id) }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column property="name" label="组名" ></el-table-column>
                <el-table-column property="leader.username" label="组长" width="150"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "CourseInfo",
    props: ['course'],
    data() {
        return {
            courseMemberVisible: false,
            courseGroupVisible: false,
            searchList: [],
            assistSelect: '',
            loading: false,

        }
    },
    computed: {
        isTeacher: function () {
            return function (userId) {
                return userId + '' === this.course.teacher.id + ''
            }
        },
        groupMember: function () {
            return function (members, id) {
                let allMembers = "";
                for (let i = 0; i < members.length; i++) {
                    if (members[i].id !== id)
                        allMembers += (members[i].username + ' ')
                }
                return allMembers;
            }
        },
        isAssistant: function () {
            return function (userId) {
                return userId + '' === this.course.assist + ''
            }
        },
    },
    methods: {
        getGroups() {
            let _this = this;
            _this.$axios.get("/courseGroups?courseId=" + _this.course.id).then(
                resp => {
                    if (resp.data.code === 200) {
                        _this.course.groups = resp.data.result;
                        _this.courseGroupVisible = true;
                    }
                }
            ).catch(resp => {
                console.log(resp);
                _this.$message({
                    type: 'error',
                    message: '未知错误!'
                });
            })
        },
        getMembers() {
            console.log(this.isTeacher(this.$store.state.user.id) && this.course.assist == 0)
            let _this = this;
            _this.$axios.get("/courseMembers?courseId=" + _this.course.id).then(
                resp => {
                    if (resp.data.code === 200) {
                        _this.course.members = resp.data.result;
                        _this.courseMemberVisible = true;
                    }
                }
            ).catch(resp => {
                console.log(resp);
                _this.$message({
                    type: 'error',
                    message: '未知错误!'
                });
            })
        },
        promote(userId) {
            let _this = this;
            _this.$axios.get("/changeLeader?userId=" + userId + "&&groupId=" + _this.group.id).then(
                resp => {
                    if (resp.data.code === 200) {
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        location.reload()
                    }
                }
            ).catch()
        },
        getAssist(query) {
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
        setAssist() {
            this.loading = true;
            this.searchList = [];
            var _this = this;
            _this.$axios.get('/setAssist?courseId=' + _this.course.id + '&&userId=' + _this.assistSelect.id).then(resp => {
                console.log(resp.data.result);
                if (resp && resp.status === 200) {
                    _this.$message({
                        type: 'success',
                        message: '操作成功!'
                    });
                    _this.course.assist = _this.assistSelect;
                }
            });
        },
        removeAssist() {
            this.loading = true;
            this.searchList = [];
            var _this = this;
            _this.$axios.get('/setAssist?courseId=' + _this.course.id + '&&userId=0').then(resp => {
                console.log(resp.data.result);
                if (resp && resp.status === 200) {
                    _this.$message({
                        type: 'success',
                        message: '操作成功!'
                    });
                    _this.course.assist = null;
                }
            });
        }
    },
    mounted() {
        console.log(this.isTeacher(this.$store.state.user.id))
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
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 0;
    width: 65%;
}
</style>