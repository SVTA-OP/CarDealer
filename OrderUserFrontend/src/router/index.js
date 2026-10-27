import { createRouter, createWebHistory } from 'vue-router'
import UsersView from '../views/UsersView.vue'
import OrdersView from '../views/OrdersView.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: UsersView },
    { path: '/orders', component: OrdersView },
  ],
})
