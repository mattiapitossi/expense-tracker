<template>
   <main class="container">
      <div class="py-5">
         <h1>HOME</h1>
      </div>
      <div class="container" style="width:30vw; float: left;">
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
      <div class="container" style="width:30vw; float: right;">
         <div class="py-1 d-flex justify-content-center">
            <h3>Wallet distribution</h3>
         </div>
         <Polar class="py-1 d-flex justify-content-center" ref="walletChart" />
      </div>
      <div class="container" style="width:30vw; float: left;">
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
import Polar from "@/components/partials/PolarArea.vue";
import MixedChart from "@/components/partials/MixedChart.vue";

export default {
   name: "Home",
   components: { Bar, Polar, MixedChart },

   data() {
      return {
         chartData: {
            labels: ['January', 'February', 'March'],
            datasets: [
               {
                  label: 'Data One',
                  backgroundColor: '#f87979',
                  data: [40, 20, 12]
               }
            ]
         },
         //Today year and date ->
         time: {
            month: Number(this.getTodayMonth()),
            year: Number(this.getTodayYear())
         },
         year: Number(this.getTodayYear()),
         rightArrow: "&gt",
         leftArrow: "&lt",
         expensesYear: null,
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
   }
}

</script>