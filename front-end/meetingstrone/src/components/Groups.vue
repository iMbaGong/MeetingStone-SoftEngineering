<template>
    <div style="height: 100%;display: flex;flex-direction: column">
        <el-row>
            <el-button
                size="small"
                icon="el-icon-plus"
                style="float: left;margin: 15px"
                @click="dialogFormVisible = true"
            >
                创建小组
            </el-button>
            <el-radio-group v-model="display" size="mini" style="float: right;margin: 15px">
                <el-radio-button label="卡片" icon="el-icon-search"></el-radio-button>
                <el-radio-button label="列表"></el-radio-button>
            </el-radio-group>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12" style="margin-bottom: 20px" v-for="item in groups" :key="item.id">
                <el-card :body-style="{ padding: '0px' }" class="group" shadow="hover"
                         @click.native="getGroupInfo(item)">
                    <div style="height: 100px;background-color:#409EFF">
                        <h1 class="name">{{ item.name }}</h1>
                    </div>
                    <div class="leader">
                        组长：{{ item.leader.username }}
                    </div>
                    <div class="info">
                        简介：{{ item.info }}
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-dialog :visible.sync="dialogFormVisible" width="450px">
            <template slot="title">
                <h3 style="width: max-content;">创建小组</h3>
            </template>
            <el-form :model="form" :rules="rules" ref="groupForm" label-width="80px" label-position="left">
                <el-form-item label="小组名称" prop="name">
                    <el-input type="text" placeholder="请输入小组名称" v-model="form.name" maxlength="10"
                              show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="小组类型" prop="type">
                    <el-select v-model="form.type" @change="selectType" placeholder="课内/课外小组" style="width: 100%">
                        <el-option label="课内小组" value="1"></el-option>
                        <el-option label="课外小组" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-show=typeBtn label="绑定课程" prop="course">
                    <el-select
                        style="width: 100%"
                        v-model="form.course"
                        filterable
                        remote
                        reserve-keyword
                        placeholder="请输入关键词"
                        :remote-method="findCourses"
                        :loading="loading">
                        <el-option
                            v-for="item in searchList"
                            :key=item.id
                            :label="item.name"
                            :value=item>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="简介">
                    <el-input type="textarea" v-model="form.intro" maxlength="64" show-word-limit
                              style="width: 100%"></el-input>
                </el-form-item>
            </el-form>
        </el-dialog>
        <div style="flex-grow: 1;"></div>
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pagesize"
            :total="Math.ceil(this.groups.length/this.pagesize)"
        >
        </el-pagination>
    </div>
</template>

<script>
export default {
    name: "Groups",
    components: {},
    data() {
        return {
            display: "卡片",
            currentPage: 1,
            pagesize: 6,
            groups: [],
            dialogFormVisible: false,
            form: {},
            typeBtn: false,
            searchList: [],
            loading: false,
            rules: {
                name: [{required: true, message: '请输入小组名称', trigger: 'blur'}],
                type: [{required: true, message: '请选择小组类型', trigger: 'change'}],
                course: [{required: true, message: '请选择绑定的课程', trigger: 'change'}],
            }
        }
    },
    mounted() {
        this.loadGroups()
    },
    computed: {},
    methods: {
        loadGroups() {
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
        getGroupInfo(group) {
            console.log("click Group")
            this.$emit("getGroupInfo", group);
        },
        selectType(value) {
            if (value === "1") {
                console.log("select 1:" + this.form.type);
                this.typeBtn = true
            } else {
                console.log("select 2:" + this.form.type);
                this.typeBtn = false
            }

        },
        findCourses(query) {
            if (query !== '') {
                this.loading = true;
                this.searchList = [];
                var _this = this;
                _this.$axios.get('/joinedCourses').then(resp => {
                    console.log(resp.data.result);
                    if (resp && resp.status === 200) {
                        _this.searchList = resp.data.result
                    }
                });
                setTimeout(() => {
                    console.log("timeout:" + _this.searchList);
                    this.searchList = this.searchList.filter(item => {
                        return item.name
                            .indexOf(query) > -1;
                        //return item.id===query
                    });
                    this.loading = false;
                }, 1000);
            } else {
                this.searchList = [];
            }
        },
        handleReply() {

        }

    }
}
</script>

<style scoped>
.group {
    height: 200px;
    text-align: left;
    border-radius: 20px;
}

.name {
    padding: 60px 0 0 10px;
    margin-top: 0;
    color: white;
}

.leader {
    padding: 10px;
}

.info {
    padding: 0 10px 5px;
    font-size: 15px;
}


</style>