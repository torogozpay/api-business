package com.porempresa.jwt.controladores;


import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.EmpresaDTO;
import com.porempresa.jwt.modelos.dto.ProveedorDTO;
import com.porempresa.jwt.modelos.repositorios.EmpresaRepository;
import com.porempresa.jwt.modelos.tablas.Empresa;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comercio")
public class EmpresaController {

    @GetMapping(("/{id}"))
    public ResponseEntity<ApiResponse> consultarEmpresa(@PathVariable(value = "id", required = true) Integer id){
        try{
            Optional<Empresa> empresaOpc = empresaRepo.findById(id);
            if(empresaOpc.isPresent()) {
                Empresa emp = empresaOpc.get();
                EmpresaDTO empresaDTO = new EmpresaDTO();
                empresaDTO.setId(emp.getId());
                empresaDTO.setNombre(emp.getNombre()!=null?emp.getNombre():"");
                empresaDTO.setDescripcion(emp.getDescripcion()!=null?emp.getDescripcion():"");
                empresaDTO.setLnAddress(emp.getLnAddress()!=null?emp.getLnAddress():"");
                empresaDTO.setActivo(emp.getActivo()!=null?emp.getActivo():0);
                empresaDTO.setApiId("");
                empresaDTO.setSecretId("");
                return ResponsEntityUtil.responseApi201("Empresa encontrada.", empresaDTO, true);
            }else{
                return ResponsEntityUtil.responseApi400(String.format("Id %d de empresa no encontrado",id),null,false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    EmpresaRepository empresaRepo;


    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarEmpresa(@RequestBody EmpresaDTO empresaDTO){
        try{
            Optional<Empresa> empresaOpc = empresaRepo.findById(empresaDTO.getId());
            if(empresaOpc.isPresent()) {
                return ResponsEntityUtil.responseApi400("Ya existe una empresa con el mismo ID",null,false, HttpStatus.CONFLICT);
            }else {
                Empresa emp = new Empresa();
                emp.setId(empresaDTO.getId());
                emp.setNombre(empresaDTO.getNombre());
                emp.setDescripcion(empresaDTO.getDescripcion());
                emp.setActivo(empresaDTO.getActivo());
                emp.setLnAddress(empresaDTO.getLnAddress());
                empresaRepo.save(emp);
                return ResponsEntityUtil.responseApi201("Empresa agregada con exito.", empresaDTO, true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/modificar")
    public ResponseEntity<ApiResponse> modificarEmpresa(@RequestBody EmpresaDTO empresaDTO){
        try{
            Optional<Empresa> empresaOpc = empresaRepo.findById(empresaDTO.getId());
            if(empresaOpc.isPresent()) {
                Empresa emp = empresaOpc.get();
                emp.setNombre(empresaDTO.getNombre());
                emp.setDescripcion(empresaDTO.getDescripcion());
                emp.setActivo(empresaDTO.getActivo());
                emp.setLnAddress(empresaDTO.getLnAddress());
                empresaRepo.save(emp);
                return ResponsEntityUtil.responseApi201("Empresa actualizada con exito.", empresaDTO, true);
            }else{
                return ResponsEntityUtil.responseApi400(String.format("Id %d de empresa no encontrado",empresaDTO.getId()),null,false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/eliminar")
    public ResponseEntity<ApiResponse> eliminarEmpresa(@RequestBody EmpresaDTO empresaDTO){
        try{
            Optional<Empresa> empresaOpc = empresaRepo.findById(empresaDTO.getId());
            if(empresaOpc.isPresent()) {
                empresaRepo.delete(empresaOpc.get());
                return ResponsEntityUtil.responseApi201("Empresa borrada de API Business con exito.", empresaDTO, true);
            }else{
                return ResponsEntityUtil.responseApi400(String.format("Id %d de empresa no encontrado",empresaDTO.getId()),null,false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }
    }
}
