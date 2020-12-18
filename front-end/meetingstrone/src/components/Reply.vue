<template>
  <search-bar @onSearch="searchResult" ref="searchBar" style="margin-top: 20px"></search-bar>
</template>

<script>
import SearchBar from "@/common/SearchBar";

export default {
  name: "Reply",
  components:{
    SearchBar
  },
  mounted() {
    this.$refs.searchBar.select = "组队请求"
  }
}
</script>

<style scoped>

</style>