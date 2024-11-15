package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.*;
import com.porempresa.jwt.modelos.repositorios.PresentacionProductoRepository;
import com.porempresa.jwt.modelos.tablas.*;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PresentacionProductoService {

    @Autowired
    PresentacionProductoRepository presentacionProductoRepository;

    public ResponseEntity<ApiResponse> consultarPresentacionProducto(){
        ArrayList<PresentacionProducto> lista = null;
        ArrayList<PresentacionProductoDTO> listaDTO = null;
        lista = (ArrayList<PresentacionProducto>) presentacionProductoRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }



    }



    public ResponseEntity<ApiResponse> agregar(PresentacionProductoDTO dto){

        try {

            PresentacionProducto presentacionProducto = convertir(dto);
            presentacionProducto = presentacionProductoRepository.save(presentacionProducto);
            if (presentacionProducto != null){
                dto = convertirDTO(presentacionProducto);
            }
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<ApiResponse> actualizar(PresentacionProductoDTO dto){

        try {

            PresentacionProducto presentacionProducto = convertir(dto);
            presentacionProducto = presentacionProductoRepository.saveAndFlush(presentacionProducto);
            if (presentacionProducto != null){
                dto = convertirDTO(presentacionProducto);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(PresentacionProductoDTO dto){

        try {
            PresentacionProducto presentacionProducto = convertir(dto);
            presentacionProductoRepository.delete(presentacionProducto);
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public PresentacionProducto convertir(PresentacionProductoDTO dto){
        PresentacionProducto presentacionProducto = new PresentacionProducto();
        PresentacionProductoID id = new PresentacionProductoID();
        id.setProveedor(convertirProveedor(dto.getProveedor()));
        id.setPresentacion(convertirPresentacion(dto.getPresentacion()));


        presentacionProducto.setId((id != null) ? id:null);
        presentacionProducto.setProducto(convertirProducto(dto.getProducto()));
        presentacionProducto.setPrecio_venta(dto.getPrecioVenta());
        presentacionProducto.setResiduo_monto(dto.getResiduoMonto());
        presentacionProducto.setResiduo_porcentaje(dto.getResiduoPorcentaje());

        return presentacionProducto;

    }

    public ArrayList<PresentacionProductoDTO> convertirLista(ArrayList<PresentacionProducto> lista){
        ArrayList<PresentacionProductoDTO> presentacion = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            PresentacionProductoDTO dto = convertirDTO(lista.get(i));
            presentacion.add(dto);
        }

        return presentacion;

    }

    public PresentacionProductoDTO convertirDTO(PresentacionProducto presentacionProducto){
        PresentacionProductoDTO dto = new PresentacionProductoDTO();

        dto.setPresentacion(convertirPresentacionDTO(presentacionProducto.getId().getPresentacion()));
        dto.setProveedor(convertirProveedorDTO(presentacionProducto.getId().getProveedor()));
        dto.setProducto(convertirProductoDTO(presentacionProducto.getProducto()));
        dto.setPrecioVenta(presentacionProducto.getPrecio_venta());
        dto.setResiduoMonto(presentacionProducto.getResiduo_monto());
        dto.setResiduoPorcentaje(presentacionProducto.getResiduo_porcentaje());


        return dto;
    }

    public ProveedorDTO convertirProveedorDTO(Proveedor proveedor){
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

    public Proveedor convertirProveedor(ProveedorDTO dto){
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

    public PresentacionDTO convertirPresentacionDTO(Presentacion presentacion){
        PresentacionDTO dto = new PresentacionDTO();

        dto.setId(presentacion.getId());
        dto.setNombre(presentacion.getNombre());
        dto.setEmpresa(convertirEmpresaDTO(presentacion.getEmpresa_id()));

        return dto;
    }

    public Presentacion convertirPresentacion(PresentacionDTO dto){
        Presentacion producto = new Presentacion();
        producto.setId((dto.getId() != null) ? dto.getId():null);
        producto.setNombre(dto.getNombre());
        producto.setEmpresa_id(convertirEmpresa(dto.getEmpresa()));

        return producto;
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

    public Producto convertirProducto(ProductoDTO productoDTO){
        Producto producto = new Producto();

        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPuntaje(productoDTO.getPuntaje());
        producto.setEmpresa_id(convertirEmpresa(productoDTO.getEmpresa()));

        return producto;
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


}
