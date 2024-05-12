import Vue from 'vue'
import VueRouter from 'vue-router'
import BulletinView from '../views/BulletinView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/bulletin',
    name: 'BulletinView',
    component: BulletinView
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router