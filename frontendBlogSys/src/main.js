import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap-icons/font/bootstrap-icons.css'
import router from './router'
import { createPinia } from 'pinia'
import axios from 'axios';

const app = createApp(App)

axios.interceptors.request.use(config => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = "Bearer " + token; 
    }
    return config;
}, error => {
    return Promise.reject(error);
});

app.use(router)
app.use(createPinia())
app.mount('#app')
