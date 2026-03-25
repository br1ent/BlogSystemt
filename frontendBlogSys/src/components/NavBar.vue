﻿<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-md">
            <a class="navbar-brand">MyBlog</a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link :class="route_name === 'home' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: 'home' }">
                            主页
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name === 'article' ? 'nav-link active' : 'nav-link'"
                            :to="{ name: 'article' }">
                            文章发布
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown" ref="dropdownContainer">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false" ref="dropdownToggle"
                            @click.prevent="toggleDropdown">
                            {{ userStore.username }}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown" ref="dropdownMenu">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'userprofile', params: { id: 1 } }"
                                    @click="closeDropdown">
                                    个人中心
                                </router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item text-danger" href="#" @click="logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { computed, onMounted, onBeforeUnmount, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '../stores/user';

const router = useRouter();
const route = useRoute();
const route_name = computed(() => route.name);
const userStore = useUserStore();
const dropdownToggle = ref(null);
const dropdownMenu = ref(null);
const dropdownContainer = ref(null);

let isDropdownOpen = ref(false);

const toggleDropdown = (e) => {
    e.preventDefault();
    e.stopPropagation();
    isDropdownOpen.value = !isDropdownOpen.value;
    updateDropdownVisibility();
};

const updateDropdownVisibility = () => {
    if (dropdownMenu.value) {
        if (isDropdownOpen.value) {
            dropdownMenu.value.classList.add('show');
            dropdownToggle.value.setAttribute('aria-expanded', 'true');
        } else {
            dropdownMenu.value.classList.remove('show');
            dropdownToggle.value.setAttribute('aria-expanded', 'false');
        }
    }
};

const closeDropdown = () => {
    isDropdownOpen.value = false;
    updateDropdownVisibility();
};

const handleClickOutside = (e) => {
    if (dropdownContainer.value && !dropdownContainer.value.contains(e.target)) {
        closeDropdown();
    }
};

onMounted(() => {
    document.addEventListener('click', handleClickOutside);
});

onBeforeUnmount(() => {
    document.removeEventListener('click', handleClickOutside);
});

const logout = () => {
    closeDropdown();
    userStore.logout();
    router.push({ name: "login" });
}
</script>

<style scoped></style>