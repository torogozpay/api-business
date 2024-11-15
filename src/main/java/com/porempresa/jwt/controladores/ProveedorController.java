package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.ProveedorDTO;
import com.porempresa.jwt.modelos.repositorios.ProveedorRepository;
import com.porempresa.jwt.modelos.service.ProveedorService;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/proveedor")
public class ProveedorController {


    @Autowired
    ProveedorService proveedorService;

    @Autowired
    ProveedorRepository proveedorRepository;

    @GetMapping("")
    public ResponseEntity<ApiResponse> ObtenerProveedor(){
        try {
            return proveedorService.consultarProveedor();
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarProveedor(@RequestBody ProveedorDTO proveedor){
        try {
            return proveedorService.agregar(proveedor);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse> actualizarProveedor(@RequestBody ProveedorDTO proveedor){
        try {
            return proveedorService.actualizar(proveedor);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarProveedor(@RequestBody ProveedorDTO proveedor){
        try {
            return proveedorService.eliminar(proveedor);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


}
