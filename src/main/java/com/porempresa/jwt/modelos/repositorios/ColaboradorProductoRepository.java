package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.ColaboradorProducto;
import com.porempresa.jwt.modelos.tablas.Empresa;
import com.porempresa.jwt.modelos.tablas.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColaboradorProductoRepository extends JpaRepository<ColaboradorProducto, Integer> {
    public Optional<List<ColaboradorProducto>> findByIdProducto(Producto producto);

}
