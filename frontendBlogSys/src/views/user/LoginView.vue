<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-11 col-sm-8 col-md-6 col-lg-4">
                <div class="card shadow-sm"> <div class="card-body">
                        <h4 class="card-title text-center mb-4">登录</h4>
                        <form @submit.prevent="">
                            <div class="mb-3">
                                <label for="Email" class="form-label">邮箱地址</label>
                                <input type="email" class="form-control" id="Email">
                            </div>
                            <div class="mb-3">
                                <label for="Password" class="form-label">密码</label>
                                <input type="password" class="form-control" id="Password">
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="Check">
                                <label class="form-check-label" for="Check">记住我</label>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">登录</button>
                            <div class="register">
                                还没有账号？点击
                                <router-link :to="{name: 'register'}" 
                                class="link-offset-2 link-underline link-underline-opacity-25">
                                注册
                                </router-link>
                                一个。
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
div.register {
    margin-top: 10px;
}
</style>