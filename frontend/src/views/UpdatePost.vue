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
        <label for="image">更新圖片：</label><br />
        <input
          type="file"
          id="image"
          @change="handleFileChange"
          ref="imageInput"
        />
        <p v-if="imageError" style="color: red">{{ imageError }}</p>
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
      imageError: "",
    };
  },
  mounted() {
    const postId = this.$route.params.postId;
    this.fetchPost(postId);
  },
  methods: {
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        const validTypes = ["image/jpeg", "image/png", "image/gif"];
        if (!validTypes.includes(file.type)) {
          this.imageError = "只支持圖片格式：JPG, PNG, GIF";
          this.imageFile = null;
        } else if (file.size > 5 * 1024 * 1024) {
          this.imageError = "圖片大小不能超過 5MB";
          this.imageFile = null;
        } else {
          this.imageError = "";
          this.imageFile = file;
        }
      }
    },
    getFullImage(path) {
      return `http://localhost:8080${path}`;
    },
    async fetchPost(postId) {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效";
          return;
        }
        const res = await axios.get(
          `http://localhost:8080/api/post/getPost/${postId}`,
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

      if (!this.content) {
        this.error = "貼文內容不可為空";
        return;
      }

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
          `http://localhost:8080/api/post/updatePost/${this.$route.params.postId}`,
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
  width: 50%;
  margin: auto;
  padding: 20px;
  background-color: #f9fafb;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

textarea {
  width: 80%;
  height: 100px;
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  margin-bottom: 1rem;
}

button {
  padding: 0.8rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  background-color: #409eff;
  color: white;
  border: none;
  margin-top: 20px;
}

button:hover {
  background-color: #318ce3;
}

p {
  margin-top: 1rem;
}

p.error {
  color: red;
}

p.success {
  color: green;
}

input[type="file"] {
  margin-top: 1rem;
  padding: 5px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

input[type="file"]:hover {
  border-color: #409eff;
}
</style>
