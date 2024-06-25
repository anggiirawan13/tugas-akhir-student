<template>
  <v-row>
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>ADD MAHASISWA</v-toolbar>
        <v-card-text>
          <v-alert v-if="message" color="red lighten-2" dark>{{
            $t(message)
          }}</v-alert>
          <v-breadcrumbs :items="breadcrumbs" class="pa-0"></v-breadcrumbs>
          <v-form ref="form">
            <v-text-field
              name="mahasiswaCode"
              label="NIM"
              type="text"
              :rules="rules.mahasiswaCode"
              v-model="form.mahasiswa_code"
            />
            <v-text-field
              name="mahasiswaName"
              label="Nama Mahasiswa"
              type="text"
              :rules="rules.mahasiswaName"
              v-model="form.mahasiswa_name"
            />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-btn to="/mahasiswa" color="secondary">Back</v-btn>
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
    title: "Add Mahasiswa",
  },
  data() {
    return {
      breadcrumbs: [
        { text: "Mahasiswa", to: "/mahasiswa", disabled: false, exact: true },
        { text: "Add", disabled: true },
      ],
      btnSaveDisable: false,
      message: "",
      status: ["active", "inactive"],
      form: {
        mahasiswa_code: "",
        mahasiswa_name: "",
        status: "",
      },
      rules: {
        mahasiswaCode: [
          (v) =>
            !!v || this.$t("FIELD_IS_REQUIRED", { field: "NIM" }),
        ],
        mahasiswaName: [
          (v) =>
            !!v || this.$t("FIELD_IS_REQUIRED", { field: "Nama Mahasiswa" }),
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
          .$post("/mahasiswa", this.form)
          .then(() => {
            this.$router.push({
              name: `mahasiswa___${this.$i18n.locale}`,
              params: {
                type: "success",
                message: "ADD_SUCCESS",
                title: this.form.mahasiswa_code,
              },
            });
          })
          .catch((error) => {
            this.message = error.response.data.message;
          });

        this.btnSaveDisable = false;
      }
    },
  },
};
</script>
