<template>
    <el-collapse v-model="activeName" accordion style="max-width: 800px">
        <el-collapse-item v-for="(reply,index) in myReplies" :name="reply.id" :key="reply.id">
            <template slot="title">
                <el-tag :type=replyState(reply.state) size="small" style="margin-right: 15px">
                    {{replyState(reply.state+3)}}
                </el-tag>
                <h1>
                    to:{{reply.apply.title}}
                </h1>
            </template>
            <h3 align="left" style="margin-left: 10%" >目标请求:</h3>
            <div class="apply">
                <el-form label-position="left" label-width="80px"  class="demo-table-expand">
                    <el-form-item>
                        <template slot="label">
                            <h3>标题</h3>
                        </template>
                        <span class="content">{{reply.apply.title}}</span>
                    </el-form-item>
                    <el-form-item >
                        <template slot="label">
                            <h3>简介</h3>
                        </template>
                        <span class="content">{{reply.apply.intro}}</span>
                    </el-form-item>
                    <el-form-item>
                        <template slot="label">
                            <h3>类型</h3>
                        </template>
                        <span class="content">{{applyType(reply.apply.type)}}</span>
                    </el-form-item>
                    <el-form-item v-if=isGroup(reply.apply.type)>
                        <template slot="label">
                            <h3>关联小组</h3>
                        </template>
                        <span class="content">{{reply.apply.group.name}}</span>
                    </el-form-item>
                    <el-form-item v-if=isCourse(reply.apply.type)>
                        <template slot="label">
                            <h3>关联课程</h3>
                        </template>
                        <span class="content">{{reply.apply.course.name}}</span>
                    </el-form-item>
                    <el-form-item >
                        <template slot="label">
                            <h3>标签</h3>
                        </template>
                        <span class="content">
                            <el-tag size="small" v-for="tag in reply.apply.tags" :key="tag" style="margin-left: 5px">
                            {{tag}}
                            </el-tag>
                        </span>
                    </el-form-item>
                    <el-form-item>
                        <template slot="label">
                            <h3>创建时间</h3>
                        </template>
                        <span class="content">
                            {{reply.apply.crtDate.split('T').join(' ').split('.').splice(0,1).join('')}}
                        </span>
                    </el-form-item>
                    <el-form-item>
                        <template slot="label">
                            <h3>有效期至</h3>
                        </template>
                        <span class="content">
                            {{reply.apply.ddlDate.split('T').join(' ').split('.').splice(0,1).join('')}}
                        </span>
                    </el-form-item>
                </el-form>
            </div>
            <h3 align="left" style="margin-left: 10%;margin-top: 5px">备注:</h3>
            <span style="float: left;margin-left: 20%">
                {{reply.remark}}
            </span>
            <el-button type="danger" icon="el-icon-delete" circle style="float: right;margin:0 10px 15px 0" @click="withdraw(index,reply.id)"></el-button>
        </el-collapse-item>
    </el-collapse>
</template>

<script>
    export default {
        name: "MyReply",
        data(){
            return {
                activeName:'',
                myReplies:[],
            }
        },
        computed:{
            replyState:function () {
                return function (state) {
                    switch (state) {
                        case 0:return "warning";
                        case 1:return "success";
                        case 2:return "danger";
                        case 3:return "待处理";
                        case 4:return "已接收";
                        case 5:return "被拒绝";
                        default: return ""
                    }
                }
            },
            applyType:function(){
                return function (type) {
                    switch (type) {
                        case 1:return "招募队友/课内请求";
                        case 2:return "招募队友/课外请求";
                        case 3:return "寻求组队/课内请求";
                        case 4:return "寻求组队/课外请求";
                        default: return ""
                    }
                }
            },
            applyState:function () {
                return function (state) {
                    switch (state) {
                        case 0:return "warning";
                        case 1:return "success";
                        case 2:return "danger";
                        case 3:return "审核中";
                        case 4:return "已发布";
                        case 5:return "被遣返";
                        default: return ""
                    }
                }
            },
            isGroup:function () {
                return function (type) {
                    switch (type) {
                        case 1:return true;
                        case 2:return true;
                        case 3:return false;
                        case 4:return false;
                        default: return false;
                    }
                }
            },
            isCourse:function () {
                return function (type) {
                    switch (type) {
                        case 1:return true;
                        case 2:return false;
                        case 3:return true;
                        case 4:return false;
                        default: return false;
                    }
                }
            },
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                let _this = this;
                _this.$axios.get("/myReply")
                    .then(resp=>{
                        if(resp.data.code===200){
                            _this.myReplies = resp.data.result
                        }
                    }).catch()
            },
            withdraw(index,id){
                console.log(id);
                let _this = this;
                this.$confirm('确定要删除响应吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.$axios.get("/withdrawReply?replyId="+id)
                        .then(resp=>{
                            if(resp.data.code===200){
                                _this.myReplies.splice(index, 1);
                                this.$message({
                                    type: 'success',
                                    message: '删除成功!'
                                });
                            }
                        }).catch(() => {
                            this.$message({
                                type: 'error',
                                message: '删除失败'
                            });
                        }
                    );
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消撤回'
                    });
                });


            }
        }
    }
</script>

<style scoped>
    .demo-table-expand .el-form-item {
        margin-left: 40px;
        margin-right: 0;
        margin-bottom: 0;
        width: 65%;
    }
    h3{
        margin: 2px 0 2px 0;
    }
    .content{
        float: left;
        margin: 2px 0 2px 0;
    }
    .apply{
        border-radius:15px;
        /*background-clip: padding-box;*/
        margin: 10px auto;
        width: 70%;

        padding-top: 20px;
        padding-bottom: 20px;
        background: #ffffff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 5px #cac6c6;
    }
</style>