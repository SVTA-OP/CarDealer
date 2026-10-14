// ===== PINIA STORE : one shared place for data used by many components =====
// Every component can read the same "students" list, and every change
// is made through the actions below. Actions call the Spring Boot REST API.
import { defineStore } from 'pinia'

const API = 'http://localhost:8080/api/students'

export const useStudentStore = defineStore('students', {

  // STATE = the data
  state: () => ({
    students: [],     // list of student objects
    error: ''         // message shown if something fails
  }),

  // GETTERS = computed values based on state
  getters: {
    total: (state) => state.students.length
  },

  // ACTIONS = functions that change the state (CRUD)
  actions: {
    // READ all
    async fetchStudents() {
      try {
        const res = await fetch(API)                 // GET
        this.students = await res.json()             // convert JSON text -> JS array
        this.error = ''
      } catch (e) {
        this.error = 'Cannot reach the backend. Is Spring Boot running?'
      }
    },

    // READ one (used by the edit page)
    async fetchOne(id) {
      const res = await fetch(API + '/' + id)        // GET /api/students/{id}
      return await res.json()
    },

    // CREATE
    async addStudent(student) {
      await fetch(API, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(student)                // JS object -> JSON text
      })
      await this.fetchStudents()                     // refresh the list
    },

    // UPDATE
    async updateStudent(id, student) {
      await fetch(API + '/' + id, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(student)
      })
      await this.fetchStudents()
    },

    // DELETE
    async deleteStudent(id) {
      await fetch(API + '/' + id, { method: 'DELETE' })
      await this.fetchStudents()
    }
  }
})
