<template>
  <ContentField>
    <div class="container">
      <div class="row">
        <div class="col-12">
          <div class="card border-0 shadow-sm">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-start mb-3">
                <div class="text-muted small">发布于 {{ article.createTime || '获取时间失败' }}</div>
              </div>
              <h1 class="card-title h2 mb-4">{{ article.title }}</h1>
              <div class="d-flex justify-content-between align-items-center mb-4">
                <div class="text-muted small">
                  <span>作者: {{ article.authorName || '未知作者' }}</span>
                </div>
              </div>
              <div class="card-text markdown-content" v-html="renderedContent"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ContentField>
</template>

<script setup>
import ContentField from '../../components/ContentField.vue'
import { onMounted, ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import { useUserStore } from '../../stores/user'
import { marked } from 'marked'

const article = ref({})
const userStore = useUserStore()
const route = useRoute()

const renderedContent = computed(() => {
  if (article.value.content) {
    return marked(article.value.content)
  }
  return ''
})

// 获取文章详情
const getArticleDetail = async (articleId) => {
  try {
    console.log('开始获取文章详情，文章ID:', articleId)
    console.log('用户Token:', userStore.token)
    const res = await axios.get(`http://localhost:8080/api/article/getdetail/${articleId}`, {
      headers: {
        Authorization: "Bearer " + userStore.token
      }
    })
    console.log('API响应:', res.data)
    if (res.data.code === 200) {
      article.value = res.data.data
      console.log('文章详情:', article.value)
    } else {
      console.error('获取文章详情失败:', res.data.message)
    }
  } catch (error) {
    console.error('获取文章详情失败:', error)
  }
}

// 获取URL参数中的文章ID
const getArticleIdFromUrl = () => {
  // 使用Vue Router的路由参数
  return route.query.id
}

onMounted(() => {
  const articleId = getArticleIdFromUrl()
  console.log('获取到的文章ID:', articleId)
  console.log('路由信息:', route.query)
  if (articleId) {
    getArticleDetail(articleId)
  } else {
    console.error('未获取到文章ID')
  }
})
</script>

<style scoped>
.card {
  transition: all 0.3s ease;
  border-radius: 12px;
}

.card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1) !important;
}

.card-title {
  color: #2c3e50;
  font-weight: 600;
}

.markdown-content {
  line-height: 1.8;
  color: #333;
}

.markdown-content h1 {
  font-size: 2rem;
  font-weight: 600;
  margin: 1.5rem 0 1rem;
  color: #2c3e50;
  border-bottom: 2px solid #e9ecef;
  padding-bottom: 0.5rem;
}

.markdown-content h2 {
  font-size: 1.75rem;
  font-weight: 600;
  margin: 1.25rem 0 0.75rem;
  color: #2c3e50;
  border-bottom: 1px solid #e9ecef;
  padding-bottom: 0.3rem;
}

.markdown-content h3 {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 1rem 0 0.5rem;
  color: #2c3e50;
}

.markdown-content h4 {
  font-size: 1.25rem;
  font-weight: 600;
  margin: 0.75rem 0 0.5rem;
  color: #2c3e50;
}

.markdown-content p {
  margin: 1rem 0;
  line-height: 1.8;
}

.markdown-content ul,
.markdown-content ol {
  margin: 1rem 0;
  padding-left: 2rem;
}

.markdown-content li {
  margin: 0.5rem 0;
  line-height: 1.6;
}

.markdown-content code {
  background-color: #f8f9fa;
  padding: 0.2rem 0.4rem;
  border-radius: 3px;
  font-family: 'Courier New', monospace;
  font-size: 0.9em;
  color: #d63384;
}

.markdown-content pre {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  padding: 1rem;
  overflow-x: auto;
  margin: 1rem 0;
}

.markdown-content pre code {
  background-color: transparent;
  padding: 0;
  border-radius: 0;
  color: #333;
}

.markdown-content blockquote {
  border-left: 4px solid #007bff;
  padding-left: 1rem;
  margin: 1rem 0;
  color: #6c757d;
  background-color: #f8f9fa;
  padding: 0.5rem 1rem;
}

.markdown-content a {
  color: #007bff;
  text-decoration: none;
}

.markdown-content a:hover {
  text-decoration: underline;
}

.markdown-content table {
  width: 100%;
  border-collapse: collapse;
  margin: 1rem 0;
}

.markdown-content th,
.markdown-content td {
  border: 1px solid #dee2e6;
  padding: 0.75rem;
  text-align: left;
}

.markdown-content th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.markdown-content img {
  max-width: 100%;
  height: auto;
  border-radius: 4px;
  margin: 1rem 0;
}
</style>