<!-- =====================================================================
  StudentsView.vue : the HOME PAGE (route "/").
  PARENT of StudentTable. It
    - gets data from the Pinia store
    - gives it to the child through PROPS
    - listens to the child's EMITS (edit / remove)
====================================================================== -->
<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStudentStore } from '../stores/studentStore'
import StudentTable from '../components/StudentTable.vue'

const store = useStudentStore()   // use the shared Pinia store
const router = useRouter()        // lets us change page from code

const search = ref('')

// Load students from the backend when this page appears
onMounted(() => store.fetchStudents())

// Array method filter() + String methods toLowerCase() and includes()
const filtered = computed(() =>
  store.students.filter(s => s.name.toLowerCase().includes(search.value.toLowerCase()))
)

// Handlers for the child's emitted events
function goEdit(id) { router.push('/edit/' + id) }
function remove(id) {
  if (confirm('Delete this student?')) store.deleteStudent(id)
}

// KEYBOARD EVENT: pressing Enter in the search box
function onSearchKey(e) {
  if (e.key === 'Enter') console.log('Searching for:', search.value)
}
</script>

<template>
  <div class="card">
    <!-- INLINE STYLE SHEET (CSS type #3): style written directly on the element -->
    <h1 id="page-title" style="color: darkblue; font-size: 26px;">
      Student List <span class="badge">{{ store.total }}</span>
    </h1>

    <p class="error">{{ store.error }}</p>

    <!-- @keyup = keyboard event, v-model keeps "search" in sync with the box -->
    <input type="text" v-model="search" @keyup="onSearchKey" placeholder="Search by name..." />

    <!-- Child component: :students = PROP down, @edit/@remove = EMITS up -->
    <StudentTable :students="filtered" @edit="goEdit" @remove="remove" />

    <p v-if="filtered.length === 0">No students found.</p>
  </div>
</template>
