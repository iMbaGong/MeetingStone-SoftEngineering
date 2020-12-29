import Vue from 'vue'
import App from './App.vue'
import router from "./router"
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from "./store"
import './assets/icons/iconfont.css'
import moment from 'moment'

var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'//http://47.94.34.136:8443/api
//axios.defaults.withCredentials = true
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据

axios.interceptors.request.use(config=>{
    let token = localStorage.getItem('token')
    if (token) {
        config.headers['token'] = token;
    }
    return config;
})
Vue.prototype.$axios = axios
Vue.prototype.$moment = moment
Vue.config.productionTip = false

Vue.use(ElementUI)




router.beforeEach((to, from, next) => {
    console.log("route["+"to:"+to.name+"  from:"+from.name+"]")
      if (to.meta.requireAuth) {
        if (store.state.token) {
            console.log("token found!")
            axios.get('/authentication').then(resp => {
                console.log(resp)
                if (resp.data.code === 200){
                    console.log("auth!")
                    next()
                }else {
                    console.log("expired!")
                    next({
                        path: 'login',
                        query: {redirect: to.fullPath}
                    })
                }
            })
        } else {
          next({
            path: 'login',
            query: {redirect: to.fullPath}
          })
        }
      } else {
        next()
      }
    }
)

new Vue({
  render: h => h(App),
  router,//使用路由配置
  store,
 //使用 Vuex 进行状态管理
}).$mount('#app')
