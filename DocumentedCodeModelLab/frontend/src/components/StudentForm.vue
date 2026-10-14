<!-- =====================================================================
  StudentForm.vue : CHILD component with an HTML FORM, used for both
  adding and editing.
    PROP  : "student" -> the starting values (empty for add, filled for edit)
    EMITS : "save"    -> sends the finished student object to the parent
            "cancel"  -> tells the parent the user gave up
====================================================================== -->
<script setup>
import { reactive, ref, watch } from 'vue'

// PROP with a default value (an empty student)
const props = defineProps({
  student: {
    type: Object,
    default: () => ({ name: '', email: '', course: 'Java', age: 18, photoUrl: '', skills: [], joinedDate: '' })
  }
})
const emit = defineEmits(['save', 'cancel'])

// Local copy so we do not change the parent's data until "Save" is pressed
const form = reactive({ ...props.student })

// The skills input is plain text ("Java, Vue"); we convert it to an array on save
const skillsText = ref((props.student.skills || []).join(', '))

// If the parent gives us a different student (edit page loaded), refill the form
watch(() => props.student, (newStudent) => {
  Object.assign(form, newStudent)
  skillsText.value = (newStudent.skills || []).join(', ')
})

const message = ref('')

// ---- FORM EVENT: input (fires on every keystroke in a field) ----
function onNameInput() {
  // String built-ins: trim() and length
  message.value = form.name.trim().length < 3 ? 'Name must have at least 3 letters' : ''
}

// ---- FORM EVENT: submit ----
function onSubmit() {
  if (form.name.trim().length < 3) {
    message.value = 'Please enter a valid name'
    return
  }
  // String methods: split() makes an array, map() + trim() cleans each item,
  // filter() removes empty items
  form.skills = skillsText.value.split(',').map(x => x.trim()).filter(x => x !== '')
  emit('save', { ...form })     // send a copy up to the parent
}

// ---- KEYBOARD EVENT: pressing Escape cancels ----
function onKeyUp(event) {
  if (event.key === 'Escape') emit('cancel')
}
</script>

<template>
  <!-- @submit.prevent = run onSubmit and stop the browser reloading the page -->
  <form @submit.prevent="onSubmit" @keyup="onKeyUp">

    <label class="required">Name</label>
    <input type="text" v-model="form.name" @input="onNameInput" placeholder="Full name" />
    <p class="error">{{ message }}</p>

    <label class="required">Email</label>
    <input type="email" v-model="form.email" required />

    <label>Course</label>
    <!-- FORM EVENT: change (fires when a choice is made) -->
    <select v-model="form.course" @change="message = 'Course set to ' + form.course">
      <option>Java</option>
      <option>Web Development</option>
      <option>Data Science</option>
    </select>

    <label>Age</label>
    <input type="number" v-model.number="form.age" min="15" max="60" />

    <label>Joined date</label>
    <input type="date" v-model="form.joinedDate" />

    <label>Photo URL</label>
    <input type="text" v-model="form.photoUrl" placeholder="https://..." />
    <!-- Live image preview, only shown when a URL exists -->
    <img v-if="form.photoUrl" :src="form.photoUrl" class="photo" alt="preview" />

    <label>Skills (comma separated)</label>
    <input type="text" v-model="skillsText" placeholder="Java, Vue, SQL" />

    <button type="submit" class="btn">Save</button>
    <button type="button" class="btn btn-grey" @click="emit('cancel')">Cancel (Esc)</button>
  </form>
</template>
