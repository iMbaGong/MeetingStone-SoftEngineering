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
        }
    },
    mutations: {
        login (state, loginInfo) {
            state.token = loginInfo.token;
            window.localStorage.setItem('token',loginInfo.token);
            window.localStorage.setItem('id',loginInfo.id);
            window.localStorage.setItem('username',loginInfo.username);
            window.localStorage.setItem('usernum',loginInfo.usernum);
        },
        logout(state){
            console.log("logout");
            state.token = "";
            state.localId=undefined;
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('id');
            window.localStorage.removeItem('username');
            window.localStorage.removeItem('usernum')
        }
    }
})