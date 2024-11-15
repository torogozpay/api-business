package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.DistribucionProductoDTO;
import com.porempresa.jwt.modelos.dto.PresentacionProductoDTO;
import com.porempresa.jwt.modelos.service.PresentacionProductoService;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/presentacionProducto")
public class PresentacionProductoController {

    @Autowired
    PresentacionProductoService presentacionProductoService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> ObtenerPresentacionProducto(){
        try {
            return presentacionProductoService.consultarPresentacionProducto();
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarPresentacionProducto(@RequestBody PresentacionProductoDTO presentacionProductoDTO){
        try {
            return presentacionProductoService.agregar(presentacionProductoDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse> actualizarPresentacionProducto(@RequestBody PresentacionProductoDTO presentacionProductoDTO){
        try {
            return presentacionProductoService.actualizar(presentacionProductoDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarPresentacionProducto(@RequestBody PresentacionProductoDTO presentacionProductoDTO){
        try {
            return presentacionProductoService.eliminar(presentacionProductoDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


}
