import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import gsap from 'gsap'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import "./assets/style/style.scss"

const app = createApp(App)

app.use(router)
app.use(VueAxios, axios)
app.use(gsap)

app.mount('#app')
