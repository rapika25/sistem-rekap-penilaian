<template>
  <v-row>
    <!-- Input Fields -->
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>EDIT MAHASISWA</v-toolbar>
        <v-card-text>
          <v-alert v-if="message" color="red lighten-2" dark>{{ $t(message) }}</v-alert>
          <v-breadcrumbs :items="breadcrumbs" class="pa-0"></v-breadcrumbs>
          <v-form ref="form">
            <v-text-field
                name="nim"
                label="NIM"
                type="text"
                :rules="rules.nim"
                v-model="form.nim"
            />
            <v-text-field
                name="nama_mahasiswa"
                label="Nama Mahasiswa"
                type="text"
                :rules="rules.nama_mahasiswa"
                v-model="form.nama_mahasiswa"
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn to="/mahasiswa" color="secondary">Back</v-btn>
          <v-spacer />
          <v-btn @click="doSave" color="primary" :loading="btnSaveDisable">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>

    <!-- Input Mata Kuliah & Nilai -->
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>MATA KULIAH & NILAI</v-toolbar>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="4">
                <v-select
                    v-model="selectedMataKuliah"
                    :items="mataKuliahOptions"
                    item-text="nama"
                    item-value="id"
                    label="Mata Kuliah"
                ></v-select>
              </v-col>
              <v-col cols="4">
                <v-text-field v-model="nilai" label="Nilai"></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-btn @click="addMataKuliahNilai" color="primary">Add</v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
      </v-card>
    </v-col>

    <!-- Tabel Mata Kuliah & Nilai -->
    <v-col cols="10" offset="1">
      <v-card>
        <v-card-text>
          <v-data-table
              :headers="tableHeaders"
              :items="mataKuliahNilai"
              :items-per-page="5"
              class="elevation-1"
          >
            <template v-slot:item.actions="{ item }">
              <v-icon small @click="editMataKuliahNilai(item)">mdi-pencil</v-icon>
              <v-icon small @click="deleteMataKuliahNilai(item)">mdi-delete</v-icon>
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
    title: "Edit Mahasiswa",
  },
  async asyncData({ params }) {
    return {
      id: params.id,
    };
  },
  data() {
    return {
      breadcrumbs: [
        { text: "Mahasiswa", to: "/mahasiswa", disabled: false, exact: true },
        { text: "Edit", disabled: true },
      ],
      btnSaveDisable: false,
      message: "",
      form: {
        nim: "",
        nama_mahasiswa: "",
      },
      rules: {
        nim: [(v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "NIM" })],
        nama_mahasiswa: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Nama Mahasiswa" }),
        ],
      },
      selectedMataKuliah: null,
      nilai: "",
      mataKuliahNilai: [],
      mataKuliahOptions: [], // Will be populated from API
      editedItem: null,
    };
  },
  computed: {
    tableHeaders() {
      return [
        { text: "Mata Kuliah", value: "mata_kuliah" },
        { text: "Nilai", value: "nilai" },
        { text: "Actions", value: "actions", sortable: false },
      ];
    },
  },
  methods: {
    async doSave() {
      this.message = "";
      if (this.$refs.form.validate()) {
        this.btnSaveDisable = true;

        try {
          const requestData = {
            nim: this.form.nim,
            nama_mahasiswa: this.form.nama_mahasiswa,
            nilai: this.mataKuliahNilai.map(item => ({
              mata_kuliah_id: this.mataKuliahOptions.find(mk => mk.nama === item.mata_kuliah).id,
              nilai: parseInt(item.nilai)
            }))
          };

          console.log(requestData)

          const response = await this.$axios.put(`/mahasiswa/${this.id}`, requestData);

          this.$router.push({
            name: `mahasiswa___${this.$i18n.locale}`,
            params: {
              type: "success",
              message: "UPDATE_SUCCESS",
              title: this.form.nim,
            },
          });
        } catch (error) {
          console.error('Error:', error);
          this.message = 'Failed to save data. Please try again.';
        } finally {
          this.btnSaveDisable = false;
        }
      }
    },
    addMataKuliahNilai() {
      if (this.selectedMataKuliah && this.nilai) {
        if (this.editedItem) {
          const selectedNamaMataKuliah = this.mataKuliahOptions.find(
              option => option.id === this.selectedMataKuliah
          ).nama;
          this.editedItem.mata_kuliah = selectedNamaMataKuliah;
          this.editedItem.nilai = this.nilai;
          this.editedItem = null;
        } else {
          const selectedNamaMataKuliah = this.mataKuliahOptions.find(
              option => option.id === this.selectedMataKuliah
          ).nama;
          this.mataKuliahNilai.push({
            mata_kuliah: selectedNamaMataKuliah,
            nilai: this.nilai,
          });
        }
        this.resetInputFields();
      }
    },
    editMataKuliahNilai(item) {
      this.editedItem = item;
      this.selectedMataKuliah = this.mataKuliahOptions.find(
          mk => mk.nama === item.mata_kuliah
      ).id;
      this.nilai = item.nilai;
    },
    deleteMataKuliahNilai(item) {
      const index = this.mataKuliahNilai.indexOf(item);
      if (index !== -1) {
        this.mataKuliahNilai.splice(index, 1);
      }
    },
    resetForm() {
      this.form.nim = "";
      this.form.nama_mahasiswa = "";
      this.mataKuliahNilai = [];
    },
    resetInputFields() {
      this.selectedMataKuliah = null;
      this.nilai = "";
    },
    async getData() {
      try {
        const response = await this.$axios.$get(`/mahasiswa/${this.id}`);
        const { data } = response;

        this.form.nim = data.nim;
        this.form.nama_mahasiswa = data.nama_mahasiswa;
        this.mataKuliahNilai = data.nilai.map(item => ({
          mata_kuliah: this.mataKuliahOptions.find(mk => mk.id === item.mataKuliahID).nama,
          nilai: item.nilai
        }));
      } catch (error) {
        console.error('Error:', error);
      }
    },
    async getMataKuliahOptions() {
      try {
        const response = await this.$axios.get('/mata-kuliah');
        if (response.data.success) {
          this.mataKuliahOptions = response.data.data.map(matkul => ({
            id: matkul.id,
            nama: matkul.nama_mata_kuliah,
          }));
        } else {
          console.error('Failed to fetch mata kuliah:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching mata kuliah:', error);
      }
    },
  },
  async mounted() {
    await this.getMataKuliahOptions();
    this.getData();
  },
};
</script>
