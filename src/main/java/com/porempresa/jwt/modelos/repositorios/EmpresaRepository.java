package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}
