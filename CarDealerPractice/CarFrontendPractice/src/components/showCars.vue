<script setup>

import {ref} from 'vue'

const items = ref([])

async function getAll(){
	try{
		const resp = await fetch("http://localhost:8080")
		items.value = await resp.json();
	}
	catch (error){ alert("Error pulling the data")}
}

</script>

<template>
	<h1>Hello</h1>
	<button @click="getAll">Get All Cars</button>

<div v-for="item in items" :key="item.id">{{ item.id }} : {{ item.model }} : {{ item.type }}</div>

</template>

<style scoped>

</style>
