<template>
   <main class="container">
      <div class="py-5">
         <h1>HOME</h1>
      </div>
      <div>
         <Bar v-if="loaded" :chart-data="chartData" />
      </div>
   </main>
</template>

<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
   name: "Home",
    components: { Bar },
    data() {
      return {
         chartData: {
            labels: [],
            datasets: [
               {
                  label: 'Data One',
                  backgroundColor: '#f87979',
                  data: []
               }
            ]
         },
         expenses: null,
         totalValue: [ ],
         expensesdate: [ ],
         time: {
            month: Number(11),
            year: Number(this.getTodayYear())
         },
         loaded: false
      }
    },
    
    methods: {
      populateChart(labels, datasets) {
         labels.forEach(label => {
            this.chartData.labels.push(String(label));
         });
         datasets.forEach(data => {
            this.chartData.datasets.at(-1).data.push(Number(data));
         });
         console.log(this.chartData.datasets.data);
         console.log(this.chartData.labels);
      },

      getMonthExpenses() {
        this.isLoading = true;
        this.axios.get("api/expenses/date?month=" + this.time.month + "&year=" + this.time.year)
            .then(response => {
              this.expenses = response.data;
              this.createChartValues(this.expenses);
              this.isLoading = false;
            });
      },

      createChartValues(expenses) {
         expenses.forEach((expense) => {
         if(expense.typeOfTransaction === "OUT" || expense.typeOfTransaction === "OUTIN") {
            if (this.totalValue.length === 0) {
               this.totalValue.push(expense.value);
            } else {
               this.totalValue.push(Number(this.totalValue.at(-1)) + Number(expense.value));
            }
               this.expensesdate.push(expense.expense_date);
         }
         });

         this.populateChart(this.expensesdate, this.totalValue, this.expensesdate);
         this.loaded = true;
      },

      getTodayMonth() {
        return dayjs(Date.now()).format("MM");
      },

      getTodayYear() {
        return dayjs(Date.now()).format("YYYY");
      },
    },

   mounted() {
      this.getMonthExpenses();
   }
}
</script>