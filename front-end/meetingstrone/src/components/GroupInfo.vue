<template>
    <div>
        <el-form label-position="left" class="demo-table-expand">
            <el-form-item>
                <template slot="label" class="label">
                    <h3>小组名称</h3>
                </template>
                <span class="content">{{ group.name }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>简介</h3>
                </template>
                <span class="content">{{ group.info }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>组长</h3>
                </template>
                <span class="content">{{ group.leader.username }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>类型</h3>
                </template>
                <span class="content">{{ groupType(group.type) }}</span>
            </el-form-item>
            <el-form-item v-if="group.course">
                <template slot="label" class="label">
                    <h3>关联课程</h3>
                </template>
                <span class="content">{{ group.course.name }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>小组成员</h3>
                </template>
                <span class="content">{{ groupMember(group.members) }}</span>
            </el-form-item>
            <el-form-item>
                <template slot="label" class="label">
                    <h3>创建日期</h3>
                </template>
                <span class="content">{{ tranDate(group.crtDate) }}</span>
            </el-form-item>
            <el-form-item>

            </el-form-item>
            <el-form-item >
                <el-button v-if="$store.state.localId===group.leader.id">管理小组</el-button>
                <el-button @click="quitGroup" type="danger">退出小组</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    name: "GroupInfo",
    props: ['group'],
    data() {
        return {
        }
    },
    computed: {
        groupType: function () {
            return function (type) {
                switch (type) {
                    case 1:
                        return "课内小组";
                    case 2:
                        return "课外小组";
                    default:
                        return ""
                }
            }
        },
        tranDate: function () {
            return function (date) {
                return this.$moment(date).format(
                    "YYYY-MM-DD HH:mm:ss"
                );
            }
        },
        groupMember: function () {
            return function (members){
                let allMembers = "";
                for (let i = 0; i < members.length; i++) {
                    if(members[i].username!==this.group.leader.username)
                    allMembers += (members[i].username + ' ')
                }
                return allMembers;
            }
        },
        isLeader: function () {
            return this.$store.state.localId===this.group.leader.id
        },
    },
    mounted() {
    },
    methods: {
        quitGroup(){

        }
    }

}
</script>

<style scoped>
h3 {
    margin: 2px 0 2px 0;
    width: 80px;
}

.content {
    float: left;
    margin: 2px 0 2px 0;
}

.demo-table-expand .el-form-item {
    margin-left: 80px;
    margin-right: 0;
    margin-bottom: 0;
    width: 65%;
}
</style>