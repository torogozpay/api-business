package com.porempresa.jwt.modelos.repositorios;


import com.porempresa.jwt.modelos.tablas.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {


    @Modifying
    @Query("delete from Producto where id=:id")
    void deleteProducto(@Param("id") Integer id);

}
