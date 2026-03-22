<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-11 col-sm-8 col-md-6 col-lg-4">
                <div class="card shadow-sm"> 
                    <div class="card-body">
                        <h4 class="card-title text-center mb-4">重置密码</h4>
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
.container {
    margin-top: 20vh;
}

div.login {
    margin-top: 10px;
}
</style>