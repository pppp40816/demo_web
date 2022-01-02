import DashBoard from '../components/DashBoard.vue';
import Monitor from '../components/Monitor.vue';
import Rack from '../components/Rack.vue';
export default [
  // {
  //   path: '/Login',
  //   name: 'Login',
  //   component: Login
  // },
  {
    path: '/DashBoard',
    name: 'DashBoard',
    component: DashBoard
  },
  { path: "/", name : 'DashBoard',component: DashBoard },
  { path: "/Monitor", name : 'Monitor',component: Monitor },
  {
    path: '/Rack',
    name: 'Rack',
    component: Rack
  }
];
