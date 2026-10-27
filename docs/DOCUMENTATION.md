# CarDealer Repository — Complete Code Documentation

A guided tour of every project in this repository: **what each file does, what the syntax means, and how the pieces work together.**

---

## Table of Contents

1. [Repository map](#1-repository-map)
2. [The big picture](#2-the-big-picture)
3. [Cheat sheets (read these first)](#3-cheat-sheets)
4. [CarBackend (Spring Boot + MongoDB)](#4-carbackend)
5. [CarFrontend (Vue 3)](#5-carfrontend)
6. [CarDealerPractice](#6-cardealerpractice)
7. [HelloServlet and CheckServlet](#7-helloservlet-and-checkservlet)
8. [DocumentedCodeModelLab](#8-documentedcodemodellab)
9. [OrderUserBackend — two collections joined with `$lookup`](#9-orderuserbackend)
10. [OrderUserFrontend — Vue + Pinia + Router + props/emits](#10-orderuserfrontend)
11. [DBServlet — JDBC table viewer](#11-dbservlet)
12. [Running everything](#12-running-everything)
13. [Troubleshooting & known gotchas](#13-troubleshooting--known-gotchas)

---

## 1. Repository map

```
CarDealer/
├── CarBackend/                 Spring Boot REST API for cars (MongoDB)
├── CarFrontend/                Vue 3 single-page UI for CarBackend
├── CarDealerPractice/
│   ├── CarBackendPractice/demo/    Practice copy of the car backend
│   └── CarFrontendPractice/        Practice Vue app using vue-router (partly empty)
├── HelloServlet/               Servlet: form -> prints your name
├── CheckServlet/               Servlet: age slider -> "eligible to vote?"
├── DocumentedCodeModelLab/     Fully commented lab: Student CRUD
│   ├── backend/                    Spring Boot + MongoDB
│   ├── frontend/                   Vue + Router + Pinia + props/emits
│   └── servlet-app/                Servlet + MySQL (JDBC) + Web Storage, packaged as a WAR
├── OrderUserBackend/           NEW: two collections (users, orders) + aggregate "join"
├── OrderUserFrontend/          NEW: Vue + Pinia + Router + props/emits for the above
├── DBServlet/                  NEW: servlet that prints every row of a MySQL table
├── docs/DOCUMENTATION.md       This file
└── commands.txt                Cheat sheet: compile & run HelloServlet on Tomcat
```

---

## 2. The big picture

Almost every project follows the same flow:

```
 Browser (Vue page)                Spring Boot server                  Database
 ┌──────────────┐  fetch(JSON)  ┌────────────────────────┐          ┌──────────┐
 │ Component    │ ────────────► │ Controller  (URLs)     │          │          │
 │  .vue files  │               │   ▼                    │          │ MongoDB  │
 │              │ ◄──────────── │ Service     (logic)    │ ───────► │ (or      │
 └──────────────┘   JSON back   │   ▼                    │          │  MySQL)  │
                                │ Repository  (DB calls) │          │          │
                                └────────────────────────┘          └──────────┘
```

| Layer | Job | Analogy |
|---|---|---|
| **Model** | Describes one record (fields + types) | A row in a spreadsheet |
| **Repository** | Reads/writes records in the DB | The librarian who fetches books |
| **Service** | Business logic, combines repository calls | The manager who decides what to do |
| **Controller** | Maps HTTP URLs to service methods | The receptionist who answers the phone |
| **Vue component** | Displays data, collects user input | The shop window |

**Servlets** are the older, lower-level way to do the "Controller" job: a Java class that Tomcat calls when a URL is requested. They have no Service/Repository layers; the servlet does everything itself.

---

## 3. Cheat sheets

### 3.1 Spring Boot annotations used in this repo

| Annotation | Put on | Meaning |
|---|---|---|
| `@SpringBootApplication` | main class | Starts Spring, scans this package and sub-packages for components |
| `@RestController` | class | Methods return data (JSON), not HTML pages |
| `@RequestMapping("/x")` | class/method | Base URL for everything in the class |
| `@GetMapping` `@PostMapping` `@PutMapping` `@DeleteMapping` | method | Handle that HTTP verb (optionally with a sub-path) |
| `@PathVariable` | parameter | Takes `{id}` out of the URL path |
| `@RequestParam` | parameter | Takes `?key=value` from the URL query string |
| `@RequestBody` | parameter | Converts the JSON request body into a Java object |
| `@CrossOrigin(origins="*")` | class | Allows a page on another port (Vue on 5173) to call this API (CORS) |
| `@Service` | class | Marks a business-logic bean; Spring creates it for you |
| `@Repository` | interface | Marks a data-access bean |
| `@Document(collection="x")` | model class | Stores this class in MongoDB collection `x` |
| `@Id` | field | The document's unique `_id` |
| `@Data` (Lombok) | class | Auto-generates getters, setters, `toString`, `equals`, `hashCode` |
| `@NoArgsConstructor` / `@AllArgsConstructor` (Lombok) | class | Auto-generates empty / all-fields constructors |
| `@RequiredArgsConstructor` (Lombok) | class | Generates a constructor for all `final` fields |

**Dependency injection** — you never write `new CarService()`. Instead a class asks for what it needs in its constructor, and Spring supplies it:

```java
private final CarRepository repo;
public CarService(CarRepository repo) { this.repo = repo; }
```

**`MongoRepository<Model, IdType>`** — extending this interface gives you `save`, `findAll`, `findById`, `deleteById`, `existsById`, `count`… with **no implementation code**; Spring generates it at runtime.

**`Optional<T>`** — what `findById` returns. `.map(x -> ...)` runs only if present, `.orElse(null)` supplies a fallback.

### 3.2 Vue 3 (`<script setup>`) syntax

| Syntax | Meaning |
|---|---|
| `const x = ref(0)` | Reactive value. Use `x.value` in JS, plain `x` in the template |
| `{{ x }}` | Print a value in the template |
| `v-model="x"` | Two-way bind an input to a variable |
| `@click="fn"` | Run `fn` on click (`@` = `v-on:`) |
| `:src="url"` | Bind an attribute to a JS value (`:` = `v-bind:`) |
| `v-for="i in list" :key="i.id"` | Repeat an element per item (key must be unique) |
| `v-if="cond"` | Render only when true |
| `defineProps({...})` | Declare data a **parent passes down** |
| `defineEmits([...])` | Declare events this component **sends up** |
| `onMounted(fn)` | Run `fn` once the component appears on screen |
| `<RouterLink to="/x">` / `<RouterView/>` | Navigation link / placeholder where the current page renders |

**Props & emits in one sentence:** *props flow down (parent → child), events flow up (child → parent).* A child never edits parent data directly; it emits an event and the parent decides.

**Pinia** — a shared store that any component can read, so you do not have to pass data through many layers. A store has *state* (data), *getters* (computed values), and *actions* (functions that change state).

### 3.3 Servlet essentials

| Concept | Meaning |
|---|---|
| `extends HttpServlet` | Makes your class a servlet |
| `doGet` / `doPost` | Called for GET (links, URL typing) / POST (forms with `method="post"`) |
| `req.getParameter("x")` | Reads form/query field `x` (always a `String`, or `null`) |
| `res.setContentType("text/html")` | Tells the browser what you're sending |
| `res.getWriter()` | A `PrintWriter` you print the response into |
| `web.xml` | Maps a URL pattern (`/hello`) to a servlet class |
| `@WebServlet("/x")` | Same mapping, written as an annotation instead of XML |
| `javax.servlet.*` vs `jakarta.servlet.*` | **Tomcat 9 and older = `javax`**; **Tomcat 10+ = `jakarta`** |

### 3.4 JDBC in five steps

```java
Class.forName("com.mysql.cj.jdbc.Driver");                       // 1. load the driver
Connection con = DriverManager.getConnection(URL, USER, PASS);   // 2. connect
Statement st = con.createStatement();                            // 3. create a statement
ResultSet rs = st.executeQuery("SELECT * FROM students");        // 4. run SQL
while (rs.next()) { rs.getString("name"); }                      // 5. read rows
```

Use `PreparedStatement` with `?` placeholders whenever user input is part of the SQL (prevents SQL injection). `try-with-resources` closes everything automatically.

---

## 4. CarBackend

**Location:** `CarBackend/` · **Package:** `com.example.CarBackend` · **Port:** 8080 · **DB:** MongoDB `Car`, collection `CarDealership`

### 4.1 File tree

```
CarBackend/
├── pom.xml                              Maven build file (dependencies)
├── mvnw, mvnw.cmd, .mvn/                Maven Wrapper: run Maven without installing it
├── .gitattributes, .gitignore
└── src/
    ├── main/java/com/example/CarBackend/
    │   ├── CarBackendApplication.java   Entry point
    │   ├── Model/CarModel.java          The Car document
    │   ├── Repository/CarRepository.java  DB access
    │   ├── Service/CarService.java      Business logic
    │   └── Controller/CarController.java  REST endpoints
    ├── main/resources/application.properties
    └── test/java/.../CarBackendApplicationTests.java
```

### 4.2 `pom.xml`

Maven's project file. Key parts:

- **`<parent> spring-boot-starter-parent`** — supplies compatible versions of everything, so dependencies need no version numbers.
- **`<java.version>21</java.version>`** — compiles for Java 21.
- **Dependencies**
  - `spring-boot-starter-data-mongodb` — MongoDB support (`MongoRepository`, `MongoTemplate`).
  - `spring-boot-starter-webmvc` — REST controllers and the embedded web server.
  - `lombok` (optional) — generates boilerplate code at compile time.
  - `*-test` starters — testing support (test scope only).
- **`maven-compiler-plugin` → `annotationProcessorPaths`** — tells the compiler to run Lombok so `@Data` works.

### 4.3 `application.properties`

```properties
spring.application.name=CarBackend
spring.mongodb.uri=mongodb://localhost:27017/Car   # connect to local MongoDB, database "Car"
server.port=8080                                   # the web server port
```

> In Spring Boot 4 the property is `spring.mongodb.uri`. Older Boot 3.x projects (e.g. the lab) use `spring.data.mongodb.uri`.

### 4.4 `CarBackendApplication.java`

```java
@SpringBootApplication
public class CarBackendApplication {
    public static void main(String[] args) { SpringApplication.run(CarBackendApplication.class, args); }
}
```

The entry point. `@SpringBootApplication` makes Spring scan `com.example.CarBackend` and every sub-package, creating the controller, service and repository automatically. Run this to start the server.

### 4.5 `Model/CarModel.java`

```java
@Data
@Document(collection = "CarDealership")
public class CarModel {
    @Id private String id;           // Mongo generates it if null
    private String brand, model, type, fuelType;
    private int year, pricePerDay;
    private boolean available;
    private List<String> features;
}
```

One Java object ⇄ one MongoDB document. Stored JSON looks like:

```json
{ "_id": "65af...", "brand": "Toyota", "model": "Corolla", "year": 2022, "type": "Sedan",
  "fuelType": "Petrol", "pricePerDay": 50, "available": true, "features": ["AC", "GPS"] }
```

### 4.6 `Repository/CarRepository.java`

```java
@Repository
public interface CarRepository extends MongoRepository<CarModel, String> { }
```

An **empty** interface — `MongoRepository<CarModel, String>` (model type, id type) already provides `findAll()`, `findById()`, `save()`, `deleteById()`.

### 4.7 `Service/CarService.java`

| Method | What it does |
|---|---|
| `getAll()` | `repo.findAll()` → every car |
| `getById(id)` | `repo.findById(id).orElse(null)` → one car or `null` (currently unused by the controller) |
| `addCar(model)` | `repo.save(model)` → insert (id is `null`) |
| `deleteById(id)` | `repo.deleteById(id)` and returns the text `"Deleted"` |
| `changeNameById(id, newname)` | Finds the car; if present sets `model = newname` and saves; otherwise `null` |

`changeNameById` shows the standard **find → modify → save** pattern: calling `save` on an object that already has an `id` performs an **update**.

### 4.8 `Controller/CarController.java`

Class-level: `@RestController`, `@RequestMapping("/")`, `@CrossOrigin(origins = "*")`.

| HTTP | URL | Method | Result |
|---|---|---|---|
| GET | `/` | `getAll()` | JSON array of cars |
| POST | `/create` | `add(@RequestBody CarModel)` | The saved car |
| PUT | `/update/{id}?newname=Civic` | `update(...)` | The car with its new `model`, or `null` |
| DELETE | `/delete/{id}` | `delete(...)` | `"Deleted"` |

Try it:

```bash
curl http://localhost:8080/
curl -X POST http://localhost:8080/create -H "Content-Type: application/json" \
     -d '{"brand":"Honda","model":"Civic","year":2023}'
curl -X PUT "http://localhost:8080/update/<id>?newname=Accord"
curl -X DELETE http://localhost:8080/delete/<id>
```

### 4.9 `CarBackendApplicationTests.java`

A single `@SpringBootTest` with an empty `contextLoads()` test: it just checks that the whole application context starts. Run with `./mvnw test`.

---

## 5. CarFrontend

**Location:** `CarFrontend/` · **Tooling:** Vue 3 + Vite · **Dev URL:** http://localhost:5173

### 5.1 Files

| File | Purpose |
|---|---|
| `package.json` | Dependencies (`vue`) and scripts (`dev`, `build`, `preview`); needs Node 22.18+ or 24.12+ |
| `vite.config.js` | Enables the Vue plugin + Vue DevTools; defines the `@` alias for `./src` |
| `jsconfig.json` | Lets the editor understand the `@/` alias |
| `index.html` | Page skeleton with `<div id="app">`; loads `/src/main.js` |
| `src/main.js` | `createApp(App).mount('#app')` — starts Vue |
| `src/App.vue` | Root component; just renders `<Car />` |
| `src/components/Car.vue` | **The real UI** (all car logic lives here) |
| `src/assets/main.css`, `base.css` | Styling (from the Vite template) |
| `src/components/TheWelcome.vue`, `HelloWorld.vue`, `WelcomeItem.vue`, `icons/*` | Unused scaffold from the Vite starter template |

### 5.2 `Car.vue` explained

**State**

```js
const cars = ref([])          // list shown on screen
const brand = ref(''), model = ref(''), year = ref(2024)   // "add car" inputs
const newName = ref('')       // text used when renaming
```

**Functions** (each calls the backend with `fetch`, then calls `getCars()` to refresh the list)

| Function | Request |
|---|---|
| `getCars()` | `GET http://localhost:8080/` → `cars.value = await res.json()` |
| `addCar()` | `POST /create` with header `Content-Type: application/json` and `JSON.stringify({brand, model, year})` |
| `updateCar(id)` | `PUT /update/<id>?newname=<newName>` |
| `deleteCar(id)` | `DELETE /delete/<id>` |

`getCars()` is also called once at the bottom of `<script setup>`, so the list loads when the page opens.

**Template** — three inputs with `v-model`, an **Add** button, then `v-for="car in cars"` printing each car with **Rename** and **Delete** buttons.

---

## 6. CarDealerPractice

A practice copy of the same app, used to rehearse writing it from scratch.

### 6.1 `CarBackendPractice/demo/` (package `com.example.demo`)

Same architecture as CarBackend, with small differences:

| Difference | CarBackend | Practice |
|---|---|---|
| Package | `com.example.CarBackend` | `com.example.demo` |
| `pricePerDay` | `int` | `double` |
| Service update method | `changeNameById` | `updateCarNamee` |
| Delete response | `"Deleted"` | `"Deleted <id>"` (controller builds it) |
| Properties | `spring.mongodb.uri=` | `spring.mongodb.uri:` (colon is also valid in `.properties`) |

The `.idea/` folder next to `demo/` is IntelliJ project configuration (compiler, encodings, Maven repositories) and has no runtime effect.

### 6.2 `CarFrontendPractice/` (Vue Router practice)

| File | Role |
|---|---|
| `src/main.js` | Creates the app, then `app.use(router)` before `mount` |
| `src/route/index.js` | Router definition (below) |
| `src/App.vue` | `<nav>` of `<Router-Link>`s + `<RouterView/>` |
| `src/components/showCars.vue` | **Implemented**: button calls `getAll()` → `fetch("http://localhost:8080")` → fills `items`; shows `id : model : type`; errors trigger `alert(...)` |
| `addCar.vue`, `updateCar.vue`, `deleteCar.vue` | **Empty scaffolds** (blank `<script setup>`, `<template>`, `<style scoped>`) ready to fill in |

**Router**

```js
const routes = [
  { path: "/",          component: ShowCars },
  { path: "/addCar",    component: AddCar },
  { path: "/updateCar", component: UpdateCar },
  { path: "/deleteCar", component: DeleteCar },
];
const router = createRouter({ history: createWebHistory(), routes });
```

- A **route** = `{ path, component }`: when the URL is `path`, show `component` inside `<RouterView/>`.
- `createWebHistory()` gives clean URLs (`/addCar` rather than `/#/addCar`).
- `@/components/...` uses the alias configured in `vite.config.js`.

---

## 7. HelloServlet and CheckServlet

Both use the layout **Tomcat expects for a web app**:

```
<name>/
├── index.html                   Front page (the form)
└── WEB-INF/
    ├── web.xml                  URL -> servlet mapping
    └── classes/
        ├── <Name>.java          Source
        └── <Name>.class         Compiled by javac (committed to the repo)
```

Put the folder in `tomcat/webapps/` and open `http://localhost:8080/<name>/`.

### 7.1 HelloServlet

**`index.html`** — a form: `POST` to `hello`, one text input named `name`.

**`HelloServlet.java`**

```java
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String name = req.getParameter("name");      // value from <input name="name">
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<h1>" + name + "</h1>");        // print it back
        out.close();
    }
}
```

**`web.xml`** maps `/hello` → class `HelloServlet`. The form's `action="hello"` therefore reaches `doPost`.

> Only `doPost` exists, so opening `/hello` directly in the address bar (a GET) gives **405**.

### 7.2 CheckServlet

**`index.html`** — a form posting to `check` with a slider (`type="range"`, 0–50) called `age`.

**`CheckServlet.java`**

```java
int age = Integer.parseInt(req.getParameter("age"));   // text -> number
String result = age >= 18 ? "eligible to vote" : "not elegible to vote";
out.print("<h1> You are " + result + "</h1>");
```

**`web.xml`** maps `/check` → `CheckServlet`.

### 7.3 `commands.txt`

A step-by-step cheat sheet for HelloServlet: check the Tomcat version (9 = `javax`, 10+ = `jakarta`), stop Spring Boot (it also uses 8080), compile with
`javac -cp ../../lib/servlet-api.jar -d WEB-INF/classes WEB-INF/classes/HelloServlet.java`, restart Tomcat with `bin/shutdown.sh && bin/startup.sh`, and a table of HTTP errors (404 = wrong url-pattern, 405 = wrong method signature, 500 = read `logs/catalina.out`).

---

## 8. DocumentedCodeModelLab

A heavily commented **Student Manager** that maps each course topic to a file. Its own `README.md` has the topic → file table. Student fields: `id, name, email, course, age, photoUrl, skills[], joinedDate`.

### 8.1 `backend/` (Spring Boot 3.3.5, Java 17, MongoDB `studentdb`)

Package `com.java.example.demo`, port 8080, CORS allowed only from `http://localhost:5173`.

| File | Description |
|---|---|
| `DemoApplication.java` | Entry point (`@SpringBootApplication`) |
| `model/Student.java` | `@Document(collection="students")` with Lombok `@Data @NoArgsConstructor @AllArgsConstructor` |
| `repository/StudentRepository.java` | `extends MongoRepository<Student, String>` |
| `service/StudentService.java` | `@Service @RequiredArgsConstructor`; CRUD logic |
| `controller/StudentController.java` | REST endpoints under `/api/students` |
| `application.properties` | `server.port=8080`, `spring.data.mongodb.uri=mongodb://localhost:27017/studentdb` |
| `pom.xml` | `spring-boot-starter-web`, `spring-boot-starter-data-mongodb`, `lombok` |
| `target/`, `.idea/` | Build output / IDE settings (generated) |

**Endpoints**

| HTTP | URL | Service call | Response |
|---|---|---|---|
| GET | `/api/students` | `getAll()` | list |
| GET | `/api/students/{id}` | `getById(id)` → `Optional` | 200 + student, or **404** |
| POST | `/api/students` | `create(student)` | saved student |
| PUT | `/api/students/{id}` | `update(id, data)` | 200 + student, or **404** |
| DELETE | `/api/students/{id}` | `delete(id)` | **204** if deleted, **404** if missing |

`ResponseEntity` lets a controller choose the HTTP status. The pattern
`service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build())` means *"if found → 200 with body; otherwise → 404"*.

`StudentService.update` copies each field from the incoming object onto the existing one, then `save`s it. `delete` first checks `existsById` so it can report success or failure.

### 8.2 `frontend/` (Vue 3 + Router + Pinia, port 5173)

| File | Role |
|---|---|
| `src/main.js` | `createApp(App)` → `.use(createPinia())` → `.use(router)` → `.mount('#app')` |
| `src/router/index.js` | Routes: `/` students list, `/add` form, `/edit/:id` form (same view), `/playground` |
| `src/stores/studentStore.js` | **Pinia store**: state `students`, `error`; getter `total`; actions `fetchStudents`, `fetchOne`, `addStudent`, `updateStudent`, `deleteStudent` (all call the REST API, then refresh the list) |
| `src/App.vue` | Nav bar (`<router-link>`) + `<router-view />`; contains an *internal* scoped style block |
| `src/views/StudentsView.vue` | Home page: search box (computed `filtered` list), passes `:students` **prop** to the table, listens to `@edit`/`@remove` **emits** |
| `src/views/FormView.vue` | Add *and* edit page. `route.params.id` decides: if present → load student and call `updateStudent`, else `addStudent`; then `router.push('/')` |
| `src/views/PlaygroundView.vue` | Mini demos: functions, objects, arrays, String, Date, JSON, mouse/keyboard/form events |
| `src/components/StudentTable.vue` | Child. **Prop** `students`; **emits** `edit`, `remove`. Shows an HTML table, nested `<ul>` for skills, `<img>`, hover highlight via mouse events, `Date` formatting |
| `src/components/StudentForm.vue` | Child. **Prop** `student` (defaults to an empty one); **emits** `save`, `cancel`. Keeps a local `reactive` copy, validates name on `input`, converts the comma-separated skills text into an array on submit, `Esc` cancels |
| `src/assets/main.css` | **External** stylesheet demonstrating selectors (element, id, class, descendant, child, attribute, pseudo-class/element), box model, transitions (`.btn:hover`) and `@keyframes` animations (`fadeIn`, `pulse`) |
| `index.html`, `vite.config.js` | Page shell; Vite config fixes the port to **5173** (must match the backend's CORS setting) |

**Data flow for "delete a student"**

```
click Delete (StudentTable)  ──emit('remove', id)──►  StudentsView.remove(id)
        ──► confirm()  ──► store.deleteStudent(id)  ──► DELETE /api/students/id
        ──► store.fetchStudents()  ──► store.students changes  ──► table re-renders
```

The three CSS "style sheet types" are all present: **external** (`main.css`), **internal** (`<style scoped>` in `App.vue`), **inline** (`style="..."` on the `<h1>` in `StudentsView.vue`).

### 8.3 `servlet-app/` (Tomcat 9 + MySQL, built as a WAR)

```
servlet-app/
├── pom.xml            packaging=war, finalName=servletdemo; deps: javax.servlet-api 4.0.1 (provided), mysql-connector-j 8.0.33
├── database.sql       Creates DB `servletdb`, table `students`, one sample row
├── lib/README.txt     Which JARs to download if you are not using Maven
├── README.md          Setup steps
└── src/main/
    ├── java/com/example/servlet/
    │   ├── DBConnection.java    JDBC helper
    │   ├── HelloServlet.java    Basic servlet (web.xml mapping)
    │   └── StudentServlet.java  MySQL CRUD (annotation mapping)
    └── webapp/
        ├── index.html           Home links
        ├── storage.html         localStorage / sessionStorage demo
        └── WEB-INF/web.xml      Registers HelloServlet at /hello
```

**`DBConnection.getConnection()`** — loads `com.mysql.cj.jdbc.Driver`, then `DriverManager.getConnection(URL, USER, PASSWORD)` with
`jdbc:mysql://localhost:3306/servletdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`. **Change `USER`/`PASSWORD` to your own.**

**`HelloServlet`** — shows the servlet **life cycle**: `init()` (once), `doGet()`, `doPost()` (just calls `doGet`), `destroy()` (once). Reads `?name=`, defaults to `"World"`, and uses an `escape()` helper that replaces `& < >` so user text cannot inject HTML/JavaScript (XSS protection).

**`StudentServlet`** (`@WebServlet("/students")`)

- `doGet` — prints an add form plus a table built from `SELECT id, name, email, course FROM students`. Each row has a tiny delete form with hidden fields `action=delete` and `id`.
- `doPost` — reads hidden field `action`: `add` runs `INSERT ... VALUES (?, ?, ?)`; `delete` runs `DELETE ... WHERE id = ?`.
- Ends with `response.sendRedirect(.../students)` — the **Post-Redirect-Get** pattern, so refreshing the page does not resubmit the form.

**`storage.html`** — pure browser JavaScript: `localStorage` (persists), `sessionStorage` (cleared when the tab closes), and `JSON.stringify` / `JSON.parse` to store arrays (storage holds text only).

URLs (context path `/servletdemo`): `/hello?name=Asha`, `/students`, `/storage.html`.

---

## 9. OrderUserBackend

**Location:** `OrderUserBackend/` · **Package:** `com.example.demo` · **Port:** 8080 · **DB:** MongoDB `shop` · two collections: `users` and `orders`

### 9.1 What's new here: joining two collections

MongoDB has no SQL `JOIN`. Instead you build an **aggregation pipeline** — a list of stages, each transforming the output of the previous one. `$lookup` is the stage that performs the join.

| Collection | Fields |
|---|---|
| `users` | `_id`, `userId` (int), `name` |
| `orders` | `_id`, `orderId` (String), `userId` (int), `total` (int) |

`orders.userId` points at `users.userId`.

### 9.2 Files

| File | Role |
|---|---|
| `Model/UserModel.java` | `@Document(collection="users")`: `id`, `userId`, `name` |
| `Model/OrderModel.java` | `@Document(collection="orders")`: `id`, `orderId`, `userId`, `total` |
| `Model/OrderDetails.java` | A Java `record(orderId, total, userName)` — the shape of one **joined** row |
| `Repository/UserRepository.java`, `OrderRepository.java` | Empty `MongoRepository` interfaces |
| `Service/Service.java` | CRUD helpers **and the aggregate** |
| `Controller/Controller.java` | REST endpoints |
| `DemoApplication.java`, `application.properties`, `pom.xml` | Same roles as in CarBackend |

> The class is called `Service`, so the annotation is written fully qualified, `@org.springframework.stereotype.Service`; importing `@Service` would clash with the class name.

### 9.3 The aggregate, step by step

```java
public List<OrderDetails> orderDetails(String orderId) {
    List<AggregationOperation> steps = new ArrayList<>();

    if (orderId != null) {
        steps.add(Aggregation.match(Criteria.where("orderId").is(orderId)));          // 1
    }
    steps.add(Aggregation.lookup("users", "userId", "userId", "user"));               // 2
    steps.add(Aggregation.unwind("user"));                                            // 3
    steps.add(Aggregation.project("orderId", "total").and("user.name").as("userName")); // 4

    return mongo.aggregate(Aggregation.newAggregation(steps), "orders", OrderDetails.class)
                .getMappedResults();
}
```

| # | Stage | Meaning | Result for order `O-101` (userId 1) |
|---|---|---|---|
| 1 | `match` | Like `WHERE`. Skipped when `orderId` is `null` (= all orders) | keeps only `O-101` |
| 2 | `lookup(from, localField, foreignField, as)` | Like `JOIN`: for each order find `users` whose `userId` equals the order's `userId`; store them as an **array** called `user` | `{orderId, total, userId:1, user:[{name:"Asha",...}]}` |
| 3 | `unwind("user")` | Turns the 1-element array into a single object (and drops orders with no matching user, like an inner join) | `{..., user:{name:"Asha",...}}` |
| 4 | `project` | Keeps `orderId`, `total`, and renames `user.name` → `userName` | `{orderId:"O-101", total:250, userName:"Asha"}` |

`mongo.aggregate(pipeline, "orders", OrderDetails.class)` runs the pipeline on the `orders` collection and maps each output document onto the `OrderDetails` record. `MongoTemplate` is injected through the constructor like any other bean.

### 9.4 Endpoints

| HTTP | URL | Returns |
|---|---|---|
| GET | `/users` | all users |
| POST | `/users` | saved user (`{"userId":1,"name":"Asha"}`) |
| GET | `/orders` | all raw orders |
| POST | `/orders` | saved order (`{"orderId":"O-101","userId":1,"total":250}`) |
| GET | `/orders/details` | **joined** rows for every order |
| GET | `/orders/details/{orderId}` | **joined** row for one order — i.e. *"name of the user for the selected order"* |

```bash
curl -X POST localhost:8080/users  -H "Content-Type: application/json" -d '{"userId":1,"name":"Asha"}'
curl -X POST localhost:8080/orders -H "Content-Type: application/json" -d '{"orderId":"O-101","userId":1,"total":250}'
curl localhost:8080/orders/details/O-101
# [{"orderId":"O-101","total":250,"userName":"Asha"}]
```

---

## 10. OrderUserFrontend

**Location:** `OrderUserFrontend/` · **Stack:** Vue 3, Vite, **Pinia**, **vue-router** · **Dev URL:** http://localhost:5173 · talks to `OrderUserBackend` on 8080

### 10.1 Files

| File | Role |
|---|---|
| `src/main.js` | `createApp(App).use(createPinia()).use(router).mount('#app')` |
| `src/router/index.js` | `/` → `UsersView`, `/orders` → `OrdersView` |
| `src/stores/shopStore.js` | Pinia store (state + actions) |
| `src/App.vue` | Nav links + `<RouterView/>` + global styles |
| `src/views/UsersView.vue` | Users page |
| `src/views/OrdersView.vue` | Orders page |
| `src/components/UserForm.vue` | Child: **emits** `save` |
| `src/components/OrderForm.vue` | Child: **prop** `users`, **emits** `save` |
| `src/components/OrderTable.vue` | Child: **prop** `orders`, **emits** `select` |

### 10.2 State management — `shopStore.js`

A *setup store*: `defineStore('shop', () => { ... return {...} })`. What you `return` is the public API.

| Name | Kind | Does |
|---|---|---|
| `users` | state (`ref`) | list of users |
| `details` | state (`ref`) | joined order rows from `/orders/details` |
| `loadUsers()` / `loadDetails()` | action | `GET` and store the result |
| `addUser(user)` / `addOrder(order)` | action | `POST`, then reload the list |
| `detailsOf(orderId)` | action | `GET /orders/details/<id>`, returns the first row |

Components call `const store = useShopStore()` and share the same data, so adding a user on one page is instantly visible on the other.

### 10.3 Props and emits — who talks to whom

```
            ┌──────────── OrdersView (parent, uses store) ────────────┐
            │                                                          │
  :users ──►│ OrderForm ──emit('save', order)──► store.addOrder        │
 (prop)     │                                                          │
 :orders ──►│ OrderTable ──emit('select', orderId)──► pick(orderId)    │
 (prop)     │                                   └─► store.detailsOf()  │
            └──────────────────────────────────────────────────────────┘
```

- **`OrderForm`** receives `users` as a prop to fill its dropdown (shows names, submits `userId`), and emits `save` with `{orderId, userId, total}`.
- **`OrderTable`** receives the joined `orders` as a prop and emits `select` with the clicked `orderId`.
- **`OrdersView`** wires them: `@save="store.addOrder"` and `@select="pick"`. `pick` calls the aggregate endpoint for that single order and shows *"Order O-101 was placed by Asha."*
- **`UserForm`** emits `save`; `UsersView` passes it to `store.addUser`.

Template syntax recap: `:users="store.users"` = **pass a prop**; `@save="handler"` = **listen to an emitted event**.

---

## 11. DBServlet

**Location:** `DBServlet/` · same layout as HelloServlet · **Tomcat 9** (`javax.servlet`) · **MySQL JDBC driver jar**

```
DBServlet/
├── index.html                 One link: "Show all rows" -> db
├── schema.sql                 Sample database/table (optional helper)
└── WEB-INF/
    ├── web.xml                /db -> DBServlet
    ├── lib/                   <-- put mysql-connector-j-8.0.33.jar here
    └── classes/DBServlet.java
```

### 11.1 `DBServlet.java`

```java
static final String URL   = "jdbc:mysql://localhost:3306/testdb";   // default local MySQL
static final String USER  = "root";
static final String PASS  = "";
static final String TABLE = "students";
```

`doGet` flow:

1. `Class.forName("com.mysql.cj.jdbc.Driver")` — loads the driver from the jar.
2. `DriverManager.getConnection(URL, USER, PASS)` — opens the connection (try-with-resources closes it for you).
3. `executeQuery("SELECT * FROM " + TABLE)` — fetches every row.
4. `ResultSetMetaData` gives the column count and names, so the servlet works for **any** table without hard-coding columns.
5. Prints `<th>` for each column name, then loops `while (rs.next())` printing `<td>` for each value via `rs.getString(i)` (columns are numbered from **1**).
6. Any exception is shown as an `<h1>Error: ...</h1>` message.

> `TABLE` is a constant in the code, not user input, so concatenating it into the SQL is safe here. Never concatenate request parameters into SQL.

### 11.2 Set up and run

```bash
# 1. create sample data
mysql -u root -p < DBServlet/schema.sql

# 2. add the JDBC driver
curl -L -o DBServlet/WEB-INF/lib/mysql-connector-j-8.0.33.jar \
  https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar

# 3. copy to Tomcat and compile
cp -r DBServlet ~/Downloads/tomcat/webapps/
cd ~/Downloads/tomcat/webapps/DBServlet
javac -cp ../../lib/servlet-api.jar -d WEB-INF/classes WEB-INF/classes/DBServlet.java

# 4. restart Tomcat and open http://localhost:8080/DBServlet/
cd ~/Downloads/tomcat && bin/shutdown.sh; bin/startup.sh
```

If your MySQL has a password or a different database name, edit the four constants at the top of the servlet and recompile.

---

## 12. Running everything

| Project | Prerequisites | Command | URL |
|---|---|---|---|
| CarBackend | Java 21, MongoDB on 27017 | `cd CarBackend && ./mvnw spring-boot:run` | http://localhost:8080 |
| CarFrontend | Node 22.18+ | `cd CarFrontend && npm install && npm run dev` | http://localhost:5173 |
| CarBackendPractice | Java 21, MongoDB | `cd CarDealerPractice/CarBackendPractice/demo && ./mvnw spring-boot:run` | :8080 |
| CarFrontendPractice | Node | `cd CarDealerPractice/CarFrontendPractice && npm install && npm run dev` | :5173 |
| Lab backend | Java 17, MongoDB | `cd DocumentedCodeModelLab/backend && mvn spring-boot:run` | :8080 |
| Lab frontend | Node | `cd DocumentedCodeModelLab/frontend && npm install && npm run dev` | :5173 |
| Lab servlet-app | Tomcat 9, MySQL | `mvn clean package`, copy `target/servletdemo.war` to `webapps/` | :8080/servletdemo/ |
| **OrderUserBackend** | Java 21, MongoDB | `cd OrderUserBackend && ./mvnw spring-boot:run`  | :8080 |
| **OrderUserFrontend** | Node 22.18+ | `cd OrderUserFrontend && npm install && npm run dev` | :5173 |
| **DBServlet** | Tomcat 9, MySQL, JDBC jar | see section 11.2 | :8080/DBServlet/ |

Every backend and Tomcat use **port 8080**, so run only **one at a time** (or change `server.port`).

---

## 13. Troubleshooting & known gotchas

| Symptom | Cause / fix |
|---|---|
| Browser console: *blocked by CORS policy* | Controller needs `@CrossOrigin`; the lab backend only allows origin `http://localhost:5173` |
| `Port 8080 was already in use` | Another backend or Tomcat is running — stop it |
| Frontend shows an empty list | Backend or MongoDB is not running; check the browser's Network tab |
| Servlet **404** | `url-pattern` in `web.xml` doesn't match the form `action` / link, or the app folder isn't directly under `webapps/` |
| Servlet **405** | Servlet only has `doPost` but you opened it with a GET (typed the URL) |
| Servlet **500** | Read `tomcat/logs/catalina.out` for the stack trace |
| `package javax.servlet does not exist` | You are on Tomcat 10+; switch imports to `jakarta.servlet` (or use Tomcat 9) |
| DBServlet prints `Error: com.mysql.cj.jdbc.Driver` | The JDBC jar is not in `WEB-INF/lib` |
| DBServlet prints `Access denied` / `Unknown database` | Fix `USER`/`PASS`/`URL` and run `schema.sql` |
| `$lookup` returns nothing | `orders.userId` has no matching `users.userId` (an inner join through `unwind` drops those orders) |
| `lombok` getters not found | The IDE needs the Lombok plugin/annotation processing enabled |

**Small things worth knowing about the existing code**

- `CheckServlet` does not set a content type and would return a 500 if `age` were missing or non-numeric.
- `HelloServlet` prints the name without escaping; the lab's `HelloServlet.escape()` shows the safe way.
- `Car.vue` builds the PUT URL by string concatenation; use `encodeURIComponent(newName)` for names with spaces or symbols.
- The `year` field in `Car.vue` comes from a text box as a string; Spring's JSON parser converts `"2024"` to `int`.
- `CarService.getById` is not exposed by any endpoint yet.
- `CarFrontendPractice/package.json` lists a `wad-lab` dependency that none of the viewed code imports.
- Compiled `.class` files and `target/` folders are committed in a few projects; they can be regenerated and normally belong in `.gitignore`.
