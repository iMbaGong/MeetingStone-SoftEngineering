<template>
  <div style="height: 100%;display: flex;flex-direction: column">
    <el-row>
      <el-radio-group v-model="display" size="mini" style="float: right;">
        <el-radio-button label="卡片" icon="el-icon-search"></el-radio-button>
        <el-radio-button label="列表"></el-radio-button>
      </el-radio-group>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8" style="margin-bottom: 20px" v-for="item in courses" :key="item.id">
        <el-card :body-style="{ padding: '0px' }" class="courses" shadow="hover">
          <div style="height: 150px;background-color:#72767b">
            <h1 class="name">{{item.name}}</h1>
          </div>
          <div class="teacher">
            老师：{{item.teacher.username}}
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div style="flex-grow: 1;"></div>
    <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pagesize"
            :total="courses.length"
    >
    </el-pagination>
  </div>

</template>

<script>
export default {
  name: "Courses",
  data(){
    return{
      courses:[],
      currentPage:1,
      pagesize:1,
      display:"卡片"
    }
  },
  mounted() {
    this.loadCourses()
  },
  methods:{
    loadCourses(){
      var _this = this;
      _this.$axios.get('/joinedCourses').then(resp => {
        console.log(resp.data.result);
        if (resp && resp.status === 200) {
          _this.courses = resp.data.result
        }
      });
    },
  }
}
</script>

<style scoped>
.courses {
  height: 200px;
  text-align:left;
  border-radius: 20px;
}
.name{
  padding:60px 0 0 10px;
  margin-top: 0;
  color:white;
}
.teacher{
  padding: 10px;
}
</style>