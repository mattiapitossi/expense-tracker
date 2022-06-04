<template>
   <table class="table">
      <thead>
         <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Actions</th>
         </tr>
      </thead>
      <tbody>
         <tr v-for="category in categories" :key="category.id">
            <th scope="row">{{ category.id }}</th>
            <td>{{ category.name }}</td>
            <td>
                  <button @click="$emit('edit', true), $emit('fill', category)" class="btn btn-primary mx-2">
                     <i class="fa-solid fa-pen"></i>
                  </button>
                  <button @click="$emit('delete', category.id)" class="btn btn-danger mx-2">
                     <i class="fa-solid fa-trash"></i>
                  </button>
            </td>
         </tr>
      </tbody>
   </table>
</template>

<script>
export default {

   name: "Table",
   props: {
      categories: Array
   },

   data() {
      return {
         
      }
   },

   methods: {

   },

   mounted() {

   }
}
</script>

<style>

</style>