import VueRouter from "vue-router";
import Vue from "vue";

import PostApply from "../components/User/PostApply";
import MyApply from "../components/User/MyApply";
import MyReply from "../components/User/MyReply";
import ApplyCenter from "../components/User/ApplyCenter";
import AdminIndex from "../components/Admin/AdminIndex";
import Home from "../components/User/Home";
import Index from "../components/User/Index"
import GroupIndex from "../components/User/GroupIndex";
import CourseIndex from "../components/User/CourseIndex";
import Personal from "../components/User/Personal";
import Login from "../components/User/Login";
import Register from "../components/User/Register";
import UserManage from "../components/Admin/UserManage";
import AdminOverview from "../components/Admin/AdminOverview";
import test from "../components/Admin/test";

const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => {console.log(err)})
}

Vue.use(VueRouter)



export default new VueRouter({
    mode: 'history',
    routes:[
        {
            path:"/",
            name:"Default",
            component:Index,
            redirect: '/GroupIndex',
        },
        {
            path: "/Home",
            name: "Home",
            component: Home,
            redirect: '/GroupIndex',
            children:[
                {
                    path: "/Index",
                    name: "Index",
                    component: Index,
                    redirect: '/GroupIndex',
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
                            path: "/CourseIndex",
                            name: "CourseIndex",
                            component: CourseIndex,
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
        },
        {
            path: "/AdminIndex",
            name: "AdminIndex",
            component: AdminIndex,
            meta: {
                requireAuth: true,
                requireAdmin: true
            },
        },
        {
            path: "/test",
            name: "test",
            component: test,
        }
    ]
})

export const createRouter = () => new VueRouter({
    mode: 'history',
    routes:[
        {
            path:"/",
            name:"Default",
            component:Index,
            redirect: '/GroupIndex',
        },
        {
            path: "/Home",
            name: "Home",
            component: Home,
            redirect: '/GroupIndex',
            children:[
                {
                    path: "/Index",
                    name: "Index",
                    component: Index,
                    redirect: '/GroupIndex',
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
                            path: "/CourseIndex",
                            name: "CourseIndex",
                            component: CourseIndex,
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
        },
        {
            path: "/AdminIndex",
            name: "AdminIndex",
            component: AdminIndex,
            children:[
                {
                    path: "/UserManage",
                    name: "UserManage",
                    component: UserManage
                },
                {
                    path: "/AdminOverview",
                    name: "AdminOverview",
                    component: AdminOverview
                },
            ]
        }
    ]
})