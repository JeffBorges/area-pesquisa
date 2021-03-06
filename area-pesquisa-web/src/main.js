// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Vuetify from 'vuetify';
import VueResource from 'vue-resource';
import 'vuetify/dist/vuetify.min.css';
import App from './App';
import router from './router';
import APIResources from './resource';

Vue.use(Vuetify);
Vue.use(VueResource);
Vue.use(APIResources);
Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
});
