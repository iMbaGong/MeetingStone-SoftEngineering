<template>
  <el-card class="admin-header">
    <div @click="fold" style="float: left;margin: -6px -5px 0">
      <i class="el-icon-s-fold" v-if="!isCollapse" style="font-size: 30px;"></i>
      <i class="el-icon-s-unfold" v-if="isCollapse" style="font-size: 30px;"></i>
    </div>
    <i class="el-icon-switch-button" v-on:click="logout" style="font-size: 20px;float: right"></i>
  </el-card>
</template>

<script>
  import {createRouter} from '../../router'
  import {mapMutations} from 'vuex'
  export default {
    name: 'Header',
    data(){
      return{
        isCollapse:false,
      }
    },
    methods: {
      logout () {
        var _this = this;
        this.$axios.get('/logout').then(resp => {
          if (resp && resp.data.code === 200) {
            _this.$store.commit('logout');
            _this.$router.replace('/Index');
            // 清空路由，防止路由重复加载
            const newRouter = createRouter();
            _this.$router.matcher = newRouter.matcher
          }
        })
      },
      fold(){
        this.isCollapse = !this.isCollapse;
        this.collapse(this.isCollapse)
      },
      ...mapMutations(['collapse'])
    }
  }
</script>

<style scoped>
  .admin-header {
    height: 50px;
    opacity: 0.85;
    line-height: 40px;
  }
  .el-icon-switch-button {
    cursor: pointer;
    outline:0;
  }
</style>
