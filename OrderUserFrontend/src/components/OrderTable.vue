<script setup>
defineProps({ orders: { type: Array, required: true } })   // PROP: joined rows to show
const emit = defineEmits(['select'])                        // EMIT: which order was clicked
</script>

<template>
  <table>
    <thead><tr><th>Order</th><th>User</th><th>Total</th></tr></thead>
    <tbody>
      <tr v-for="o in orders" :key="o.orderId" class="clickable" @click="emit('select', o.orderId)">
        <td>{{ o.orderId }}</td><td>{{ o.userName }}</td><td>{{ o.total }}</td>
      </tr>
    </tbody>
  </table>
</template>
