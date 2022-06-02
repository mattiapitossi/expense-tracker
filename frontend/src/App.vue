<template>
   <div class="wrapper">
      <Header />

      <RouterView />
   </div>
</template>

<script setup>
   import { RouterLink, RouterView } from 'vue-router'
   import Header from '@/components/partials/Header.vue'
</script>

<style lang="scss">

</style>
