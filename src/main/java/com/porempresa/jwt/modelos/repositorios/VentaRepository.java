package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.Venta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface VentaRepository extends JpaRepository<Venta, String> {

    Optional<Venta> findByUid(String Uid);
}
