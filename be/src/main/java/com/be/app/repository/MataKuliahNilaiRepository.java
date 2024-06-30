package com.be.app.repository;

import com.be.app.entity.MataKuliahNilaiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MataKuliahNilaiRepository extends JpaRepository<MataKuliahNilaiEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mata_kuliah_nilai WHERE mahasiswa_id = :mahasiswaID", nativeQuery = true)
    void deleteByMahasiswaID(@Param("mahasiswaID") Long mahasiswaID);

}
