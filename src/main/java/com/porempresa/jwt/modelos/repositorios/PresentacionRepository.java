package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.Presentacion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion, Integer> {

    @Modifying
    @Query("delete from Presentacion where id=:id")
    void deletePresentacion(@Param("id") Integer id);

}
