<template>
   <table class="table">
      <thead>
         <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th v-if="(categories.length > 0) && (categories[0].hasOwnProperty('category'))" scope="col">Category</th>
            <th scope="col">Actions</th>
         </tr>
      </thead>
      <tbody>
         <tr v-for="category in categories" :key="category.id">
            <th scope="row">{{ category.id }}</th>
            <td>{{ category.name }}</td>
            <td v-if="category.hasOwnProperty('category')">{{ category.category.name }}</td>
            <td>
               <button @click="openModifyModal(category)" class="btn btn-primary mx-2">
                  <i class="fa-solid fa-pen"></i>
               </button>
               
               <!-- //TODO DELETE ALERT CAUSE CASCADE ON SUBCATEGORIES -->
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
      categories: Array,
   },

   data() {
      return {
         subcategoryOperation: false
      }
   },

   methods: {
      openModifyModal(category) {

         this.categories[0].hasOwnProperty('category') ? this.subcategoryOperation = true : this.subcategoryOperation = false;
         
         this.$emit('setCategoryType', this.subcategoryOperation);
         this.$emit('edit', true);
         this.$emit('fill', category);
      }
   },

   mounted() {

   }
}
</script>

<style>

</style>