import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

const ArticleView = () => import('../views/ArticleView.vue')
const HomeView = () => import('../views/HomeView.vue')
const UserProfileView = () => import('../views/UserProfileView.vue')
const NotFoundView = () => import('../views/NotFoundView.vue')
const LoginView = () => import('../views/user/LoginView.vue')
const RegisterView = () => import ('../views/user/RegisterView.vue')
const ForgetPasswordView = () => import('../views/user/ForgetPasswordView.vue')


// 配置路由映射表
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      title: "个人博客",
      requestAuth: true,
    }
  },
  {
    path: '/article',
    name: 'article',
    component: ArticleView,
    meta: {
      title: "所有文章",
      requestAuth: true,
    }
  },
  {
    path: '/userprofile/:id',
    name: 'userprofile',
    component: UserProfileView,
    meta: {
      title: "我的资料",
      requestAuth: true,
    }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: {
      title: "登录",
      requestAuth: false,
    }
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    meta: {
      title: "注册",
      requestAuth: false,
    }
  },
  {
    path: '/forgetpwd',
    name: 'forgetpwd',
    component: ForgetPasswordView,
    meta: {
      title: "忘记密码",
      requestAuth: false,
    }
  },
  {
    path: '/404',
    name: '404',
    component: NotFoundView,
    meta: {
      title: "网页丢失",
      requestAuth: false,
    }
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
    const userStore = useUserStore();
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    
    if (to.meta.requestAuth && !userStore.is_login) {
        next({name: "login"});
    } else if (to.name === "login" && userStore.is_login) {
        next({name: "home"});
    } else {
      next();
    }
})

export default router