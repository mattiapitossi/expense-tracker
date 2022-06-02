<template>
   <header class="container">
      <RouterLink to="#">
         Home
      </RouterLink>
      <RouterLink to="#">
         Expenses
      </RouterLink>
      <RouterLink to="#">
         Categories
      </RouterLink>
      <RouterLink to="#">
         Wallet
      </RouterLink>
   </header>
</template>

<script>
import { RouterLink, RouterView } from 'vue-router'
export default {
   
   name: "Header"
}
</script>