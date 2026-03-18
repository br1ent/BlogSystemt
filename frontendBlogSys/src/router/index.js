import { createRouter, createWebHistory } from 'vue-router'


const ArticleView = () => import('../views/ArticleView.vue')
const HomeView = () => import('../views/HomeView.vue')
const UserProfileView = () => import('../views/UserProfileView.vue')
const NotFoundView = () => import('../views/NotFoundView.vue')


// 配置路由映射表
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/article',
    name: 'article',
    component: ArticleView,
  },
  {
    path: '/userprofile/:id',
    name: 'userprofile',
    component: UserProfileView,
  },
  {
    path: '/404',
    name: '404',
    component: NotFoundView,
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

export default router