<template>
  <div class="home">
    <h1>歡迎回來！</h1>
    <p>這是首頁內容。</p>
    <button @click="goToCreatePost">發布貼文</button>

    <div v-if="posts.length">
      <h3>其他使用者的貼文：</h3>
      <ul>
        <li v-for="post in posts" :key="post.postId">
          <p>{{ post.content }}</p>
          <img
            v-if="post.image"
            :src="getFullImage(post.image)"
            alt="貼文圖片"
            style="max-width: 200px"
          />
          <br />
          <button @click="goToComments(post.postId)">留言</button>
        </li>
      </ul>
    </div>

    <p v-if="error" style="color: red">{{ error }}</p>
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
          "http://140.119.160.250:8080/api/post/othersPosts",
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
      return `http://140.119.160.250:8080${relativePath}`;
    },

    goToCreatePost() {
      this.$router.push({ name: "CreatePost" });
    },

    goToComments(postId) {
      this.$router.push({ name: "CommentPage", params: { postId } });
    },
  },
};
</script>

<style scoped>
.home {
  padding: 20px;
}
</style>
