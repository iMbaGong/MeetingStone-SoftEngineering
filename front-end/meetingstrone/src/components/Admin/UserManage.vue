<template>
    <div>
        <el-table
                :data="userTable"
                style="width: 100%">
            <el-table-column
                    label="学号"
                    prop="date">
            </el-table-column>
            <el-table-column
                    label="姓名"
                    prop="name">
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">注销</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: "UserManage",
        data(){
            return{
                userTable:[],
                search:'',
            }
        },
        methods:{
            handleEdit(){

            },
            handleDelete(){

            }
        }
    }
</script>

<style scoped>

</style>