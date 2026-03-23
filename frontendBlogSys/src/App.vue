<template>
  <div id="app">
    <NavBar v-if="userStore.is_login"></NavBar>
    <router-view/>
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
import { useUserStore } from './stores/user';

const userStore = useUserStore();
</script>
