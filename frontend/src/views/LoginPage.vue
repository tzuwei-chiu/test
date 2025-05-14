<template>
  <div class="loginPage">
    <div class="loginBox">
      <h2>登入</h2>
      <form @submit.prevent="login">
        <div class="formGroup">
          <label for="phoneNum">手機號碼</label>
          <input type="text" id="phoneNum" v-model="phoneNum" required />
        </div>
        <div class="formGroup">
          <label for="pass">密碼</label>
          <input type="password" id="pass" v-model="pass" required />
        </div>
        <div class="buttonGroup">
          <button type="submit" class="primary">登入</button>
          <button type="button" class="secondary" @click="goToRegisterPage">
            註冊
          </button>
        </div>
        <p class="error" v-if="error">{{ error }}</p>
      </form>
    </div>
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
          "http://localhost:8080/api/user/login",
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

<style scoped>
.loginPage {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(to right, #f0f2f5, #d9e4f5);
}

.loginBox {
  background-color: white;
  padding: 2rem 2.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

.formGroup {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #333;
}

input {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

input:focus {
  border-color: #409eff;
  outline: none;
}

.buttonGroup {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1rem;
}

button {
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.primary {
  background-color: #409eff;
  color: white;
}

.primary:hover {
  background-color: #318ce3;
}

.secondary {
  background-color: #eee;
  color: #333;
}

.secondary:hover {
  background-color: #ddd;
}

.error {
  margin-top: 1rem;
  color: red;
  text-align: center;
}
</style>
