<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-md">
            <a class="navbar-brand">MyBlog</a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link :class="route_name === 'home' ? 'nav-link active' : 'nav-link'" :to="{name: 'home'}">
                            主页
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name === 'article' ? 'nav-link active' : 'nav-link'" :to="{name: 'article'}">
                            文章
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav" v-if="userStore.is_login">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            {{ userStore.username }}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <router-link class="dropdown-item" :to="{name: 'userprofile', params: {id: 1}}">
                                    个人中心
                                </router-link>
                            </li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="#" @click="logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="navbar-nav" v-else>  
                    <li class="nav-item">
                        <router-link class="nav-link" :to="{name: 'login'}">登录</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" :to="{name: 'register'}">注册</router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '../stores/user';

const router = useRouter();
const route = useRoute();
const route_name = computed(() => route.name);
const userStore = useUserStore();

const logout = () => {
    userStore.logout();
    router.push({name: "login"});
}
</script>

<style scoped>
</style>