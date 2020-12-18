import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/components/Login";
import Index from "@/components/Index";
import Home from "@/components/Home";
import LibraryIndex from "@/components/LibraryIndex";
import Register from "@/components/Register";
import Reply from "@/components/Reply";
import Groups from "@/components/Groups";
import Courses from "@/components/Courses";
import Personal from "@/components/Personal";




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
                        },
                        {
                            path: "/Courses",
                            name: "Courses",
                            component: Courses,
                        },
                        {
                            path: "/Personal",
                            name: "Personal",
                            component: Personal,
                        },
                    ]
                },
                {
                    path: '/LibraryIndex',
                    name: 'LibraryIndex',
                    component: LibraryIndex,
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