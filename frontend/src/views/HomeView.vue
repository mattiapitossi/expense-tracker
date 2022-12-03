<template>
   <main class="container">
      <div class="py-5">
         <h1>HOME</h1>
      </div>
      <div style="height:15vh; width:30vw">
         <div class="py-1 d-flex">
            <button class="btn-margin btn btn-success" @click="changeMonthAndYearvalue(-1); $refs.monthChart.updateChart(this.time.month, this.time.year)" v-html="leftArrow">
            </button>
            <h3>{{ getMonthName(time.month) }} {{time.year}}</h3>
            <button class="btn-margin btn btn-success" @click="changeMonthAndYearvalue(1); $refs.monthChart.updateChart(this.time.month, this.time.year)" v-html="rightArrow">
            </button>
         </div>
         <Chart ref="monthChart"/>
      </div>
   </main>
</template>

<script>
import Chart from "@/components/partials/Chart.vue";

export default {
   name: "Home",
   components: {Chart},

   data() {
      return {
         chartData: {
            labels: [ 'January', 'February', 'March'],
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
         rightArrow: "&gt",
         leftArrow: "&lt",
      }
   },

   methods: {
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
      
      changeMonthAndYearvalue(value) {
         this.time.month += Number(value);

         if(this.time.month === 0) {
            this.time.year -= 1;
            this.time.month = 12;
         } else if(this.time.month === 13) {
            this.time.year += 1;
            this.time.month = 1;
         }
      },
   }     
}

</script>