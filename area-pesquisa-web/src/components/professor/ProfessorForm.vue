<template>
  <v-form ref="form" lazy-validation>
    <v-card>
      <v-card-title>
        <h3 class="headline mb-0">Professor</h3>
      </v-card-title>
      <v-container grid-list-xl fluid>
        <v-layout wrap>
          <v-flex xs12 sm6 md4>
            <v-text-field
              id="nome"
              name="nome"
              v-model="professor.nome"
              label="Nome "
              :counter="100"
              :rules="nomeRules"
              required
            ></v-text-field>
          </v-flex>
          <v-flex xs12 sm6 md4>
            <v-select
              :items="graus"
              v-model="professor.grauAcademico"
              item-text="text"
              item-value="value"
              label="Grau académico "
              :rules="grauRules"
              required>
            </v-select>
          </v-flex>
          <v-flex xs12 sm6 md4>
            <v-select
              :items="unidades"
              :filter="customFilter"
              v-model="professor.unidade"
              item-text="nome"
              item-value="id"
              label="Unidade "
              return-object
              :rules="unidadeRules"
              no-data-text="Não há dados disponíveis"
              required
              autocomplete>
            </v-select>
          </v-flex>
          <v-flex xs12 sm6 md4>
            <v-select
              :items="areas"
              v-model="professor.areasPesquisa"
              label="Áreas de Pesquisa "
              item-text="nome"
              item-value="id"
              return-object
              :rules="areasRules"
              no-data-text="Não há dados disponíveis"
              multiple
              required
              deletable-chips
              chips>
            </v-select>
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
    name: 'ProfessorForm',
    props: {
      idProfessor: {
        Type: Number,
        default: null,
      },
    },
    data() {
      return {
        resources: this.$api.professor(this.$resource),
        areaResources: this.$api.areaPesquisa(this.$resource),
        unidadeResources: this.$api.unidade(this.$resource),
        unidades: [],
        areas: [],
        graus: [
          { text: 'Especialista', value: 'ESPECIALISTA' },
          { text: 'Mestre', value: 'MESTRE' },
          { text: 'Doutor', value: 'DOUTOR' },
        ],
        professor: {
          nome: '',
          grauAcademico: 'ESPECIALISTA',
          unidade: {},
          areasPesquisa: [],
        },
        nomeRules: [
          () => !!this.professor.nome || 'Campo obrigatório!',
          v => (v && v.length <= 100) || 'Limite de caracteres excedido!',
        ],
        unidadeRules: [
          () => !!(this.professor.unidade && this.professor.unidade.id) || 'Campo obrigatório!',
        ],
        areasRules: [
          () => !!this.professor.areasPesquisa.length || 'Campo obrigatório!',
        ],
        grauRules: [
          () => !!this.professor.grauAcademico || 'Campo obrigatório!',
        ],
      };
    },
    watch: {
      idProfessor(value) {
        if (value) {
          this.resources.obter({ id: value })
            .then(this.preencher,
              () => this.$emit('erro', 'Erro ao buscar professor!'));
        }
      },
    },
    methods: {
      customFilter(item, queryText, itemText) {
        const text = itemText != null ? itemText : '';
        const query = queryText != null ? queryText : '';
        return text.toString()
          .toLowerCase()
          .indexOf(query.toString().toLowerCase()) > -1;
      },
      salvar() {
        if (!this.$refs.form.validate()) return;
        let acao = null;
        const id = this.professor.id;
        if (id) {
          acao = this.resources.alterar({ id }, this.professor);
        } else {
          acao = this.resources.inserir(this.professor);
        }
        acao.then(this.buscar,
          () => {
            this.$emit('erro', 'Erro ao salvar professor!');
          });
      },
      preencher(response) {
        this.professor = response.data;
      },
      limpar() {
        this.$refs.form.reset();
        this.professor.id = null;
        this.$emit('limpar');
      },
      buscar() {
        this.limpar();
        this.$emit('buscar');
      },
      buscarAreas() {
        this.areaResources.buscar({ search: '' }).then((response) => {
          this.areas = response.data.content;
        }, () => {
          this.$emit('erro', 'Erro ao buscar área de pesquisa!');
        });
      },
      buscarUnidades() {
        this.unidadeResources.buscar({ search: '' }).then((response) => {
          this.unidades = response.data.content;
        }, () => {
          this.$emit('erro', 'Erro ao buscar unidade!');
        });
      },
    },
    mounted() {
      this.buscarAreas();
      this.buscarUnidades();
    },
  };
</script>
