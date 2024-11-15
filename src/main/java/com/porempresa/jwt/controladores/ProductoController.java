package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.ProductoDTO;
import com.porempresa.jwt.modelos.dto.ProveedorDTO;
import com.porempresa.jwt.modelos.service.ProductoService;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> ObtenerProducto(){
        try {
            return productoService.consultarProducto();
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarProducto(@RequestBody ProductoDTO producto){
        try {
            return productoService.agregar(producto);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse> actualizarProducto(@RequestBody ProductoDTO producto){
        try {
            return productoService.actualizar(producto);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarProducto(@RequestBody ProductoDTO producto){
        try {
            return productoService.eliminar(producto);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

}
