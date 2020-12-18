<template>
  <el-menu
      class="categories"
      default-active="/Groups"
      router
      active-text-color="blue">
    <el-menu-item index="/Groups">
      <i class="iconfont el-icon-Personalmanagement-" style="margin: 0 3px 2px 0;font-size:20px"></i>
      <span slot="title">我的小组</span>
    </el-menu-item>
    <el-menu-item index="/Courses">
      <i class="iconfont el-icon-course" style="margin: 0 3px 2px 0"></i>
      <span slot="title">我的课程</span>
    </el-menu-item>
    <el-menu-item index="Personal">
      <i class="iconfont el-icon-Personalcenter-icon" style="margin: 0 3px 2px 0"></i>
      <span slot="title">个人信息</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "SideMenu",
  data () {
    return {
    }
  },
  methods: {

  }
}
</script>

<style scoped>
.categories {
  position: fixed;
  margin-left: 50%;
  top: 100px;
  left: -600px;
  width: 150px;

}
</style>