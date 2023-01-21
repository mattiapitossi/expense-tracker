<template>
    <main class="container" style="margin-top: 10px; height: 25vw;">
        <div style="height:10vh; width:20vw">
            <Bar v-if="loaded" :chart-data="chartData" />
        </div>
    </main>
</template>
  
  
<script>
import { Bar } from 'vue-chartjs'
import Chart from 'chart.js/auto'

export default {
    name: "Chart",
    components: { Bar },

    data() {
        return {
            chartData: {
                datasets: [{
                    label: 'In Expenses',
                    data: [],
                    backgroundColor: '#157347',
                    order: 2
                }, {
                    label: 'Total',
                    data: [],
                    type: 'line',
                    backgroundColor: '#0d6efd',
                    order: 1
                }, {
                    label: 'Out Expenses',
                    data: [],
                    type: 'bar',
                    backgroundColor: '#bb2d3b',
                    order: 2
                }
                ],
                labels: []
            },
            expensesYear: [],
            year: Number(this.getTodayYear()),
            loaded: false,
        }
    },

    methods: {
        getYearExpenses(year) {
            this.expensesYear = [];

            this.axios.get("api/expenses/" + year)
                .then(response => {
                    if (response.data.length != 0) {
                        this.expensesYear = response.data;
                        this.createChartValues();
                    }
                })
                .catch(error => {
                    console.log(error);
                })
        },

        getTodayYear() {
            return dayjs(Date.now()).format("YYYY");
        },

        createChartValues() {
            this.chartData.labels = [];
            this.chartData.datasets.at(0).data = [];
            this.chartData.datasets.at(1).data = [];
            this.chartData.datasets.at(2).data = [];

            for(var i = 0; i < this.expensesYear.length; ) {
               this.chartData.labels.push(this.expensesYear.at(i).date);

               if((this.expensesYear.length > i + 1) && this.expensesYear.at(i).date == this.expensesYear.at(i + 1).date) {
                    if(this.expensesYear.at(i).transaction === "IN") {
                        this.addTransactions(this.expensesYear.at(i).value, this.expensesYear.at(i + 1).value)
                        i+=2;
                    } else if(this.expensesYear.at(i).transaction === "OUT") {
                        this.addTransactions(this.expensesYear.at(i + 1).value, this.expensesYear.at(i).value)
                        i+=2;
                    }

               } else {
                    if(this.expensesYear.at(i).transaction === "IN") {
                        this.addTransactions(this.expensesYear.at(i).value, 0);
                        i++;
                    } else if(this.expensesYear.at(i).transaction === "OUT") {
                        this.addTransactions(0, this.expensesYear.at(i).value);
                        i++;
                    }
                }
            }

            this.loaded = true;
        },

        addTransactions(inExpenseValue, outExpenseValue) {
            this.chartData.datasets.at(0).data.push(Number(inExpenseValue));
            this.chartData.datasets.at(2).data.push(-(Number(outExpenseValue)));
            
            if (this.chartData.datasets.at(1).data.length === 0) {
                    this.chartData.datasets.at(1).data.push(Number(inExpenseValue) - (Number(outExpenseValue)));
            } else {
                    this.chartData.datasets.at(1).data.push(Number(this.chartData.datasets.at(1).data.at(-1)) + Number(inExpenseValue) - (Number(outExpenseValue)));
            }
        },

        addInTransaction(value) {
            this.chartData.datasets.at(0).data.push((Number(value)));

            if (this.chartData.datasets.at(1).data.length === 0) {
                    this.chartData.datasets.at(1).data.push((Number(value)));
            } else {
                    this.chartData.datasets.at(1).data.push(Number(this.chartData.datasets.at(1).data.at(-1)) + Number(value));
            }
        },

        addOutTransaction(value) {
            this.chartData.datasets.at(2).data.push(-(Number(value)));

            if (this.chartData.datasets.at(1).data.length === 0) {
                    this.chartData.datasets.at(1).data.push(-(Number(value)));
            } else {
                    this.chartData.datasets.at(1).data.push(Number(this.chartData.datasets.at(1).data.at(-1)) - Number(value));
            }
        },
    },

    mounted() {
        this.getYearExpenses(this.year);
    }
}
</script>