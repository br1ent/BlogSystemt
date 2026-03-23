<template>
    <div class="auth-brand text-center mb-2"> 
        <div class="brand-logo mb-1"> 
            <div class="icon-stack">
          <i class="bi bi-person-badge-fill icon-bg"></i>
          <i class="bi bi-pencil-fill icon-main"></i>
        </div>
      </div>
      
      <h2 class="brand-title">MyBlog</h2>
      
      <div class="brand-subtitle">
        <span class="badge bg-light text-primary border border-primary-subtle px-2 py-1">
          个人博客系统
        </span>
      </div>
    </div>
</template>

<style scoped>
.icon-stack {
position: relative;
display: inline-block;
width: 45px;  /* 缩小宽度 */
height: 45px; /* 缩小高度 */
}
.icon-bg {
font-size: 2.5rem; /* 缩小装饰图标 */
color: #f1f3f5;
position: absolute;
left: -5px;
top: -5px;
}
.icon-main {
font-size: 1.4rem; /* 缩小主图标 */
color: #0d6efd;
position: relative;
top: 5px;
}
.brand-title {
font-size: 1.6rem; /* 减小字号，视觉更紧凑 */
font-weight: 800;
background: linear-gradient(45deg, #0d6efd, #0dcaf0);
-webkit-background-clip: text;
-webkit-text-fill-color: transparent;
margin-bottom: 0.1rem; /* 极小间距 */
}
.brand-subtitle .badge {
font-size: 0.75rem;
}
</style>