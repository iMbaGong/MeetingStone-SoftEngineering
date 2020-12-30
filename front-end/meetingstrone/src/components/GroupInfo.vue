<template>
    <div style="width: 100%;">
        <el-form label-position="left" class="demo-table-expand">
            <el-form-item>
                <template slot="label" class="label">
                    <h3>小组名称</h3>
                </template>
                <span class="content">{{ group.name }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>简介</h3>
                </template>
                <span class="content">{{ group.info }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>组长</h3>
                </template>
                <span class="content">{{ group.leader.username }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>类型</h3>
                </template>
                <span class="content">{{ groupType(group.type) }}</span>
            </el-form-item>
            <el-form-item v-if="group.course">
                <template slot="label" class="label">
                    <h3>关联课程</h3>
                </template>
                <span class="content">{{ group.course.name }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>小组成员</h3>
                </template>
                <span class="content">{{ groupMember(group.members) }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>创建日期</h3>
                </template>
                <span class="content">{{ tranDate(group.crtDate) }}</span>
            </el-form-item>
            <el-form-item>

            </el-form-item>
            <el-form-item style="margin-top: 30px">
                <el-button v-show=isLeader($store.state.user.id) @click="manageGroup">管理小组</el-button>
                <el-popconfirm
                        confirm-button-text='确定'
                        cancel-button-text='取消'
                        @confirm="quitGroup"
                        icon="el-icon-info"
                        icon-color="red"
                        :title="quitType(isLeader($store.state.user.id))"
                        style="margin-left: 20px"
                >
                    <el-button type="danger" slot="reference">
                        {{quitType(isLeader($store.state.user.id))}}
                    </el-button>
                </el-popconfirm>

            </el-form-item>
        </el-form>

        <el-dialog title="管理小组" :visible.sync="dialogTableVisible">
            <el-table :data="group.members">
                <el-table-column property="usernum" label="学号" width="100"></el-table-column>
                <el-table-column property="username" label="姓名" width="100"></el-table-column>
                <el-table-column label="操作" align="right">
                    <template slot-scope="scope">
                        <el-button
                                v-show="isLeader(scope.row.id) === false"
                                size="mini"
                                type="primary"
                                @click="promote(scope.row.id)"> 提升为组长
                        </el-button>
                        <el-popconfirm
                                v-show="isLeader(scope.row.id) === false"
                                confirm-button-text='确定'
                                @confirm="removeMember(scope.row.id,scope.$index)"
                                cancel-button-text='取消'
                                icon="el-icon-info"
                                icon-color="red"
                                title="确定要移除组员吗"
                                style="margin-left: 15px"
                        >
                            <el-button
                                    size="mini"
                                    type="danger"
                                    slot="reference"> 移除
                            </el-button>
                        </el-popconfirm>

                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "GroupInfo",
        props: ['group'],
        data() {
            return {
                dialogTableVisible: false
            }
        },
        computed: {
            groupType: function () {
                return function (type) {
                    switch (type) {
                        case 0:
                            return "课内小组";
                        case 1:
                            return "课外小组";
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
            groupMember: function () {
                return function (members) {
                    let allMembers = "";
                    for (let i = 0; i < members.length; i++) {
                        if (members[i].username !== this.group.leader.username)
                            allMembers += (members[i].username + ' ')
                    }
                    return allMembers;
                }
            },
            quitType: function () {
                return function (type) {
                    switch (type) {
                        case true:
                            return "解散小组";
                        case false:
                            return "退出小组";
                        default:
                            return ""
                    }
                }
            },
            isLeader: function () {
                return function (userId) {
                    return userId + '' === this.group.leader.id + ''
                }
            },
        },
        mounted() {

        },
        methods: {
            quitGroup() {
                let _this = this;
                _this.$axios.get("/removeMember?userId=" + _this.$store.state.user.id + "&&groupId=" + _this.group.id).then(
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
            manageGroup() {
                this.dialogTableVisible = true
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
            removeMember(userId, index) {
                let _this = this;
                _this.$axios.get("/removeMember?userId=" + userId + "&&groupId=" + _this.group.id).then(
                    resp => {
                        if (resp.data.code === 200) {
                            _this.$message({
                                type: 'success',
                                message: '操作成功!'
                            });
                            _this.group.members.splice(index, 1)
                        }
                    }
                ).catch()
            },

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