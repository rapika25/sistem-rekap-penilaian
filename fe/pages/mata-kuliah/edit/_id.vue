<template>
  <v-row>
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>EDIT MATA KULIAH</v-toolbar>
        <v-card-text>
          <v-alert v-if="message" color="red lighten-2" dark>{{
            $t(message)
          }}</v-alert>
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
    title: "Edit Mata Kuliah",
  },
  asyncData({ params }) {
    return {
      id: params.id,
    };
  },
  data() {
    return {
      breadcrumbs: [
        { text: "Mata Kuliah", to: "/mata-kuliah", disabled: false, exact: true },
        { text: "Edit", disabled: true },
      ],
      btnSaveDisable: false,
      message: "",
      mahasiswa: [],
      status: ["active", "inactive"],
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
      this.message = "";
      if (this.$refs.form.validate()) {
        this.btnSaveDisable = true;

        await this.$axios
          .$put(`/mata-kuliah/${this.id}`, this.form)
          .then(() => {
            this.$router.push({
              name: `mata-kuliah___${this.$i18n.locale}`,
              params: {
                type: "success",
                message: "UPDATE_SUCCESS",
                title: this.form.title,
              },
            });
          })
          .catch(() => {
            this.$router.push({
              name: `mata-kuliah___${this.$i18n.locale}`,
              params: {
                type: "error",
                message: "UPDATE_FAILED",
                title: this.form.title,
              },
            });
          });

        this.btnSaveDisable = false;
      }
    },
    async getData() {
      await this.$axios
        .$get(`/mata-kuliah/${this.id}`, this.form)
        .then((response) => {
          let matkul = response.data;
          this.form.kode_mata_kuliah = matkul.kode_mata_kuliah;
          this.form.nama_mata_kuliah = matkul.nama_mata_kuliah;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getMahasiswa() {
      await this.$axios
        .$get(`/mahasiswa`)
        .then((response) => {
          const { data } = response;
          this.mahasiswa = data.map(item => ({ value: item.id, text: item.mahasiswa_name }));
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // setMahasiswaId() {
    //   if (this.mahasiswa.length && this.form.mahasiswa_id) {
    //     const mahasiswaExists = this.mahasiswa.filter(item => {
    //       if(item.value === parseInt(this.form.mahasiswa_id)) {
    //         return item;
    //       }
    //     });
    //
    //     if (!mahasiswaExists) {
    //       this.form.mahasiswa_id = '';
    //     } else {
    //       this.form.mahasiswa_id = mahasiswaExists[0].value;
    //     }
    //   }
    // }
  },
  async mounted() {
    await this.getMahasiswa();
    await this.getData();
  },
};
</script>
