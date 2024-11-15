package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.DistribucionProductoDTO;
import com.porempresa.jwt.modelos.dto.ProductoDTO;
import com.porempresa.jwt.modelos.service.DistribucionProductoService;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/distribucionProducto")
public class DistribucionProductoController {

    @Autowired
    DistribucionProductoService distribucionProductoService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> ObtenerDistribucionProducto(){
        try {
            return distribucionProductoService.consultarDistribucionProducto();
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarDistribucionProducto(@RequestBody DistribucionProductoDTO distribucionProductoDTO){
        try {
            return distribucionProductoService.agregar(distribucionProductoDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse> actualizarDistribucionProducto(@RequestBody DistribucionProductoDTO distribucionProductoDTO){
        try {
            return distribucionProductoService.actualizar(distribucionProductoDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarDistribucionProducto(@RequestBody DistribucionProductoDTO DistribucionProductoDTO){
        try {
            return distribucionProductoService.eliminar(DistribucionProductoDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

}
