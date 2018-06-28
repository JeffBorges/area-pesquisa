<template>
  <div>
    <area-pesquisa-form
      :id-area="idArea"
      @buscar="buscar"
      @limpar="limpar">
    </area-pesquisa-form>
    <area-pesquisa-tabela
      class="mt-3"
      ref="tabela"
      @erro="exibirErro"
      @info="exibirInfo"
      @edit="edit">
    </area-pesquisa-tabela>
    <v-snackbar
      :timeout="6000"
      color="error"
      v-model="snackbar">
      {{ text }}
      <v-btn dark flat @click.native="snackbar = false">Close</v-btn>
    </v-snackbar>
  </div>
</template>

<script>
  import AreaPesquisaTabela from './AreaPesquisaTabela';
  import AreaPesquisaForm from './AreaPesquisaForm';

  export default {
    name: 'AreaPesquisa',
    components: {
      AreaPesquisaForm,
      AreaPesquisaTabela,
    },
    data() {
      return {
        snackbar: false,
        text: '',
        color: 'error',
        idArea: null,
      };
    },
    methods: {
      exibirErro(erro) {
        this.exibirMensagem(erro, 'error');
      },
      exibirInfo(info) {
        this.exibirMensagem(info, 'info');
      },
      exibirSucesso(mensagem) {
        this.exibirMensagem(mensagem, 'success');
      },
      edit(id) {
        this.idArea = id;
      },
      limpar() {
        this.idArea = null;
      },
      buscar() {
        this.$refs.tabela.buscar();
      },
      exibirMensagem(mensagem, tipo) {
        this.text = mensagem;
        this.snackbar = true;
        this.color = tipo;
      },
    },
  };
</script>

<style scoped>

</style>
