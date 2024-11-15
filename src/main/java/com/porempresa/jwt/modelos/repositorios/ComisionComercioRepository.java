package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.ComisionComercio;
import com.porempresa.jwt.modelos.tablas.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComisionComercioRepository extends JpaRepository<ComisionComercio, Integer> {

    public Optional<List<ComisionComercio>> findByEmpresa(Empresa empresa);

}
