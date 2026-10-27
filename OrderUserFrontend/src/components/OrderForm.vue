<script setup>
import { ref } from 'vue'

defineProps({ users: { type: Array, required: true } })   // PROP: users to pick from
const emit = defineEmits(['save'])                         // EMIT: new order goes up

const orderId = ref('')
const userId = ref('')
const total = ref('')

function submit() {
  emit('save', { orderId: orderId.value, userId: Number(userId.value), total: Number(total.value) })
  orderId.value = userId.value = total.value = ''
}
</script>

<template>
  <input v-model="orderId" placeholder="orderId" />
  <select v-model="userId">
    <option value="" disabled>user</option>
    <option v-for="u in users" :key="u.id" :value="u.userId">{{ u.name }}</option>
  </select>
  <input v-model="total" type="number" placeholder="total" />
  <button @click="submit">Add order</button>
</template>
