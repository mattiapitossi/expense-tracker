<template>
  <main class="container chartContainer">
    <div class="chartDimensions">
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
            label: 'Categories Total Out Value',
            backgroundColor: '#bb2d3b',
            data: []
          }
        ]
      },
      expenses: null,
      totalValue: [],
      categories: [],
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
      this.axios.get("api/expenses/categories?month=" + month + "&year=" + year)
        .then(response => {
          this.expenses = response.data;
          this.createChartValues();
        });
    },

    createChartValues() {
      this.totalValue = [];
      this.categories = [];
      this.expenses.forEach((expense) => {
      this.totalValue.push(expense.value);
      this.categories.push(expense.category.name);
      });

      this.populateChart();
    },

    populateChart() {
      this.chartData.labels = this.categories;
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