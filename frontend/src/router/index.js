import { createRouter, createWebHistory } from "vue-router";
import RegisterPage from "../views/RegisterPage.vue";
import LoginPage from "../views/LoginPage.vue";
import HomePage from "../views/HomePage.vue";
import PersonalPage from "../views/PersonalPage.vue";
import CreatePost from "../views/CreatePost.vue";
import UpdatePost from "../views/UpdatePost.vue";
import CommentPage from "../views/CommentPage.vue";

const routes = [
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
    path: "/home",
    name: "Home",
    component: HomePage,
  },
  {
    path: "/personalPage",
    name: "PersonalPage",
    component: PersonalPage,
  },
  {
    path: "/createPost",
    name: "CreatePost",
    component: CreatePost,
  },
  {
    path: "/updatePost/:postId",
    name: "UpdatePost",
    component: UpdatePost,
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
