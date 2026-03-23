<template>
    <div class="auth-page-wrapper">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-11 col-sm-9 col-md-7 col-lg-5 col-xl-4">
                    <div class="card shadow-sm border-0"> 
                        <div class="card-body">
                            <AuthBrand />
                            <form @submit.prevent="register">
                                <div class="mb-3">
                                    <label for="userName" class="form-label fw-bold small">用户名</label>
                                    <input type="text" class="form-control" id="userName" v-model="userName">
                                </div>
                                <div class="mb-3">
                                    <label for="Email" class="form-label fw-bold small">邮箱地址</label>
                                    <input type="email" class="form-control" id="Email" v-model="email">
                                </div>
                                <div class="mb-3">
                                    <label for="Password" class="form-label fw-bold small">密码</label>
                                    <input type="password" class="form-control" id="Password" v-model="password">
                                </div>
                                <div class="mb-3">
                                    <label for="ConfirmPassword" class="form-label fw-bold small">确认密码</label>
                                    <input type="password" class="form-control" id="ConfirmPassword" v-model="confirmedPassword">
                                </div>
                                <div v-if="errMsg" class="alert alert-danger p-2 small mt-2">{{ errMsg }}</div>
                                <button type="submit" class="btn btn-primary w-100">注册</button>
                            </form>
                            <div class="login mt-3" style="text-align: center">
                                已有账号？点击
                                <router-link :to="{name: 'login'}" 
                                class="link-offset-2 link-underline link-underline-opacity-25">
                                返回
                                </router-link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>
import { ref } from 'vue';
import { useUserStore } from '../../stores/user';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter()

const userName = ref("");
const email = ref("");
const password = ref("");
const confirmedPassword = ref("");
const errMsg = ref("");

const register = async () => {
    errMsg.value = "";

    if (!userName.value || !email.value || !password.value) {
        errMsg.value = "请填写所有必填项!";
        return;
    }

    if (password.value !== confirmedPassword.value) {
        errMsg.value = "两次输入的密码不一致!";
        return;
    }
    
    const resp = await userStore.register({
        userName: userName.value,
        email: email.value,
        password: password.value,
        confirmedPassword: confirmedPassword.value
    });

    if (resp && resp.code === 200) {
        alert("注册成功，请先登录!");
        router.push({name: "login"});
    } else {
        errMsg.value = resp || "请求失败";
    }
};

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