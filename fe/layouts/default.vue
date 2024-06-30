<template>
  <v-app>
    <v-navigation-drawer disable-resize-watcher fixed app>
      <v-list>
        <v-list-item
          v-for="(item, i) in sideMenu"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <v-container fill-height fluid>
        <Nuxt />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "DefaultLayout",
  data() {
    return {
      sideDrawer: false,
      sideMenu: [
        {
          icon: "mdi-library",
          title: "Data Mata Kuliah",
          to: "/mata-kuliah",
        },
        {
          icon: "mdi-account-school",
          title: "Data Mahasiswa",
          to: "/mahasiswa",
        },
        {
          icon: "mdi-file",
          title: "Report",
          to: "/report",
        },
      ],
    };
  },
  mounted() {
    this.$router.push("/mata-kuliah")
  },
};
</script>
