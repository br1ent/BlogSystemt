<template>
    <div class="auth-page-wrapper">
      <div class="container">
        <div class="row justify-content-center m-0">
          <div class="col-11 col-sm-9 col-md-7 col-lg-5 col-xl-4">
            <div class="card shadow-lg border-0"> 
              <div class="card-body p-4">
                <AuthBrand />
                
                <form @submit.prevent="login">
                  <div class="mb-3">
                    <label class="form-label fw-bold small">邮箱地址</label>
                    <input type="email" class="form-control" v-model="email">
                  </div>
                  <div class="mb-3">
                    <label class="form-label fw-bold small">密码</label>
                    <input type="password" class="form-control" v-model="password">
                  </div>
                  <div v-if="err_msg" class="text-danger small mb-2">{{ err_msg }}</div>
                  <button type="submit" class="btn btn-primary w-100 py-2 mt-2">登录</button>
                </form>
                
                <div class="mt-4 text-center">
                   <router-link :to="{name: 'register'}" class="small text-decoration-none">注册账号</router-link>
                   <span class="mx-2 text-muted">|</span>
                   <router-link :to="{name: 'forgetpwd'}" class="small text-decoration-none text-muted">忘记密码</router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>


<script setup>
import router from '../../router';
import { useUserStore } from '../../stores/user';
import { ref } from 'vue';
import AuthBrand from '../../components/AuthBrand.vue';

const userStore = useUserStore();
const email = ref("");
const password = ref("");
const err_msg = ref("");

const login = async () => {
    err_msg.value = '';
    const resp = await userStore.login({
        email: email.value,
        password: password.value,
    });

    if (resp === "success") {
        router.push({name: "home"});
    } else {
        err_msg.value = resp;
    }
}


</script>

<style scoped>
.auth-page-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  width: 100%;
  background-color: #f0f2f5;
}

.card {
  border-radius: 1rem;
}
</style>