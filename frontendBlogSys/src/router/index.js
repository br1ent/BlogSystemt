import { createRouter, createWebHistory } from 'vue-router'

const HelloWorld = () => import('../views/HelloWorld.vue')

// 2. 配置路由映射表
const routes = [
  {
    path: '/',
    name: 'Home',
    component: HelloWorld
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router