<template>
   <form @submit.prevent="modify ? $emit('modifyCategory', category) : $emit('sendCategory', category)">
         <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" v-model="category.name" placeholder="Name">
         </div>
         <div v-if="category.hasOwnProperty('category')" class="mb-3 me-3 d-inline-block">
            <label for="category" class="form-label d-block">Category</label>
            <select name="category" id="category" v-model="category.category">
               <option v-for="item in categories" :key="item.id" :value="item.name">{{ item.name }}</option>
            </select>
         </div>
         <div class="py-3 d-flex justify-content-center">
            <button type="submit" class="btn btn-primary d-block">Submit</button>
         </div>
   </form>
</template>

<script>
export default {

   name: "FormCategory",
   props: {
      category: Object,
      categories: Array,
      modify: Boolean
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