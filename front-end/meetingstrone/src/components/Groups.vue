<template>
    <div style="height: 100%;display: flex;flex-direction: column">
      <el-row>
        <el-radio-group v-model="display" size="mini" style="float: right;">
          <el-radio-button label="卡片" icon="el-icon-search"></el-radio-button>
          <el-radio-button label="列表"></el-radio-button>
        </el-radio-group>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12" style="margin-bottom: 20px" v-for="item in groups" :key="item.id">
          <el-card :body-style="{ padding: '0px' }" class="group" shadow="hover">
              <div style="height: 100px;background-color:#409EFF">
                <h1 class="name">{{item.name}}</h1>
              </div>
              <div class="leader">
                 组长：{{item.leader.username}}
              </div>
              <div class="info">
                简介：{{item.info}}
              </div>
          </el-card>
        </el-col>
      </el-row>
        <div style="flex-grow: 1;"></div>
            <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="pagesize"
                    :total="groups.length"
                    >
            </el-pagination>

    </div>
</template>

<script>
export default {
  name: "Groups",
  components: {},
  data(){
    return{
      display:"卡片",
      currentPage:1,
      pagesize:1,
      groups:[]
    }
  },
  mounted() {
    this.loadGroups()
  },
  methods:{
    loadGroups(){
      var _this = this;
      _this.$axios.get('/joinedGroups').then(resp => {
        console.log(resp.data);
        if (resp && resp.status === 200) {
          _this.groups = resp.data.result
        }
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
      console.log(this.currentPage)
    },
    searchResult () {
      var _this = this;
      this.$axios
          .get('/searchGroup?keywords=' + this.$refs.searchBar.keywords, {
          }).then(resp => {
        if (resp && resp.status === 200) {
          _this.groups = resp.data.result
        }
      })
    },

  }
}
</script>

<style scoped>
.group {
  height: 200px;
  text-align:left;
  border-radius: 20px;
}
.name{
  padding:60px 0 0 10px;
  margin-top: 0;
  color:white;
}
.leader{
  padding: 10px;
}
.info{
  padding:0 10px 5px;
  font-size: 15px;
}


</style>