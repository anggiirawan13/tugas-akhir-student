<template>
  <v-row>
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>ADD USER</v-toolbar>
        <v-card-text>
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
        matkul_code: "",
        matkul_name: "",
      },
      rules: {
        matkulCode: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Kode Mata Kuliah" }),
        ],
        matkulName: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Nama Mata Kuliah" }),
        ],
      },
    };
  },
  methods: {
    async doSave() {
      if (this.$refs.form.validate()) {
        this.btnSaveDisable = true;

        await this.$axios
          .$post("/matkul", this.form)
          .then(() => {
            this.$router.push({
              name: `matkul___${this.$i18n.locale}`,
              params: {
                type: "success",
                message: "ADD_SUCCESS",
                title: this.form.matkul_code,
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
