<template>
   <main class="container">
      <!-- //TABLE -->
      <div class="d-flex justify-content-center">

         <table class="table">
            <thead>
               <tr>
                  <th scope="col" class="d-none">#</th>
                  <th scope="col">Category</th>
                  <th scope="col">Subcategory</th>
                  <th scope="col">January</th>
                  <th scope="col">February</th>
                  <th scope="col">March</th>
                  <th scope="col">April</th>
                  <th scope="col">May</th>
                  <th scope="col">June</th>
                  <th scope="col">July</th>
                  <th scope="col">August</th>
                  <th scope="col">September</th>
                  <th scope="col">October</th>
                  <th scope="col">November</th>
                  <th scope="col">December</th>
               </tr>
            </thead>
            <tbody>
               <tr v-for="expense in expenses" :key="expense.id">

                  <th scope="row" class="d-none">{{ expense.subcategory.name }}</th>
                  <td>{{ expense.subcategory.category.name }}</td>
                  <td>{{ expense.subcategory.name }}</td>
                  <td>{{ expense.january}}</td>
                  <td>{{ expense.february}}</td>
                  <td>{{ expense.march}}</td>
                  <td>{{ expense.april}}</td>
                  <td>{{ expense.may}}</td>
                  <td>{{ expense.june}}</td>
                  <td>{{ expense.july}}</td>
                  <td>{{ expense.august}}</td>
                  <td>{{ expense.september}}</td>
                  <td>{{ expense.october}}</td>
                  <td>{{ expense.november}}</td>
                  <td>{{ expense.december}}</td>
               </tr>
            </tbody>
         </table>

      </div>












      <div class="container categories tableContainer tableShadow chartSize">
            <div class="py-1 d-flex justify-content-center">
               <button class="btn-margin btn btn-success"
                  @click="changeMonthAndYearValue(-1); $refs.monthChart.updateMonthExpensesChart(this.time.month, this.time.year)"
                  v-html="leftArrow">
               </button>
               <h3>{{ getMonthName(time.month) }} {{ time.year }}</h3>
               <button class="btn-margin btn btn-success"
                  @click="changeMonthAndYearValue(1); $refs.monthChart.updateMonthExpensesChart(this.time.month, this.time.year)"
                  v-html="rightArrow">
               </button>
            </div>
            <Bar class="py-1 d-flex justify-content-center" ref="monthChart" />
      </div>
      <div class="container categories tableContainer tableShadow chartSize">
            <div class="py-1 d-flex justify-content-center">
               <h3>Wallet distribution</h3>
            </div>
            <Pie class="py-1 d-flex justify-content-center" ref="walletChart" />
      </div>
      <div class="container categories tableContainer tableShadow chartSize">
            <div class="py-1 d-flex justify-content-center">
               <button class="btn-margin btn btn-success"
                  @click="this.year += Number(-1); $refs.yearChart.getYearExpenses(year)"
                  v-html="leftArrow">
               </button>
               <h3>Expenses {{ year }}</h3>
               <button class="btn-margin btn btn-success"
                  @click="this.year += Number(1); $refs.yearChart.getYearExpenses(year)"
                  v-html="rightArrow">
               </button>
            </div>
            <MixedChart class="py-1 d-flex justify-content-center" ref="yearChart" />
      </div>
   </main>
</template>

<script>
import Bar from "@/components/partials/BarChart.vue";
import Pie from "@/components/partials/Pie.vue";
import MixedChart from "@/components/partials/MixedChart.vue";

export default {
   name: "Home",
   components: { Bar, Pie, MixedChart },

   data() {
      return {
         //Today year and date ->
         time: {
            month: Number(this.getTodayMonth()),
            year: Number(this.getTodayYear())
         },
         year: Number(this.getTodayYear()),
         rightArrow: "&gt",
         leftArrow: "&lt",
         expensesYear: null,
         expenses: null,
      }
   },

   methods: {
      getYearExpenses() {
         this.axios.get("api/expenses/" + this.year)
            .then(response => {
               //check if no wallets are present
               if (response.data.length != 0) {
                  this.expensesYear = response.data;
               }
            })
            .catch(error => {
               console.log(error);
            })
      },

      //UTILS

      getTodayDate() {
         return dayjs(Date.now()).format("YYYY-MM-DD");
      },

      getTodayMonth() {
         return dayjs(Date.now()).format("MM");
      },

      getTodayYear() {
         return dayjs(Date.now()).format("YYYY");
      },

      getMonthName(monthNumber) {
         const date = new Date();
         date.setMonth(monthNumber - 1);

         return date.toLocaleString('en-US', { month: 'long' });
      },

      changeMonthAndYearValue(value) {
         this.time.month += Number(value);

         if (this.time.month === 0) {
            this.time.year -= 1;
            this.time.month = 12;
         } else if (this.time.month === 13) {
            this.time.year += 1;
            this.time.month = 1;
         }
      },

      getYearExpensesPerSubcategories(year) {
      this.axios.get("api/expenses/subcategories?year=" + year)
         .then(response => {
            console.log(JSON.stringify(response.data))
            this.expenses = response.data;
         });
      }
   },

   mounted() {
      this.getYearExpensesPerSubcategories(this.time.year);
   }
}

</script>