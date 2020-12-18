import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        // user: {
        //     id: window.localStorage.getItem('user'|| '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).id
        // },
        token:window.localStorage.getItem('token')
    },
    mutations: {
        login (state, token) {
            state.token = token
            window.localStorage.setItem('token',token)
        },
        logout(state){
            console.log("logout")
            state.token = ""
            window.localStorage.removeItem('token')
        }
    }
})