package com.be.app.repository;

import com.be.app.entity.MataKuliahEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliahEntity, Long> {

    @Query(value = "SELECT * FROM category WHERE uuid = :uuid AND status = 'active'", nativeQuery = true)
    MataKuliahEntity findByUUID(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM category WHERE category_code = :category_code AND status = 'active'", nativeQuery = true)
    MataKuliahEntity findByKodeMataKuliah(@Param("category_code") String category_code);

}
