<template>
  <v-form ref="form" lazy-validation>
    <v-card>
      <v-card-title>
        <h3 class="headline mb-0">Unidade</h3>
      </v-card-title>
      <v-container grid-list-xl fluid>
        <v-layout wrap>
          <v-flex xs12 sm6 md4>
            <v-text-field
              id="nome"
              name="nome"
              v-model="unidade.nome"
              label="Nome"
              :counter="100"
              :rules="nomeRules"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 sm8>
            <v-text-field
              v-model="unidade.cidade"
              id="cidade"
              name="cidade"
              label="Cidade"
              :rules="cidadeRules"
              :counter="100"
              required>
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
    name: 'UnidadeForm',
    props: {
      idUnidade: {
        Type: Number,
        default: null,
      },
    },
    data() {
      return {
        resources: this.$api.unidade(this.$resource),
        unidade: {
          nome: '',
          cidade: '',
        },
        nomeRules: [
          () => !!this.unidade.nome || 'Campo obrigatório!',
          v => (v && v.length <= 100) || 'Limite de caracteres excedido!',
        ],
        cidadeRules: [
          () => !!this.unidade.cidade || 'Campo obrigatório!',
          v => (!v || v.length <= 100) || 'Limite de caracteres excedido!',
        ],
      };
    },
    watch: {
      idUnidade(value) {
        if (value) {
          this.resources.obter({ id: value })
            .then(this.preencher,
              () => this.$emit('erro', 'Erro ao buscar unidade!'));
        }
      },
    },
    methods: {
      salvar() {
        if (!this.$refs.form.validate()) return;
        let acao = null;
        const id = this.unidade.id;
        if (id) {
          acao = this.resources.alterar({ id }, this.unidade);
        } else {
          acao = this.resources.inserir(this.unidade);
        }
        acao.then(this.buscar,
          () => {
            this.$emit('erro', 'Erro ao salvar unidade!');
          });
      },
      preencher(response) {
        this.unidade = response.data;
      },
      limpar() {
        this.$refs.form.reset();
        this.unidade.id = null;
        this.$emit('limpar');
      },
      buscar() {
        this.limpar();
        this.$emit('buscar');
      },
    },
  };
</script>
