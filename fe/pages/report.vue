<template>
  <v-container>
    <v-btn @click="generatePDF" color="primary" class="mb-2">Download PDF</v-btn>
    <v-card>
      <v-card-title>Laporan Mahasiswa</v-card-title>
      <v-card-text>
        <v-simple-table>
          <template v-slot:default>
            <thead>
            <tr>
              <th class="text-left">No</th>
              <th class="text-left">NIM</th>
              <th class="text-left">Nama Mahasiswa</th>
              <th class="text-left">Mata Kuliah</th>
              <th class="text-left">Nilai</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(mahasiswa, index) in mahasiswaList" :key="mahasiswa.uuid">
              <td>{{ index + 1 }}</td>
              <td>{{ mahasiswa.nim }}</td>
              <td>{{ mahasiswa.nama_mahasiswa }}</td>
              <td>
                <template v-for="(nilai, idx) in mahasiswa.nilai">
                  {{ getMataKuliahName(nilai.mataKuliahID) }}<br />
                </template>
              </td>
              <td>
                <template v-for="(nilai, idx) in mahasiswa.nilai">
                  {{ nilai.nilai }}<br />
                </template>
              </td>
            </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

export default {
  data() {
    return {
      mahasiswaList: [],
      mataKuliahList: []
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {
        const [mahasiswaResponse, mataKuliahResponse] = await Promise.all([
          this.$axios.$get('/mahasiswa'),
          this.$axios.$get('/mata-kuliah')
        ]);

        this.mahasiswaList = mahasiswaResponse.data;
        this.mataKuliahList = mataKuliahResponse.data;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    generatePDF() {
      const documentDefinition = {
        content: [
          { text: 'Laporan Mahasiswa', style: 'header' },
          '\n',
          {
            table: {
              headerRows: 1,
              widths: ['auto', 'auto', '*', 'auto', 'auto'],
              body: [
                ['No', 'NIM', 'Nama Mahasiswa', 'Mata Kuliah', 'Nilai'],
                ...this.mahasiswaList.flatMap((mahasiswa, index) =>
                    mahasiswa.nilai.map((nilai, idx) => [
                      idx === 0 ? index + 1 : '',
                      idx === 0 ? mahasiswa.nim : '',
                      idx === 0 ? mahasiswa.nama_mahasiswa : '',
                      this.getMataKuliahName(nilai.mataKuliahID),
                      nilai.nilai
                    ])
                )
              ]
            }
          }
        ],
        styles: {
          header: {
            fontSize: 18,
            bold: true
          }
        }
      };

      pdfMake.createPdf(documentDefinition).download("report.pdf");
    },
    getMataKuliahName(id) {
      const mataKuliah = this.mataKuliahList.find(mk => mk.id === id);
      return mataKuliah ? mataKuliah.nama_mata_kuliah : 'Unknown';
    }
  }
};
</script>

<style scoped>
.text-left {
  text-align: left;
}
</style>
