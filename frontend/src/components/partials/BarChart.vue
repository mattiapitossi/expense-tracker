<template>
  <main class="container" style="margin-top: 10px; height: 25vw;">
    <div style="height:10vh; width:20vw">
      <Bar v-if="loaded" :chart-data="chartData" />
    </div>
  </main>
</template>


<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: "Chart",
  components: { Bar },

  data() {
    return {
      chartData: {
        labels: [],
        datasets: [
          {
            label: 'Expenses Value',
            backgroundColor: '#f87979',
            data: []
          }
        ]
      },
      expenses: null,
      totalValue: [],
      expensesdate: [],
      time: {
        month: Number(this.getTodayMonth()),
        year: Number(this.getTodayYear())
      },
      loaded: false,
      isLoading: true
    }
  },

  methods: {
    updateMonthExpensesChart(month, year) {
      this.isLoading = true;
      this.axios.get("api/expenses/date?month=" + month + "&year=" + year)
        .then(response => {
          this.expenses = response.data;
          this.createChartValues();
        });
    },

    createChartValues() {
      this.totalValue = [];
      this.expensesdate = [];
      this.expenses.forEach((expense) => {
        if (expense.typeOfTransaction === "OUT" || expense.typeOfTransaction === "OUTIN") {
          if (this.totalValue.length === 0) {
            this.totalValue.push(expense.value);
          } else {
            this.totalValue.push(Number(this.totalValue.at(-1)) + Number(expense.value));
          }

          this.expensesdate.push(expense.expense_date);
        }
      });

      this.populateChart();
    },

    populateChart() {
      this.chartData.labels = this.expensesdate;
      this.chartData.datasets.at(-1).data = this.totalValue;
      this.loaded = true;
      this.isLoading = false;
    },

    getTodayMonth() {
      return dayjs(Date.now()).format("MM");
    },

    getTodayYear() {
      return dayjs(Date.now()).format("YYYY");
    },
  },

  mounted() {
    this.updateMonthExpensesChart(this.time.month, this.time.year);
  }
}
</script>