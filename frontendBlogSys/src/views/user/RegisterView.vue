<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-11 col-sm-8 col-md-6 col-lg-4">
                <div class="card shadow-sm"> <div class="card-body">
                        <h4 class="card-title text-center mb-4">注册</h4>
                        <form @submit.prevent="">
                            <div class="mb-3">
                                <label for="Email" class="form-label">邮箱地址</label>
                                <input type="email" class="form-control" id="Email">
                            </div>
                            <div class="mb-3">
                                <label for="Password" class="form-label">密码</label>
                                <input type="password" class="form-control" id="Password">
                            </div>
                            <div class="mb-3">
                                <label for="ConfirmPassword" class="form-label">确认密码</label>
                                <input type="password" class="form-control" id="ConfirmPassword">
                            </div>
                            <button type="submit" class="btn btn-primary w-100">注册</button>
                            <div class="login">
                                已有账号？点击
                                <router-link :to="{name: 'login'}" 
                                class="link-offset-2 link-underline link-underline-opacity-25">
                                返回
                                </router-link>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>

</script>

<style scoped>
.container {
    margin-top: 20vh;
}

div.login {
    margin-top: 10px;
}
</style>