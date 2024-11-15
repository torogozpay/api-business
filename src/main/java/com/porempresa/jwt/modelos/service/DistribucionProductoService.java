package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.*;
import com.porempresa.jwt.modelos.repositorios.DistribucionProductoRepository;
import com.porempresa.jwt.modelos.tablas.*;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DistribucionProductoService {

    @Autowired
    DistribucionProductoRepository distribucionProductoRepository;

    public ResponseEntity<ApiResponse> consultarDistribucionProducto(){
        ArrayList<DistribucionProducto> lista = null;
        ArrayList<DistribucionProductoDTO> listaDTO = null;
        lista = (ArrayList<DistribucionProducto>) distribucionProductoRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }



    }



    public ResponseEntity<ApiResponse> agregar(DistribucionProductoDTO dto){

        try {

            DistribucionProducto distribucionProducto = convertir(dto);
            distribucionProducto = distribucionProductoRepository.save(distribucionProducto);
            if (distribucionProducto != null){
                dto = convertirDTO(distribucionProducto);
            }
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<ApiResponse> actualizar(DistribucionProductoDTO dto){

        try {

            DistribucionProducto distribucionProducto = convertir(dto);
            distribucionProducto = distribucionProductoRepository.saveAndFlush(distribucionProducto);
            if (distribucionProducto != null){
                dto = convertirDTO(distribucionProducto);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(DistribucionProductoDTO dto){

        try {
            DistribucionProducto distribucionProducto = convertir(dto);
            distribucionProductoRepository.delete(distribucionProducto);
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public DistribucionProducto convertir(DistribucionProductoDTO dto){
        DistribucionProducto distribucionProducto = new DistribucionProducto();
        DistribucionProductoID id = new DistribucionProductoID();
        id.setProducto(convertirProducto(dto.getProducto()));
        id.setTipoDistribucion(convertirTipoDistribucion(dto.getTipoDistribucion()));


        distribucionProducto.setId((id != null) ? id:null);
        distribucionProducto.setEstado(dto.getEstado());
        distribucionProducto.setPorcentaje(dto.getPorcentaje());

        return distribucionProducto;

    }

    public ArrayList<DistribucionProductoDTO> convertirLista(ArrayList<DistribucionProducto> lista){
        ArrayList<DistribucionProductoDTO> productos = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            DistribucionProductoDTO dto = convertirDTO(lista.get(i));
            productos.add(dto);
        }

        return productos;

    }

    public DistribucionProductoDTO convertirDTO(DistribucionProducto distribucionProducto){
        DistribucionProductoDTO dto = new DistribucionProductoDTO();

        dto.setEstado(distribucionProducto.getEstado());
        dto.setPorcentaje(distribucionProducto.getPorcentaje());
        dto.setProducto(convertirProductoDTO(distribucionProducto.getId().getProducto()));
        dto.setTipoDistribucion(convertirTipoDistribucionDTO(distribucionProducto.getId().getTipoDistribucion()));
        return dto;
    }

    public ProductoDTO convertirProductoDTO(Producto producto){
        ProductoDTO dto = new ProductoDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPuntaje(producto.getPuntaje());
        dto.setEmpresa(convertirEmpresaDTO(producto.getEmpresa_id()));

        return dto;
    }
    public TipoDistribucionDTO convertirTipoDistribucionDTO(TipoDistribucion tipoDistribucion){
        TipoDistribucionDTO dto = new TipoDistribucionDTO();

        dto.setId(tipoDistribucion.getId());
        dto.setNombre(tipoDistribucion.getNombre());
        dto.setDescripcion(tipoDistribucion.getDescripcion());
        dto.setPuntaje(tipoDistribucion.getPuntaje());
        dto.setEmpresa(convertirEmpresaDTO(tipoDistribucion.getEmpresa_id()));

        return dto;
    }

    public Producto convertirProducto(ProductoDTO productoDTO){
        Producto producto = new Producto();

        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPuntaje(productoDTO.getPuntaje());
        producto.setEmpresa_id(convertirEmpresa(productoDTO.getEmpresa()));

        return producto;
    }
    public TipoDistribucion convertirTipoDistribucion(TipoDistribucionDTO tipoDistribucionDTO){
        TipoDistribucion tipoDistribucion = new TipoDistribucion();

        tipoDistribucion.setId(tipoDistribucionDTO.getId());
        tipoDistribucion.setNombre(tipoDistribucionDTO.getNombre());
        tipoDistribucion.setDescripcion(tipoDistribucionDTO.getDescripcion());
        tipoDistribucion.setPuntaje(tipoDistribucionDTO.getPuntaje());
        tipoDistribucion.setEmpresa_id(convertirEmpresa(tipoDistribucionDTO.getEmpresa()));

        return tipoDistribucion;
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
        //dto.setActivo(empresa.getEstado());

        return dto;
    }


}
