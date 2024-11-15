package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.DistribucionProductoDTO;
import com.porempresa.jwt.modelos.dto.DistribucionPropinaDTO;
import com.porempresa.jwt.modelos.service.DistribucionPropinaService;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/distribucionPropina")
public class DistribucionPropinaController {

    @Autowired
    DistribucionPropinaService distribucionPropinaService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> ObtenerDistribucionPropina(){
        try {
            return distribucionPropinaService.consultarDistribucionPropina();
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarDistribucionPropina(@RequestBody DistribucionPropinaDTO distribucionPropinaDTO){
        try {
            return distribucionPropinaService.agregar(distribucionPropinaDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar")
    public ResponseEntity<ApiResponse> actualizarDistribucionPropina(@RequestBody DistribucionPropinaDTO distribucionPropinaDTO){
        try {
            return distribucionPropinaService.actualizar(distribucionPropinaDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarDistribucionPropina(@RequestBody DistribucionPropinaDTO distribucionPropinaDTO){
        try {
            return distribucionPropinaService.eliminar(distribucionPropinaDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


}
