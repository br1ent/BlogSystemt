import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
    state: () => ({
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
                
                    this.token = userInfo.token;
                    this.id = userInfo.id;
                    this.username = userInfo.username;
                    this.photo = userInfo.photo;
                    this.is_login = true;

                    localStorage.setItem("token", this.token);
                    localStorage.setItem("userId", this.id);
                    localStorage.setItem("username", this.username);
                    localStorage.setItem("photo", this.photo);
                    
                    return "success"
                } else {
                    return resp.data.msg || "登录失败";
                }
            } catch (err) {
                return "服务器连接失败";
            }
        },

        async register(data) {
            try {
                const resp = await axios.post("http://localhost:8080/api/auth/register", {
                    userName: data.userName,
                    email: data.email,
                    password: data.password,
                    confirmedPassword: data.confirmedPassword,
                });
                return resp.data.msg;

            } catch(err) {
                return { code: 500, msg: "网络连接异常" };
            }
        },

        async resetPassword(data) {
            try {
                const resp = await axios.post("http://localhost:8080/api/auth/forgetpwd", {
                    username: data.username,
                    email: data.email,
                    newPassword: data.newPassword,
                    confirmPassword: data.confirmPassword,
                });

                return resp.data.msg;
                
            } catch(err) {
                return { code: 500, msg: "网络连接异常" };
            }
        },

        logout() {
            this.id = "";
            this.username = "";
            this.photo = "";
            this.token = "";
            this.is_login = false;
            
            localStorage.clear();
        }
    }
});