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
width: 45px;
height: 45px;
}
.icon-bg {
font-size: 2.5rem;
color: #f1f3f5;
position: absolute;
left: -5px;
top: -5px;
}
.icon-main {
font-size: 1.4rem;
color: #0d6efd;
position: relative;
top: 5px;
}
.brand-title {
font-size: 1.6rem;
font-weight: 800;
background: linear-gradient(45deg, #0d6efd, #0dcaf0);
-webkit-background-clip: text;
-webkit-text-fill-color: transparent;
margin-bottom: 0.1rem;
}
.brand-subtitle .badge {
font-size: 0.75rem;
}
</style>