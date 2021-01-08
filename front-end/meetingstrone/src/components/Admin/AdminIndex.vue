<template>
    <div>
        <el-container>
            <el-aside style="width: fit-content;">
                <SideBar/>
            </el-aside>
            <el-container>
                <el-header>

                </el-header>
                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script>
    import SideBar from "@/common/SideBar";
    export default {
        name: "AdminIndex",
        components:{
            SideBar,
        }
    }
</script>

<style scoped>

</style>