<template>
    <el-container style="margin: 0 auto auto;max-width: 1100px">
        <el-aside style="width: 200px;margin-top:20px;padding: 5px;">
            <switch></switch>
            <SideMenu @indexSelect="listByCategory" ref="sideMenu" class="shadow"></SideMenu>
        </el-aside>
        <el-main class="shadow main">
            <router-view/>
        </el-main>
    </el-container>
</template>

<script>
import SideMenu from "@/components/SideMenu";


export default {
    name: 'Index',
    components: {
        SideMenu
    },
    mounted() {
        //this.$router.replace("GroupIndex")
    },
    methods: {
        listByCategory() {
            var _this = this
            var cid = this.$refs.sideMenu.cid
            var url = 'categories/' + cid + '/books'
            this.$axios.get(url).then(resp => {
                if (resp && resp.status === 200) {
                    _this.$refs.booksArea.books = resp.data
                }
            })
        }
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

