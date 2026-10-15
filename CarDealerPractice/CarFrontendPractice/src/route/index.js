import AddCar from "@/components/addCar.vue";
import DeleteCar from "@/components/deleteCar.vue";
import ShowCars from "@/components/showCars.vue";
import UpdateCar from "@/components/updateCar.vue";
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  { path: "/", component: ShowCars },
  { path: "/addCar", component: AddCar },
  { path: "/updateCar", component: UpdateCar },
  { path: "/deleteCar", component: DeleteCar },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
