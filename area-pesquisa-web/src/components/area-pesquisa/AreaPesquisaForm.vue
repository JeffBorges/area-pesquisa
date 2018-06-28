<template>
  <v-form ref="form" lazy-validation>
    <v-card>
      <v-card-title>
        <h3 class="headline mb-0">Área de Pesquisa</h3>
      </v-card-title>
      <v-container grid-list-xl fluid>
        <v-layout wrap>
          <v-flex xs12 sm6 md4>
            <v-text-field
              id="nome"
              name="nome"
              v-model="area.nome"
              label="Nome"
              :counter="100"
              :rules="nomeRules"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 sm8>
            <v-text-field
              v-model="area.descricao"
              id="descricao"
              name="descricao"
              label="Descrição"
              :rules="descricaoRules"
              :counter="500">
            </v-text-field>
          </v-flex>
        </v-layout>
      </v-container>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn @click.native="limpar">Limpar</v-btn>
        <v-btn @click.native="salvar" color="primary">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </v-form>
</template>

<script>
  export default {
    name: 'AreaPesquisaForm',
    props: {
      idArea: {
        Type: Number,
        default: null,
      },
    },
    data() {
      return {
        resources: this.$api.areaPesquisa(this.$resource),
        area: {
          nome: '',
          descricao: '',
        },
        nomeRules: [
          () => !!this.area.nome || 'Campo obrigatório!',
          v => (v && v.length <= 100) || 'Limite de caracteres excedido!',
        ],
        descricaoRules: [
          v => (!v || v.length <= 500) || 'Limite de caracteres excedido!',
        ],
      };
    },
    watch: {
      idArea(value) {
        if (value) {
          this.resources.obter({ id: value })
            .then(this.preencher,
              () => this.$emit('erro', 'Erro ao buscar área de pesquisa!'));
        }
      },
    },
    methods: {
      salvar() {
        if (!this.$refs.form.validate()) return;
        let acao = null;
        const id = this.area.id;
        if (id) {
          acao = this.resources.alterar({ id }, this.area);
        } else {
          acao = this.resources.inserir(this.area);
        }
        acao.then(this.buscar,
          () => {
            this.$emit('erro', 'Erro ao salvar área de pesquisa!');
          });
      },
      preencher(response) {
        this.area = response.data;
      },
      limpar() {
        this.$refs.form.reset();
        this.area.id = null;
        this.$emit('limpar');
      },
      buscar() {
        this.limpar();
        this.$emit('buscar');
      },
    },
  };
</script>
