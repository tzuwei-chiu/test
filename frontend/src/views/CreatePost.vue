<template>
  <div class="create-post">
    <h2>建立貼文</h2>
    <form @submit.prevent="submitPost">
      <div>
        <label for="content">貼文內容：</label><br />
        <textarea id="content" v-model="content" required></textarea>
      </div>
      <div>
        <label for="image">上傳圖片：</label><br />
        <input
          type="file"
          id="image"
          @change="handleFileChange"
          ref="imageInput"
        />
      </div>
      <button type="submit">送出貼文</button>
      <p v-if="error" style="color: red">{{ error }}</p>
      <p v-if="success" style="color: green">{{ success }}</p>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CreatePost",
  data() {
    return {
      content: "",
      imageFile: null,
      error: "",
      success: "",
    };
  },
  methods: {
    handleFileChange(event) {
      this.imageFile = event.target.files[0];
    },
    async submitPost() {
      this.error = "";
      this.success = "";

      const sessionId = localStorage.getItem("sessionId");
      if (!sessionId) {
        this.error = "未登入或 session 已過期。";
        return;
      }

      const formData = new FormData();

      // 把貼文內容包成 JSON 字串
      const post = {
        content: this.content,
      };
      formData.append(
        "post",
        new Blob([JSON.stringify(post)], { type: "application/json" })
      );

      // 圖片檔案（如果有）
      if (this.imageFile) {
        formData.append("image", this.imageFile);
      }

      try {
        await axios.post(
          "http://140.119.160.250:8080/api/post/newPost",
          formData,
          {
            headers: {
              sessionId: sessionId,
              "Content-Type": "multipart/form-data",
            },
          }
        );
        this.success = "貼文成功！";
        this.content = "";
        this.imageFile = null;
        this.$refs.imageInput.value = "";
      } catch (err) {
        console.error(err);
        this.error = "貼文失敗，請稍後再試。";
      }
    },
  },
};
</script>

<style scoped>
.create-post {
  max-width: 600px;
  margin: auto;
  padding: 20px;
}
textarea {
  width: 100%;
  height: 100px;
}
</style>
