<template>
  <div class="comment-page">
    <h1>用戶名: {{ post?.userName }}</h1>
    <h2>{{ post?.content }}</h2>

    <div class="image">
      <img v-if="post?.image" :src="getFullImage(post.image)" alt="貼文圖片" />
    </div>

    <hr />

    <h3>留言：</h3>
    <div class="comment">
      <ul>
        <li v-for="c in comments" :key="c.commentId">
          <strong>{{ c.userName }}</strong
          >: {{ c.content }}
        </li>
      </ul>
    </div>

    <div class="new-comment">
      <div class="textarea">
        <textarea v-model="newComment" placeholder="輸入留言..."></textarea>
      </div>
      <div class="btn">
        <button @click="submitComment" :disabled="!newComment.trim()">
          送出留言
        </button>
      </div>
    </div>

    <p v-if="error" style="color: red">{{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CommentPage",
  data() {
    return {
      post: null,
      comments: [],
      newComment: "",
      error: "",
    };
  },
  mounted() {
    const postId = this.$route.params.postId;
    this.fetchPost(postId);
    this.fetchComments(postId);
  },
  methods: {
    async fetchPost(postId) {
      try {
        const sessionId = localStorage.getItem("sessionId");
        if (!sessionId) {
          this.error = "尚未登入或 session 已失效，請重新登入。";
          return;
        }
        const res = await axios.get(
          `http://localhost:8080/api/post/getPost/${postId}`,
          {
            headers: { sessionId },
          }
        );
        if (res.data) {
          this.post = res.data;
        } else {
          this.error = "貼文不存在。";
        }
      } catch (err) {
        console.error("取得貼文失敗", err);
        this.error = "取得貼文失敗，請稍後再試。";
      }
    },

    async fetchComments(postId) {
      try {
        const sessionId = localStorage.getItem("sessionId");
        const res = await axios.get(
          `http://localhost:8080/api/comment/${postId}/comments`,
          {
            headers: { sessionId },
          }
        );
        this.comments = res.data;
      } catch (err) {
        console.error("取得留言失敗", err);
        this.error = "取得留言失敗，請稍後再試。";
      }
    },

    async submitComment() {
      if (!this.newComment.trim()) {
        this.error = "留言內容不可為空！";
        return;
      }
      try {
        const sessionId = localStorage.getItem("sessionId");
        await axios.post(
          "http://localhost:8080/api/comment/newComment",
          {
            postId: this.$route.params.postId,
            content: this.newComment,
          },
          {
            headers: { sessionId },
          }
        );
        this.newComment = "";
        this.error = "";
        this.fetchComments(this.$route.params.postId);
      } catch (err) {
        console.error("送出留言失敗", err);
        this.error = "送出留言失敗，請稍後再試。";
      }
    },

    getFullImage(path) {
      return `http://localhost:8080${path}`;
    },
  },
};
</script>

<style scoped>
.comment-page {
  width: 50%;
  height: 100vh;
  margin: auto;
  padding: 40px;
  background-color: #f9fafb;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

img {
  width: 600px;
  height: 400px;
}

.comment {
  font-size: 20px;
}

.new-comment {
  display: flex;
  align-items: center;
}

.textarea {
  width: 70%;
}

textarea {
  width: 100%;
  height: 50px;
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  margin-bottom: 1rem;
}

button {
  padding: 0.8rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  background-color: #409eff;
  color: white;
  border: none;
  margin-left: 50px;
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

ul {
  list-style-type: none;
  padding-left: 0;
}

ul li {
  margin: 10px 0;
}

ul li:not(:last-child) {
  border-bottom: 1px solid #e5e5e5;
}
</style>
