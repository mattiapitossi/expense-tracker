<template>
    <main class="container chartContainer">
        <div class="chartDimensions">
            <PolarArea :chart-data="data" :options="options" />
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
    name: 'PolarArea',
    components: {
        PolarArea
    },
    data() {
        return {
            data: {
                labels: [
                    'Eating',
                    'Drinking',
                    'Sleeping',
                    'Designing',
                    'Coding',
                    'Cycling',
                    'Running'
                ],
                datasets: [
                    {
                        label: 'My First dataset',
                        backgroundColor: 'rgba(179,181,198,0.2)',
                        pointBackgroundColor: 'rgba(179,181,198,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(179,181,198,1)',
                        data: [65, 59, 90, 81, 56, 55, 40]
                    },
                    {
                        label: 'My Second dataset',
                        backgroundColor: 'rgba(255,99,132,0.2)',
                        pointBackgroundColor: 'rgba(255,99,132,1)',
                        pointBorderColor: '#fff',
                        pointHoverBackgroundColor: '#fff',
                        pointHoverBorderColor: 'rgba(255,99,132,1)',
                        data: [28, 48, 40, 19, 96, 27, 100]
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false
            }
        }
    }
}
</script>