package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.EmpresaDTO;
import com.porempresa.jwt.modelos.dto.PresentacionDTO;
import com.porempresa.jwt.modelos.dto.ProductoDTO;
import com.porempresa.jwt.modelos.repositorios.PresentacionRepository;
import com.porempresa.jwt.modelos.repositorios.ProductoRepository;
import com.porempresa.jwt.modelos.tablas.Empresa;
import com.porempresa.jwt.modelos.tablas.Presentacion;
import com.porempresa.jwt.modelos.tablas.Producto;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PresentacionService {

    @Autowired
    PresentacionRepository presentacionRepository;

    public ResponseEntity<ApiResponse> consultarPresentacion(){
        ArrayList<Presentacion> lista = null;
        ArrayList<PresentacionDTO> listaDTO = null;
        lista = (ArrayList<Presentacion>) presentacionRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }



    }


    public ResponseEntity<ApiResponse> agregar(PresentacionDTO dto){

        try {

            Presentacion presentacion = convertir(dto);
            presentacion = presentacionRepository.save(presentacion);
            if (presentacion != null){
                dto = convertirDTO(presentacion);
            }
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<ApiResponse> actualizar(PresentacionDTO dto){

        try {

            Presentacion presentacion = convertir(dto);
            presentacion = presentacionRepository.saveAndFlush(presentacion);
            if (presentacion != null){
                dto = convertirDTO(presentacion);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(PresentacionDTO dto){

        try {

            presentacionRepository.deletePresentacion(dto.getId());
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public Presentacion convertir(PresentacionDTO dto){
        Presentacion producto = new Presentacion();
        producto.setId((dto.getId() != null) ? dto.getId():null);
        producto.setNombre(dto.getNombre());
        producto.setEmpresa_id(convertirEmpresa(dto.getEmpresa()));

        return producto;

    }

    public ArrayList<PresentacionDTO> convertirLista(ArrayList<Presentacion> lista){
        ArrayList<PresentacionDTO> productos = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            PresentacionDTO dto = convertirDTO(lista.get(i));
            productos.add(dto);
        }

        return productos;

    }

    public PresentacionDTO convertirDTO(Presentacion presentacion){
        PresentacionDTO dto = new PresentacionDTO();

        dto.setId(presentacion.getId());
        dto.setNombre(presentacion.getNombre());
        dto.setEmpresa(convertirEmpresaDTO(presentacion.getEmpresa_id()));

        return dto;
    }



    public Empresa convertirEmpresa(EmpresaDTO empresaDTO){
        Empresa dto = new Empresa();

        dto.setId(empresaDTO.getId());
        dto.setNombre(empresaDTO.getNombre());
        dto.setDescripcion(empresaDTO.getDescripcion());
        //dto.setActivo(empresaDTO.getActivo());

        return dto;
    }

    public EmpresaDTO convertirEmpresaDTO(Empresa empresa){
        EmpresaDTO dto = new EmpresaDTO();

        dto.setId(empresa.getId());
        dto.setNombre(empresa.getNombre());
        dto.setDescripcion(empresa.getDescripcion());
        //dto.setActivo(empresa.getActivo());

        return dto;
    }



}
