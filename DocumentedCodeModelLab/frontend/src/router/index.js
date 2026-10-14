// ===== ROUTING : decides which page (view) to show for each URL =====
import { createRouter, createWebHistory } from 'vue-router'
import StudentsView from '../views/StudentsView.vue'
import FormView from '../views/FormView.vue'
import PlaygroundView from '../views/PlaygroundView.vue'

const routes = [
  { path: '/',           name: 'students',   component: StudentsView },
  { path: '/add',        name: 'add',        component: FormView },
  // ":id" is a route parameter -> /edit/65af... ; read it with useRoute().params.id
  { path: '/edit/:id',   name: 'edit',       component: FormView },
  { path: '/playground', name: 'playground', component: PlaygroundView }
]

export default createRouter({
  history: createWebHistory(),  // clean URLs without '#'
  routes
})
