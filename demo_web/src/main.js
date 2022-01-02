import Vue from 'vue';
import App from './App.vue';
import VueRouter from "vue-router";
import VueAxios from 'vue-axios';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import 'font-awesome/css/font-awesome.css';
import 'element-ui/lib/theme-chalk/index.css';
import Routes from './js/routes.js';
import VueTimers from 'vue-timers';
import ElementUI from 'element-ui';
import global_ from './components/Global.vue'
import VueSvgGauge from 'vue-svg-gauge'


Vue.config.productionTip = false
Vue.use(VueRouter);
Vue.use(VueAxios,axios);
Vue.use(VueTimers);
Vue.use(ElementUI);
Vue.use(VueSvgGauge)
Vue.prototype.GLOBAL = global_;


const router = new VueRouter({
  routes: Routes,
  mode: "history",
});

// router.beforeEach((to, from, next)=>{
//   const isLogin = localStorage.getItem('token') == 'ImLogin' ;
//   if( isLogin ){
//     next();
//   } else {
//     if( to.path !== '/Login')
//       next('/Login');
//     else
//       next();
//   }
// });


new Vue({
  render: h => h(App),
  router: router,
}).$mount('#app')
