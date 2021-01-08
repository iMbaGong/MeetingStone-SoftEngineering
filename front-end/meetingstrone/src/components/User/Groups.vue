<template>
    <div style="height: 100%;display: flex;flex-direction: column">
        <el-row>
            <el-button
                    size="small"
                    icon="el-icon-plus"
                    style="float: left;margin: 15px"
                    @click="dialogFormVisible = true"
            >
                创建小组
            </el-button>
            <el-radio-group v-model="display" size="mini" style="float: right;margin: 15px">
                <el-radio-button label="卡片" icon="el-icon-search"></el-radio-button>
                <el-radio-button label="列表"></el-radio-button>
            </el-radio-group>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12" style="margin-bottom: 20px" v-for="item in groups" :key="item.id">
                <el-card :body-style="{ padding: '0px' }" class="group" shadow="hover"
                         @click.native="getGroupInfo(item)">
                    <div style="height: 100px;background-color:#409EFF">
                        <h1 class="name">{{ item.name }}</h1>
                    </div>
                    <div class="leader">
                        组长：{{ item.leader.username }}
                    </div>
                    <div class="info">
                        简介：{{ item.info }}
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-dialog :visible.sync="dialogFormVisible" width="450px">
            <template slot="title">
                <h3 style="width: max-content;">创建小组</h3>
            </template>
            <el-form :model="form" :rules="rules" ref="groupForm" label-width="80px" label-position="left">
                <el-form-item label="小组名称" prop="name">
                    <el-input type="text" placeholder="请输入小组名称" v-model="form.name" maxlength="10"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="小组类型" prop="type">
                    <el-select v-model="form.type" @change="selectType" placeholder="课内/课外小组" style="width: 100%">
                        <el-option label="课内小组" value="1"></el-option>
                        <el-option label="课外小组" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-show=typeBtn label="绑定课程" prop="course" >
                    <el-select
                            style="width: 100%"
                            v-model="form.course"
                            placeholder="请输入关键词"
                            value-key="name"
                    >
                        <el-option
                                v-for="item in myCourse"
                                :key=item.id
                                :label="item.name"
                                :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="小组成员">
                    <el-table :data="form.members" v-if="form.members.length>0">
                        <el-table-column property="usernum" label="学号" width="120"></el-table-column>
                        <el-table-column property="username" label="姓名"></el-table-column>
                        <el-table-column label="操作" align="right">
                            <template slot-scope="scope">
                                <el-popconfirm
                                        confirm-button-text='确定'
                                        @confirm="deleteMember(scope.row.id,scope.$index)"
                                        cancel-button-text='取消'
                                        icon="el-icon-info"
                                        icon-color="red"
                                        title="确定移除组员吗"
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
                <el-form-item label="简介">
                    <el-input type="textarea" v-model="form.intro" maxlength="64" show-word-limit
                              style="width: 100%"></el-input>
                </el-form-item>
                <el-button type="primary" @click="createGroup">创建小组</el-button>
            </el-form>
        </el-dialog>
        <div style="flex-grow: 1;"></div>
        <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="pagesize"
                :total="Math.ceil(this.groups.length/this.pagesize)"
        >
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "Groups",
        components: {},
        data() {
            return {
                display: "卡片",
                currentPage: 1,
                pagesize: 6,
                groups: [],
                dialogFormVisible: false,
                form: {
                    name: '',
                    type: '',
                    course: {},
                    intro: '',
                    members: [],
                },
                typeBtn: false,
                searchList: [],
                myCourse:[],
                loading: false,
                inputVisible: false,
                inputValue: {},
                rules: {
                    name: [{required: true, message: '请输入小组名称', trigger: 'blur'}],
                    type: [{required: true, message: '请选择小组类型', trigger: 'change'}],
                    course: [{required: true, message: '请选择绑定的课程', trigger: 'change'}],
                }
            }
        },
        mounted() {
            this.loadGroups()
        },
        computed: {},
        methods: {
            loadGroups() {
                var _this = this;
                _this.$axios.get('/joinedGroups').then(resp => {
                    console.log(resp.data);
                    if (resp && resp.status === 200) {
                        _this.groups = resp.data.result
                    }
                });

            },
            handleCurrentChange: function (currentPage) {
                this.currentPage = currentPage;
                console.log(this.currentPage)
            },
            getGroupInfo(group) {
                console.log("click Group")
                this.$emit("getGroupInfo", group);
            },
            selectType(value) {
                if (value === "1") {
                    let _this = this;
                    _this.typeBtn = true;
                    _this.$axios.get('/joinedCourses').then(resp => {
                        if (resp && resp.status === 200) {
                            _this.myCourse = resp.data.result;
                        }
                    });
                } else {
                    this.typeBtn = false
                }

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
            showInput() {
                if (this.form.members.length === 20) {
                    this.$message.error('最多20个成员');
                } else {
                    this.inputVisible = true;
                }
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    console.log(inputValue);
                    this.form.members.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            deleteMember(id, index) {
                this.form.members.splice(index, 1)
            },
            createGroup() {
                let _this = this;
                let tmparr = _this.form.members;
                tmparr.push(_this.$store.state.user);
                _this.$axios.post("/createGroup", {
                    name: _this.form.name,
                    type: _this.form.type,
                    info: _this.form.intro,
                    leader: _this.$store.state.user,
                    members: tmparr,
                    crtDate: new Date(),
                    course: _this.form.course
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                        setTimeout(() => {
                            location.reload()
                        }, 1000);
                    }
                })
            }

        }
    }
</script>

<style scoped>
    .group {
        height: 200px;
        text-align: left;
        border-radius: 20px;
    }

    .name {
        padding: 60px 0 0 10px;
        margin-top: 0;
        color: white;
    }

    .leader {
        padding: 10px;
    }

    .info {
        padding: 0 10px 5px;
        font-size: 15px;
    }


</style>