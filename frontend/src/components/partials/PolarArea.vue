<template>
    <main class="container chartContainer">
        <div class="chartDimensions">
            <PolarArea v-if="loaded" :chart-data="chartData" :options="options" />
        </div>
    </main>
</template>
  
<script lang="ts">
import {
    Chart as ChartJS,
    RadialLinearScale,
    ArcElement,
    Tooltip,
    Legend
} from 'chart.js'
import { PolarArea } from 'vue-chartjs'
ChartJS.register(RadialLinearScale, ArcElement, Tooltip, Legend)

export default {
    name: 'Polar',
    components: {
        PolarArea
    },
    data() {
        return {
            chartData: {
                labels: [],
                datasets: [
                    {
                        label: 'Wallets',
                        backgroundColor: [],
                        pointBackgroundColor: 'rgba(179,181,198,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(179,181,198,1)',
                        data: []
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            },
            wallets: null,
            loaded: false,
            colors: []
        }
    },

    methods: {
        getWallets() {
            this.axios.get("api/wallets")
                .then(response => {

                    //check if no wallets are present
                    if (response.data.length != 0) {
                        this.wallets = response.data;
                        this.createChartValues();
                    }
                })
                .catch(error => {
                    console.log(error);
                })
        },

        generateRandomColors() {
            for (let i = 0; i < this.wallets.length; i++) {
                var r = Math.floor(Math.random() * 255);
                var g = Math.floor(Math.random() * 255);
                var b = Math.floor(Math.random() * 255);
                this.colors.push("rgba(" + r + "," + g + "," + b +")");
            }
        },

        createChartValues() {
            this.chartData.labels = [];
            this.chartData.datasets.at(-1).data = [];
            this.chartData.datasets.at(-1).backgroundColor = [];

            this.wallets.forEach((wallet) => {
                this.chartData.datasets.at(-1).data.push(wallet.value);

                this.chartData.labels.push(wallet.name);

                var r = Math.floor(Math.random() * 255);
                var g = Math.floor(Math.random() * 255);
                var b = Math.floor(Math.random() * 255);
                this.chartData.datasets.at(-1).backgroundColor.push("rgb(" + r + "," + g + "," + b + ")");
            });

            this.loaded = true;
        },


    },

    mounted() {
        this.getWallets();
    }
}
</script>