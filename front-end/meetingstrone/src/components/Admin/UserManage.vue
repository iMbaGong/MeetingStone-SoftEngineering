<template>
    <div style="width: 80%;margin: 0 auto;">
        <el-table
            :data="userTable"
            style="width: 100%;">
            <el-table-column
                    label="编号"
                    prop="id">
            </el-table-column>
            <el-table-column
                label="学号"
                prop="usernum">
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

            :page-size="page.pageSize"
            :total="page.total">
        </el-pagination>
    </div>
</template>

<script>
export default {
    name: "UserManage",
    data() {
        return {
            userTable: [],
            search: '',
            page: {
                currentPage: 1,
                total: 17,
                pageSize:10,
            },
        }
    },
    mounted(){
        let _this = this;
        _this.$axios.get("/searchUser/"+_this.page.currentPage+"?kw=").then(resp=>{
            _this.userTable = resp.data.result;
            _this.page.total = resp.data.result2;
        })
    },
    methods: {
        handleEdit() {

        },
        handleDelete() {

        },
        handleSearch() {
            let _this = this;
            _this.page.currentPage = 1;
            _this.$axios.get("/searchUser/"+_this.page.currentPage+"?kw="+_this.search).then(resp=>{
                _this.userTable = resp.data.result;
                _this.page.total = resp.data.result2;
            })
        },
        handleCurrentChange() {
            let _this = this;
            _this.$axios.get("/searchUser/"+_this.page.currentPage+"?kw="+_this.search).then(resp=>{
                _this.userTable = resp.data.result;
                _this.page.total = resp.data.result2;
            })
        }
    }
}
</script>

<style scoped>

</style>