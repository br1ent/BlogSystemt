<template>
  <div class="container-fluid p-0 immersive-wrapper">
    <transition name="toast-fade">
      <div v-if="showToast" class="toast-container shadow-sm">
        <div class="toast-content">
          <i class="bi bi-check-circle-fill me-2"></i>
          {{ toastMessage }}
        </div>
      </div>
    </transition>

    <div class="d-flex justify-content-between align-items-center p-2 px-4 bg-white border-bottom shadow-sm draft-header">
      <div class="d-flex align-items-center gap-2">
        <i class="bi bi-pencil-square text-primary fs-5"></i>
        <h5 class="mb-0 fw-bold text-secondary">撰写文章</h5>
      </div>
      
      <div class="d-flex align-items-center gap-3">
        <span class="text-muted small">
          <i class="bi bi-cloud-check-fill me-1 text-success" v-if="saveStatus.includes('保存')"></i>
          {{ saveStatus }}
        </span>
        <button 
          @click="publishArticle" 
          :disabled="isPublishing" 
          class="btn btn-success btn-sm px-4 fw-bold shadow-sm"
        >
          <span v-if="isPublishing" class="spinner-border spinner-border-sm me-1"></span>
          {{ isPublishing ? '发布中...' : '发布' }}
        </button>
      </div>
    </div>

    <div class="editor-main-container">
      <div class="input-card border rounded mx-4 mt-3 mb-2 px-4 py-2 bg-white">
        <input 
          v-model="article.title" 
          type="text" 
          class="form-control border-0 p-0 shadow-none title-input" 
          placeholder="在此输入标题..."
        >
      </div>
      
      <div class="input-card border rounded mx-4 mb-3 px-4 py-2 bg-white">
        <textarea 
          v-model="article.description" 
          class="form-control border-0 p-0 shadow-none description-input" 
          rows="1" 
          placeholder="添加文章描述..."
          @input="autoResize"
          ref="descRef"
        ></textarea>
      </div>

      <div class="editor-flex-wrapper">
        <MdEditor 
          v-model="article.content" 
          :toolbars-exclude="['github']"
          @onSave="() => saveToLocal('手动')" 
          @onChange="handleContentChange"
          placeholder="开始你的创作..."
          class="full-height-editor"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, nextTick } from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const descRef = ref(null);
const DRAFT_KEY = 'article_simple_draft_v2';

const saveStatus = ref('就绪');
const isPublishing = ref(false);
const showToast = ref(false);
const toastMessage = ref('');
let autoSaveTimer = null;

const article = reactive({
  title: '',
  description: '',
  content: '', 
});

const triggerToast = (msg) => {
  toastMessage.value = msg;
  showToast.value = true;
  setTimeout(() => {
    showToast.value = false;
  }, 2500);
};

const autoResize = () => {
  const el = descRef.value;
  if (el) {
    el.style.height = 'auto';
    el.style.height = el.scrollHeight + 'px';
  }
};

// 发布并清空
const publishArticle = () => {
  if (!article.title?.trim() || !article.content?.trim()) {
    triggerToast("标题和内容不能为空！");
    return;
  }

  isPublishing.value = true;
  axios.post("http://localhost:8080/api/article/add", article)
  .then(resp => {
    if (resp.data.code === 200) {
      triggerToast("发布成功！文章已发布到首页");
      
      localStorage.removeItem(DRAFT_KEY);
      article.title = '';
      article.description = '';
      article.content = '';
      saveStatus.value = '就绪';
      
      nextTick(autoResize); 
      router.push({name: "home"})
    } else {
      triggerToast("发布失败：" + resp.data.msg);
    }
  })
  .catch(() => triggerToast("网络异常，请稍后再试"))
  .finally(() => isPublishing.value = false);
};

// 自动保存逻辑
const saveToLocal = (type = '自动') => {
  if (!article.title && !article.content && !article.description) return; 
  localStorage.setItem(DRAFT_KEY, JSON.stringify(article));
  const now = new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' });
  saveStatus.value = `${type}保存 ${now}`;
};

const handleContentChange = () => {
  saveStatus.value = '正在输入...';
  clearTimeout(autoSaveTimer);
  autoSaveTimer = setTimeout(() => saveToLocal('自动'), 2500);
};

onMounted(() => {
  const saved = localStorage.getItem(DRAFT_KEY);
  if (saved) {
    try {
      const parsed = JSON.parse(saved);
      Object.assign(article, parsed);
      saveStatus.value = '草稿已恢复';
      nextTick(autoResize);
    } catch (e) {
      localStorage.removeItem(DRAFT_KEY);
    }
  }
});

onUnmounted(() => {
  if (autoSaveTimer) clearTimeout(autoSaveTimer);
});
</script>

<style>
.full-height-editor {
  height: 100% !important;
  --md-font-size: 20px !important; 
}
.md-editor-content .cm-content,
.md-editor-content .cm-line,
.md-editor-preview p {
  font-size: 20px !important;
  line-height: 1.8 !important;
}
.md-editor { --md-border-color: transparent; height: 100%; }
</style>

<style scoped>
.toast-container {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
  background-color: #28a745;
  color: white;
  padding: 10px 25px;
  border-radius: 50px;
  font-weight: 500;
}

.toast-fade-enter-active, .toast-fade-leave-active {
  transition: all 0.4s ease;
}
.toast-fade-enter-from, .toast-fade-leave-to {
  opacity: 0;
  transform: translate(-50%, -20px);
}

.immersive-wrapper {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f8f9fa;
  overflow: hidden;
}

.editor-main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.editor-flex-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.input-card {
  flex-shrink: 0;
  border: 1px solid #dee2e6 !important;
  transition: all 0.2s ease-in-out;
}

.input-card:focus-within {
  border-color: #86b7fe !important;
  box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.1);
}

.title-input {
  font-size: 1.5rem;
  font-weight: 700;
}

.description-input {
  font-size: 20px;
  color: #6c757d;
  resize: none;
  min-height: 24px;
  overflow: hidden;
}
</style>