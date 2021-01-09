<template>
    <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            :collapse="$store.state.isCollapse"
            style="height: 100%"
    >
        <el-menu-item index="UserManage">
            <i class="el-icon-menu"></i>
            <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">课程管理</span>
        </el-menu-item>
        <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">小组管理</span>
        </el-menu-item>
        <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">请求管理</span>
        </el-menu-item>
    </el-menu>
</template>

<script>
    export default {
        name: "SideBar",
        data(){
            return{

            }
        },
        methods:{
            handleOpen(){

            },
            handleClose(){

            },
        }
    }
</script>

<style scoped>

</style>