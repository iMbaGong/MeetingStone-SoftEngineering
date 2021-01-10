<template>
    <div style="width: 80%;margin: 0 auto;">
        <el-table
                :data="groupTable.slice((page.currentPage-1)*page.pageSize,page.currentPage*page.pageSize)"
                style="width: 100%;">
            <el-table-column
                    label="编号"
                    prop="id">
            </el-table-column>
            <el-table-column
                    label="组名"
                    prop="name">
            </el-table-column>
            <el-table-column
                    label="组长"
                    prop="leader.username">
            </el-table-column>
            <el-table-column
                    label="类型">
                <template slot-scope="scope">
                    <el-tag size="small" style="margin-left: 5px;" :type="groupType(scope.row.type+2)">
                        {{ groupType(scope.row.type) }}
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
                            @click="handleEdit(scope.$index, scope.row)">编辑
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
                style="margin-top: 10px"
                :page-size="page.pageSize"
                :total="page.total">
        </el-pagination>
        <el-dialog
                title="小组信息"
                :visible.sync="dialogTableVisible"
                style="width: 1000px;margin: auto auto"
        >
            <el-form v-model="groupInfo.change" style="width: 80%;margin: 0 auto">
                <el-form-item label="小组组名" label-width="80px">
                    <el-input
                            v-model="groupInfo.change.name"
                    >
                    </el-input>
                </el-form-item>
                <el-form-item label="小组类型" label-width="80px">
                    <el-tag  style="margin-left: 5px;" :type="groupType(groupInfo.change.type+2)">
                        {{ groupType(groupInfo.change.type) }}
                    </el-tag>
                </el-form-item>
                <el-form-item v-if="groupInfo.change.course" label="关联课程" label-width="80px">
                    <span class="content">{{ groupInfo.change.course.name }}</span>
                </el-form-item>
                <el-form-item label="小组简介" label-width="80px">
                    <el-input type="textarea"
                              v-model="groupInfo.change.info"
                    >
                    </el-input>
                </el-form-item>
                <el-form-item label="小组组长" label-width="80px">
                    <el-select
                            style="width: 100%"
                            filterable
                            v-model="groupInfo.change.leader"
                            value-key="username"
                    >
                        <el-option
                                v-for="item in groupInfo.change.members"
                                :key=item.id
                                :label="item.username"
                                :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="小组组员" label-width="80px">
                    <el-table :data="groupInfo.change.members">
                        <el-table-column property="usernum" label="学号" width="100"></el-table-column>
                        <el-table-column property="username" label="姓名" width="100"></el-table-column>
                        <el-table-column label="操作" align="right">
                            <template slot-scope="scope">
                                <el-popconfirm
                                        confirm-button-text='确定'
                                        @confirm="removeMember(scope.$index)"
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
                    <div style="margin-top: 15px">
                        <el-select
                                style="width: 100%"
                                filterable
                                remote
                                reserve-keyword
                                placeholder="请输入学号或姓名"
                                :remote-method="findUser"
                                :loading="loading"
                                class="input-new-tag"
                                v-if="inputVisible"
                                v-model="inputValue"
                                ref="saveTagInput"
                                @change="handleInputConfirm">
                            <el-option
                                    v-for="item in searchList"
                                    :key=item.id
                                    :label="item.username"
                                    :value=item>
                            </el-option>
                        </el-select>
                        <el-button v-else class="button-new-tag" @click="showInput">+ 添加小组成员</el-button>
                    </div>
                </el-form-item>
                <el-form-item label="创建日期" label-width="80px">
                    <span class="content">{{ tranDate(groupInfo.change.crtDate) }}</span>
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


    export default {
        name: "AdminGroup",
        props:['tabData'],
        data() {
            return {
                groupTable: [],
                search: '',
                page: {
                    currentPage: 1,
                    total: 0,
                    pageSize: 10,
                },
                groupInfo: {
                    origin: {},
                    change: {},
                    index:0
                },
                inputVisible: false,
                inputValue: {},
                searchList: [],
                loading: false,
                dialogTableVisible: false,
            }
        },
        mounted() {
            this.groupTable = this.tabData;
            this.page.total = this.tabData.length;
        },
        computed: {
            groupType: function () {
                return function (type) {
                    switch (type) {
                        case 0:
                            return "课内小组";
                        case 1:
                            return "课外小组";
                        case 2:
                            return "success";
                        case 3:
                            return "primary";
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
            handleEdit(index, group) {
                this.groupInfo.origin =group;
                this.groupInfo.change = JSON.parse(JSON.stringify(group));
                this.searchList = [];
                this.groupInfo.index = index
                this.dialogTableVisible = true
            },
            handleDelete() {

            },
            handleSearch() {
                let _this = this;
                _this.page.currentPage = 1;
                this.groupTable =  this.tabData.filter(data => !this.search || data.name.toLowerCase().includes(this.search.toLowerCase()))
                _this.page.total  = this.groupTable.length;
            },
            handleCurrentChange() {
                let _this = this;

            },
            handleUpdate() {
                let _this = this;
                _this.$axios.post('/createGroup' , _this.groupInfo.change).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        _this.groupTable[_this.groupInfo.index] = JSON.parse(JSON.stringify(_this.groupInfo.change));
                        for (let i = 0; i < _this.tabData; i++) {
                            if(_this.tabData[i].id===_this.groupInfo.change.id){
                                _this.tabData[i] = JSON.parse(JSON.stringify(_this.groupInfo.change))
                                break;
                            }
                        }
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
                this.groupInfo.change = JSON.parse(JSON.stringify(this.groupInfo.origin));
                this.searchList = [];
            },
            showInput() {
                if (this.groupInfo.change.members.length === 20) {
                    this.$message.error('最多20个成员');
                } else {
                    this.inputVisible = true;
                }
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    let flag = true;
                    for (let i = 0; i < this.groupInfo.change.members.length; i++) {
                        if (this.groupInfo.change.members[i].id === inputValue.id) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        this.groupInfo.change.members.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            removeMember(index) {
                this.groupInfo.change.members.splice(index, 1);
            },
            findUser(query) {
                if (query !== '') {
                    this.loading = true;
                    this.searchList = [];
                    var _this = this;
                    if (_this.groupInfo.change.type === 1) {
                        _this.$axios.get('/searchUser?keyword=' + query).then(resp => {
                            if (resp && resp.status === 200) {
                                _this.searchList = resp.data.result
                            }
                        });
                    } else {
                        _this.$axios.get('/courseMembers?courseId=' + _this.groupInfo.change.course.id).then(resp => {
                            if (resp && resp.status === 200) {
                                _this.searchList = resp.data.result
                            }
                        });
                    }
                    setTimeout(() => {
                        this.loading = false;
                    }, 1000);
                }
            },

        },
    }
</script>

<style scoped>

</style>