<template>
  <v-row>
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>ADD MATA KULIAH</v-toolbar>
        <v-card-text>
          <v-breadcrumbs :items="breadcrumbs" class="pa-0"></v-breadcrumbs>
          <v-form ref="form">
            <v-text-field
              name="kode_mata_kuliah"
              label="Kode Mata Kuliah"
              type="text"
              :rules="rules.kode_mata_kuliah"
              v-model="form.kode_mata_kuliah"
            />
            <v-text-field
              name="nama_mata_kuliah"
              label="Nama Mata Kuliah"
              type="text"
              :rules="rules.nama_mata_kuliah"
              v-model="form.nama_mata_kuliah"
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn to="/mata-kuliah" color="secondary">Back</v-btn>
          <v-spacer />
          <v-btn @click="doSave" color="primary" :loading="btnSaveDisable"
            >Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  head: {
    title: "Add Mata Kuliah",
  },
  data() {
    return {
      breadcrumbs: [
        { text: "Mata Kuliah", to: "/mata-kuliah", disabled: false, exact: true },
        { text: "Add", disabled: true },
      ],
      btnSaveDisable: false,
      status: ["active", "inactive"],
      mahasiswa: [],
      form: {
        kode_mata_kuliah: "",
        nama_mata_kuliah: "",
      },
      rules: {
        kode_mata_kuliah: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Kode Mata Kuliah" }),
        ],
        nama_mata_kuliah: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Nama Mata Kuliah" }),
        ],
      },
    };
  },
  methods: {
    async doSave() {
      if (this.$refs.form.validate()) {
        this.btnSaveDisable = true;
        console.log(this.form)
        await this.$axios
          .$post("/mata-kuliah", this.form)
          .then(() => {
            this.$router.push({
              name: `mata-kuliah___${this.$i18n.locale}`,
              params: {
                type: "success",
                message: "ADD_SUCCESS",
                title: this.form.nama_mata_kuliah,
              },
            });
          })
          .catch((error) => {
            console.log(error);
          });

        this.btnSaveDisable = false;
      }
    },
  },
};
</script>
