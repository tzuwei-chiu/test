<template>
  <div class="personal-page">
    <h1>我的塗鴉牆</h1>

    <!-- 顯示個人資訊 -->
    <div class="user-info">
      <h3>個人資訊</h3>
      <p><strong>使用者名稱：</strong>{{ user.userName }}</p>
      <p><strong>Email：</strong>{{ user.email }}</p>
      <p><strong>手機號碼：</strong>{{ user.phoneNum }}</p>
      <p v-if="user.coverImage">
        <strong>個人封面：</strong>
        <img
          :src="getFullImage(user.coverImage)"
          alt="個人封面"
          style="max-width: 200px"
        />
      </p>
    </div>

    <button @click="goToCreatePost">發布貼文</button>

    <div v-if="posts.length">
      <h3>我的貼文：</h3>
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
          <button @click="goToUpdatePost(post.postId)">編輯</button>
          <button @click="deletePost(post.postId)">刪除</button>
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
          "http://140.119.160.250:8080/api/user/personalInfo", // 假設這是獲取使用者資料的 API
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
          "http://140.119.160.250:8080/api/post/myPosts",
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
          `http://140.119.160.250:8080/api/post/deletePost/${postId}`,
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
}
.user-info {
  padding: 1rem;
  border: 1px solid #ccc;
  margin-bottom: 2rem;
}
button {
  padding: 0.5rem 1rem;
  margin-top: 1rem;
}
</style>
