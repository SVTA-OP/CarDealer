<!-- =====================================================================
  PlaygroundView.vue : tiny demos of every JavaScript topic in the syllabus.
  (Functions, Objects, Arrays, String, Date, JSON, Mouse/Keyboard/Form events)
====================================================================== -->
<script setup>
import { ref, reactive } from 'vue'

/* ---------- 1. FUNCTIONS ---------- */
// Normal function
function greet(name) { return 'Hello, ' + name + '!' }
// Arrow function (short form)
const add = (a, b) => a + b
const functionResult = greet('Student') + '  |  2 + 3 = ' + add(2, 3)

/* ---------- 2. OBJECT ---------- */
// An object stores related data as key: value pairs
const person = reactive({ name: 'Asha', age: 20, city: 'Pune' })
function birthday() { person.age++ }            // change a property

/* ---------- 3. ARRAY ---------- */
const fruits = ref(['apple', 'banana', 'mango'])
function pushFruit()  { fruits.value.push('kiwi') }   // add at the end
function popFruit()   { fruits.value.pop() }          // remove from the end
function sortFruits() { fruits.value.sort() }         // alphabetical order

/* ---------- 4. STRING built-in object ---------- */
const text = ref('  Hello Vue World  ')
// each line is a built-in String method / property
const stringDemo = () => [
  'length: '      + text.value.length,
  'trim: '        + text.value.trim(),
  'toUpperCase: ' + text.value.toUpperCase(),
  'replace: '     + text.value.replace('Vue', 'Java'),
  'slice(2,7): '  + text.value.slice(2, 7),
  'indexOf Vue: ' + text.value.indexOf('Vue')
]

/* ---------- 5. DATE built-in object ---------- */
const now = new Date()    // current date & time
const dateInfo = [
  'Full: '  + now.toString(),
  'Year: '  + now.getFullYear(),
  'Month: ' + (now.getMonth() + 1),   // months start at 0, so add 1
  'Day: '   + now.getDate(),
  'Hour: '  + now.getHours()
]

/* ---------- 6. JSON ---------- */
const jsonText   = JSON.stringify(person)     // object -> JSON string
const jsonObject = JSON.parse(jsonText)       // JSON string -> object again
const jsonDemo   = 'stringify: ' + jsonText + '  |  parse -> name = ' + jsonObject.name

/* ---------- 7. EVENT HANDLING ---------- */
const mouseMsg = ref('Move or click on the box')
const keyMsg   = ref('Type in the box')
const formMsg  = ref('')
const typed    = ref('')

// Mouse events
const onClick     = () => mouseMsg.value = 'click!'
const onDblClick  = () => mouseMsg.value = 'double click!'
const onMouseOver = () => mouseMsg.value = 'mouse is over the box'
const onMouseOut  = () => mouseMsg.value = 'mouse left the box'

// Keyboard events: event.key tells which key was pressed
const onKeyDown = (e) => keyMsg.value = 'keydown: ' + e.key
const onKeyUp   = (e) => keyMsg.value = 'keyup: ' + e.key

// Form events
const onFormInput  = () => formMsg.value = 'input event: ' + typed.value
const onFormSubmit = () => formMsg.value = 'submitted: ' + typed.value
</script>

<template>
  <div class="card">
    <h2>JavaScript Playground</h2>

    <h3>1. Function</h3>
    <p>{{ functionResult }}</p>

    <h3>2. Object</h3>
    <p>{{ person }}</p>
    <button class="btn" @click="birthday">Add 1 year</button>

    <h3>3. Array</h3>
    <p>{{ fruits }}</p>
    <button class="btn" @click="pushFruit">push</button>
    <button class="btn" @click="popFruit">pop</button>
    <button class="btn" @click="sortFruits">sort</button>

    <h3>4. String</h3>
    <ul><li v-for="line in stringDemo()" :key="line">{{ line }}</li></ul>

    <h3>5. Date</h3>
    <ul><li v-for="line in dateInfo" :key="line">{{ line }}</li></ul>

    <h3>6. JSON</h3>
    <p>{{ jsonDemo }}</p>

    <h3>7. Events</h3>
    <!-- Mouse events -->
    <div style="border:2px dashed #3b5bdb; padding:20px; text-align:center;"
         @click="onClick" @dblclick="onDblClick"
         @mouseover="onMouseOver" @mouseout="onMouseOut">
      {{ mouseMsg }}
    </div>

    <!-- Keyboard events -->
    <p>{{ keyMsg }}</p>
    <input type="text" placeholder="type here" @keydown="onKeyDown" @keyup="onKeyUp" />

    <!-- Form events -->
    <form @submit.prevent="onFormSubmit">
      <input type="text" v-model="typed" @input="onFormInput" placeholder="form field" />
      <button class="btn" type="submit">Submit</button>
    </form>
    <p>{{ formMsg }}</p>
  </div>
</template>
