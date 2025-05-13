import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../views/HomePage.vue";
import LoginPage from "../views/LoginPage.vue";
import CreatePost from "../views/CreatePost.vue";
import CommentPage from "../views/CommentPage.vue";

const routes = [
  {
    path: "/home",
    name: "Home",
    component: HomePage,
  },
  {
    path: "/",
    name: "Login",
    component: LoginPage,
  },
  {
    path: "/createPost",
    name: "CreatePost",
    component: CreatePost,
  },
  {
    path: "/post/:postId/comments",
    name: "CommentPage",
    component: CommentPage,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
