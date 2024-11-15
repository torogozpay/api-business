package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.PresentacionProducto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PresentacionProductoRepository extends JpaRepository<PresentacionProducto, Integer>  {
}
