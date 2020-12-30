<template>
  <el-menu
    :default-active="'Index'"
    router
    mode="horizontal"
    background-color="white"
    text-color="darkblue"
    active-text-color="blue"
    >
    <el-menu-item v-for="(item,i) in navList" :index="item.name" :key="i">
      {{item.navItem}}
    </el-menu-item>

    <el-menu-item style="float: right" @click="logout" index="/Login">
      退出登录
    </el-menu-item>
    <span style="float: right;margin: 20px 5px 10px">
      您好，{{$store.state.user.usernum}} {{$store.state.user.username}}
    </span>
    <el-badge :value="12" style="float:right;margin:17px 15px 0 0;">
      <el-button icon="el-icon-bell" circle></el-button>
    </el-badge>
  </el-menu>
</template>

<script>
export default {
  name: "NavMenu",
  data(){
    return{
      navList:[
        {name:"Index",navItem:"用户中心"},
        {name:"PostApply",navItem:"发布请求"},
        {name:"ApplyCenter",navItem:"响应请求"},
      ]
    }
  },
  methods:{
    logout(){
      var _this = this
      this.$axios.get('/logout').then(resp => {
        if (resp.data.code === 200) {
          // 前后端状态保持一致
          _this.$store.commit('logout')
          _this.$router.replace('/login')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>