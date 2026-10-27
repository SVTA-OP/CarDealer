import { defineStore } from 'pinia'
import { ref } from 'vue'

const API = 'http://localhost:8080'

const send = (path, body) =>
  fetch(API + path, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  })

// One shared store: every page reads the same users / orders / joined details.
export const useShopStore = defineStore('shop', () => {
  const users = ref([])
  const details = ref([])   // orders joined with user names (from the aggregate)

  async function loadUsers() {
    users.value = await (await fetch(API + '/users')).json()
  }
  async function loadDetails() {
    details.value = await (await fetch(API + '/orders/details')).json()
  }
  async function addUser(user) {
    await send('/users', user)
    await loadUsers()
  }
  async function addOrder(order) {
    await send('/orders', order)
    await loadDetails()
  }
  // the joined row for a single order id
  async function detailsOf(orderId) {
    const rows = await (await fetch(`${API}/orders/details/${orderId}`)).json()
    return rows[0]
  }

  return { users, details, loadUsers, loadDetails, addUser, addOrder, detailsOf }
})
