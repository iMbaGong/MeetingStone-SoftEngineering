<template>
    <div class="table" >
        <el-table
            :data="applyTable.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                stripe
                max-height="600"
                :default-sort="{prop: 'ddlDate', order: 'descending'}"

                >
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="标题">
                            <span>{{ props.row.title }}</span>
                        </el-form-item>
                        <el-form-item label="简介">
                            <span>{{ props.row.intro }}</span>
                        </el-form-item>
                        <el-form-item label="发布人">
                            <span>{{ props.row.applicant.name }}</span>
                        </el-form-item>
                        <el-form-item label="关联小组">
                            <span>{{ props.row.group.name }}</span>
                        </el-form-item>
                        <el-form-item label="关联课程">
                            <span>{{ props.row.course.name }}</span>
                        </el-form-item>
                        <el-form-item label="标签">
                            <span>
                                <el-tag size="small" v-for="tag in apply.tags" :key="tag" style="margin-left: 5px">
                                    {{tag}}
                                </el-tag>
                            </span>
                        </el-form-item>
                        <el-form-item label="创建日期">
                            <span>{{ props.row.crtDate }}</span>
                        </el-form-item>
                        <el-form-item label="截止日期">
                            <span>{{ props.row.ddlDate }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column align="right">
                <template slot="header">
                    <el-input
                        v-model="search"
                        size="mini"
                        placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                        size="mini"
                        @click="handleReply(scope.$index, scope.row)">Edit</el-button>
                </template>
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="标题"
                    sortable="true"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="type"
                    label="类型"
                    sortable="true"
                    :filters="[{text: '招募队友/课内请求', value: '招募队友/课内请求'}, {text: '招募队友/课外请求', value: '招募队友/课外请求'}, {text: '寻求组队/课内请求', value: '寻求组队/课内请求'}, {text: '寻求组队/课外请求', value: '寻求组队/课外请求'}]"
                    :filter-method="filterHandler"
                    filter-placement="bottom-start"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="applicant.name"
                    label="发布者"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="ddlDate"
                    label="截止日期"
                    sortable="true"
                    >
            </el-table-column>
        </el-table>
    </div>

</template>

<script>
    export default {
        name: "ApplyCenter",
        data(){
            return{
                applyTable:[],
                search:'',
            }
        },
        computed:{
            applyType:function (type) {
                switch (type) {
                    case 1:return "招募队友/课内请求";
                    case 2:return "招募队友/课外请求";
                    case 3:return "寻求组队/课内请求";
                    case 4:return "寻求组队/课外请求";
                    default: return ""
                }
            }
        },
        mounted() {
            this.init()
        },
        methods:{
            init(){
                let _this = this;
                _this.$axios.get("searchApply?pageSize=10&pageNum=1&keyword=测试").then(resp=>{
                    if(resp.data.code===200){
                        _this.applyTable = resp.data.result;
                    }
                }).catch()
            },
            filterHandler(value, row, column) {
                const property = column['property'];
                return row[property] === value;
            },
            handleReply(index, row) {
                console.log(index, row);
            },
        }
    }
</script>

<style scoped>
.table{
    max-width: 1200px;
    margin:25px auto;
    padding: 10px 40px;
    background-color: white;
    min-height: 600px;
    box-shadow: 0 0 5px  rgba(0,0,0,.05);
}
</style>