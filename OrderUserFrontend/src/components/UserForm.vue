<script setup>
import { ref } from 'vue'

const emit = defineEmits(['save'])          // EMIT: send the new user up to the parent
const userId = ref('')
const name = ref('')

function submit() {
  emit('save', { userId: Number(userId.value), name: name.value })
  userId.value = name.value = ''
}
</script>

<template>
  <input v-model="userId" type="number" placeholder="userId" />
  <input v-model="name" placeholder="name" />
  <button @click="submit">Add user</button>
</template>
