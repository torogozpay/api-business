package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="producto")
@Setter
@Getter
public class Producto implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(unique=true, nullable=false)
        private Integer id;

        @Column(name = "descripcion",length=255)
        private String descripcion;

        @Column(name = "nombre",length=255)
        private String nombre;

        @Column(name = "puntaje")
        private Integer puntaje;
        @ManyToOne
        @JoinColumn(name="empresa_id")
        private Empresa empresa_id;

        public Producto(Integer id) {
                this.id = id;
        }

        public Producto() {
        }
}
