// ===== main.js : starts the Vue app and plugs in Router + Pinia =====
import { createApp } from 'vue'
import { createPinia } from 'pinia'   // state management
import router from './router'         // page navigation
import App from './App.vue'
import './assets/main.css'            // EXTERNAL style sheet (CSS type #1)

const app = createApp(App)
app.use(createPinia())  // enable Pinia stores
app.use(router)         // enable Vue Router
app.mount('#app')       // show the app inside <div id="app">
