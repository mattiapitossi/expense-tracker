<template>
   <main class="container">

      <div class="d-flex justify-content-between py-5">
         <h1>EXPENSES</h1>
         <button class="btn btn-success"
            @click="animateModal(true)"
         >ADD EXPENSE</button>
      </div>

      <!-- //MODAL// -->
      <div class="back_overlay"
         @click="animateModal(false)"
         v-show="showModal"
      ></div> <!-- //OVERLAY -->


      <!-- //TODO CLOSE BUTTON -->
      <div v-show="showModal" class="pop_up_form">
         <form>
            <div class="mb-3">
               <label for="title" class="form-label">Title</label>
               <input type="text" class="form-control" id="title" name="title" placeholder="Title">
            </div>
            <div class="mb-3">
               <label for="description" class="form-label">Description</label>
               <textarea class="form-control" id="description" name="description" placeholder="Description..."></textarea>
            </div>
            <div class="mb-3">
               <label for="location" class="form-label">Location</label>
               <input type="text" class="form-control" id="location" name="location" placeholder="Location">
            </div>
            <div class="mb-3">
               <label for="expense_date" class="form-label">Date</label>
               <input type="date" class="form-control" id="expense_date" name="expense_date">
            </div>
            <div class="mb-3">
               <label for="value" class="form-label">Value</label>
               <input type="number" class="form-control" id="value" name="value">
            </div>
            <div class="mb-3 me-3 d-inline-block">
               <label for="category" class="form-label d-block">Category</label>
               <select name="category" id="category">
                  <option value="">Choose one</option>
                  <option v-for="item in categories"
                     :key="item.id"
                     :value="item.name"
                  >{{item.name}}</option>
               </select>
            </div>
            <div class="mb-3 me-3 d-inline-block">
               <label for="type_of_transaction" class="form-label d-block">Transaction type</label>
               <select name="type_of_transaction" id="type_of_transaction">
                  <option value="OUT">OUT</option>
                  <option value="IN">IN</option>
                  <option value="INOUT">INOUT</option>
                  <option value="OUTIN">OUTIN</option>
               </select>
            </div>
            <div class="mb-3 me-3 form-check d-inline-block">
               <label for="type_of_payment" class="form-label d-block">Payment type</label>
               <select name="type_of_payment" id="type_of_payment">
                  <option value="">Choose one</option>
                  <option value="CASH">CASH</option>
                  <option value="CARD">CARD</option>
               </select>
            </div>
            <div class="py-3 d-flex justify-content-center">
               <button type="submit" class="btn btn-primary d-block">Submit</button>
            </div>
         </form>
      </div>
      

      <!-- //TABLE -->
      <div class="d-flex justify-content-center">

         <Loading v-if="isLoading" />
   
         <table v-else class="table">
            <thead>
               <tr>
                  <th scope="col">#</th>
                  <th scope="col">Title</th>
                  <th scope="col">Description</th>
                  <th scope="col">Category</th>
                  <th scope="col">Value</th>
                  <th scope="col">Date</th>
                  <th scope="col">Trasaction type</th>
                  <th scope="col">Payment type</th>
                  <th scope="col">Location</th>
               </tr>
            </thead>
            <tbody>
               <tr v-for="expense in expenses"
                  :key="expense.id"
               >
                  <th scope="row">{{expense.id}}</th>
                  <td>{{expense.title}}</td>
                  <td>{{expense.description}}</td>
                  <td>{{expense.category.name}}</td>
                  <td>{{expense.value}}</td>
                  <td>{{expense.expense_date}}</td>
                  <td>{{expense.typeOfPayment}}</td>
                  <td>{{expense.typeOfTransaction}}</td>
                  <td>{{expense.location}}</td>
               </tr>
            </tbody>
         </table>

      </div>
      
   </main>
</template>

<script>
import Loading from "@/components/partials/Loading.vue";
import gsap from "gsap";

export default {

   name: "ExpensesView",
   components: { Loading },

   data() {
      return {
         isLoading: false,
         showModal: false,
         expenses: null,
      };
   },

   methods: {

      animateModal(action) {

         if (action) {
            this.showModal = action;
            gsap.fromTo(".pop_up_form", {
                  opacity: 0,
                  top: "-100%",
               },
               {
                  opacity: 1,
                  top: "50%",
                  duration: 0.4
               }
            );
            gsap.fromTo(".back_overlay", {
                  filter: "blur(0px)",
                  backgroundColor: "rgba(0,0,0,0.0)",
               },
               {
                  filter: "blur(1px)",
                  backgroundColor: "rgba(0,0,0,0.4)",
                  duration: 0.4
               }
            );
         } else {
            gsap.fromTo(".pop_up_form", {
                  opacity: 1,
                  top: "50%",
               },
               {
                  opacity: 0,
                  top: "-100%",
                  duration: 0.4
               }
            );
            gsap.fromTo(".back_overlay", {
                  filter: "blur(1px)",
                  backgroundColor: "rgba(0,0,0,0.4)",
               },
               {
                  filter: "blur(0px)",
                  backgroundColor: "rgba(0,0,0,0.0)",
                  delay: 0.2,
                  duration: 0.2
               }
            );
            setTimeout(() => this.showModal = action, 400)
         }
      }
   },

   mounted() {
      this.isLoading = true;
      this.axios.get("api/expenses")
         .then(response => {
         this.expenses = response.data;
         this.isLoading = false;
      });
   },
}
</script>