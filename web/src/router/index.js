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
  {
    path: '/home',
    name: 'MainBulletin',
    component: MainBulletin
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router