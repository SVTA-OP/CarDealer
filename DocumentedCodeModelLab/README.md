# Student Manager (Vue 3 + Spring Boot + MongoDB)

## Run
1. Start MongoDB (default: localhost:27017).
2. Backend:   cd backend  && mvn spring-boot:run          (http://localhost:8080)
3. Frontend:  cd frontend && npm install && npm run dev   (http://localhost:5173)

## Where each topic lives
| Topic                         | File |
|-------------------------------|------|
| HTML Table / List / Image     | components/StudentTable.vue, App.vue |
| HTML Form                     | components/StudentForm.vue |
| CSS selectors, box model, inheritance, transitions, animations | assets/main.css |
| CSS style sheet types         | main.css (external), App.vue (internal), StudentsView.vue (inline) |
| JS function/object/array/String/Date/JSON/events | views/PlaygroundView.vue |
| Form/Mouse/Keyboard events    | StudentForm.vue, StudentTable.vue, StudentsView.vue |
| Vue Routing                   | router/index.js, FormView.vue |
| Pinia                         | stores/studentStore.js |
| Props & Emits                 | StudentTable.vue, StudentForm.vue and their parent views |
| Spring Boot CRUD (MongoDB)    | backend/.../controller, service, repository, model |
