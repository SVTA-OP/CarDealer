<script setup>
import { onMounted } from 'vue'
import { useShopStore } from '../stores/shopStore'
import UserForm from '../components/UserForm.vue'

const store = useShopStore()
onMounted(store.loadUsers)
</script>

<template>
  <h2>Users</h2>
  <UserForm @save="store.addUser" />
  <ul>
    <li v-for="u in store.users" :key="u.id">#{{ u.userId }} {{ u.name }}</li>
  </ul>
</template>
