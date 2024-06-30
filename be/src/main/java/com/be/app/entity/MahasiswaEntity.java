package com.be.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mahasiswa")
public class MahasiswaEntity extends BaseEntity {

    @Column(name = "nim", unique = true, nullable = false, length = 20)
    @JsonProperty("nim")
    private String nim;

    @Column(name = "nama_mahasiswa", nullable = false)
    @JsonProperty("nama_mahasiswa")
    private String namaMahasiswa;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mahasiswa_id")
    @JsonProperty("nilai")
    private List<MataKuliahNilaiEntity> nilai;

}
