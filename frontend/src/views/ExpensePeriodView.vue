<template>
  <main class="container">
    <div class="tableContainer tableShadow">
      <div class="d-flex justify-content-between py-3">
        <h1>EXPENSES PERIOD</h1>
        <button
          class="btn btn-success"
          @click="
            animateModal(true), getCategories(), getWallets()
          "
        >
          ADD EXPENSE PERIOD
        </button>
      </div>

      <!-- //MODAL// -->
      <div
        class="back_overlay"
        @click="animateModal(false)"
        v-show="showModal"
      ></div>
      <!-- //OVERLAY -->

      <!-- //TODO CLOSE BUTTON -->
      <div v-show="showModal" class="pop_up_form">
        <Loading v-if="isLoadingForm" />

        <form v-else @submit.prevent="modify ? modifyExpensePeriod() : addExpensePeriod()">
          <div class="mb-3">
            <label for="value" class="form-label">Value</label>
            <input
              type="number"
              class="form-control"
              id="value"
              name="value"
              v-model="data.value"
              placeholder="0.01" 
              step="0.01"
              min="0.01"
            />
          </div>
          <div class="mb-3 me-3 d-inline-block">
            <label for="category" class="form-label d-block">Category</label>
            <RouterLink
              v-if="categories == null || categories.length == 0"
              class="fs-4 text-black"
              :to="{ name: 'categories' }"
              >&plus;</RouterLink
            >
            <select v-else name="category" id="category" v-model="data.category" class="halfSizeSelectOption" @change="getSubcategoriesFromCategory()">
              <option v-for="item in categories" :key="item.id" :value="item.name">
                {{ item.name }}
              </option>
            </select>
          </div>
          <div class="mb-3 me-3 d-inline-block">
            <label for="subcategory" class="form-label d-block">Subcategory</label>
            <RouterLink
              v-if="subcategories == null || subcategories.length == 0"
              class="fs-4 text-black"
              :to="{ name: 'categories' }"
              >&plus;</RouterLink
            >
            <select
              v-else
              name="subcategory"
              id="subcategory"
              v-model="data.subcategory"
              class="halfSizeSelectOption"
              >
              <option
                v-for="item in subcategories"
                :key="item.id"
                :value="item.name"
              >
                {{ item.name }}
              </option>
            </select>
          </div>
          <div class="mb-3 me-3 d-inline-block">
            <label for="wallet" class="form-label d-block">Wallet</label>
            <RouterLink
              v-if="wallets == null || wallets.length == 0"
              class="fs-4 text-black"
              :to="{ name: 'wallets' }"
              >&plus;</RouterLink
            >
            <select v-else name="wallet" id="wallet" v-model="data.wallet" class="halfSizeSelectOption">
              <option v-for="item in wallets" :key="item.id" :value="item.name">
                {{ item.name }}
              </option>
            </select>
          </div>
          <div class="mb-3 me-3 d-inline-block">
            <label for="type_of_transaction" class="form-label d-block"
              >Transaction type</label
            >
            <select
              name="type_of_transaction"
              id="type_of_transaction"
              v-model="data.typeOfTransaction"
              class="halfSizeSelectOption"
            >
              <option value="OUT" selected>OUT</option>
              <option value="IN">IN</option>
            </select>
          </div>
    <div class="mb-3">
            <label for="period_start_date" class="form-label">Start Date</label>
            <input
              type="date"
              class="form-control"
              id="period_start_date"
              name="period_start_date"
              v-model="data.period_start_date"
            />
          </div>
    <div class="mb-3">
            <label for="period_end_date" class="form-label">End Date</label>
            <input
              type="date"
              class="form-control"
              id="period_end_date"
              name="period_end_date"
              v-model="data.period_end_date"
            />
          </div>
          <div class="mb-3 me-3 d-inline-block">
            <label for="period_type" class="form-label d-block"
              >Period type</label
            >
            <select
              name="period_type"
              id="period_type"
              v-model="data.period_type"
              class="halfSizeSelectOption"
            >
              <option value="YEAR">YEAR</option>
              <option value="MONTH">MONTH</option>
              <option value="DAY" selected>DAY</option>
            </select>
          </div>
          <div class="mb-3">
            <label for="value" class="form-label">Period Date</label>
            <input
              type="number"
              class="form-control"
              id="period_date"
              name="period_date"
              v-model="data.period_date"
            />
          </div>
          <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea
              class="form-control"
              id="description"
              name="description"
              v-model="data.description"
              placeholder="Description..."
            ></textarea>
          </div>
          <div class="mb-3">
            <label for="location" class="form-label">Location</label>
            <input
              type="text"
              class="form-control"
              id="location"
              name="location"
              v-model="data.location"
              placeholder="Location"
            />
          </div>
          <div class="py-3 d-flex justify-content-center">
            <button
              :disabled="
                (categories == null || categories.length == 0) &&
                (subcategories == null || subcategories.length == 0)
              "
              type="submit"
              class="btn btn-primary d-block"
            >
              Submit
            </button>
          </div>
        </form>
      </div>

        <!-- //TABLE -->
        <div class="d-flex justify-content-center">

          <Loading v-if="isLoading" />

          <h2 v-else-if="expensesPeriod == null || expensesPeriod.length == 0">No expenses period present. Add one!</h2>

          <table v-else class="table">
              <thead>
                <tr>
                    <th scope="col" class="d-none">#</th>
                    <th scope="col">Start date</th>
                    <th scope="col">Next Payment date</th>
                    <th scope="col">Last Date</th>
                    <th scope="col">Date</th>
                    <th scope="col">Type</th>
                    <th scope="col">Value</th>
                    <th scope="col">Category</th>
                    <th scope="col">Subcategory</th>
                    <th scope="col">Wallet</th>
                    <th scope="col">Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="expensePeriod in expensesPeriod" :key="expensePeriod.id">
                    <th scope="row" class="d-none">{{ expensePeriod.id }}</th>
                    <td>{{ expensePeriod.period_start_date }}</td>
                    <td>{{ expensePeriod.period_next_payment != null ? expensePeriod.period_next_payment : "Recurring payment ended"}}</td>
                    <td>{{ expensePeriod.period_end_date  != null ? expensePeriod.period_end_date : null}}</td>
                    <td>{{ expensePeriod.period_date}}</td>
                    <td>{{ expensePeriod.period_type}}</td>
                    <td v-if="expensePeriod.typeOfTransaction === 'IN'" class="positiveExpense">{{ expensePeriod.value}} €</td>
                    <td v-else-if="expensePeriod.typeOfTransaction === 'OUT'" class="negativeExpense">{{ expensePeriod.value}} €</td>
                    <td>{{ expensePeriod.category.name}}</td>
                    <td>{{ expensePeriod.subcategory != null ? expensePeriod.subcategory.name : null}}</td>
                    <td>{{ expensePeriod.wallet.name }}</td>
                    <td>
                      <button @click="animateModal(true), fillFormFields(expensePeriod)" class="btn btn-primary mx-2">
                          <i class="fa-solid fa-pen"></i>
                      </button>
                      <button @click="!isLoading ? duplicateExpensePeriod(expensePeriod.id) : null" class="btn btn-success mx-2">
                          <i class="fa-regular fa-clone"></i>
                      </button>
                      <button @click="deleteExpensePeriod(expensePeriod.id)" class="btn btn-danger mx-2">
                          <i class="fa-solid fa-trash"></i>
                      </button>
                    </td>
                </tr>
              </tbody>
          </table>
        </div>
      </div>
   </main>
</template>

<script>
import Loading from "@/components/partials/Loading.vue";
import gsap from "gsap";

export default {

   name: "ExpensesPeriodView",
   components: { Loading },

  data() {
    return {
      modify: false,
      isLoading: false,
      isLoadingForm: false,
      showModal: false,
      expenses: null,
      expensesPeriod: null,
      categories: null,
      wallets: null,
      subcategories: null,
      //form field ->
      data: {
        id: null,
        description: null,
        location: null,
        period_start_date: this.getTodayDate(),
        period_end_date: null,
        period_type: null,
        period_date: null,
        category: null,
        subcategory: null,
        wallet: null,
        typeOfTransaction: "OUT",
        value: null,
      },
    };
  },

  methods: {
    getCategories() {
      this.isLoadingForm = true;
      this.axios
        .get("api/category")
        .then((response) => {
          this.categories = response.data;
          if (this.categories != 0) {
            this.data.category = this.categories[0].name;
            this.getSubcategoriesFromCategory();
          } else {
            this.getSubcategories();
          }
          this.isLoadingForm = false;
        })
        .catch((error) => {
          console.log(error);
        });
    },

      getCategoriesForModify(expensePeriodCategoryId) {
         this.isLoadingForm = true
         this.axios.get("api/category")
            .then(response => {
               this.categories = response.data
               this.setCategory(expensePeriodCategoryId)
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

    getSubcategories() {
      this.isLoadingForm = true;
      this.axios.get("api/subcategory").then((response) => {
        this.subcategories = response.data;
        if (this.subcategories != 0) {
          this.data.subcategory = this.subcategories[0].name;
        }
        this.isLoadingForm = false;
      });
    },

      getSubcategoriesForModify(expensePeriodSubcategoryId) {
         this.isLoadingForm = true
         this.axios.get("api/subcategory")
            .then(response => {
               this.subcategories = response.data
               this.setSubcategory(expensePeriodSubcategoryId)
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

      getSubcategoriesFromCategory() {
            this.isLoadingForm = true;
            this.axios.get("api/subcategories/" + this.data.category)
              .then(response => {
                  this.subcategories = response.data;
                  if (this.subcategories != 0) {
                  this.data.subcategory = this.subcategories[0].name;
              }
                  this.isLoadingForm = false;
              });
      },

    getWallets() {
      this.isLoadingForm = true;
      this.axios
        .get("api/wallets")
        .then((response) => {
          //check if no wallets are present
          if (response.data.length != 0) {
            this.wallets = response.data;
            this.data.wallet = this.wallets[0].name;
          }
          this.isLoadingForm = false;
        })
        .catch((error) => {
          console.log(error);
        });
    },

      getWalletsForModify(expensePeriodWalletId) {
         this.isLoadingForm = true
         this.axios.get("api/wallets")
            .then(response => {
               this.wallets = response.data
               this.setWallet(expensePeriodWalletId);
               this.isLoadingForm = false
            })
            .catch(error => {
               console.log(error);
            })
      },

    addExpensePeriod() {
         this.isLoadingForm = true;

         this.axios.post("api/period", this.data)
            .then(response => {
               this.getExpensesPeriod()
               this.isLoadingForm = false
               this.animateModal(false);
            })
            .catch(error => {
               console.log(error);
            })
      },

      getExpensesPeriod() {
         this.isLoading = true;

         this.axios.get("api/period")
            .then(response => {
               this.expensesPeriod = response.data;
               this.isLoading = false;
            });
      },

      duplicateExpensePeriod(expensePeriodId) {
         this.isLoading = true;

         this.axios.post("api/period/" + expensePeriodId)
            .then(response => {
               this.getExpensesPeriod();
               this.isLoading = false;
            })
            .catch(error => {
               console.log(error);
            })
      },

      modifyExpensePeriod() {
         this.isLoadingForm = true;

         this.axios.put("api/period", this.data)
            .then(response => {
               this.getExpensesPeriod();
               this.isLoadingForm = false;
               this.animateModal(false);
            })
            .catch(error => {
               console.log(error);
            })
      },

      deleteExpensePeriod(expensePeriodId) {
         this.axios.delete("api/period/" + expensePeriodId)
            .then(response => {
               this.getExpensesPeriod();
            })
            .catch(error => {
               console.log(error);
            })
      },

    //UTILS

    getTodayDate() {
      return dayjs(Date.now()).format("YYYY-MM-DD");
    },

    fillFormFields(expensePeriod) {
      this.getCategoriesForModify(expensePeriod.category.id);
      this.getWalletsForModify(expensePeriod.wallet.id);
      if (expensePeriod.subcategory == null) {
        this.getSubcategoriesForModify(0);
      } else {
        this.getSubcategoriesForModify(expensePeriod.subcategory.id);
      }

      //modify mode ON
      this.modify = true;

      this.data.id = expensePeriod.id;
      this.data.description = expensePeriod.description;
      this.data.location = expensePeriod.location;
      this.data.period_start_date = expensePeriod.period_start_date,
      this.data.period_end_date = expensePeriod.period_end_date,
      this.data.period_type = expensePeriod.period_type,
      this.data.period_date = expensePeriod.period_date,
      this.data.typeOfTransaction = expensePeriod.typeOfTransaction;
      this.data.value = expensePeriod.value;
    },

      setCategory(expensePeriodCategoryId) {
         this.categories.forEach(category => {
            if (category.id === expensePeriodCategoryId) {
               this.data.category = category.name;
            }
         });
      },

      setSubcategory(expensePeriodSubcategoryId) {
         this.subcategories.forEach(subcategory => {
            if (subcategory.id === expensePeriodSubcategoryId) {
               this.data.subcategory = subcategory.name;
            }
         });
      },

      setWallet(expensePeriodWalletId) {
         this.wallets.forEach(wallet => {
            if (wallet.id === expensePeriodWalletId) {
               this.data.wallet = wallet.name;
            }
         });
      },

    resetData() {
      //modify mode OFF
      this.modify = false;

      this.data.id = null;
      this.data.description = null;
      this.data.location = null;
      this.data.period_start_date = this.getTodayDate(),
      this.data.period_end_date = this.getTodayDate(),
      this.data.period_type = null,
      this.data.period_date = null,
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
          gsap.fromTo(
            ".pop_up_form",
            {
              opacity: 0,
              top: "-100%",
            },
            {
              opacity: 1,
              top: "50%",
              duration: 0.4,
            }
          );
          gsap.fromTo(
            ".back_overlay",
            {
              filter: "blur(0px)",
              backgroundColor: "rgba(0,0,0,0.0)",
            },
            {
              filter: "blur(1px)",
              backgroundColor: "rgba(0,0,0,0.4)",
              duration: 0.4,
            }
          );
        } else {
          this.resetData();
          gsap.fromTo(
            ".pop_up_form",
            {
              opacity: 1,
              top: "50%",
            },
            {
              opacity: 0,
              top: "-100%",
              duration: 0.4,
            }
          );
          gsap.fromTo(
            ".back_overlay",
            {
              filter: "blur(1px)",
              backgroundColor: "rgba(0,0,0,0.4)",
            },
            {
              filter: "blur(0px)",
              backgroundColor: "rgba(0,0,0,0.0)",
              delay: 0.2,
              duration: 0.2,
            }
          );
          setTimeout(() => (this.showModal = action), 400);
        }
      }
    },
  },

   mounted() {
      this.getExpensesPeriod();
   },
};
</script>

