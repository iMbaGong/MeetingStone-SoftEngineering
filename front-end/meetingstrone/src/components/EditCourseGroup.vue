<template>
    <div style="max-width: 70%;margin: 30px auto">
        <el-form :rules="rules" ref="groupForm" label-width="80px" label-position="left">
            <el-form-item label="小组名称" prop="name">
                <el-input type="text" placeholder="请输入小组名称" v-model="group.name" maxlength="10"
                          show-word-limit></el-input>
            </el-form-item>
            <el-form-item label="组长" prop="leader">
                <el-select
                        style="width: 100%"
                        filterable
                        v-model="group.leader"
                        value-key="username"
                >
                    <el-option
                            v-for="item in group.members"
                            :key=item.id
                            :label="item.username"
                            :value=item>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="小组成员">
                <el-table :data="group.members">
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
        </el-form>
        <el-button type="primary" @click="save" style="margin-top: 20px">保存</el-button>
    </div>
</template>

<script>
    export default {
        name: "EditCourseGroup",
        props:['group'],
        data(){
            return{
                inputVisible: false,
                inputValue: {},
                searchList: [],
                loading: false,
                rules: {
                    name: [{required: true, message: '请输入小组名称', trigger: 'blur'}],
                    leader: [{required: true, message: '请选择组长', trigger: 'change'}],
                }
            }
        },
        methods:{
            showInput() {
                if (this.group.members.length === 20) {
                    this.$message.error('最多20个成员');
                } else {
                    this.inputVisible = true;
                }
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    this.group.members.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            removeMember(index){
                this.group.members.splice(index,1)
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
            save(){
                let _this = this;
                _this.$axios.post('/createGroup' , this.group).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.$message({
                            type: 'success',
                            message: '操作成功!'
                        });
                    }
                });
                setTimeout(() => {
                    this.loading = false;
                }, 1000);
            }
        }
    }
</script>

<style scoped>

</style>