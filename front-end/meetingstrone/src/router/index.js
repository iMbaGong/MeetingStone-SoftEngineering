import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/components/Login";
import Index from "@/components/Index";
import Home from "@/components/Home";
import Register from "@/components/Register";
import Reply from "@/components/Reply";
import Groups from "@/components/Groups";
import Courses from "@/components/Courses";
import Personal from "@/components/Personal";
import PostApply from "../components/PostApply";
import MyApply from "../components/MyApply";
import MyReply from "../components/MyReply";




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
                            path: "/Groups",
                            name: "Group",
                            component: Groups,
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
                    path: '/Reply',
                    name: 'Reply',
                    component: Reply,
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