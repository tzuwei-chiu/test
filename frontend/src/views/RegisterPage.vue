<template>
  <div class="register-container">
    <div class="register-box">
      <h2>註冊</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label>使用者名稱</label>
          <input v-model="user.userName" type="text" required />
        </div>

        <div class="form-group">
          <label>Email</label>
          <input v-model="user.email" type="email" required />
        </div>

        <div class="form-group">
          <label>手機號碼</label>
          <input v-model="user.phoneNum" type="text" required />
        </div>

        <div class="form-group">
          <label>密碼</label>
          <input v-model="user.pass" type="password" required />
        </div>

        <div class="form-group">
          <label>個人封面圖片</label>
          <input type="file" @change="handleFileChange" />
        </div>

        <div class="form-group">
          <label>自我介紹</label>
          <textarea v-model="user.biography" rows="3"></textarea>
        </div>

        <div class="button-group">
          <button type="submit" class="primary">註冊</button>
        </div>

        <p class="error" v-if="error">{{ error }}</p>
        <p class="success" v-if="success">{{ success }}</p>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "RegisterPage",
  data() {
    return {
      user: {
        userName: "",
        email: "",
        phoneNum: "",
        pass: "",
        coverImage: "",
        biography: "",
      },
      error: "",
      success: "",
      imageFile: null,
    };
  },
  methods: {
    handleFileChange(event) {
      this.imageFile = event.target.files[0];
    },
    async submitForm() {
      this.error = "";
      this.success = "";

      const sessionId = localStorage.getItem("sessionId");
      if (!sessionId) {
        this.error = "未登入或 session 已過期。";
        return;
      }

      const formData = new FormData();
      const user = {
        userName: this.user.userName,
        email: this.user.email,
        phoneNum: this.user.phoneNum,
        pass: this.user.pass,
        biography: this.user.biography,
      };

      formData.append("user", JSON.stringify(user));

      if (this.imageFile) {
        formData.append("image", this.imageFile);
      }

      try {
        await axios.post("http://localhost:8080/api/user/register", formData, {
          headers: {
            sessionId: sessionId,
          },
        });
        this.success = "註冊成功！";
        this.$router.push({ name: "LoginPage" });
      } catch (err) {
        console.error(err);
        this.error = "註冊失敗，請稍後再試。";
      }
    },
  },
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(to right, #f4f6f9, #dbeafe);
}

.register-box {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 480px;
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #333;
}

input,
textarea {
  width: 100%;
  padding: 0.6rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  transition: border-color 0.2s;
}

input:focus,
textarea:focus {
  border-color: #409eff;
  outline: none;
}

.button-group {
  text-align: center;
  margin-top: 1.5rem;
}

button.primary {
  background-color: #409eff;
  color: white;
  padding: 0.6rem 1.4rem;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
}

button.primary:hover {
  background-color: #318ce3;
}

.error {
  color: red;
  text-align: center;
  margin-top: 1rem;
}

.success {
  color: green;
  text-align: center;
  margin-top: 1rem;
}
</style>
