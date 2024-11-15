package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.EmpresaDTO;
import com.porempresa.jwt.modelos.dto.ProveedorDTO;
import com.porempresa.jwt.modelos.repositorios.ProveedorRepository;
import com.porempresa.jwt.modelos.tablas.Empresa;
import com.porempresa.jwt.modelos.tablas.Proveedor;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;


    public ResponseEntity<ApiResponse> consultarProveedor(){
        ArrayList<Proveedor> lista = null;
        ArrayList<ProveedorDTO> listaDTO = null;
        lista = (ArrayList<Proveedor>) proveedorRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }



    }


    public ResponseEntity<ApiResponse> agregar(ProveedorDTO dto){

        try {

            Proveedor proveedor = convertir(dto);
            proveedor = proveedorRepository.save(proveedor);
            if (proveedor != null){
                dto = convertirDTO(proveedor);
            }
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<ApiResponse> actualizar(ProveedorDTO dto){

        try {

            Proveedor proveedor = convertir(dto);
            proveedor = proveedorRepository.saveAndFlush(proveedor);
            if (proveedor != null){
                dto = convertirDTO(proveedor);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(ProveedorDTO dto){

        try {

            proveedorRepository.deleteProveedor(dto.getId());
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public Proveedor convertir(ProveedorDTO dto){
        Proveedor proveedor = new Proveedor();
        proveedor.setId((dto.getId() != null) ? dto.getId():null);
        proveedor.setNombres(dto.getNombres());
        proveedor.setApellidos(dto.getApellidos());
        proveedor.setEmpresa_id(convertirEmpresa(dto.getEmpresa()));
        proveedor.setBitcoin_address(dto.getBitcoin());
        proveedor.setLightning_address(dto.getLightning());
        proveedor.setWallet_alternativo(dto.getWallet());

        return proveedor;

    }

    public ArrayList<ProveedorDTO> convertirLista(ArrayList<Proveedor> lista){
        ArrayList<ProveedorDTO> proveedores = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            ProveedorDTO dto = convertirDTO(lista.get(i));
            proveedores.add(dto);
        }

        return proveedores;

    }

    public ProveedorDTO convertirDTO(Proveedor proveedor){
        ProveedorDTO dto = new ProveedorDTO();

        dto.setId(proveedor.getId());
        dto.setNombres(proveedor.getNombres());
        dto.setApellidos(proveedor.getApellidos());
        dto.setBitcoin(proveedor.getBitcoin_address());
        dto.setLightning(proveedor.getLightning_address());
        dto.setWallet(proveedor.getWallet_alternativo());
        dto.setEmpresa(convertirEmpresaDTO(proveedor.getEmpresa_id()));

        return dto;
    }


    public Empresa convertirEmpresa(EmpresaDTO empresaDTO){
        Empresa dto = new Empresa();

        dto.setId(empresaDTO.getId());
        dto.setNombre(empresaDTO.getNombre());
        dto.setDescripcion(empresaDTO.getDescripcion());
        //dto.setActivo(empresaDTO.getEstado());

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
