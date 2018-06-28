import Vue from 'vue';
import Router from 'vue-router';
import Professor from '@/components/professor/Professor';
import Unidade from '@/components/unidade/Unidade';
import AreaPesquisa from '@/components/area-pesquisa/AreaPesquisa';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'professor',
      component: Professor,
    },
    {
      path: '/unidade',
      name: 'unidade',
      component: Unidade,
    },
    {
      path: '/area-pesquisa',
      name: 'area-pesquisa',
      component: AreaPesquisa,
    },
  ],
});
