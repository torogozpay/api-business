package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.PresentacionDTO;
import com.porempresa.jwt.modelos.dto.ProductoDTO;
import com.porempresa.jwt.modelos.service.PresentacionService;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/presentacion")
public class PresentacionController {

    @Autowired
    PresentacionService presentacionService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> ObtenerPresentacion(){
        try {
            return presentacionService.consultarPresentacion();
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarPresentacion(@RequestBody PresentacionDTO presentacionDTO){
        try {
            return presentacionService.agregar(presentacionDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse> actualizarPresentacion(@RequestBody PresentacionDTO presentacionDTO){
        try {
            return presentacionService.actualizar(presentacionDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarPresentacion(@RequestBody PresentacionDTO presentacionDTO){
        try {
            return presentacionService.eliminar(presentacionDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

}
