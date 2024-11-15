package com.porempresa.jwt.controladores;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.ProcesarFacturaDTO;
import com.porempresa.jwt.modelos.dto.ProveedorDTO;
import com.porempresa.jwt.modelos.dto.VentaDTO;
import com.porempresa.jwt.modelos.repositorios.VentaRepository;
import com.porempresa.jwt.modelos.service.VentaService;
import com.porempresa.jwt.modelos.tablas.Venta;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class VentaController {

    @Autowired
    VentaService ventaService;
    @Autowired
    VentaRepository ventaRepo;


    @GetMapping("/{uid}")
    public ResponseEntity<ApiResponse> ObtenerVenta(@PathVariable(value = "uid", required = true) String uid){
        try {
            Optional<VentaDTO> ventaOpc = ventaService.consultarDTOPorUid(uid);
            if(ventaOpc!=null && ventaOpc.isPresent())
                return ventaOpc.map(venta -> ResponsEntityUtil.responseApiOk("Registro encontrado.", venta, true)).orElseGet(() -> ResponsEntityUtil.responseApi400(String.format("Registro no encontrado uid:%s", uid), null, false, HttpStatus.NOT_FOUND));
            else return ResponsEntityUtil.responseApi400("Venta no encontrada",uid,false, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return ResponsEntityUtil.responseApi400("Valor de busqueda no valido",e.getMessage(),false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> agregarVenta(@RequestBody VentaDTO ventaDTO){
        try {
            return ventaService.agregar(ventaDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/process/")
    public ResponseEntity<ApiResponse> actualizarVenta(@RequestBody ProcesarFacturaDTO factura){
        try {
            Optional<Venta> ventaOpc = ventaService.buscarPorUid(factura.getInvoiceUid());
            if(ventaOpc.isPresent()){
                Venta v = ventaOpc.get();
                v.setEstado("P");
                ventaRepo.save(v);
                //TODO: enviar correos a quienes se les distribuya la recompensa
                return ResponsEntityUtil.responseApiOk(String.format("Registro Procesado con exito uid:%s.",factura.getInvoiceUid()), null, true);
            }else{
                return ResponsEntityUtil.responseApi400(String.format("Registro no encontrado uid:%s", factura.getInvoiceUid()), null, false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> eliminarVenta(@RequestBody VentaDTO ventaDTO){
        try {
            return ventaService.eliminar(ventaDTO);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false,HttpStatus.BAD_REQUEST);
        }

    }


}
