package com.be.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mata_kuliah_nilai")
public class MataKuliahNilaiEntity extends BaseEntity {

    @Column(name = "mahasiswa_id", nullable = false)
    @JsonProperty("mahasiswa_id")
    private Long mahasiswaID;

    @Column(name = "mata_kuliah_id", nullable = false)
    private Long mataKuliahID;

    @Column(name = "nilai", nullable = false)
    private int nilai;
}
