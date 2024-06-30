package com.be.app.repository;

import com.be.app.entity.MahasiswaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MahasiswaRepository extends JpaRepository<MahasiswaEntity, Long> {

    @Query(value = "SELECT * FROM mahasiswa WHERE uuid = :uuid", nativeQuery = true)
    MahasiswaEntity findByUUID(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM mahasiswa WHERE nim LIKE %:search% OR nama_mahasiswa LIKE %:search%", nativeQuery = true)
    List<MahasiswaEntity> findByNimOrName(@Param("search") String search);

}
