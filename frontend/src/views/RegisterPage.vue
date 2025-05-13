<template>
  <div class="register-container">
    <h2>註冊</h2>
    <form @submit.prevent="submitForm">
      <label>
        使用者名稱：
        <input v-model="user.userName" type="text" required />
      </label>

      <label>
        Email：
        <input v-model="user.email" type="email" required />
      </label>

      <label>
        手機號碼：
        <input v-model="user.phoneNum" type="text" required />
      </label>

      <label>
        密碼：
        <input v-model="user.pass" type="password" required />
      </label>

      <label>
        個人封面圖片（可選）：
        <input type="file" @change="handleFileChange" />
      </label>

      <label>
        自我介紹（可選）：
        <textarea v-model="user.biography"></textarea>
      </label>

      <button type="submit">送出註冊</button>
    </form>

    <p v-if="message">{{ message }}</p>
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
        coverImage: "", // 新增的封面圖片屬性
        biography: "",
      },
      message: "",
      imageFile: null, // 用來存放圖片檔案
    };
  },
  methods: {
    handleFileChange(event) {
      this.imageFile = event.target.files[0]; // 儲存檔案
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
      // 這裡將 user 資料轉為 Blob 並附加到 formData
      const user = {
        userName: this.user.userName,
        email: this.user.email,
        phoneNum: this.user.phoneNum,
        pass: this.user.pass,
        biography: this.user.biography,
      };

      formData.append("user", JSON.stringify(user)); // 這行改成純文字

      if (this.imageFile) {
        formData.append("image", this.imageFile); // 上傳圖片
      }

      try {
        // 注意: 不需要手動設置 Content-Type，axios 會自動處理邊界
        await axios.post(
          "http://140.119.160.250:8080/api/user/register",
          formData, // 發送 FormData
          {
            headers: {
              sessionId: sessionId, // 如果需要的話，發送 sessionId
            },
          }
        );
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
  max-width: 400px;
  margin: 0 auto;
  padding: 1rem;
  border: 1px solid #ccc;
}
label {
  display: block;
  margin-bottom: 1rem;
}
button {
  padding: 0.5rem 1rem;
}
</style>
