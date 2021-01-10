import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token:window.localStorage.getItem('token'),
        user:{
            id:window.localStorage.getItem('id'),
            username:window.localStorage.getItem('username'),
            usernum:window.localStorage.getItem('usernum'),
            roles:window.localStorage.getItem('roles'),
        },
        isCollapse:false,
    },
    mutations: {
        login (state, loginInfo) {
            state.token = loginInfo.token;
            window.localStorage.setItem('token',loginInfo.token);
            window.localStorage.setItem('id',loginInfo.id);
            window.localStorage.setItem('username',loginInfo.username);
            window.localStorage.setItem('usernum',loginInfo.usernum);
            window.localStorage.setItem('roles',loginInfo.roles);
        },
        logout(state){
            console.log("logout");
            state.token = "";
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('id');
            window.localStorage.removeItem('username');
            window.localStorage.removeItem('usernum');
            window.localStorage.removeItem('roles')
        },
        collapse(state,is){
            console.log("collapse:"+is);
            state.isCollapse = is;
        }
    }
})