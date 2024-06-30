<template>
  <v-row class="frame-content">
    <v-col cols="10" offset="1">
      <v-card class="my-3">
        <v-toolbar color="primary" dark>
          Mata Kuliah
          <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-toolbar>
        <v-card-text>
          <v-alert v-if="message" :type="alertType">{{ message }}</v-alert>
          <div class="d-flex mb-4">
            <v-breadcrumbs :items="breadcrumbs" class="pa-0" />
            <v-spacer></v-spacer>
            <v-btn to="/mata-kuliah/add" color="primary" elevation="3" small
              >Add <v-icon right>mdi-plus-circle</v-icon></v-btn
            >
          </div>

          <v-data-table
            :isLoading="isLoading"
            :items="matkul"
            :headers="headers"
            :items-per-page="10"
            :server-items-length="totalData"
            :options.sync="options"
            :search.sync="search"
            :footer-props="{
              itemsPerPageOptions: [10, 25, 50, 75, 100],
            }"
          >
            <template v-slot:top>
              <v-dialog v-model="dialogDelete" max-width="500px">
                <v-card>
                  <v-card-title
                    >Kamu yakin ingin menghapus data
                    {{ itemDelete.nama_mata_kuliah }}?</v-card-title
                  >
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary" text @click="cancelDelete"
                      >Cancel</v-btn
                    >
                    <v-btn
                      color="error"
                      text
                      @click="confirmDelete(itemDelete.uuid)"
                      >Delete</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </template>
            <template v-slot:item.actions="{ item }">
              <v-btn :to="`/mata-kuliah/edit/${item.uuid}`" icon
                ><v-icon small>mdi-pencil</v-icon></v-btn
              >
              <v-btn small icon @click="deleteItem(item)"
                ><v-icon small>mdi-delete</v-icon></v-btn
              >
            </template>
          </v-data-table>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  head: {
    title: "MataKuliah",
  },
  data() {
    return {
      matkul: [],
      options: {},
      totalData: 0,
      search: "",
      isLoading: false,
      message: "",
      alertType: "",
      dialogDelete: false,
      itemDelete: "",
      headers: [
        { text: "No.", value: "number", sortable: false },
        { text: "Kode Mata Kuliah", value: "kode_mata_kuliah", sortable: false },
        { text: "Nama Mata Kuliah", value: "nama_mata_kuliah", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      breadcrumbs: [
        {
          text: "",
          disabled: true,
          to: "/mata-kuliah",
        },
      ],
    };
  },
  methods: {
    async getMataKuliah() {
      this.isLoading = true;
      const { page, itemsPerPage } = this.options;

      await this.$axios
        .$get(
          `/mata-kuliah?page=${page - 1}&limit=${itemsPerPage}&search=${this.search}`
        )
        .then((response) => {
          this.matkul = []
          this.totalData = 0;

          if (response.success) {
            this.matkul = response.data;
            this.totalData = response.additionalEntity.totalData;

            let i = response.additionalEntity.number * itemsPerPage + 1;
            i = isNaN(i) ? 1 : i;
            this.matkul.map((matkul) => (matkul.number = i++));
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.isLoading = false;
        });
    },
    confirmDelete(id) {
      this.$axios
        .$delete(`/mata-kuliah/${id}`)
        .then(async () => {
          await this.getMataKuliah();
          this.alertType = "success";
          this.message = this.$t("DELETE_SUCCESS", {
            title: this.itemDelete.nama_mata_kuliah,
          });
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.cancelDelete();
        });
    },
    deleteItem(item) {
      this.dialogDelete = true;
      this.itemDelete = item;
    },
    cancelDelete() {
      this.dialogDelete = false;
    },
  },
  watch: {
    options: {
      handler() {
        this.getMataKuliah();
      },
      deep: true,
    },
    search: {
      handler() {
        this.getMataKuliah();
      },
    },
  },
  mounted() {
    if (this.$route.params.message) {
      this.alertType = this.$route.params.type;
      this.message = this.$t(this.$route.params.message, {
        title: this.$route.params.title,
      });
    }
  },
};
</script>
