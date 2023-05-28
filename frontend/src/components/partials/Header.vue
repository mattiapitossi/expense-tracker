<template>
   <header style="position: sticky; z-index: 1;">
      <RouterLink class="nav_item" :to="{name: 'home'}">
         Home
      </RouterLink>
      <RouterLink class="nav_item" :to="{name: 'expenses'}">
         Expenses
      </RouterLink>
      <RouterLink class="nav_item" :to="{name: 'categories'}">
         Categories
      </RouterLink>
      <RouterLink class="nav_item" :to="{name: 'wallets'}">
         Wallets
      </RouterLink>
      <RouterLink class="nav_item" :to="{name: 'period'}">
         Expenses Period
      </RouterLink>
      <RouterLink class="nav_item" :to="{name: 'budgets'}">
         Budgets
      </RouterLink>
   </header>
</template>

<script>
export default {
   name: "Header"
}
</script>