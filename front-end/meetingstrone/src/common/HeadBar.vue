<template>
    <div style="background-color: white;width: 100%">
        <div @click="fold" style="float: left">
            <i class="el-icon-s-fold" v-if="!isCollapse" style="font-size: 30px"></i>
            <i class="el-icon-s-unfold" v-if="isCollapse" style="font-size: 30px"></i>
        </div>
    </div>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    export default {
        name: "HeadBar",
        data() {
            return {
                isCollapse: false,
            }
        },
        methods: {
            fold(){
                this.isCollapse = !this.isCollapse;
                this.collapse(this.isCollapse)
            },
            ...mapMutations(['collapse'])

        }
    }
</script>

<style scoped>

</style>