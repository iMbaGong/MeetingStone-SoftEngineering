<template>

  <el-container style="margin: 0 auto auto;max-width: 1100px">
      <el-aside style="width: 200px;margin-top:20px;">
        <switch></switch>
        <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
      </el-aside>
      <el-main style="margin-top: 20px">
        <router-view/>
      </el-main>
  </el-container>
</template>

<script>
import SideMenu from "@/components/SideMenu";


export default {
  name: 'Index',
  components: {
    SideMenu
  },
  mounted() {
    this.$router.replace("Groups")
  },
  methods: {
    listByCategory () {
      var _this = this
      var cid = this.$refs.sideMenu.cid
      var url = 'categories/' + cid + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.$refs.booksArea.books = resp.data
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

