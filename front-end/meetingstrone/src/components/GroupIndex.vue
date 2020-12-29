<template>
    <div>
        <el-tabs v-model="editableTabsValue" type="card" @tab-remove="removeTab">
            <el-tab-pane name="index" label="我的小组">
                <Groups @getGroupInfo="getGroupInfo"/>
            </el-tab-pane>
            <el-tab-pane
                :key="item.name"
                v-for="item in editableTabs"
                :label="item.title"
                :name="item.name"
                closable
            >
                <GroupInfo :group="item.group"></GroupInfo>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import Groups from "@/components/Groups";
import GroupInfo from "@/components/GroupInfo";

export default {
    name: "GroupIndex",
    components: {
        Groups,
        GroupInfo
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
        getGroupInfo(group) {
            let _this = this;
            _this.editableTabs.push({
                title: group.name,
                name: group.id + '',
                group: group
            });
            _this.editableTabsValue = group.id + '';
        }
    }
}
</script>

<style scoped>

</style>