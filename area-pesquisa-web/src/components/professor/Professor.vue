<template>
  <div>
    <professor-form
      :id-professor="idProfessor"
      @buscar="buscar"
      @limpar="limpar">
    </professor-form>
    <professor-tabela
      class="mt-3"
      ref="tabela"
      @erro="exibirErro"
      @edit="edit">
    </professor-tabela>
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
  import ProfessorForm from './ProfessorForm';
  import ProfessorTabela from './ProfessorTabela';

  export default {
    name: 'Professor',
    components: {
      ProfessorTabela,
      ProfessorForm,
    },
    data() {
      return {
        snackbar: false,
        text: '',
        color: 'error',
        idProfessor: null,
      };
    },
    methods: {
      exibirErro(erro) {
        this.exibirMensagem(erro, 'error');
      },
      exibirSucesso(mensagem) {
        this.exibirMensagem(mensagem, 'success');
      },
      edit(id) {
        this.idProfessor = id;
      },
      limpar() {
        this.idProfessor = null;
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
