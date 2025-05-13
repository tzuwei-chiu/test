<template>
  <div class="update-post">
    <h2>編輯貼文</h2>
    <form @submit.prevent="submitPost">
      <div>
        <label for="content">貼文內容：</label><br />
        <textarea id="content" v-model="content" required></textarea>
      </div>

      <div v-if="existingImage">
        <label>原本圖片：</label><br />
        <img
          :src="getFullImage(existingImage)"
          alt="原本圖片"
          style="max-width: 200px"
        />
      </div>

      <div>
        <label for="image">更新圖片（可選）：</label><br />
        <input
          type="file"
          id="image"
          @change="handleFileChange"
          ref="imageInput"
        />
      </div>

      <button type="submit">更新貼文</button>
      <p v-if="error" style="color: red">{{ error }}</p>
      <p v-if="success" style="color: green">{{ success }}</p>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "EditPost",
  data() {
    return {
      content: "",
      imageFile: null,
      existingImage: "",
      error: "",
      success: "",
    };
  },
  mounted() {
    const postId = this.$route.params.postId;
    this.fetchPost(postId);
  },
  methods: {
    handleFileChange(event) {
      this.imageFile = event.target.files[0];
    },
    getFullImage(path) {
      return `http://140.119.160.250:8080${path}`;
    },
    async fetchPost(postId) {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效";
          return;
        }
        const res = await axios.get(
          `http://140.119.160.250:8080/api/post/getPost/${postId}`,
          {
            headers: { sessionId },
          }
        );
        const post = res.data;
        this.content = post.content;
        this.existingImage = post.image;
      } catch (err) {
        console.error("取得貼文失敗", err);
        this.error = "取得貼文失敗，請稍後再試。";
      }
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

      const post = {
        content: this.content,
      };

      formData.append(
        "post",
        new Blob([JSON.stringify(post)], { type: "application/json" })
      );

      if (this.imageFile) {
        formData.append("image", this.imageFile);
      }

      try {
        await axios.put(
          `http://140.119.160.250:8080/api/post/updatePost/${this.$route.params.postId}`,
          formData,
          {
            headers: {
              sessionId: sessionId,
              "Content-Type": "multipart/form-data",
            },
          }
        );
        this.success = "貼文更新成功！";
        this.imageFile = null;
        this.$refs.imageInput.value = "";
      } catch (err) {
        console.error("更新貼文失敗", err);
        this.error = "更新失敗，請稍後再試。";
      }
    },
  },
};
</script>

<style scoped>
.update-post {
  max-width: 600px;
  margin: auto;
  padding: 20px;
}
textarea {
  width: 100%;
  height: 100px;
}
</style>
