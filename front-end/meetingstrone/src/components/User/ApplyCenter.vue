<template>
    <div class="table">
        <el-table
            :data="applyTable.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
            stripe
            max-height="1000px"
            :default-sort="{prop: 'ddlDate', order: 'descending'}"
            @sort-change="mySort">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" class="demo-table-expand">
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>标题</h3>
                            </template>
                            <span class="content">{{ props.row.title }}</span>
                        </el-form-item>
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>简介</h3>
                            </template>
                            <span class="content">{{ props.row.intro }}</span>
                        </el-form-item>
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>发布人</h3>
                            </template>
                            <span class="content">{{ props.row.applicant.username }}</span>
                        </el-form-item>
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>类型</h3>
                            </template>
                            <span class="content">{{ applyType(props.row.type) }}</span>
                        </el-form-item>
                        <el-form-item v-if="props.row.group">
                            <template slot="label" class="label">
                                <h3>关联小组</h3>
                            </template>
                            <span class="content">{{ props.row.group.name }}</span>
                        </el-form-item>
                        <el-form-item v-if="props.row.course">
                            <template slot="label" class="label">
                                <h3>关联课程</h3>
                            </template>
                            <span class="content">{{ props.row.course.name }}</span>
                        </el-form-item>
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>标签</h3>
                            </template>
                            <span class="content">
                                <el-tag size="small" v-for="tag in props.row.tags" :key="tag" style="margin-left: 5px">
                                    {{ tag }}
                                </el-tag>
                            </span>
                        </el-form-item>
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>创建日期</h3>
                            </template>
                            <span class="content">{{ tranDate(props.row.crtDate) }}</span>
                        </el-form-item>
                        <el-form-item>
                            <template slot="label" class="label">
                                <h3>截止日期</h3>
                            </template>
                            <span class="content">{{ tranDate(props.row.ddlDate) }}</span>
                        </el-form-item>
                    </el-form>
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
                <template slot-scope="scope">
                    <el-tag
                        :type="tagType(scope.row.type)"
                        disable-transitions>{{ applyType(scope.row.type) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column
                prop="applicant.username"
                label="发布者"
                width="100">
            </el-table-column>
            <el-table-column
                prop="ddlDate"
                label="截止日期"
                sortable="true"
            >
                <template slot-scope="scope">
                    {{ tranDate(scope.row.ddlDate) }}
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
                        :type="tagType(scope.row.type)"
                        @click="handleOperate(scope.$index, scope.row)">{{ opType(scope.row.type) }}
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog :visible.sync="dialogFormVisible" width="400px">
            <template slot="title">
                <h3 style="width: max-content;">完善信息</h3>
            </template>
            <el-form :model="form" :rules="rules" ref="replyForm">
                <el-form-item label="选择小组" :label-width="formLabelWidth" v-show="formType(applySelected.type)"
                              prop="groupSelected">
                    <el-select v-model="form.groupSelected" placeholder="选择已加入的小组" style="width: 100%">
                        <el-option
                            v-for="item in form.myGroups"
                            :key="item.id"
                            :label="item.name"
                            :value=item>
                            <span style="float: left">{{ item.name }}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{
                                    item.leader.username
                                }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="form.remark" maxlength="64" show-word-limit
                              style="width: 100%"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleReply">确 定</el-button>
            </div>
        </el-dialog>
    </div>

</template>

<script>
export default {
    name: "ApplyCenter",
    data() {
        return {
            applyTable: [],
            search: '',
            dialogFormVisible: false,
            formLabelWidth: '80px',
            applySelected: {
                index: -1,
                type: '',
            },
            form: {
                myGroups: [],
                groupSelected: {},
                remark: ''
            },
            rules: {
                groupSelected: [{required: true, message: '请选择小组(若无该课程的小组则无法邀请)', trigger: 'blur'},]
            }
        }
    },
    computed: {
        applyType: function () {
            return function (type) {
                switch (type) {
                    case 1:
                        return "招募队友/课内请求";
                    case 2:
                        return "招募队友/课外请求";
                    case 3:
                        return "寻求组队/课内请求";
                    case 4:
                        return "寻求组队/课外请求";
                    default:
                        return ""
                }
            }
        },
        opType: function () {
            return function (type) {
                switch (type) {
                    case 1:
                        return "申请入组";
                    case 2:
                        return "申请入组";
                    case 3:
                        return "邀请加入";
                    case 4:
                        return "邀请加入";
                    default:
                        return ""
                }
            }
        },
        formType: function () {
            return function (type) {
                switch (type) {
                    case 1:
                        return false;
                    case 2:
                        return false;
                    case 3:
                        return true;
                    case 4:
                        return true;
                    default:
                        return ""
                }
            }
        },
        tagType: function () {
            return function (type) {
                switch (type) {
                    case 1:
                        return "success";
                    case 2:
                        return "success";
                    case 3:
                        return "primary";
                    case 4:
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
    mounted() {
        this.init()
    },
    methods: {
        init() {
            let _this = this;
            _this.$axios.get("allApply").then(resp => {
                if (resp.data.code === 200) {
                    _this.applyTable = resp.data.result;
                }
            }).catch();
            _this.$axios.get('/joinedGroups').then(resp => {
                console.log(resp.data);
                if (resp && resp.status === 200) {
                    _this.form.myGroups = resp.data.result
                }
            })
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        handleReply() {
            this.$refs['replyForm'].validate((valid) => {
                if (valid) {
                    let _this = this;
                    _this.$axios.post("reply", {
                        type: Math.ceil(_this.applySelected.type / 2),
                        state: 0,
                        remark: _this.form.remark,
                        apply: _this.applyTable[_this.applySelected.index],
                        group: _this.form.groupSelected,
                        crtDate: new Date()
                    }).then(resp => {
                        if (resp.data.code === 200) {
                            _this.$message({
                                type: 'success',
                                message: '提交成功!'
                            });
                        } else {
                            _this.$message({
                                type: 'error',
                                message: resp.data.message
                            });
                        }
                    }).catch(function (resp) {
                        console.log(resp);
                        _this.$message({
                            type: 'error',
                            message: '提交失败!'
                        });
                    });
                    _this.dialogFormVisible = false;
                    _this.form.remark = '';
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        handleOperate(index, row) {
            let _this = this;
            _this.applySelected = {index: index, type: row.type};
            if (row.course) {
                _this.form.myGroups = [];
                _this.$axios.get("myGroupByCourse?courseId=" + row.course.id)
                    .then(resp => {
                        if (resp.data.code === 200) {
                            _this.form.myGroups = resp.data.result
                            _this.dialogFormVisible = true;
                        }
                    })
            } else
                _this.dialogFormVisible = true;
        },

        mySort(column) {
            console.log("here");
            let fieldName = column.prop;
            let sortingType = column.order;

            //如果字段名称为“创建时间”，将“创建时间”转换为时间戳，才能进行大小比较
            if (fieldName === "ddlDate") {
                this.applyTable.map(item => {
                    item.ddlDate = this.$moment(item.ddlDate).valueOf();
                });
                //按照降序排序
                if (sortingType === "descending") {
                    this.applyTable = this.applyTable.sort((a, b) => b[fieldName] - a[fieldName]);
                }
                //按照升序排序
                else {
                    this.applyTable = this.applyTable.sort((a, b) => a[fieldName] - b[fieldName]);
                }
                //如果字段名称为“创建时间”，将时间戳格式的“创建时间”再转换为时间格式
                if (fieldName === "ddlDate") {
                    this.applyTable.map(item => {
                        item.ddlDate = this.$moment(item.ddlDate).format(
                            "YYYY-MM-DD HH:mm:ss"
                        );
                    });
                }
            } else {
                if (sortingType === "descending") {
                    this.applyTable = this.applyTable.sort((a, b) => (a + '').localeCompare(b + '', 'zh-CN'));
                }
                //按照升序排序
                else {
                    this.applyTable = this.applyTable.sort((a, b) => (b + '').localeCompare(a + '', 'zh-CN'));
                }
            }
        }
    }
}
</script>

<style scoped>
.table {
    max-width: 1000px;
    margin: 25px auto;
    padding: 10px 40px;
    background-color: white;
    min-height: 600px;
    box-shadow: 0 0 5px rgba(0, 0, 0, .05);
}

h3 {
    margin: 2px 0 2px 0;
    width: 80px;
}

.content {
    float: left;
    margin: 2px 0 2px 0;
}

.demo-table-expand .el-form-item {
    margin-left: 80px;
    margin-right: 0;
    margin-bottom: 0;
    width: 65%;
}
</style>