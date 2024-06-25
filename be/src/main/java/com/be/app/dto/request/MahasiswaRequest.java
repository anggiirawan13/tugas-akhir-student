package com.be.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class MahasiswaRequest {

    @JsonProperty(value = "nim")
    private String nim;

    @JsonProperty(value = "nama_mahasiswa")
    private String namaMahasiswa;

}
