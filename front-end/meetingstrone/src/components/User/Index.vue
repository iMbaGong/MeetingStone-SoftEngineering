<template>
    <el-container style="margin: 0 auto auto;max-width: 1100px">
        <el-aside style="width: 200px;margin-top:20px;padding: 5px;">
            <switch></switch>
            <SideMenu class="shadow"></SideMenu>
        </el-aside>
        <el-main class="shadow main">
            <router-view/>
        </el-main>
    </el-container>
</template>

<script>
import SideMenu from "@/components/User/SideMenu";


export default {
    name: 'Index',
    components: {
        SideMenu
    },
    mounted() {
    },
    methods: {

    }
}
</script>

<style scoped>
.shadow {
    box-shadow: 0 0 5px rgba(0, 0, 0, .05);
}

.main {
    margin: 25px 5px 0 0;
    background-color: white;
    min-height: 500px;
}

</style>

