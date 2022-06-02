<template>
   <main class="container">

      <div class="d-flex justify-content-between py-5">
         <h1>EXPENSES</h1>
         <button class="btn btn-success"
            @click="animateModal(true), getCategories()"
         >ADD EXPENSE</button>
      </div>

      <!-- //MODAL// -->
      <div class="back_overlay"
         @click="animateModal(false)"
         v-show="showModal"
      ></div> <!-- //OVERLAY -->


      <!-- //TODO CLOSE BUTTON -->
      <div v-show="showModal" class="pop_up_form">

         <Loading v-if="isLoadingForm" />

         <form v-else @submit.prevent="addExpense()">
            <div class="mb-3">
               <label for="title" class="form-label">Title</label>
               <input type="text" class="form-control" id="title" name="title" v-model="data.title" placeholder="Title">
            </div>
            <div class="mb-3">
               <label for="description" class="form-label">Description</label>
               <textarea class="form-control" id="description" name="description" v-model="data.description" placeholder="Description..."></textarea>
            </div>
            <div class="mb-3">
               <label for="location" class="form-label">Location</label>
               <input type="text" class="form-control" id="location" name="location" v-model="data.location" placeholder="Location">
            </div>
            <div class="mb-3">
               <label for="expense_date" class="form-label">Date</label>
               <input type="date" class="form-control" id="expense_date" name="expense_date" v-model="data.expense_date">
            </div>
            <div class="mb-3">
               <label for="value" class="form-label">Value</label>
               <input type="number" class="form-control" id="value" name="value" v-model="data.value">
            </div>
            <div class="mb-3 me-3 d-inline-block">
               <label for="category" class="form-label d-block">Category</label>
               <select name="category" id="category" v-model="data.category">
                  <option v-for="item in categories"
                     :key="item.id"
                     :value="item.name"
                  >{{item.name}}</option>
               </select>
            </div>
            <div class="mb-3 me-3 d-inline-block">
               <label for="type_of_transaction" class="form-label d-block">Transaction type</label>
               <select name="type_of_transaction" id="type_of_transaction" v-model="data.typeOfTransaction">
                  <option value="OUT" selected>OUT</option>
                  <option value="IN">IN</option>
                  <option value="INOUT">INOUT</option>
                  <option value="OUTIN">OUTIN</option>
               </select>
            </div>
            <div class="mb-3 me-3 form-check d-inline-block">
               <label for="type_of_payment" class="form-label d-block">Payment type</label>
               <select name="type_of_payment" id="type_of_payment" v-model="data.typeOfPayment">
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
                  <th scope="col">Actions</th>
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
                  <td>
                     <button class="btn btn-primary mx-2">
                        <i class="fa-solid fa-pen"></i>
                     </button>
                     <button class="btn btn-success mx-2">
                        <i class="fa-regular fa-clone"></i>
                     </button>
                     <button class="btn btn-danger mx-2">
                        <i class="fa-solid fa-trash"></i>
                     </button>
                  </td>
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
         isLoadingForm: false,
         showModal: false,
         expenses: null,
         //form field ->
         data: {
            title: null,
            description: null,
            location: null,
            expense_date: null,
            value: null,
            category: null,
            typeOfTransaction: "OUT",
            typeOfPayment: "CASH",
         },
         categories: null
      }
   },

   methods: {

      getCategories() {
         this.isLoadingForm = true
         this.axios.get("api/category")
            .then(response => {
               this.categories = response.data
               this.data.category = this.categories[0].name;
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

      addExpense() {
         this.isLoadingForm = true;

         this.axios.post("api/expenses", this.data)
         .then(response => {
            this.getExpenses()
            this.isLoadingForm = false
            this.animateModal(false);
         })
         .catch(error => {
            console.log(error);
         })
      },

      animateModal(action) {

         ///check if form is sending new expense
         if (!this.isLoadingForm) {
            
            /////////// true -> open modal
            /////////// false -> close modal
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

      getExpenses() {
         this.isLoading = true;
         this.axios.get("api/expenses")
            .then(response => {
            this.expenses = response.data;
            this.isLoading = false;
         });
      }
   },

   mounted() {
      this.getExpenses();
   },
}
</script>