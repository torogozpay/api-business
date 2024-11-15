package com.porempresa.jwt.modelos.repositorios;


import com.porempresa.jwt.modelos.tablas.Proveedor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {


    @Modifying
    @Query("delete from Proveedor where id=:id")
    void deleteProveedor(@Param("id") Integer id);

}
