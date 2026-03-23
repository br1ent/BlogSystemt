<template>
    <div class="auth-page-wrapper">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-11 col-sm-9 col-md-7 col-lg-5 col-xl-4">
                    <div class="card shadow-sm border-0"> 
                        <div class="card-body">
                            <AuthBrand />
                            <form @submit.prevent="handleReset">
                                <div class="mb-3">
                                    <label for="userName" class="form-label">用户名</label>
                                    <input type="text" class="form-control" id="userName" v-model="userName">
                                </div>
                                <div class="mb-3">
                                    <label for="Email" class="form-label">邮箱地址</label>
                                    <input type="email" class="form-control" id="Email" v-model="email">
                                </div>
                                <div class="mb-3">
                                    <label for="Password" class="form-label">新密码</label>
                                    <input type="password" class="form-control" id="Password" v-model="password">
                                </div>
                                <div class="mb-3">
                                    <label for="ConfirmPassword" class="form-label">确认新密码</label>
                                    <input type="password" class="form-control" id="ConfirmPassword" v-model="confirmPassword">
                                </div>
                                <div v-if="errMsg" class="alert alert-danger p-2 small mt-2">{{ errMsg }}</div>
                                
                                <div>
                                    <button type="submit" class="btn btn-primary w-100 mb-2">确认重置</button>
                                </div>
                                
                                <div class="login">
                                    <button type="button" class="btn btn-outline-secondary w-100" @click="router.push({name: 'login'})">
                                    返回登录
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { useRouter } from 'vue-router';
import ContentField from '../../components/ContentField.vue';
import { ref } from 'vue';
import { useUserStore } from '../../stores/user';

const router = useRouter();
const userStore = useUserStore();
const userName = ref("");
const email = ref("");
const password = ref("");
const confirmPassword = ref("");
const errMsg = ref("");

const handleReset = async () => {
    errMsg.value = "";

    if (!userName.value || !email.value || !password.value) {
        errMsg.value = "请填写所有必填项!";
        return;
    }

    if (password.value !== confirmPassword.value) {
        errMsg.value = "两次输入的密码不一致!";
        return;
    }

    const resp = await userStore.resetPassword({
        username: userName.value,
        email: email.value,
        newPassword: password.value,
        confirmPassword: confirmPassword.value,
    });

    if (resp && resp.code === 200) {
        alert("密码重置成功!");
        router.push({name: 'login'});
    } else {
        errMsg.value = resp.msg || "重置失败！"
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