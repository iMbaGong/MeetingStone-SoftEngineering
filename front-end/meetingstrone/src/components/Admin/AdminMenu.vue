<template>
    <el-menu
            class="el-menu-admin"
            router
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            :collapse="$store.state.isCollapse"
            style="height: 100%;"
    >
        <el-menu-item @click="clickMenu(0)">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item @click="clickMenu(1)">
            <i class="el-icon-menu"></i>
            <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item @click="clickMenu(2)">
            <i class="el-icon-menu"></i>
            <span slot="title">课程管理</span>
        </el-menu-item>
        <el-menu-item @click="clickMenu(3)">
            <i class="el-icon-document"></i>
            <span slot="title">小组管理</span>
        </el-menu-item>
        <el-submenu style="overflow-x: hidden" index="">
            <template slot="title">
                <i class="el-icon-document" style="margin: 0 0 2px 0;font-size:19px"></i>
                <span>请求管理</span>
            </template>
            <el-menu-item class="submenu" @click="clickMenu(4)" index="">
                <div style="margin-left: 20px">
                    组队请求管理
                </div>
            </el-menu-item>
            <el-menu-item class="submenu" @click="clickMenu(5) " index="">
                <div style="margin-left: 20px">
                    组队请求审核
                </div>
            </el-menu-item>
            <el-menu-item class="submenu" @click="clickMenu(6)" index="">
                <div style="margin-left: 20px">
                    响应请求管理
                </div>
            </el-menu-item>
        </el-submenu>
    </el-menu>

</template>

<script>
    export default {
        name: 'AdminMenu',
        data() {
            return {
                isCollapse: false
            }
        },
        computed: {},
        methods: {
            clickMenu(index) {
                this.$emit("clickMenu", index);
            }
        }
    }
</script>

<style scoped>
    .el-menu-admin {
        border-radius: 5px;
        height: 100%;
    }
</style>
