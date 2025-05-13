<template>
  <div class="comment-page">
    <h2>貼文內容</h2>
    <p>{{ post?.content }}</p>

    <img
      v-if="post?.image"
      :src="getFullImage(post.image)"
      alt="貼文圖片"
      style="max-width: 200px"
    />

    <hr />

    <h3>留言：</h3>
    <ul>
      <li v-for="c in comments" :key="c.commentId">{{ c.content }}</li>
    </ul>

    <textarea v-model="newComment" placeholder="輸入留言..."></textarea>
    <br />
    <button @click="submitComment" :disabled="!newComment.trim()">
      送出留言
    </button>
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
        const res = await axios.get(
          `http://140.119.160.250:8080/api/post/getpost/${postId}`,
          {
            headers: { sessionId },
          }
        );
        this.post = res.data;
      } catch (err) {
        console.error("取得貼文失敗", err);
      }
    },

    async fetchComments(postId) {
      try {
        const sessionId = localStorage.getItem("sessionId");
        const res = await axios.get(
          `http://140.119.160.250:8080/api/comment/comments/${postId}`,
          {
            headers: { sessionId },
          }
        );
        this.comments = res.data;
      } catch (err) {
        console.error("取得留言失敗", err);
      }
    },

    async submitComment() {
      try {
        const sessionId = localStorage.getItem("sessionId");
        await axios.post(
          "http://140.119.160.250:8080/api/comment/newComment",
          {
            postId: this.$route.params.postId,
            content: this.newComment,
          },
          {
            headers: { sessionId },
          }
        );
        this.newComment = "";
        this.fetchComments(this.$route.params.postId);
      } catch (err) {
        console.error("送出留言失敗", err);
      }
    },

    getFullImage(path) {
      return `http://140.119.160.250:8080${path}`;
    },
  },
};
</script>
