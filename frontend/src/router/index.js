import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ExpesesView from '../views/ExpensesView.vue'
import CategoriesView from '../views/CategoriesView.vue'
import WalletsView from '../views/WalletsView.vue'
import ExpensePeriodView from '../views/ExpensePeriodView.vue'
import BudgetView from '../views/BudgetView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  mode: 'history',
  linkExactActiveClass: 'active',

  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/expenses',
      name: 'expenses',
      component: ExpesesView
    },
    {
      path: '/categories',
      name: 'categories',
      component: CategoriesView
    },
    {
      path: '/wallets',
      name: 'wallets',
      component: WalletsView
    },
    {
      path: '/period',
      name: 'period',
      component: ExpensePeriodView
    },
    {
      path: '/budgets',
      name: 'budgets',
      component: BudgetView
    }
  ]
})

export default router
