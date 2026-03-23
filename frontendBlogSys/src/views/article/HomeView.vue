<template>
  <ContentField>
    <div class="container">
      <div class="row mb-4">
        <div class="col-12">
          <div class="card border-0 shadow-sm">
            <div class="card-header bg-white fw-bold border-bottom-0 pt-3">搜索文章</div>
            <div class="card-body">
              <div class="input-group">
                <input type="text" class="form-control" placeholder="输入关键词...">
                <button class="btn btn-primary px-4">搜索</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-12">
          <div class="card mb-4 border-0 shadow-sm" v-for="i in 3" :key="i">
            <div class="card-body">
              <div class="text-muted small mb-2">发布于 2026-03-22</div>
              <h3 class="card-title h4">Spring Security + JWT + Redis 实现无状态登录</h3>
              <p class="card-text text-secondary">
                在这篇文章中，我们将详细探讨如何利用 Spring Security 框架配合 JWT 和 Redis 缓存来构建一个安全的高并发登录系统...
              </p>
              <a href="#" class="btn btn-link p-0 text-decoration-none">阅读全文 →</a>
            </div>
          </div>
          <nav aria-label="Page navigation" class="mt-5">
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                  <a class="page-link" href="#" tabindex="-1" aria-disabled="true">上一页</a>
                </li>
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item">
                  <a class="page-link" href="#">下一页</a>
                </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </ContentField>
</template>

<script setup>
import ContentField from '../../components/ContentField.vue'
import { onMounted } from 'vue'
import { useUserStore } from '../../stores/user'

const userStore = useUserStore();

</script>


<style scoped>
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