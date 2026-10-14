<!-- =====================================================================
  App.vue : the ROOT component. Holds the navigation bar and the
  <router-view>, which is the placeholder where the current page appears.
====================================================================== -->
<script setup>
// Nothing to import: <router-link> and <router-view> are registered by app.use(router)
</script>

<template>
  <!-- HTML LIST: <ul> (unordered list) with <li> items -> used as a menu -->
  <nav>
    <ul>
      <li><router-link to="/">Students</router-link></li>
      <li><router-link to="/add">Add Student</router-link></li>
      <li><router-link to="/playground">JS Playground</router-link></li>
    </ul>
  </nav>

  <!-- The page chosen by the router is shown here -->
  <router-view />
</template>

<!-- =====================================================================
  INTERNAL STYLE SHEET (CSS type #2): a <style> block inside the file.
  "scoped" means these rules only affect THIS component.
====================================================================== -->
<style scoped>
nav { background: #3b5bdb; padding: 10px 20px; }
nav ul { list-style: none; margin: 0; padding: 0; display: flex; gap: 20px; }
nav a { color: white; text-decoration: none; transition: color 0.3s; }
nav a:hover { color: #ffd43b; }
/* Vue adds this class to the link of the page you are currently on */
nav a.router-link-exact-active { border-bottom: 2px solid white; }
</style>
