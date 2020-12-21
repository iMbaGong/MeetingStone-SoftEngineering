<template>
  <el-menu
      default-active="Groups"
      router
      active-text-color="blue"
      >
    <el-menu-item index="Groups">
      <template slot="title">
        <i class="iconfont el-icon-Personalmanagement-" style="margin: 0 3px 2px 0;font-size:20px"></i>
        <span>我的小组</span>
      </template>
    </el-menu-item>
    <el-menu-item index="Courses">
      <template slot="title">
        <i class="iconfont el-icon-course" style="margin: 0 3px 2px 0"></i>
        <span>我的课程</span>
      </template>
    </el-menu-item>
    <el-submenu index=""  style="overflow-x: hidden">
      <template slot="title">
        <i class="el-icon-document" style="margin: 0 0 2px 0;font-size:19px"></i>
        <span>我的请求</span>
      </template>
      <el-menu-item class="submenu" index="MyApply" >
        <div style="margin-left: 20px">
          发布的请求
        </div>
      </el-menu-item>
      <el-menu-item class="submenu" index="MyReply" >
        <div style="margin-left: 20px">
          响应的请求
        </div>
      </el-menu-item>
    </el-submenu>
    <el-menu-item index="Personal">
      <template slot="title">
        <i class="iconfont el-icon-Personalcenter-icon" style="margin: 0 3px 2px 0"></i>
        <span>个人信息</span>
      </template>
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

</style>