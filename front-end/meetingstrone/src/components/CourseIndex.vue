<template>
    <div>
        <el-tabs v-model="editableTabsValue" type="card" @tab-remove="removeTab">
            <el-tab-pane name="index" label="我的课程">
                <Courses @getCourseInfo="getCourseInfo" />
            </el-tab-pane>
            <el-tab-pane
                :key="item.name"
                v-for="item in editableTabs"
                :label="item.title"
                :name="item.name"
                closable
            >
                <CourseInfo :course="item.course" @getGroupInfo="getGroupInfo"></CourseInfo>
            </el-tab-pane>
            <el-tab-pane
                    :key="item.name"
                    v-for="item in editableTabs2"
                    :label="item.title"
                    :name="item.name"
                    closable
            >
                <EditCourseGroup :group="item.group"></EditCourseGroup>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import Courses from "@/components/Courses";
import CourseInfo from "@/components/CourseInfo";
import EditCourseGroup from "./EditCourseGroup";

export default {
    name: "CourseIndex",
    components:{
        EditCourseGroup,
        Courses,
        CourseInfo
    },
    data() {
        return {
            editableTabsValue: 'index',
            editableTabs: [],
            editableTabs2: [],
        }
    },
    computed: {
    },
    methods: {
        removeTab(targetName) {
            let tabs = this.editableTabs;
            let activeName = this.editableTabsValue;
            if (tabs.length === 1) {
                activeName = "index"
            } else if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }
            this.editableTabsValue = activeName;
            console.log(activeName)
            this.editableTabs = tabs.filter(tab => tab.name !== targetName);
        },
        getCourseInfo(course) {
            let _this = this;
            for (let i = 0; i < _this.editableTabs.length; i++) {
                if (_this.editableTabs[i].name===course.id+''){
                    _this.editableTabsValue = course.id + '';
                    return;
                }
            }
            _this.editableTabs.push({
                title: course.name,
                name: course.id + '',
                course: course
            });
            _this.editableTabsValue = course.id + '';
        },
        getGroupInfo(group) {
            let _this = this;
            for (let i = 0; i < _this.editableTabs2.length; i++) {
                if (_this.editableTabs2[i].name===group.name){
                    _this.editableTabsValue = group.name;
                    return;
                }
            }
            _this.editableTabs2.push({
                title: group.name,
                name: group.name,
                group: group
            });
            _this.editableTabsValue = group.name;
        }
    }
}
</script>

<style scoped>

</style>