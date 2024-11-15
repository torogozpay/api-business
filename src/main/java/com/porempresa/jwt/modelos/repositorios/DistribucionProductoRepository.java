package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.DistribucionProducto;
import com.porempresa.jwt.modelos.tablas.DistribucionPropina;
import com.porempresa.jwt.modelos.tablas.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface DistribucionProductoRepository extends JpaRepository<DistribucionProducto, Integer> {
    Optional<DistribucionProducto> findByIdProducto(Producto producto);

}
