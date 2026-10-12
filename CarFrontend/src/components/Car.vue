<script setup>
import { ref } from 'vue'

const cars = ref([])
const brand = ref('')
const model = ref('')
const year = ref(2024)
const newName = ref('')

async function getCars() {
  const res = await fetch('http://localhost:8080/')
  cars.value = await res.json()
}

async function addCar() {
  await fetch('http://localhost:8080/create', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ brand: brand.value, model: model.value, year: year.value })
  })
  getCars()
}

async function updateCar(id) {
  await fetch('http://localhost:8080/update/' + id + '?newname=' + newName.value, {
    method: 'PUT'
  })
  getCars()
}

async function deleteCar(id) {
  await fetch('http://localhost:8080/delete/' + id, { method: 'DELETE' })
  getCars()
}

getCars()
</script>

<template>
  <h1>Cars</h1>

  <input v-model="brand" placeholder="brand" />
  <input v-model="model" placeholder="model" />
  <input v-model="year" placeholder="year" />
  <button @click="addCar">Add</button>

  <hr />

  <input v-model="newName" placeholder="new model name (for rename)" />

  <div v-for="car in cars" :key="car.id">
    {{ car.id }}{{ car.brand }} {{ car.model }} {{ car.year }}
    <button @click="updateCar(car.id)">Rename</button>
    <button @click="deleteCar(car.id)">Delete</button>
  </div>
</template>