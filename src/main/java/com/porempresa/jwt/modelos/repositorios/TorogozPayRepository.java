package com.porempresa.jwt.modelos.repositorios;

import com.porempresa.jwt.modelos.tablas.TorogozPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorogozPayRepository extends JpaRepository<TorogozPay, Integer> {
    public TorogozPay findById(int id);

}
