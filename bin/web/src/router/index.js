import Vue from 'vue'
import VueRouter from 'vue-router'
import BulletinView from '../views/BulletinView.vue'
import MainBulletin from '../views/MainBulletin.vue'
import BasicLogin from '../components/BasicLogin.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'BasicLogin',
    component: BasicLogin,
  },
  {
    path: '/bulletin',
    name: 'BulletinView',
    component: BulletinView,
    //認証が必要なルート
    //meta: { requireAuth: true },
  },
  {
    path: '/home',
    name: 'MainBulletin',
    component: MainBulletin
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router