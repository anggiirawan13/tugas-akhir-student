<template>
  <v-row>
    <v-col cols="10" offset="1">
      <v-card class="mb-2">
        <v-toolbar color="primary" dark>EDIT CATEGORY</v-toolbar>
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
            <v-select
              v-model="form.status"
              :items="status"
              label="Status"
              :rules="rules.status"
            ></v-select>
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
    title: "Edit Mahasiswa",
  },
  asyncData({ params }) {
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
      status: ["active", "inactive"],
      form: {
        mahasiswa_code: "",
        mahasiswa_name: "",
        status: "",
      },
      rules: {
        mahasiswa_code: [
          (v) =>
            !!v || this.$t("FIELD_IS_REQUIRED", { field: "NIM" }),
        ],
        mahasiswa_name: [
          (v) =>
            !!v || this.$t("FIELD_IS_REQUIRED", { field: "Nama Mahasiswa" }),
        ],
        status: [
          (v) => !!v || this.$t("FIELD_IS_REQUIRED", { field: "Status" }),
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
          .$put(`/mahasiswa/${this.id}`, this.form)
          .then(() => {
            this.$router.push({
              name: `mahasiswa___${this.$i18n.locale}`,
              params: {
                type: "success",
                message: "UPDATE_SUCCESS",
                title: this.form.mahasiswa_code,
              },
            });
          })
          .catch(() => {
            this.$router.push({
              name: `mahasiswa___${this.$i18n.locale}`,
              params: {
                type: "error",
                message: "UPDATE_FAILED",
                title: this.form.mahasiswa_code,
              },
            });
          });

        this.btnSaveDisable = false;
      }
    },
    getData() {
      this.$axios
        .$get(`/mahasiswa/${this.id}`)
        .then((response) => {
          const { data } = response;

          this.form.mahasiswa_code = data.mahasiswa_code;
          this.form.mahasiswa_name = data.mahasiswa_name;
          this.form.status = data.status;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getData();
  },
};
</script>
