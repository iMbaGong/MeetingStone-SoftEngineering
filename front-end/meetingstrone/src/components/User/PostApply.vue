<template>
    <el-form :model="form" class="form" label-width="80px" ref="postForm" :rules="rules">
        <el-form-item label="请求标题" prop="title">
            <el-input type="text" placeholder="请输入请求标题" v-model="form.title" maxlength="10" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="请求类型" prop="type">
            <el-select v-model="form.type" @change="selectType" placeholder="招募队友or寻求组队" style="width: 100%">
                <el-option label="招募队友" value="1"></el-option>
                <el-option label="寻求组队" value="2"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item v-show=typeBtn label="绑定小组" prop="group">
            <el-select
                class="opt"
                v-model="form.group"
                filterable
                placeholder="请输入关键词"
                value-key="name">
                <el-option
                    v-for="item in myGroups"
                    :key=item.id
                    :label="item.name"
                    :value=item>
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="请求范围" prop="range">
            <el-select v-model="form.range" @change="selectRange" placeholder="课内请求or课外请求" style="width: 100%">
                <el-option label="课内请求" value="3"></el-option>
                <el-option label="课外请求" value="4"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item v-show=rangeBtn label="绑定课程" prop="course">
            <el-select
                class="opt"
                v-model="form.course"
                filterable
                placeholder="请输入关键词"
                value-key="name">
                <el-option
                    v-for="item in myCourses"
                    :key=item.id
                    :label="item.name"
                    :value=item>
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item v-show=tagInput label="选择标签">
            <div style="float: left">
                <el-tag
                    :key="tag"
                    v-for="tag in form.tags"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(tag)">
                    {{ tag }}
                </el-tag>
                <el-input
                    class="input-new-tag"
                    v-if="inputVisible"
                    v-model="inputValue"
                    ref="saveTagInput"
                    size="small"
                    @keyup.enter.native="handleInputConfirm"
                    @blur="handleInputConfirm"
                >
                </el-input>
                <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
            </div>

        </el-form-item>
        <el-form-item label="请求简介" prop="intro">
            <el-input type="textarea" v-model="form.intro" maxlength="64" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="有效期限" prop="ddlDate">
            <el-date-picker
                v-model="form.ddlDate"
                type="datetime"
                placeholder="选择日期时间"
                :picker-options="pickerOptions"
                style="width: 100%">
            </el-date-picker>
        </el-form-item>
        <el-button type="primary" @click="onSubmit">提交请求</el-button>
    </el-form>
</template>

<script>
export default {
    name: "PostApply",
    data() {
        return {
            form: {
                title: '',
                type: '',
                group: {},
                range: '',
                course: {},
                tags: [],
                intro: '',
                ddlDate: '',
            },
            myGroups: [],
            myCourses: [],
            states: [],

            typeBtn: false,
            rangeBtn: false,
            tagInput: false,
            inputVisible: false,
            inputValue: '',
            pickerOptions: {
                shortcuts: [{
                    text: '一天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一个月',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 3600 * 1000 * 24 * 7 * 30);
                        picker.$emit('pick', date);
                    }
                }]
            },
            rules: {
                title: [{required: true, message: '请输入标题', trigger: 'blur'}],
                group: [{required: true, message: '请选择绑定的小组', trigger: 'change'}],
                type: [{required: true, message: '请选择请求类型', trigger: 'change'}],
                range: [{required: true, message: '请选择项目范围', trigger: 'change'}],
                course: [{required: true, message: '请选择绑定的课程', trigger: 'change'}],
                ddlDate: [{type: 'date', required: true, message: '请选择截止日期', trigger: 'change'}],
            }

        }
    },
    methods: {
        onSubmit() {
            this.$refs['postForm'].validate((valid) => {
                if (valid) {
                    console.log('submit![日期：' + this.form.ddlTime + '],[' + this.form.group + ']');
                    var _this = this;
                    let type;
                    if (_this.form.type === "1") {
                        if (_this.form.range === "3") {
                            type = 1
                        } else {
                            type = 2
                        }
                    } else {
                        if (_this.form.range === "3") {
                            type = 3
                        } else {
                            type = 4
                        }
                    }
                    this.$axios
                        .post('/apply', {
                            title: _this.form.title,
                            crtDate: new Date(),
                            ddlDate: _this.form.ddlDate,
                            type: type,
                            state: 0,
                            group: _this.form.group,
                            course: _this.form.course,
                            tags: _this.form.tags,
                            intro: _this.form.intro
                        })
                        .then(resp => {
                            if (resp.data.code === 200) {
                                this.$alert('发布成功', '提示', {
                                    confirmButtonText: '确定'
                                })
                            } else {
                                console.log(resp.data.message);
                                this.$alert(resp.data.message, '提示', {
                                    confirmButtonText: '确定'
                                })
                            }
                        })
                        // eslint-disable-next-line no-unused-vars
                        .catch(failResponse => {
                        })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        selectType(value) {
            if (value === "1") {
                let _this = this;
                this.typeBtn = true;
                _this.$axios.get('/joinedGroups').then(resp => {
                    console.log(resp.data.result);
                    if (resp && resp.status === 200) {
                        _this.myGroups = resp.data.result
                    }
                });
            } else {
                this.typeBtn = false
            }

        },
        selectRange(value) {
            if (value === "3") {
                let _this = this;
                this.rangeBtn = true;
                this.tagInput = false;
                _this.$axios.get('/joinedCourses').then(resp => {
                    console.log(resp.data.result);
                    if (resp && resp.status === 200) {
                        _this.myCourses = resp.data.result
                    }
                });
            } else {
                console.log("select 4:" + this.form.range);
                this.rangeBtn = false;
                this.tagInput = true
            }
        },
        handleClose(tag) {
            this.form.tags.splice(this.form.tags.indexOf(tag), 1);
        },

        showInput() {
            if (this.form.tags.length === 3) {
                this.$message.error('最多3个标签');
            } else {
                this.inputVisible = true;
                this.$nextTick(function () {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            }
        },

        handleInputConfirm() {
            let inputValue = this.inputValue;
            if (inputValue) {
                this.form.tags.push(inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },


    }
}
</script>

<style scoped>
.form {
    margin: 130px auto;
    border-radius: 15px;
    /*background-clip: padding-box;*/
    width: 400px;
    padding: 35px 35px 15px 35px;
    background: #ffffff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

.el-tag + .el-tag {
    margin-left: 10px;
}

.button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
}

.input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
}

.opt {
    width: 100%;
}
</style>