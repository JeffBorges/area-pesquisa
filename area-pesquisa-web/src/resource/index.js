import areaPesquisa from './area-pesquisa';
import professor from './professor';
import unidade from './unidade';

function plugin(Vue) { // eslint-disable-line no-param-reassign
  Vue.prototype.$api = { // eslint-disable-line no-param-reassign
    areaPesquisa,
    professor,
    unidade,
  };
}

// module.exports = plugin;
export default plugin;
