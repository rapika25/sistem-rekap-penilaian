package com.be.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mata_kuliah")
public class MataKuliahEntity extends BaseEntity {

    @Column(name = "kode_mata_kuliah", unique = true, nullable = false, length = 20)
    @JsonProperty("kode_mata_kuliah")
    private String kodeMataKuliah;

    @Column(name = "nama_mata_kuliah", nullable = false, length = 20)
    @JsonProperty("nama_mata_kuliah")
    private String namaMataKuliah;

}
