<template>
  <v-card>
    <v-card-title>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        @input.native="filtrar"
        append-icon="search"
        label="Pesquisar"
        single-line
        hide-details>
      </v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="professores"
      :loading="loading"
      :pagination.sync="pagination"
      :total-items="totalPage"
      :rows-per-page-items="[10, 25, 50]"
      class="elevation-1"
      no-data-text="Não há dados disponíveis"
      rows-per-page-text="Linhas por página">
      <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
      <template slot="items" slot-scope="props">
        <td>{{ props.item.nome }}</td>
        <td>{{ props.item.unidade.nome }}</td>
        <td>{{ converterGrau(props.item.grauAcademico) }}</td>
        <td>{{ converterAreas(props.item.areasPesquisa) }}</td>
        <td class="justify-center layout px-0">
          <v-btn icon class="mx-0" @click="editItem(props.item)">
            <v-icon color="teal">edit</v-icon>
          </v-btn>
          <v-btn icon class="mx-0" @click="deleteItem(props.item)">
            <v-icon color="pink">delete</v-icon>
          </v-btn>
        </td>
      </template>
      <template slot="pageText" slot-scope="props">
        {{ props.pageStart }} - {{ props.pageStop }} de {{ props.itemsLength }}
      </template>
    </v-data-table>
    <confirm-dialog
      ref="cofirmDialog"
      message="Deseja deletar esta professor?"
      @agree="deletar">
    </confirm-dialog>
  </v-card>
</template>

<script>
  import ConfirmDialog from '../util/ConfirmDialog';

  export default {
    name: 'ProfessorTabela',
    components: { ConfirmDialog },
    data() {
      return {
        search: '',
        resources: this.$api.professor(this.$resource),
        loading: false,
        totalPage: 0,
        esperar: false,
        pagination: {},
        headers: [
          { text: 'Nome', value: 'nome' },
          { text: 'Unidade', value: 'unidade.nome' },
          { text: 'Grau académico', value: 'grauAcademico' },
          { text: 'Áreas de Pesquisa', value: 'areasPesquisa', sortable: false },
          { text: '', value: 'acao' },
        ],
        professores: [],
        professor: {},
      };
    },
    watch: {
      pagination: {
        handler() {
          this.buscar();
        },
        deep: true,
      },
    },
    methods: {
      converterGrau(grau) {
        switch (grau) {
          case 'ESPECIALISTA':
            return 'Especialista';
          case 'MESTRE':
            return 'Mestre';
          case 'DOUTOR':
            return 'Doutor';
          default:
            return 'Especialista';
        }
      },
      converterAreas(areas) {
        return areas.reduce((acumulador, valorAtual, indice, lista) => {
            let valor = acumulador.concat(valorAtual.nome);
            if (indice + 1 < lista.length)
              return valor.concat(', ');
            return valor;
          }, '');
      },
      filtrar() {
        if (this.esperar) return;
        this.esperar = true;
        setTimeout(() => {
          this.esperar = false;
          this.buscar();
        }, 1E3);
      },
      editItem(item) {
        this.$emit('edit', item.id);
      },
      deleteItem(item) {
        this.professor = item;
        this.$refs.cofirmDialog.open();
      },
      deletar() {
        const id = this.professor.id;
        this.loading = true;
        this.resources.deletar({ id }).then(this.buscar,
          () => {
            this.loading = false;
            this.$emit('erro', 'Erro ao deletar professor!');
          });
      },
      buscar() {
        const params = {
          search: this.search,
          page: this.pagination.page - 1,
          size: this.pagination.rowsPerPage,
          direction: this.pagination.descending ? 'DESC' : 'ASC',
          field: this.pagination.sortBy,
        };

        this.resources.buscar(params).then((response) => {
          this.professores = response.data.content;
          this.loading = false;
          this.totalPage = response.data.numberOfElements;
        }, () => {
          this.$emit('erro', 'Erro ao buscar professor!');
        });
      },
    },
    mounted() {
      this.loading = true;
      this.buscar();
    },
  };
</script>

