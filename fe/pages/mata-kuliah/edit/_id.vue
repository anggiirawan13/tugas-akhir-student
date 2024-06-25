<template>
  <v-row>
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>EDIT PRODUCT</v-toolbar>
        <v-card-text>
          <v-alert v-if="message" color="red lighten-2" dark>{{
            $t(message)
          }}</v-alert>
          <v-breadcrumbs :items="breadcrumbs" class="pa-0"></v-breadcrumbs>
          <v-form ref="form">
            <v-text-field
                name="matkulCode"
                label="Kode Mata Kuliah"
                type="text"
                :rules="rules.matkulCode"
                v-model="form.matkul_code"
            />
            <v-text-field
                name="matkulName"
                label="Nama Mata Kuliah"
                type="text"
                :rules="rules.matkulName"
                v-model="form.matkul_name"
            />
            <v-text-field
                name="thumbnail"
                label="Thumbnail"
                type="text"
                :rules="rules.thumbnail"
                v-model="form.thumbnail"
            />
            <v-text-field
                name="price"
                label="Price"
                type="number"
                :rules="rules.price"
                v-model="form.price"
            />
            <v-text-field
                name="stock"
                label="Stock"
                type="number"
                :rules="rules.stock"
                v-model="form.stock"
            />
            <v-select
                v-model="form.mahasiswa_id"
                :items="mahasiswa"
                label="Mahasiswa"
                :rules="rules.mahasiswa"
            ></v-select>
            <v-select
                v-model="form.status"
                :items="status"
                label="Status"
                :rules="rules.status"
            ></v-select>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn to="/matkul" color="secondary">Back</v-btn>
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
    title: "Edit MataKuliah",
  },
  asyncData({ params }) {
    return {
      id: params.id,
    };
  },
  data() {
    return {
      breadcrumbs: [
        { text: "MataKuliah", to: "/mata-kuliah", disabled: false, exact: true },
        { text: "Edit", disabled: true },
      ],
      btnSaveDisable: false,
      message: "",
      mahasiswa: [],
      status: ["active", "inactive"],
      form: {
        matkul_code: "",
        matkul_name: "",
        thumbnail: "",
        stock: 0,
        mahasiswa_id: "",
        price: "",
        status: "",
      },
      rules: {
        matkulCode: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Kode Mata Kuliah" }),
        ],
        matkulName: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Nama Mata Kuliah" }),
        ],
        status: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Status" }),
        ],
        thumbnail: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Thumbnail" }),
        ],
        price: [(v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Price" })],
        stock: [(v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Stock" })],
        mahasiswa: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Mahasiswa" }),
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
          .$put(`/matkul/${this.id}`, this.form)
          .then(() => {
            this.$router.push({
              name: `matkul___${this.$i18n.locale}`,
              params: {
                type: "success",
                message: "UPDATE_SUCCESS",
                title: this.form.title,
              },
            });
          })
          .catch(() => {
            this.$router.push({
              name: `matkul___${this.$i18n.locale}`,
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
        .$get(`/matkul/${this.id}`, this.form)
        .then((response) => {
          let matkul = response.data;
          this.form.matkul_code = matkul.matkul_code;
          this.form.matkul_name = matkul.matkul_name;
          this.form.thumbnail = matkul.thumbnail;
          this.form.stock = matkul.stock;
          this.form.mahasiswa_id = matkul.mahasiswa_id;
          this.form.price = matkul.price;
          this.form.status = matkul.status;
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
