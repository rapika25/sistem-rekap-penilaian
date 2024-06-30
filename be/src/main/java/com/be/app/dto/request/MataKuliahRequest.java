package com.be.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NonNull
public class MataKuliahRequest implements Serializable {

    @JsonProperty("kode_mata_kuliah")
    private String kodeMataKuliah;

    @JsonProperty("nama_mata_kuliah")
    private String namaMataKuliah;

}
