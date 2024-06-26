import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// BootstrapVueをVue.use()で使用する
Vue.use(BootstrapVue)

// Vueインスタンスを作成し、ルーターを使用して初期化
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
