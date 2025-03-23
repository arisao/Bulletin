import Vue from 'vue'
import VueRouter from 'vue-router'
import BulletinView from '../views/BulletinView.vue'
import MainBulletin from '../views/MainBulletin.vue'
import Login from '../views/Login.vue'

Vue.use(VueRouter)


const routes = [
  {
    path: '/bulletin',
    name: 'BulletinView',
    component: BulletinView,
    meta: { requiresAuth: true }
  },
  //ログイン必須ページ
  {
    path: '/home',
    name: 'MainBulletin',
    component: MainBulletin
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

router.beforeEach((to, from, next) => {
//toが移動するurl
//fromが現在のurl
//nextがtoから指定したurlに移動するため必ず呼び出さなければならない関数
  const isAuthenticated = !!sessionStorage.getItem('userId');
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login');
  } else {
    next();
  }
});

export default router