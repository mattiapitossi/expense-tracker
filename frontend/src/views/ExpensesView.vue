<template>
   <main class="container">

      <div class="d-flex justify-content-between py-5">
         <h1>EXPENSES</h1>
         <button class="btn btn-success" @click="animateModal(true), getCategories(), getSubcategories(), getWallets()">ADD EXPENSE</button>
      </div>

      <!-- //MODAL// -->
      <div class="back_overlay" @click="animateModal(false)" v-show="showModal"></div> <!-- //OVERLAY -->


      <!-- //TODO CLOSE BUTTON -->
      <div v-show="showModal" class="pop_up_form">

         <Loading v-if="isLoadingForm" />

         <form v-else @submit.prevent="modify ? modifyExpense() : addExpense()">
            <div class="mb-3 me-3 d-inline-block">
               <label for="category" class="form-label d-block">Category</label>
               <RouterLink v-if="categories == null || categories.length == 0" class="fs-4 text-black" :to="{name: 'categories'}">&plus;</RouterLink>
               <select v-else name="category" id="category" v-model="data.category">
                  <option v-for="item in categories" :key="item.id" :value="item.name">{{ item.name }}</option>
               </select>
            </div>
            <div class="mb-3 me-3 d-inline-block">
               <label for="subcategory" class="form-label d-block">Subcategory</label>
               <RouterLink v-if="subcategories == null || subcategories.length == 0" class="fs-4 text-black" :to="{name: 'categories'}">&plus;</RouterLink>
               <select v-else name="subcategory" id="subcategory" v-model="data.subcategory">
                  <option v-for="item in subcategories" :key="item.id" :value="item.name">{{ item.name }}</option>
               </select>
            </div>
            <div class="mb-3 me-3 d-inline-block">
               <label for="wallet" class="form-label d-block">Wallet</label>
               <RouterLink v-if="wallets == null || wallets.length == 0" class="fs-4 text-black" :to="{name: 'wallets'}">&plus;</RouterLink>
               <select v-else name="wallet" id="wallet" v-model="data.wallet">
                  <option v-for="item in wallets" :key="item.id" :value="item.name">{{ item.name }}</option>
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
            <div class="mb-3">
               <label for="value" class="form-label">Value</label>
               <input type="number" class="form-control" id="value" name="value" v-model="data.value" step="0.10">
            </div>
            <div class="mb-3">
               <label for="expense_date" class="form-label">Date</label>
               <input type="date" class="form-control" id="expense_date" name="expense_date" v-model="data.expense_date">
            </div>
            <div class="mb-3">
               <label for="description" class="form-label">Description</label>
               <textarea class="form-control" id="description" name="description" v-model="data.description"
                  placeholder="Description..."></textarea>
            </div>
            <div class="mb-3">
               <label for="location" class="form-label">Location</label>
               <input type="text" class="form-control" id="location" name="location" v-model="data.location"
                  placeholder="Location">
            </div>
            <div class="py-3 d-flex justify-content-center">
               <button :disabled="(categories == null || categories.length == 0) && (subcategories == null || subcategories.length == 0)"
               type="submit" class="btn btn-primary d-block">Submit</button>
            </div>
         </form>
      </div>


      <!-- //TABLE -->
      <div class="d-flex justify-content-center">

         <Loading v-if="isLoading" />

         <h2 v-else-if="expenses == null || expenses.length == 0">No expenses present. Add one!</h2>

         <table v-else class="table">
            <thead>
               <tr>
                  <th scope="col">#</th>
                  <th scope="col">Category</th>
                  <th scope="col">Subcategory</th>
                  <th scope="col">Description</th>
                  <th scope="col">Value</th>
                  <th scope="col">Date</th>
                  <th scope="col">Trasaction type</th>
                  <th scope="col">Location</th>
                  <th scope="col">Wallet</th>
                  <th scope="col">Actions</th>
               </tr>
            </thead>
            <tbody>
               <tr v-for="expense in expenses" :key="expense.id">
                  <th scope="row">{{ expense.id }}</th>
                  <td>{{ expense.category.name }}</td>
                  <td>{{ expense.subcategory != null ? expense.subcategory.name : null}}</td>
                  <td>{{ expense.description }}</td>
                  <td>{{ expense.value }} €</td>
                  <td>{{ expense.expense_date }}</td>
                  <td>{{ expense.typeOfTransaction }}</td>
                  <td>{{ expense.location }}</td>
                  <td>{{ expense.wallet.name }}</td>
                  <td>
                     <button @click="animateModal(true), fillFormFields(expense)" class="btn btn-primary mx-2">
                        <i class="fa-solid fa-pen"></i>
                     </button>
                     <button @click="!isLoading ? duplicateExpense(expense.id) : null" class="btn btn-success mx-2">
                        <i class="fa-regular fa-clone"></i>
                     </button>
                     <button @click="deleteExpense(expense.id)" class="btn btn-danger mx-2">
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
         modify: false,
         isLoading: false,
         isLoadingForm: false,
         showModal: false,
         expenses: null,
         categories: null,
         wallets: null,
         subcategories: null,
         //form field ->
         data: {
            id: null,
            description: null,
            location: null,
            expense_date: this.getTodayDate(),
            value: null,
            category: null,
            subcategory: null,
            wallet: null,
            typeOfTransaction: "OUT"
         },
      }
   },

   methods: {

      getCategories() {
         this.isLoadingForm = true
         this.axios.get("api/category")
            .then(response => {
               this.categories = response.data;
               if (this.categories != 0) {
                  this.data.category = this.categories[0].name;
               }
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

      getSubcategories() {
            this.isLoadingForm = true;
            this.axios.get("api/subcategory")
               .then(response => {
                  this.subcategories = response.data;
                  if (this.subcategories != 0) {
                  this.data.subcategory = this.subcategories[0].name;
               }
                  this.isLoadingForm = false;
               });
      },

      getCategoriesForModify(expenseCategoryId) {
         this.isLoadingForm = true
         this.axios.get("api/category")
            .then(response => {
               this.categories = response.data
               this.setCategory(expenseCategoryId)
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

      getSubcategoriesForModify(expenseSubcategoryId) {
         this.isLoadingForm = true
         this.axios.get("api/subcategory")
            .then(response => {
               this.subcategories = response.data
               this.setSubcategory(expenseSubcategoryId)
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

      getWallets() {
         this.isLoadingForm = true
         this.axios.get("api/wallets")
            .then(response => {

               //check if no wallets are present
               if (response.data.length != 0) {
                  this.wallets = response.data;
                  this.data.wallet = this.wallets[0].name;
               }
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

      getWalletsForModify(expenseWalletId) {
         this.isLoadingForm = true
         this.axios.get("api/wallets")
            .then(response => {
               this.wallets = response.data
               this.setWallet(expenseWalletId);
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

      duplicateExpense(expenseId) {
         this.isLoading = true;

         this.axios.post("api/expenses/" + expenseId)
            .then(response => {
               this.getExpenses();
               this.isLoading = false;
            })
            .catch(error => {
               console.log(error);
            })
      },

      getExpenses() {
         this.isLoading = true;
         this.axios.get("api/expenses")
            .then(response => {
               this.expenses = response.data;
               this.isLoading = false;
            });
      },

      modifyExpense() {
         this.isLoadingForm = true;

         this.axios.put("api/expenses", this.data)
            .then(response => {
               this.getExpenses();
               this.isLoadingForm = false;
               this.animateModal(false);
            })
            .catch(error => {
               console.log(error);
            })
      },

      deleteExpense(expenseId) {
         this.axios.delete("api/expenses/" + expenseId)
            .then(response => {
               this.getExpenses();
            })
            .catch(error => {
               console.log(error);
            })
      },

      //UTILS

      getTodayDate() {
         return dayjs(Date.now()).format("YYYY-MM-DD");
      },

      fillFormFields(expense) {

         this.getCategoriesForModify(expense.category.id);
         this.getWalletsForModify(expense.wallet.id);
         if (expense.subcategory == null) {
            this.getSubcategoriesForModify(0);
         } else {
            this.getSubcategoriesForModify(expense.subcategory.id);
         }

         //modify mode ON
         this.modify = true;

         this.data.id = expense.id;
         this.data.description = expense.description;
         this.data.location = expense.location;
         this.data.expense_date = expense.expense_date;
         this.data.value = expense.value;
         this.data.typeOfTransaction = expense.typeOfTransaction;
      },

      setCategory(expenseCategoryId) {
         this.categories.forEach(category => {
            if (category.id === expenseCategoryId) {
               this.data.category = category.name;
            }
         });
      },
      setSubcategory(expenseSubcategoryId) {
         this.subcategories.forEach(subcategory => {
            if (subcategory.id === expenseSubcategoryId) {
               this.data.subcategory = subcategory.name;
            }
         });
      },

      setWallet(expenseWalletId) {
         this.wallets.forEach(wallet => {
            if (wallet.id === expenseWalletId) {
               this.data.wallet = wallet.name;
            }
         });
      },

      resetData() {
         //modify mode OFF
         this.modify = false;

         this.data.id = null
         this.data.description = null;
         this.data.location = null;
         this.data.expense_date = this.getTodayDate();
         this.data.value = null;
         this.data.category = null;
         this.data.subcategory = null;
         this.data.wallet = null;
         this.data.typeOfTransaction = "OUT";
      },

      animateModal(action) {
         ///check if form is sending new expense
         if (!this.isLoadingForm) {

            /////////// true -> open modal
            /////////// false -> close modal
            if (action) {
               this.resetData();
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
               this.resetData();
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

   },

   mounted() {
      this.getExpenses();
   },
}
</script>