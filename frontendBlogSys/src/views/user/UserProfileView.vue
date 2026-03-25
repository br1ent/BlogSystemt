<template>
  <div class="container-fluid bg-light min-vh-100 p-0"> <div class="container py-5"> 
    <div class="row">
        <div class="col-lg-3 col-md-4 mb-4">
          <div class="card border-0 shadow-sm rounded-3 overflow-hidden text-center sticky-top" style="top: 20px;">
            <div class="card-header bg-white border-bottom-0 pt-4 pb-0">
               <div class="avatar-wrapper position-relative d-inline-block">
                 <img :src="userStore.photo" alt="用户头像" class="img-fluid rounded-circle border border-4 border-white shadow" style="width: 100px; height: 100px; object-fit: cover;">
               </div>
            </div>
            <div class="card-body">
              <h5 class="card-title fw-bold text-dark">{{ userStore.username }}</h5>
              <p class="card-text text-muted small">共发布 {{ totalArticles }} 篇文章</p>
              <button class="btn btn-outline-primary btn-sm rounded-pill px-3">修改资料</button>
            </div>
          </div>
        </div>
        
        <div class="col-lg-9 col-md-8">
          <div class="card border-0 shadow-sm rounded-3 mb-4">
            <div class="card-body p-3">
              <div class="input-group">
                <input 
                  type="text" 
                  class="form-control form-control-lg border-0 bg-light" 
                  placeholder="搜索文章标题或描述..." 
                  v-model="keyWord"
                  @keyup.enter="articleList(1)"
                >
                <button class="btn btn-primary btn-lg px-4 rounded-3" @click="articleList(1)">
                  <i class="bi bi-search me-1">搜索</i> 
                </button>
              </div>
            </div>
          </div>

          <div v-if="searchKeyword" class="mb-3 ps-1">
            <span class="text-muted">关于 "
              <strong class="text-primary">{{ searchKeyword }}
              </strong>" 的搜索结果，共 {{ totalArticles }} 条
            </span>
            <button class="btn btn-link btn-sm p-0 ms-2 text-decoration-none" 
            @click="clearSearch">清除搜索
            </button>
          </div>

          <div v-if="articles.length > 0">
            <div 
              class="card article-card border-0 shadow-sm rounded-3 mb-4 transition-all" 
              v-for="article in articles" 
              :key="article.id"
            >
              <div class="card-body p-4">
                  <div class="d-flex justify-content-between align-items-center mb-2">
                      <span class="text-muted small">
                          <i class="bi bi-calendar3 me-1"></i> 发布于 {{ article.createTime }}
                      </span>
                  </div>
                
                <h3 class="card-title h5 fw-bold text-dark mb-2" v-html="highlight(article.title)"></h3>
                
                <p class="card-text text-secondary mb-3 text-truncate-2" 
                v-html="highlight(article.description)"></p>
                
                <div class="d-flex justify-content-between align-items-center mt-3 pt-3 border-top border-light">
                   <router-link :to="{ path: '/articledetail', query: { id: article.id } }"
                      class="btn btn-link p-0 text-decoration-none fw-bold text-primary">
                      阅读全文 <i class="bi bi-arrow-right"></i>
                   </router-link>
                    <button type="button" 
                    class="btn btn-danger" 
                    @click="prepareDel(article.id)"
                    >
                    删除
                    </button>
                </div>
              </div>
            </div>

            <nav aria-label="Page navigation" class="mt-5 mb-3" v-if="maxPages > 1">
              <ul class="pagination pagination-md justify-content-center">
                <li class="page-item" :class="currentPage <= 1 ? 'disabled' : ''">
                  <a class="page-link shadow-sm rounded-circle me-1" href="#" @click.prevent="clickPage(currentPage - 1)">
                    <i class="bi bi-chevron-left"> 上一页</i>
                  </a>
                </li>

                <li 
                  v-for="page in pages" 
                  :key="page" 
                  class="page-item mx-1" 
                  :class="page === currentPage ? 'active' : ''"
                >
                  <a class="page-link shadow-sm rounded-3" href="#" @click.prevent="clickPage(page)">
                    {{ page }}
                  </a>
                </li>

                <li class="page-item" :class="currentPage >= maxPages ? 'disabled' : ''">
                  <a class="page-link shadow-sm rounded-circle ms-1" href="#" @click.prevent="clickPage(currentPage + 1)">
                    下一页 <i class="bi bi-chevron-right"></i>
                  </a>
                </li>
              </ul>
            </nav>
          </div>
          
          <div v-else class="card border-0 shadow-sm rounded-3 my-5 py-5 text-center">
            <div class="card-body text-muted">
                <i class="bi bi-inbox fs-1 d-block mb-3"></i>
                <span v-if="searchKeyword">没有找到匹配的文章，换个关键词试试？</span>
                <span v-else>你还没有发布过文章哦。</span>
            </div>
          </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title fw-bold text-danger">确认删除</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                该操作不可逆，你确定要删除该文章吗？
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-danger" @click="deleteArticle">
                  确认
                </button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                  取消
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { useUserStore } from '../../stores/user';
import { onMounted, ref, computed } from 'vue';
import { Modal } from 'bootstrap'

const userStore = useUserStore();
const articles = ref([]);
const keyWord = ref("");
const searchKeyword = ref("");

// 分页逻辑变量
const currentPage = ref(1);    // 当前页
const totalArticles = ref(0);  // 总条数
const pageSize = 4;           // 每页数量

// 计算总页数
const maxPages = computed(() => Math.ceil(totalArticles.value / pageSize));

let deleteModal = null; // Modal 实例
const tempArticleId = ref(null); // 记录准备删除的文章 ID
 
// 计算显示的页码数组
const pages = computed(() => {
  let p = [];
  for (let i = 1; i <= maxPages.value; i ++) {
    p.push(i);
  }
  return p;
});

const prepareDel = (id) => {
  tempArticleId.value = id;
  if (deleteModal) {
    deleteModal.show();
  }
}

const deleteArticle = async () => {
    const id = tempArticleId.value;
    if (!id) return;

    try {
        const resp = await axios({
            url: `http://127.0.0.1:8080/api/article/delete/${id}`,
            method: "POST",
            headers: {
                Authorization: "Bearer " + userStore.token
            }
        });

        if (resp.data.code === 200) {
            if (deleteModal) {
                deleteModal.hide();
                setTimeout(() => {
                    const backdrop = document.querySelector('.modal-backdrop');
                    if (backdrop) {
                        backdrop.remove();
                    }
                    document.body.classList.remove('modal-open');
                }, 300);
            }

            const isLastOne = articles.value.length === 1 && currentPage.value > 1;
            const pageToRequest = isLastOne ? currentPage.value - 1 : currentPage.value;
            
            await articleList(pageToRequest); 
        } else {
            alert(resp.data.msg || "删除失败！");
        }
    } catch(err) {
        console.error("删除请求失败:", err);
        alert("网络异常，请稍后再试");
    } finally {
        tempArticleId.value = null;
    }
};

const articleList = async (page) => {
  // 边界处理
  if (page < 1 || (maxPages.value > 0 && page > maxPages.value)) return;
  
  // 更新当前生效的搜索关键词
  searchKeyword.value = keyWord.value.trim();

  try {
    const resp = await axios({
      url: "http://127.0.0.1:8080/api/user/user_article",
      method: "GET",
      params: {
        page: page,
        size: pageSize,
        keyword: searchKeyword.value
      },
      headers: {
        Authorization: "Bearer " + userStore.token
      }
    });

    if (resp.data.code === 200) {
      articles.value = resp.data.data.records;
      totalArticles.value = resp.data.data.total;
      currentPage.value = page;
    }
  } catch(err) {
      console.error("获取文章失败!", err);
  }
}

const clickPage = (page) => {
  articleList(page);
}

const clearSearch = () => {
    keyWord.value = "";
    articleList(1);
}

// 关键词高亮函数
const highlight = (text) => {
  if (!text) return '';
  if (!searchKeyword.value) return text;

  // 使用正则表达式进行全量替换，不区分大小写
  const escapedKeyword = searchKeyword.value.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
  const regex = new RegExp(escapedKeyword, 'gi');

  // 将匹配到的内容替换为带有 mark 样式的 span
  return text.replace(regex, `<span class="highlight-mark">$&</span>`);
};

onMounted(() => {
  articleList(1);
  deleteModal = new Modal(document.getElementById('deleteConfirmModal'))
});
</script>

<style scoped>
.transition-all {
    transition: all 0.3s ease-in-out;
}

.article-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 1rem 3rem rgba(0,0,0,.1) !important;
    cursor: pointer;
}

:deep(.highlight-mark) {
    background-color: transparent;
    color: #0d6efd;
    font-weight: bold;
    padding: 0 2px;
    border-radius: 2px;
}

/* 3. 文本截断（显示2行） */
.text-truncate-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;  
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
}

/* 4. 分页控件样式自定义 */
.page-link {
    color: #6c757d;
    border: none;
    background-color: transparent;
}
.page-item.active .page-link {
    background-color: #0d6efd;
    color: white;
}
.page-item.disabled .page-link {
    background-color: transparent;
    color: #dee2e6;
}
</style>