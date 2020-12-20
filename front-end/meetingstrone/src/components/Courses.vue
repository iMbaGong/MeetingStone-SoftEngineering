<template>
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
</template>

<script>
export default {
  name: "Courses",
  data(){
    return{
      courses:[]
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