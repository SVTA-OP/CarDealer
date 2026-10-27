<template>
  <nav>
    <RouterLink to="/">Users</RouterLink>
    <RouterLink to="/orders">Orders</RouterLink>
  </nav>
  <main><RouterView /></main>
</template>

<style>
body { font-family: system-ui, sans-serif; margin: 0; background: #f6f7fb; color: #222; }
nav { display: flex; gap: 1rem; padding: 1rem 2rem; background: #3b5bdb; }
nav a { color: #fff; text-decoration: none; }
nav a.router-link-exact-active { border-bottom: 2px solid #fff; }
main { max-width: 640px; margin: 2rem auto; padding: 0 1rem; }
input, select, button { padding: .5rem; margin: 0 .25rem .5rem 0; }
table { width: 100%; border-collapse: collapse; margin-top: 1rem; }
th, td { text-align: left; padding: .5rem; border-bottom: 1px solid #ddd; }
tr.clickable { cursor: pointer; }
tr.clickable:hover { background: #e7ecff; }
</style>
