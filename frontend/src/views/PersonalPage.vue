<template>
  <div class="personal-page">
    <h1>個人頁面</h1>

    <div class="user-info">
      <div class="info">
        <h3>個人資訊</h3>
        <p><strong>使用者名稱：</strong>{{ user.userName }}</p>
        <p><strong>Email：</strong>{{ user.email }}</p>
        <p><strong>手機號碼：</strong>{{ user.phoneNum }}</p>
      </div>
      <div class="cover-image" v-if="user.coverImage">
        <img :src="getFullImage(user.coverImage)" alt="封面圖片" />
      </div>
    </div>

    <button @click="goToCreatePost" class="primary-btn">發布貼文</button>

    <div v-if="posts.length" class="post-section">
      <h3>我的貼文：</h3>
      <ul class="post-list">
        <li v-for="post in posts" :key="post.postId" class="post-card">
          <p class="post-content">{{ post.content }}</p>
          <img
            v-if="post.image"
            :src="getFullImage(post.image)"
            alt="貼文圖片"
            class="post-image"
          />
          <div class="post-actions">
            <button @click="goToComments(post.postId)" class="action-btn">
              留言
            </button>
            <button @click="goToUpdatePost(post.postId)" class="action-btn">
              編輯
            </button>
            <button @click="deletePost(post.postId)" class="action-btn danger">
              刪除
            </button>
          </div>
        </li>
      </ul>
    </div>

    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "PersonalPage",
  data() {
    return {
      posts: [],
      user: {
        userName: "",
        email: "",
        phoneNum: "",
        coverImage: "",
      },
      error: "",
    };
  },
  mounted() {
    this.fetchUserInfo();
    this.fetchPosts();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效";
          return;
        }
        const response = await axios.get(
          "http://localhost:8080/api/user/personalInfo",
          {
            headers: {
              sessionId: sessionId,
            },
          }
        );
        this.user = response.data;
      } catch (err) {
        console.error(err);
        this.error = "獲取使用者資料失敗";
      }
    },

    async fetchPosts() {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效";
          return;
        }
        const response = await axios.get(
          "http://localhost:8080/api/post/myPosts",
          {
            headers: {
              sessionId: sessionId,
            },
          }
        );
        this.posts = response.data;
      } catch (err) {
        console.error(err);
        this.error = "獲取貼文失敗";
      }
    },

    getFullImage(relativePath) {
      return `http://localhost:8080${relativePath}`;
    },

    goToCreatePost() {
      this.$router.push({ name: "CreatePost" });
    },

    goToComments(postId) {
      this.$router.push({ name: "CommentPage", params: { postId } });
    },

    goToUpdatePost(postId) {
      this.$router.push({ name: "UpdatePost", params: { postId } });
    },

    async deletePost(postId) {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效";
          return;
        }
        await axios.delete(
          `http://localhost:8080/api/post/deletePost/${postId}`,
          {
            headers: {
              sessionId: sessionId,
            },
          }
        );
        this.fetchPosts();
      } catch (err) {
        console.error(err);
        this.error = "刪除貼文失敗";
      }
    },
  },
};
</script>

<style scoped>
.personal-page {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  background-color: #f9fafb;
}

.user-info {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 1.5rem;
  background-color: rgba(238, 232, 241, 0.48);
  color: black;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
  backdrop-filter: blur(10px);
}

.info {
  flex: 1;
}

.cover-image {
  width: 200px;
  height: 200px;
  flex-shrink: 0;
  border-radius: 8px;
  overflow: hidden;
  background-color: #f0f0f0;
}

.cover-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

button {
  padding: 0.6rem 1.2rem;
  margin-top: 0.5rem;
  margin-bottom: 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  border: none;
}

.primary-btn {
  background-color: #409eff;
  color: white;
}

.primary-btn:hover {
  background-color: #318ce3;
}

.post-section {
  background-color: #fff;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.post-list {
  list-style-type: none;
  padding: 0;
}

.post-card {
  margin-bottom: 1.5rem;
  padding: 1rem;
  border-bottom: 1px solid #e5e7eb;
}

.post-content {
  font-size: 1rem;
  color: #333;
}

.post-image {
  max-width: 100%;
  border-radius: 8px;
  margin-top: 0.5rem;
}

.post-actions {
  margin-top: 1rem;
}

.action-btn {
  padding: 0.5rem 1rem;
  margin-right: 0.5rem;
  font-size: 0.9rem;
  border-radius: 6px;
}

.action-btn:hover {
  opacity: 0.8;
}

.danger {
  background-color: #f87171;
  color: white;
}

.danger:hover {
  background-color: #f43f5e;
}

.error {
  color: red;
  text-align: center;
  margin-top: 1rem;
}
</style>
