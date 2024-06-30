package com.be.app.repository;

import com.be.app.entity.MataKuliahEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliahEntity, Long> {

    @Query(value = "SELECT * FROM mata_kuliah WHERE uuid = :uuid", nativeQuery = true)
    MataKuliahEntity findByUUID(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM mata_kuliah WHERE kode_mata_kuliah LIKE %:search% OR nama_mata_kuliah LIKE %:search%", nativeQuery = true)
    List<MataKuliahEntity> findByKodeOrNamaMataKuliah(@Param("search") String search);

}
