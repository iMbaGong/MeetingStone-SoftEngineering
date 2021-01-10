<template>
    <el-form
            style="margin: 50px 0 0 100px;float:left"
            status-icon
            :rules="rules"
            ref="ruleForm"
            :model="ruleForm"
            label-position="left"
    >
        <el-form-item prop="pass" label="修改密码：">
            <el-input type="password" placeholder="密码" style="width: 300px" v-model="ruleForm.pass"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass" label="确认密码：">
            <el-input type="password" placeholder="再次输入" style="width: 300px" v-model="ruleForm.checkPass"></el-input>
        </el-form-item>
        <el-button type="primary" @click="submitForm">确认修改</el-button>
    </el-form>
</template>

<script>
    export default {
        name: "Personal",
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm: {
                    pass: '',
                    checkPass: '',
                },
                rules: {
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            submitForm() {
                let _this = this;
                _this.$refs["ruleForm"].validate((valid) => {
                    if (valid) {
                        _this.$axios.get("setPassword?pw="+_this.ruleForm.pass).then(resp=>{
                                if(resp.data.code === 200){
                                    _this.$message({
                                        type: 'success',
                                        message: '修改成功!'
                                    });
                                }
                                else {
                                    _this.$message({
                                        type: 'error',
                                        message: resp.data.message
                                    });
                                }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>