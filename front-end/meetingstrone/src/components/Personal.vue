<template>
    <el-form style="margin: 50px 0 0 100px;float:left">
        <el-form-item>
            修改姓名：
            <el-input type="text" placeholder="姓名" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item>
            修改密码：
            <el-input type="text" placeholder="密码" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item>
            确认密码：
            <el-input type="text" placeholder="确认密码" style="width: 300px"></el-input>
        </el-form-item>
        <el-button type="primary">确认修改</el-button>
    </el-form>
</template>

<script>
export default {
    name: "Personal"
}
</script>

<style scoped>

</style>