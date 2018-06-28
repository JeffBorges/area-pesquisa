<template>
  <v-dialog v-model="dialog" max-width="290">
    <v-card>
      <v-card-title class="headline">{{ title }}</v-card-title>
      <v-card-text>{{ message }}</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" flat="flat" @click.native="disagree">Não</v-btn>
        <v-btn color="green darken-1" flat="flat" @click.native="agree">Sim</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
  export default {
    name: 'ConfirmDialog',
    props: {
      title: {
        type: String,
        default: 'Atenção!',
      },
      message: '',
    },
    data() {
      return {
        dialog: false,
      };
    },
    methods: {
      open() {
        this.dialog = true;
      },
      disagree() {
        this.close();
        this.$emit('disagree');
      },
      agree() {
        this.close();
        this.$emit('agree');
      },
      close() {
        this.dialog = false;
      },
    },
  };
</script>
