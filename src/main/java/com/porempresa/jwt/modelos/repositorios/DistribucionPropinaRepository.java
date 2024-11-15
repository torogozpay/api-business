package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.DistribucionPropina;
import com.porempresa.jwt.modelos.tablas.Producto;
import com.porempresa.jwt.modelos.tablas.Venta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DistribucionPropinaRepository extends JpaRepository<DistribucionPropina, String> {
    Optional<List<DistribucionPropina>> findByVentaId(Venta venta);
    Optional<DistribucionPropina> findByProducto(Producto producto);

}
