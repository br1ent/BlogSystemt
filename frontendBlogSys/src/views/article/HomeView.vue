<template>
  <ContentField>
    <div class="container">
      <div class="row mb-4">
        <div class="col-12">
          <div class="card border-0 shadow-sm">
            <div class="card-header bg-white fw-bold border-bottom-0 pt-3">搜索文章</div>
            <div class="card-body">
              <div class="input-group">
                <input 
                  type="text" 
                  class="form-control" 
                  placeholder="输入关键词..." 
                  v-model="searchKeyword" 
                  @keyup.enter="onSearch"
                >
                <button class="btn btn-primary px-4" @click="onSearch">搜索</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-12">
          <div class="card mb-4 border-0 shadow-sm" v-for="article in articles" :key="article.id">
            <div class="card-body">
              <div class="text-muted small mb-2">发布于 {{ article.createTime || '获取时间失败' }}</div>
              <h3 class="card-title h4" v-html="getHighlightedText(article.title)"></h3>
              <p class="card-text text-secondary" v-html="getHighlightedText(article.description)"></p>
              <a href="#" class="btn btn-link p-0 text-decoration-none">阅读全文 →</a>
            </div>
          </div>

          <div v-if="articles.length === 0" class="text-center text-muted my-5">没有找到相关文章</div>

          <nav aria-label="Page navigation" class="mt-5" v-if="total > 0">
            <ul class="pagination justify-content-center">
              <li :class="['page-item', currentPage <= 1 ? 'disabled' : '']">
                <a class="page-link" href="#" @click.prevent="pull_page(currentPage - 1)">上一页</a>
              </li>
              
              <li 
                v-for="page in pages" 
                :key="page.number" 
                :class="['page-item', page.is_active ? 'active' : '']"
              >
                <a class="page-link" href="#" @click.prevent="pull_page(page.number)">{{ page.number }}</a>
              </li>
              
              <li :class="['page-item', currentPage * pageSize >= total ? 'disabled' : '']">
                <a class="page-link" href="#" @click.prevent="pull_page(currentPage + 1)">下一页</a>
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
import { onMounted, ref, computed } from 'vue'
import axios from 'axios'
import { useUserStore } from '../../stores/user';

const articles = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(5);
const searchKeyword = ref("");
const lastSearchKeyword = ref("");
const userStore = useUserStore();

// 1. 高亮逻辑函数
const getHighlightedText = (text) => {
  if (!text) return "";
  if (!lastSearchKeyword.value) return text;
  
  // 使用正则匹配，'gi' 表示全局且不区分大小写
  const reg = new RegExp(`(${lastSearchKeyword.value})`, 'gi');
  return text.replace(reg, '<span class="highlight">$1</span>');
}

// 2. 分页器逻辑计算 (最多显示5页)
const pages = computed(() => {
  let max_pages = Math.ceil(total.value / pageSize.value);
  let new_pages = [];
  
  // 计算起始页和结束页，确保当前页尽量在中间
  let start = Math.max(1, currentPage.value - 2);
  let end = Math.min(max_pages, start + 4);
  
  if (end - start < 4) {
    start = Math.max(1, end - 4);
  }

  for (let i = start; i <= end; i++) {
    new_pages.push({
      number: i,
      is_active: i === currentPage.value
    });
  }
  return new_pages;
});

const pull_page = page => {
  console.log("当前Token:", userStore.token);
  if (page < 1) return;
  let max_pages = Math.ceil(total.value / pageSize.value);
  if (total.value > 0 && page > max_pages) return;

  currentPage.value = page;
  axios.get("http://localhost:8080/api/article/getlist", {
      params: {
        page: page,
        size: pageSize.value,
        keyword: searchKeyword.value
  },
      headers: {
        Authorization: "Bearer " + userStore.token,
  }
  }).then(resp => {
      if (resp.data.msg === "获取成功" || resp.data.code === 200) {
        articles.value = resp.data.data.records;
        total.value = resp.data.data.total;
        lastSearchKeyword.value = searchKeyword.value;
    }
  }).catch(err => {
      console.error("请求失败:", err);
      if (err.response && err.response.status === 403) {
          console.error("权限不足或Token失效");
      }
  });
}

const onSearch = () => {
  currentPage.value = 1; 
  pull_page(1);
}

onMounted(() => {
  pull_page(currentPage.value);
});
</script>

<style scoped>
/* 高亮样式 */
:deep(.highlight) {
  color: #d93025;
  background-color: rgba(217, 48, 37, 0.1);
  font-weight: bold;
  padding: 0 2px;
  border-radius: 2px;
}

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
.page-link {
  cursor: pointer;
}
</style>