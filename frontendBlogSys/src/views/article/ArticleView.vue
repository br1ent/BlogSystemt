<template>
  <div class="container-fluid p-0 immersive-wrapper">
    <div class="d-flex justify-content-between align-items-center p-2 px-4 bg-white border-bottom shadow-sm draft-header">
      <div class="d-flex align-items-center gap-2">
        <i class="bi bi-pencil-square text-primary fs-5"></i>
        <h5 class="mb-0 fw-bold text-secondary">开始撰写你的文章吧！</h5>
      </div>
      
      <div class="d-flex align-items-center gap-3">
        <span class="text-muted small">
          <i class="bi bi-cloud-check-fill me-1 text-success" v-if="saveStatus.includes('保存')"></i>
          {{ saveStatus }}
        </span>
        <button @click="manualSave" class="btn btn-outline-primary btn-sm px-3">
          保存草稿
        </button>
        <button @click="publishArticle" class="btn btn-success btn-sm px-4">
          发布
        </button>
      </div>
    </div>

    <div class="editor-container">
      <div class="meta-wrapper bg-white px-4 pt-3">
        <input 
          v-model="article.title" 
          type="text" 
          class="form-control form-control-lg border-0 mb-2 p-0 shadow-none title-input" 
          placeholder="在此输入标题..."
        >
        
        <textarea 
          v-model="article.description" 
          class="form-control border-0 p-0 shadow-none description-input" 
          rows="2" 
          placeholder="请输入文章简短描述（用于主页展示）..."
        ></textarea>
      </div>

      <MdEditor 
        v-model="article.content" 
        :toolbars-exclude="['github']"
        @onSave="manualSave"
        @onChange="handleContentChange"
        placeholder="开始你的创作..."
        class="immersive-editor"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

const article = reactive({
  title: '',
  description: '',
  content: '', 
});

const saveStatus = ref('就绪');
let autoSaveTimer = null;
const DRAFT_KEY = 'article_simple_draft_v2';

onMounted(() => {
  const saved = localStorage.getItem(DRAFT_KEY);
  if (saved) {
    try {
      const parsed = JSON.parse(saved);
      Object.assign(article, parsed);
      saveStatus.value = '草稿已恢复';
    } catch (e) {
      localStorage.removeItem(DRAFT_KEY);
    }
  }
});

onUnmounted(() => {
  if (autoSaveTimer) clearTimeout(autoSaveTimer);
});

const saveToLocal = (type = '自动') => {
  if (!article.title && !article.content && !article.description) return; 
  localStorage.setItem(DRAFT_KEY, JSON.stringify(article));
  const now = new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' });
  saveStatus.value = `${type}保存 ${now}`;
};

const manualSave = () => {
  saveToLocal('手动');
};

const handleContentChange = () => {
  saveStatus.value = '正在输入...';
  clearTimeout(autoSaveTimer);
  autoSaveTimer = setTimeout(() => {
    saveToLocal('自动');
  }, 2500);
};

const publishArticle = () => {
  if (!article.title || !article.content) {
    alert("标题和内容不能为空");
    return;
  }
  console.log("最终提交数据：", article);
  alert("发布成功！");
  localStorage.removeItem(DRAFT_KEY); 
};
</script>

<style>
/* 全局覆盖编辑器样式 */
.immersive-editor {
  height: calc(100vh - 202px) !important; 
  border: none !important;
}

.immersive-editor .cm-content, 
.immersive-editor .cm-line,
.immersive-editor .md-editor-preview-wrapper,
.immersive-editor .md-editor-preview {
  font-size: 18px !important;
  line-height: 1.8 !important;
}

.md-editor {
  --md-border-color: transparent;
}
</style>

<style scoped>
.immersive-wrapper {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  overflow: hidden;
}

.draft-header {
  height: 57px;
  z-index: 10;
}

.meta-wrapper {
  border-bottom: 1px solid #f0f0f0;
}

.title-input {
  font-size: 2rem;
  font-weight: 700;
  color: #222;
}

/* 描述输入框样式 */
.description-input {
  font-size: 1.1rem;
  color: #6c757d;
  resize: none;
  line-height: 1.5;
  transition: color 0.2s;
}

.description-input:focus {
  color: #444;
}

.gap-3 { gap: 1rem; }
</style>