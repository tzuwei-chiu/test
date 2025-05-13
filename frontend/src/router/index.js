import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../views/HomePage.vue";
import LoginPage from "../views/LoginPage.vue";
import CreatePost from "../views/CreatePost.vue";
import CommentPage from "../views/CommentPage.vue";
import RegisterPage from "../views/RegisterPage.vue";
import PersonalPage from "../views/PersonalPage.vue";
import UpdatePost from "../views/UpdatePost.vue";

const routes = [
  {
    path: "/home",
    name: "Home",
    component: HomePage,
  },
  {
    path: "/register",
    name: "RegisterPage",
    component: RegisterPage,
  },
  {
    path: "/",
    name: "LoginPage",
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
  {
    path: "/personalPage",
    name: "PersonalPage",
    component: PersonalPage,
  },
  {
    path: "/updatePost/:postId",
    name: "UpdatePost",
    component: UpdatePost,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
