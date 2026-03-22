import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        // 关键：初始化时，不仅要读 token，还要读用户信息
        id: localStorage.getItem("userId") || "",
        username: localStorage.getItem("username") || "",
        photo: localStorage.getItem("photo") || "",
        token: localStorage.getItem("token") || "",
        is_login: localStorage.getItem("token") ? true : false,
    }),
    actions: {
        async login(data) {
            try {
                const resp = await axios.post("http://localhost:8080/api/auth/login", {
                    email: data.email,
                    password: data.password,
                });

                if (resp.data.code === 200) {
                    const userInfo = resp.data.data;
                    
                    // 1. 更新内存状态
                    this.token = userInfo.token;
                    this.id = userInfo.id;
                    this.username = userInfo.username;
                    this.photo = userInfo.photo;
                    this.is_login = true;

                    // 2. 关键：把所有信息同步到硬盘 (localStorage)
                    localStorage.setItem("token", this.token);
                    localStorage.setItem("userId", this.id);
                    localStorage.setItem("username", this.username);
                    localStorage.setItem("photo", this.photo);
                    
                    return "success";
                } else {
                    return resp.data.message;
                }
            } catch (err) {
                return "服务器连接失败";
            }
        },
        logout() {
            // 退出时清空内存
            this.id = "";
            this.username = "";
            this.photo = "";
            this.token = "";
            this.is_login = false;
            
            // 同时清空硬盘
            localStorage.clear(); // 或者逐个 removeItem
        }
    }
});