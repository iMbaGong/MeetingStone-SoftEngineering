<template>
    <div style="width: 70%;margin: 20px auto">
        <el-form>
            <el-form-item label="助教" v-if="course.assist">
                {{ course.assist.usernum }} {{ course.assist.username }}
                <el-button
                        type="primary"
                        style="float: right"
                        @click="removeAssist"
                >移除
                </el-button>
            </el-form-item>
            <el-form-item label="选择助教" v-if="!course.assist">
                <el-select
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
        <el-table :data="members.slice((page.currentPage-1)*page.pageSize,page.currentPage*page.pageSize)">
            <el-table-column property="usernum" label="学号" width="100"></el-table-column>
            <el-table-column property="username" label="姓名" ></el-table-column>
            <el-table-column  align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            @change="handleSearch"
                            size="mini"
                            placeholder="输入关键字搜索"/>
                </template>
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
            <el-button v-else class="button-new-tag" @click="showInput">+ 添加课程成员</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "CourseMember",
        props: ['tabData'],
        data() {
            return {
                search: '',
                page: {
                    currentPage: 1,
                    total: 0,
                    pageSize: 10,
                },
                course: {},
                searchList: [],
                assistSelect: '',
                loading: false,
                inputVisible: false,
                inputValue: {},
                members:[]
            }
        },
        mounted() {
            let _this = this;
            this.course = this.tabData;
            _this.$axios.get('/courseMembers?courseId=' + this.course.id).then(resp => {
                if (resp && resp.status === 200) {
                    _this.course.members = resp.data.result
                    _this.members = resp.data.result
                }
            });
        },
        methods:{
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
            },
            removeMember(index) {
                let _this = this;
                _this.$axios.get("/admin/course/removeMember?courseId="+_this.course.id+"&userId="+_this.members[index].id).then(resp=>{
                    if(resp.data.code ===200){
                        for (let i = 0; i < this.course.members.length; i++) {
                            if (this.course.members[i].id === _this.members[index].id) {
                                this.course.members.splice(i,1);
                                _this.members.splice(index,1);
                                break;
                            }
                        }
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                    }
                })
            },

            showInput() {
                if (this.course.members.length === 20) {
                    this.$message.error('最多20个成员');
                } else {
                    this.inputVisible = true;
                }
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    let flag = true;
                    for (let i = 0; i < this.course.members.length; i++) {
                        if (this.course.members[i].id === inputValue.id) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        let _this = this;
                        _this.$axios.get("/admin/course/addMember?courseId=" + _this.course.id + "&userId=" + inputValue.id).then(resp => {
                            if (resp.data.code === 200) {
                                _this.course.members.push(inputValue);
                                _this.$message({
                                    type: 'success',
                                    message: '操作成功!'
                                });
                                this.handleSearch()
                            }
                        })
                    }
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            findUser(query) {
                if (query !== '') {
                    this.loading = true;
                    this.searchList = [];
                    var _this = this;
                    _this.$axios.get('/searchUser?keyword=' + query).then(resp => {
                        if (resp && resp.status === 200) {
                            _this.searchList = resp.data.result
                        }
                    });
                    setTimeout(() => {
                        this.loading = false;
                    }, 1000);
                }
            },
            handleSearch() {
                let _this = this;
                _this.page.currentPage = 1;
                _this.members =  this.course.members.filter(data => !this.search || data.username.toLowerCase().includes(this.search.toLowerCase()))
                _this.page.total  = this.members.length;
            },
        }
    }
</script>

<style scoped>

</style>