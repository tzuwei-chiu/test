<template>
  <div class="loginPage">
    <h2>登入</h2>
    <form @submit.prevent="login">
      <div>
        <label for="phoneNum">手機號碼：</label>
        <input type="text" id="phoneNum" v-model="phoneNum" required />
      </div>
      <div>
        <label for="pass">密碼：</label>
        <input type="password" id="pass" v-model="pass" required />
      </div>
      <button type="submit">登入</button>
      <button @click="goToRegisterPage">註冊</button>
    </form>
    <p v-if="error">{{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginPage",
  data() {
    return {
      phoneNum: "",
      pass: "",
      error: "",
    };
  },
  methods: {
    goToRegisterPage() {
      this.$router.push({ name: "RegisterPage" });
    },
    async login() {
      try {
        const response = await axios.post(
          "http://140.119.160.250:8080/api/user/login",
          {
            phoneNum: this.phoneNum,
            pass: this.pass,
          },
          {
            withCredentials: true,
          }
        );
        localStorage.setItem("sessionId", response.data.sessionId);
        this.$router.push({ name: "Home" });
      } catch (err) {
        console.error(err);
        this.error = "登入失敗，請確認帳號密碼";
      }
    },
  },
};
</script>
