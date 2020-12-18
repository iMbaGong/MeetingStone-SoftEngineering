<template>
  <div>
    <el-input
        @keyup.enter.native="searchClick"
        placeholder="输入搜索信息..."
        size="small"
        style="width: 400px;margin-right: 10px"
        v-model="keywords">
      <el-select v-model="select" slot="prepend" placeholder="请选择" style="width: 100px;">
        <el-option label="小组" value="1"></el-option>
        <el-option label="用户" value="2"></el-option>
        <el-option label="组队请求" value="3"></el-option>
      </el-select>
    </el-input>
    <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      select:"小组",
      keywords: '',
      display:"",
      users: [],
      groups:[],
      cardLoading: []
    }
  },
  methods: {
    searchClick () {
      this.$emit('onSearch')
    }
  }
}
</script>

<style scoped>
.search{
  margin-bottom: 30px;display: flex;justify-content: center;align-items: center
}

</style>

