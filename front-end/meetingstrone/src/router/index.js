import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/components/Login";
import Index from "@/components/Index";
import Home from "@/components/Home";
import Register from "@/components/Register";
import Courses from "@/components/Courses";
import Personal from "@/components/Personal";
import PostApply from "../components/PostApply";
import MyApply from "../components/MyApply";
import MyReply from "../components/MyReply";
import ApplyCenter from "../components/ApplyCenter";
import GroupIndex from "@/components/GroupIndex";




Vue.use(VueRouter)



export default new VueRouter({
    mode: 'history',
    routes:[
        {
            path:"/",
            name:"Default",
            component:Index,
            redirect: '/Index',
        },
        {
            path: "/Home",
            name: "Home",
            component: Home,
            redirect: '/Index',
            children:[
                {
                    path: "/Index",
                    name: "Index",
                    component: Index,
                    meta: {
                        requireAuth: true
                    },
                    children:[
                        {
                            path: "/GroupIndex",
                            name: "GroupIndex",
                            component: GroupIndex,
                            meta: {
                                requireAuth: true
                            }
                        },
                        {
                            path: "/Courses",
                            name: "Courses",
                            component: Courses,
                            meta: {
                                requireAuth: true
                            }
                        },
                        {
                            path: "/Personal",
                            name: "Personal",
                            component: Personal,
                            meta: {
                                requireAuth: true
                            }
                        },
                        {
                            path: "/MyApply",
                            name: "MyApply",
                            component: MyApply,
                            meta: {
                                requireAuth: true
                            }
                        },
                        {
                            path: "/MyReply",
                            name: "MyReply",
                            component: MyReply,
                            meta: {
                                requireAuth: true
                            }
                        },
                    ]
                },
                {
                    path: '/PostApply',
                    name: 'PostApply',
                    component: PostApply,
                    meta: {
                        requireAuth: true
                    }
                },
                {
                    path: '/ApplyCenter',
                    name: 'ApplyCenter',
                    component: ApplyCenter,
                    meta: {
                        requireAuth: true
                    }
                }
            ]
        },
        {
            path:"/Login",
            name:"Login",
            component:Login
        },
        {
            path: "/Register",
            name: "Register",
            component: Register
        }

    ]
})