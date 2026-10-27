<script setup>
import { ref, onMounted } from 'vue'
import { useShopStore } from '../stores/shopStore'
import OrderForm from '../components/OrderForm.vue'
import OrderTable from '../components/OrderTable.vue'

const store = useShopStore()
const picked = ref(null)

onMounted(() => { store.loadUsers(); store.loadDetails() })

// the table emitted "select" -> ask the backend's aggregate for just that order
async function pick(orderId) {
  picked.value = await store.detailsOf(orderId)
}
</script>

<template>
  <h2>Orders</h2>
  <OrderForm :users="store.users" @save="store.addOrder" />
  <OrderTable :orders="store.details" @select="pick" />
  <p v-if="picked">Order <b>{{ picked.orderId }}</b> was placed by <b>{{ picked.userName }}</b>.</p>
</template>
