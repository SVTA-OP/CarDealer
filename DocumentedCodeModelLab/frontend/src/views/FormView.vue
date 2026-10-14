<!-- =====================================================================
  FormView.vue : ADD page ("/add") AND EDIT page ("/edit/:id").
  The same page works for both: if the URL contains an id we are editing.
  It is the PARENT of StudentForm (gives a PROP, listens to EMITS).
====================================================================== -->
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStudentStore } from '../stores/studentStore'
import StudentForm from '../components/StudentForm.vue'

const route = useRoute()       // info about the current URL (params)
const router = useRouter()     // used to move to another page
const store = useStudentStore()

const id = route.params.id     // undefined on /add, a real id on /edit/:id
const student = ref(undefined) // undefined -> child uses its default empty student

// When editing, load the student first
onMounted(async () => {
  if (id) student.value = await store.fetchOne(id)
})

// Called when the child emits "save"
async function onSave(data) {
  if (id) await store.updateStudent(id, data)   // UPDATE
  else    await store.addStudent(data)          // CREATE
  router.push('/')                              // go back to the list
}

// Called when the child emits "cancel"
function onCancel() { router.push('/') }
</script>

<template>
  <div class="card">
    <h2>{{ id ? 'Edit Student' : 'Add Student' }}</h2>
    <!-- :student = PROP, @save and @cancel = EMITS -->
    <StudentForm :student="student" @save="onSave" @cancel="onCancel" />
  </div>
</template>
