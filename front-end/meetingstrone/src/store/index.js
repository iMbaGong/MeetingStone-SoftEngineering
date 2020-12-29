import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        localId:window.localStorage.getItem('localId'),
        token:window.localStorage.getItem('token')
    },
    mutations: {
        login (state, loginInfo) {
            state.token = loginInfo.token
            window.localStorage.setItem('token',loginInfo.token)
            window.localStorage.setItem('localId',loginInfo.id)
        },
        logout(state){
            console.log("logout")
            state.token = ""
            state.localId=undefined
            window.localStorage.removeItem('token')
            window.localStorage.removeItem('localId')
        }
    }
})