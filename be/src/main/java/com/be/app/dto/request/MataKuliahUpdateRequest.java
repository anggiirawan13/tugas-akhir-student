package com.be.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NonNull
public class MataKuliahUpdateRequest implements Serializable {


    @JsonProperty("id")
    private String id;

    @JsonProperty("kode_mata_kuliah")
    private String kodeMataKuliah;

    @JsonProperty("nama_mata_kuliah")
    private String namaMataKuliah;

}
