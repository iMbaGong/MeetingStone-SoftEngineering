<template>
    <el-container id="admin-body">
        <el-aside style="height: 100%;width:fit-content">
            <admin-menu @clickMenu="clickMenu"></admin-menu>
        </el-aside>
        <el-container>
            <el-header style="z-index: 1;margin-left: -21px;">
                <Header style="position: absolute;width: 95%"></Header>
            </el-header>
            <el-main style="padding: 0;margin-top: -9px;margin-left: -1px">
                <el-tabs v-model="editableTabsValue" type="border-card" @tab-remove="removeTab" style="height: 99%">
                    <el-tab-pane name="menu:0" label="首页">
                        <admin-overview/>
                    </el-tab-pane>
                    <el-tab-pane
                            :key="item.name"
                            v-for="item in editableTabs"
                            :label="item.title"
                            :name="item.name"
                            closable
                    >
                        <component v-bind:is="item.component"></component>
                    </el-tab-pane>
                </el-tabs>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import AdminMenu from './AdminMenu'
    import Header from './Header'
    import AdminOverview from "./AdminOverview";
    import UserManage from "./UserManage";
    import CourseManage from "./CourseManage";
    import GroupManage from "./GroupManage";
    import ApplyManage from "./ApplyManage";
    import VerifyApply from "./VerifyApply";
    import ReplyManage from "./ReplyManage";

    export default {
        name: 'AdminIndex',
        components: {
            AdminOverview,
            AdminMenu,
            Header,
        },
        data() {
            return {
                dialogVisible: false,
                editableTabsValue: 'menu:0',
                editableTabs: [],
            }
        },
        mounted() {
            // this.$router.replace('/admin/dashboard')
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
            clickMenu(index) {
                let _this = this;
                for (let i = 0; i < _this.editableTabs.length; i++) {
                    if (_this.editableTabs[i].name === "menu:" + index) {
                        _this.editableTabsValue = "menu:" + index;
                        return;
                    }
                }

                switch (index) {
                    case 1: {
                        _this.editableTabs.push({
                            title: "成员管理",
                            name: "menu:" + index,
                            component: UserManage
                        });
                        break;
                    }
                    case 2:{
                        _this.editableTabs.push({
                            title: "课程管理",
                            name: "menu:" + index,
                            component: CourseManage
                        });
                        break;
                    }
                    case 3:{
                        _this.editableTabs.push({
                            title: "小组管理",
                            name: "menu:" + index,
                            component: GroupManage
                        });
                        break;
                    }
                    case 4:{
                        _this.editableTabs.push({
                            title: "组队请求管理",
                            name: "menu:" + index,
                            component: ApplyManage
                        });
                        break;
                    }
                    case 5:{
                        _this.editableTabs.push({
                            title: "审核请求",
                            name: "menu:" + index,
                            component: VerifyApply
                        });
                        break;
                    }
                    case 6:{
                        _this.editableTabs.push({
                            title: "响应请求",
                            name: "menu:" + index,
                            component: ReplyManage
                        });
                        break;
                    }

                }
                _this.editableTabsValue = "menu:" + index;
            }
        }
    }
</script>

<style scoped>
    #admin-body {
        height: 95vh;
    }
</style>
