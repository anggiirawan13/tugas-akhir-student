package com.be.app.repository;

import com.be.app.entity.MataKuliahEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliahEntity, Long> {

    @Query(value = "SELECT * FROM mata_kuliah WHERE uuid = :uuid", nativeQuery = true)
    MataKuliahEntity findByUUID(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM mata_kuliah WHERE kode_mata_kuliah = :kode_mata_kuliah", nativeQuery = true)
    MataKuliahEntity findByKodeMataKuliah(@Param("kode_mata_kuliah") String kode_mata_kuliah);

}
