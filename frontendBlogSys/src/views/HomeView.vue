<template>
    <ContentField>
      <div class="container">
        <div class="row">
          <div class="col-12 col-md-8">
            <div class="card mb-4 border-0 shadow-sm" v-for="i in 3" :key="i">
              <div class="card-body">
                <div class="text-muted small mb-2">发布于 2026-03-22 · Java 后端</div>
                <h3 class="card-title h4">Spring Security + JWT + Redis 实现无状态登录</h3>
                <p class="card-text text-secondary">
                  在这篇文章中，我们将详细探讨如何利用 Spring Security 框架配合 JWT 和 Redis 缓存来构建一个安全的高并发登录系统...
                </p>
                <a href="#" class="btn btn-link p-0 text-decoration-none">阅读全文 →</a>
              </div>
            </div>
          </div>
  
          <div class="col-12 col-md-4">
            <div class="card mb-4 border-0 shadow-sm">
              <div class="card-header bg-white fw-bold">搜索文章</div>
              <div class="card-body">
                <div class="input-group">
                  <input type="text" class="form-control" placeholder="输入关键词...">
                  <button class="btn btn-primary">搜索</button>
                </div>
              </div>
            </div>
  
            <div class="card border-0 shadow-sm">
              <div class="card-header bg-white fw-bold">关于博主</div>
              <div class="card-body text-center">
                <img src="https://api.dicebear.com/7.x/avataaars/svg?seed=Felix" 
                     class="rounded-circle mb-3" width="80" alt="avatar">
                <h5 class="card-title">Brent</h5>
                <p class="card-text small text-muted">
                  软件工程大三学生 | Java 全栈开发爱好者<br>
                  正在练习 Spring Boot + Vue 3 项目
                </p>
                <div class="d-flex justify-content-center gap-2">
                  <button class="btn btn-outline-secondary btn-sm">GitHub</button>
                  <button class="btn btn-outline-primary btn-sm">联系我</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </ContentField>
</template>

<script setup>
import ContentField from '../components/ContentField.vue'
import { onMounted } from 'vue'

onMounted(() => {
// 这里以后可以写获取文章列表的逻辑
})
</script>

<style scoped>
/* 让卡片看起来更现代，增加一点悬停效果 */
.card {
transition: all 0.3s ease;
border-radius: 12px;
}

.card:hover {
transform: translateY(-3px);
box-shadow: 0 4px 15px rgba(0,0,0,0.1) !important;
}

.card-title {
color: #2c3e50;
font-weight: 600;
}
</style>