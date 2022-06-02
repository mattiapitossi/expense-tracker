<template>
   <header>
      <RouterLink class="nav_item" :to="{name: 'home'}">
         Home
      </RouterLink>
      <RouterLink class="nav_item" :to="{name: 'expenses'}">
         Expenses
      </RouterLink>
      <RouterLink class="nav_item" to="#">
         Categories
      </RouterLink>
      <RouterLink class="nav_item" to="#">
         Wallet
      </RouterLink>
   </header>
</template>

<script>
export default {
   name: "Header"
}
</script>