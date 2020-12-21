<template>
    <div class="table" >
        <el-table
                :data="applyTable"
                stripe
                max-height="600"
                :default-sort="{prop: 'ddlDate', order: 'descending'}"
        >
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
                    :filters="[{text: '2016-05-01', value: '2016-05-01'}, {text: '2016-05-02', value: '2016-05-02'}, {text: '2016-05-03', value: '2016-05-03'}, {text: '2016-05-04', value: '2016-05-04'}]"
                    :filter-method="filterHandler"
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
        methods:{
            filterHandler(value, row, column) {
                const property = column['property'];
                return row[property] === value;
            }
        }
    }
</script>

<style scoped>
.table{
    max-width: 1200px;
    margin:25px auto;
    padding: 10px;
    background-color: white;
    min-height: 600px;
    box-shadow: 0 0 5px  rgba(0,0,0,.05);
}
</style>