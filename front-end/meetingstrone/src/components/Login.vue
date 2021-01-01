<template>
    <body id="poster">
    <el-form class="login_container" label-position="left" label-width="0px">
        <h3 class="login_title">MeetingStone</h3>
        <el-form-item>
            <el-input type="text" v-model="loginData.usernum" auto-complete="off" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item>
            <el-input type="password" v-model="loginData.password" auto-complete="off" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" style="width: 100%;background: lightskyblue;border: none" @click="login">登录
            </el-button>
        </el-form-item>
    </el-form>
    </body>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            loginData: {
                usernum: "",
                password: ""
            },
            responseResult: []
        }
    },
    methods: {
        login() {
            console.log("u re try to login")
            var _this = this
            this.$axios
                .post('/login', {
                    usernum: this.loginData.usernum,
                    password: this.loginData.password
                })
                .then(resp => {
                    if (resp.data.code === 200) {
                        _this.$store.commit('login', resp.data.result)
                        var path = this.$route.query.redirect
                        console.log("path:" + path)
                        this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
                    }
                })
                // eslint-disable-next-line no-unused-vars
                .catch(failResponse => {
                })
        }


    }
}
</script>

<style scoped>
.login_container {
    border-radius: 15px;
    /*background-clip: padding-box;*/
    margin: 130px auto;
    width: 300px;
    padding: 35px 35px 15px 35px;
    background: #ffffff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

.login_title {
    margin: 10px auto 20px auto;
    text-align: center;
    color: steelblue;
}

#poster {
    background: url("../assets/background.jpg") no-repeat;
    height: 105%;
    width: 105%;
    background-size: cover;
    position: fixed;
}

body {
    margin: -8px;
}
</style>