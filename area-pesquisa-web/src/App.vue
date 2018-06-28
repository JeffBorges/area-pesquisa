<template>
  <v-app>
    <v-navigation-drawer
      persistent
      :mini-variant="miniVariant"
      :clipped="clipped"
      v-model="drawer"
      enable-resize-watcher
      fixed
      app
    >
      <v-list>
        <template v-for="(item, index) in items">
          <v-list-tile :key="index" @click="goToRouter(item.router)" :value="isActive(item.router)">
            <v-list-tile-action>
              <v-icon v-html="item.icon"></v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title v-text="item.title"></v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar app :clipped-left="clipped">
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon v-html="miniVariant ? 'chevron_right' : 'chevron_left'"></v-icon>
      </v-btn>
      <v-toolbar-title v-text="title"></v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-content>
      <v-container fluid>
        <v-slide-y-transition mode="out-in">
          <v-layout align-center justify-center>
            <v-flex xs12 md8>
              <router-view></router-view>
            </v-flex>
          </v-layout>
        </v-slide-y-transition>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
  export default {
    data() {
      return {
        clipped: true,
        drawer: true,
        fixed: true,
        items: [
          {
            icon: 'search',
            title: 'Área de Pesquisa',
            router: 'area-pesquisa',
          },
          {
            icon: 'person',
            title: 'Professor',
            router: 'professor',
          },
          {
            icon: 'account_balance',
            title: 'Unidade',
            router: 'unidade',
          },
        ],
        miniVariant: false,
        title: 'IFTM - Área de Pesquisa',
        router: 'professor',
      };
    },
    name: 'App',
    methods: {
      goToRouter(router) {
        if (router) {
          this.router = router;
          this.$router.push({ name: router });
        }
      },
      isActive(router) {
        return this.router === router;
      },
    },
  };
</script>
