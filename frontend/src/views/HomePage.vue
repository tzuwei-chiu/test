<template>
  <div class="home-page">
    <div class="welcome">
      <h1>Welcome!</h1>
      <div class="area1-btn">
        <button @click="goToCreatePost" class="btn1">發布貼文</button>
        <button @click="goToPersonalPage" class="btn2">個人頁面</button>
      </div>
    </div>

    <div v-if="posts.length" class="posts">
      <h2>其他用戶的發文</h2>
      <ul class="post-list">
        <li class="post-card" v-for="post in posts" :key="post.postId">
          <div class="post-header">
            <strong>用戶名 : {{ post.userName }}</strong>
          </div>
          <p class="post-content">{{ post.content }}</p>
          <img
            v-if="post.image"
            :src="getFullImage(post.image)"
            alt="圖片"
            class="post-image"
          />
          <button @click="goToComments(post.postId)" class="comment-btn">
            留言
          </button>
        </li>
      </ul>
    </div>

    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "HomePage",
  data() {
    return {
      posts: [],
      error: "",
    };
  },
  mounted() {
    this.fetchPosts();
  },
  methods: {
    async fetchPosts() {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效";
          return;
        }
        const response = await axios.get(
          "http://localhost:8080/api/post/othersPosts",
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

    goToPersonalPage() {
      this.$router.push({ name: "PersonalPage" });
    },
  },
};
</script>

<style scoped>
.home-page {
  width: 70%;
  margin: 0 auto;
  padding: 30px;
  background: #f9fafb;
  min-height: 100vh;
}

.welcome {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
  text-align: center;
}

.area2 .button-group {
  margin-top: 1rem;
}

button {
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  margin: 0 0.5rem;
}

.btn1 {
  background-color: #409eff;
  color: white;
}

.btn1:hover {
  background-color: #318ce3;
}

.btn2 {
  background-color: #e0e7ff;
  color: #333;
}

.btn2:hover {
  background-color: #c7d2fe;
}

.posts {
  background: white;
  padding: 10px 150px;
  border-radius: 12px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.post-list {
  list-style: none;
}

.post-card {
  padding: 1rem;
  margin-bottom: 20px;
  border-bottom: 1px solid rgb(189, 191, 197);
}

.post-header {
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 1rem;
}

.post-content {
  font-size: 25px;
}
.post-image {
  max-width: 600px;
  height: 400px;
}

.comment-btn {
  background-color: rgb(225, 198, 92);
  color: #111827;
  padding: 0.4rem 1rem;
  border-radius: 6px;
  font-size: 1rem;
  border: none;
  margin-top: 20px;
}

.comment-btn:hover {
  background-color: rgb(202, 177, 101);
}

.error {
  color: red;
  margin-top: 1rem;
  text-align: center;
}
</style>
