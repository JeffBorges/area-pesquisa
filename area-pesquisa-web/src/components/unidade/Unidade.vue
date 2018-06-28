<template>
  <div>
    <unidade-form
      :id-unidade="idUnidade"
      @buscar="buscar"
      @limpar="limpar">
    </unidade-form>
    <unidade-tabela
      class="mt-3"
      ref="tabela"
      @erro="exibirErro"
      @info="exibirInfo"
      @edit="edit">
    </unidade-tabela>
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
  import UnidadeTabela from './UnidadeTabela';
  import UnidadeForm from './UnidadeForm';

  export default {
    name: 'UnidadePesquisa',
    components: {
      UnidadeForm,
      UnidadeTabela,
    },
    data() {
      return {
        snackbar: false,
        text: '',
        color: 'error',
        idUnidade: null,
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
        this.idUnidade = id;
      },
      limpar() {
        this.idUnidade = null;
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
