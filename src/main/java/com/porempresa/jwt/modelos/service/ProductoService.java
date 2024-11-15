package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import com.porempresa.jwt.modelos.dto.EmpresaDTO;
import com.porempresa.jwt.modelos.dto.ProductoDTO;
import com.porempresa.jwt.modelos.dto.ProveedorDTO;
import com.porempresa.jwt.modelos.repositorios.ProductoRepository;
import com.porempresa.jwt.modelos.tablas.Empresa;
import com.porempresa.jwt.modelos.tablas.Producto;
import com.porempresa.jwt.modelos.tablas.Proveedor;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public ResponseEntity<ApiResponse> consultarProducto(){
        ArrayList<Producto> lista = null;
        ArrayList<ProductoDTO> listaDTO = null;
        lista = (ArrayList<Producto>) productoRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }



    }


    public ResponseEntity<ApiResponse> agregar(ProductoDTO dto){

        try {

            Producto producto = convertir(dto);
            producto = productoRepository.save(producto);
            if (producto != null){
                dto = convertirDTO(producto);
            }
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<ApiResponse> actualizar(ProductoDTO dto){

        try {

            Producto producto = convertir(dto);
            producto = productoRepository.saveAndFlush(producto);
            if (producto != null){
                dto = convertirDTO(producto);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(ProductoDTO dto){

        try {

            productoRepository.deleteProducto(dto.getId());
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public Producto convertir(ProductoDTO dto){
        Producto producto = new Producto();
        producto.setId((dto.getId() != null) ? dto.getId():null);
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setEmpresa_id(convertirEmpresa(dto.getEmpresa()));
        producto.setPuntaje(dto.getPuntaje());

        return producto;

    }

    public ArrayList<ProductoDTO> convertirLista(ArrayList<Producto> lista){
        ArrayList<ProductoDTO> productos = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            ProductoDTO dto = convertirDTO(lista.get(i));
            productos.add(dto);
        }

        return productos;

    }

    public ProductoDTO convertirDTO(Producto producto){
        ProductoDTO dto = new ProductoDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPuntaje(producto.getPuntaje());
        dto.setEmpresa(convertirEmpresaDTO(producto.getEmpresa_id()));

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
