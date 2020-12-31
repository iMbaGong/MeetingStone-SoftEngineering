<template>
    <div>
        <el-tabs v-model="editableTabsValue" type="card" @tab-remove="removeTab">
            <el-tab-pane name="index" label="我的课程">
                <Courses @getCourseInfo="getCourseInfo"/>
            </el-tab-pane>
            <el-tab-pane
                :key="item.name"
                v-for="item in editableTabs"
                :label="item.title"
                :name="item.name"
                closable
            >
                <CourseInfo :course="item.course"></CourseInfo>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import Courses from "@/components/Courses";
import CourseInfo from "@/components/CourseInfo";

export default {
    name: "CourseIndex",
    components:{
        Courses,
        CourseInfo
    },
    data() {
        return {
            editableTabsValue: 'index',
            editableTabs: [],
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
        }
    }
}
</script>

<style scoped>

</style>