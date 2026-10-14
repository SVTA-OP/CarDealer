// Vite = the tool that runs and builds our Vue app. The Vue plugin lets it read .vue files.
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: { port: 5173 }   // must match @CrossOrigin in the Spring controller
})
