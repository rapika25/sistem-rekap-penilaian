package com.be.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NonNull
public class MataKuliahNilaiRequest implements Serializable {

    @JsonProperty("mata_kuliah_id")
    private Long mataKuliahID;

    @JsonProperty("nilai")
    private int nilai;

}
