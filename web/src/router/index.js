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
    component: BulletinView
  },
  //ログイン必須ページ
  {
    path: '/home',
    name: 'MainBulletin',
    component: MainBulletin,
    meta: { requiresAuth: true }
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
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (Object.keys(store.state.userInfo).length) {
      next()
      //ユーザーが認証されていない場合、/にリダイレクト。
    } else {
      next({ path: '/', query: { redirect: to.fullPath }})
    }
  }
  next()
})

export default router