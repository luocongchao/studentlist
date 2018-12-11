import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import {global} from './global.js'
import '@/icons' // icon
import '@/permission' // permission control 
import request from './utils/request'
import './styles/global.css'




Vue.prototype.axios = request
Vue.use(ElementUI, { locale })
Vue.prototype.global =global

Vue.config.productionTip = false


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
