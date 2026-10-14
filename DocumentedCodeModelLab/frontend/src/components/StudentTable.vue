<!-- =====================================================================
  StudentTable.vue : CHILD component that only DISPLAYS a list of students.

  PROPS  = data coming DOWN from the parent   (parent -> child)
  EMITS  = events going UP to the parent      (child  -> parent)
  The table never changes data itself; it just tells the parent "edit" or "remove".
====================================================================== -->
<script setup>
import { ref } from 'vue'

// PROPS: the parent must give us a "students" array
defineProps({
  students: { type: Array, required: true }
})

// EMITS: the two events this component can send to its parent
const emit = defineEmits(['edit', 'remove'])

// ---- Mouse events: remember which row the mouse is over ----
const hoveredId = ref(null)
function onMouseOver(id) { hoveredId.value = id }    // mouseover event
function onMouseOut()    { hoveredId.value = null }  // mouseout event

// ---- Date built-in object: turn "2024-03-15" into a readable date ----
function formatDate(text) {
  const d = new Date(text)           // create a Date object from a string
  return d.toLocaleDateString()      // e.g. 3/15/2024
}
</script>

<template>
  <!-- HTML TABLE: table > thead/tbody > tr (row) > th/td (cell) -->
  <table>
    <thead>
      <tr>
        <th>Photo</th>
        <th>Name</th>
        <th>Course</th>
        <th>Skills</th>
        <th>Joined</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <!-- v-for repeats the <tr> for every student; :key must be unique -->
      <!-- :style changes the row colour when hovered (dynamic inline style) -->
      <tr
        v-for="s in students"
        :key="s.id"
        class="fade-row"
        :style="{ background: hoveredId === s.id ? '#fff3bf' : '' }"
        @mouseover="onMouseOver(s.id)"
        @mouseout="onMouseOut"
      >
        <!-- HTML IMAGE: <img> with a URL as the source -->
        <td><img class="photo" :src="s.photoUrl" :alt="s.name" /></td>
        <td>{{ s.name }}<br /><small>{{ s.email }}</small></td>
        <td>{{ s.course }}</td>
        <td>
          <!-- Nested HTML LIST showing the skills array -->
          <ul class="skill-list">
            <li v-for="skill in s.skills" :key="skill">{{ skill }}</li>
          </ul>
        </td>
        <td>{{ formatDate(s.joinedDate) }}</td>
        <td>
          <!-- Click events -> emit tells the parent what happened -->
          <button class="btn" @click="emit('edit', s.id)">Edit</button>
          <button class="btn btn-danger" @click="emit('remove', s.id)">Delete</button>
        </td>
      </tr>
    </tbody>
  </table>
</template>
