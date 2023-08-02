import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import {store} from "core-js/internals/reflect-metadata";


//createApp(App).use(router).mount('#app')
const app = createApp(App)
app.config.globalProperties.axios=axios;
app.use(store).use(router).mount('#app')
axios.defaults.baseURL='http://localhost:8080'


