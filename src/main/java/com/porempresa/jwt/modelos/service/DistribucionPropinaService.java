package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.*;
import com.porempresa.jwt.modelos.repositorios.DistribucionPropinaRepository;
import com.porempresa.jwt.modelos.repositorios.VentaRepository;
import com.porempresa.jwt.modelos.tablas.*;
import com.porempresa.jwt.util.ResponsEntityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class DistribucionPropinaService {

    @Autowired
    DistribucionPropinaRepository distribucionPropinaRepository;
    @Autowired
    VentaRepository ventaRepo;
    public ResponseEntity<ApiResponse> consultarDistribucionPropina(){
        ArrayList<DistribucionPropina> lista = null;
        ArrayList<DistribucionPropinaDTO> listaDTO = null;
        lista = (ArrayList<DistribucionPropina>) distribucionPropinaRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ApiResponse> agregar(DistribucionPropinaDTO dto){

        try {
            UUID uuid = UUID.randomUUID();
            dto.setId(uuid.toString());
            DistribucionPropina distribucionPropina = convertir(dto);
            distribucionPropina = distribucionPropinaRepository.save(distribucionPropina);
            if (distribucionPropina != null){
                dto = convertirDTO(distribucionPropina);
            }
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    @Transactional
    public void agregarDistribucionPropina(DistribucionPropina dp){
        //Venta v = ventaRepo.save(dp.getVentaId());
        //dp.setVentaId(v);
        distribucionPropinaRepository.save(dp);
    }

    public ResponseEntity<ApiResponse> actualizar(DistribucionPropinaDTO dto){

        try {

            DistribucionPropina distribucionPropina = convertir(dto);
            distribucionPropina = distribucionPropinaRepository.saveAndFlush(distribucionPropina);
            if (distribucionPropina != null){
                dto = convertirDTO(distribucionPropina);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(DistribucionPropinaDTO dto){

        try {
            DistribucionPropina distribucionPropina = convertir(dto);
            distribucionPropinaRepository.delete(distribucionPropina);
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }



    public DistribucionPropina convertir(DistribucionPropinaDTO dto){
        DistribucionPropina distribucionPropina = new DistribucionPropina();

        distribucionPropina.setU_id(dto.getId());
        distribucionPropina.setVentaId((dto.getVenta() != null) ? convertirVenta(dto.getVenta()):null);
        distribucionPropina.setProveedor_id(convertirProveedor(dto.getProveedor()));
        distribucionPropina.setProducto(convertirProducto(dto.getDistribucionProducto().getProducto()));
        distribucionPropina.setTipoDistribucion(convertirTipoDistribucion(dto.getDistribucionProducto().getTipoDistribucion()));

        return distribucionPropina;

    }

    public ArrayList<DistribucionPropinaDTO> convertirLista(ArrayList<DistribucionPropina> lista){
        ArrayList<DistribucionPropinaDTO> propina = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            DistribucionPropinaDTO dto = convertirDTO(lista.get(i));
            propina.add(dto);
        }

        return propina;

    }

    public DistribucionPropinaDTO convertirDTO(DistribucionPropina distribucionPropina){
        DistribucionPropinaDTO dto = new DistribucionPropinaDTO();
        DistribucionProductoFKDTO distribucionProducto = new DistribucionProductoFKDTO();
        distribucionProducto.setTipoDistribucion(convertirTipoDistribucionDTO(distribucionPropina.getTipoDistribucion()));
        distribucionProducto.setProducto(convertirProductoDTO(distribucionPropina.getProducto()));

        dto.setId(distribucionPropina.getU_id());
        dto.setProveedor(convertirProveedorDTO(distribucionPropina.getProveedor_id()));
        dto.setVenta(convertirVentaDTO(distribucionPropina.getVentaId()));
        dto.setDistribucionProducto(distribucionProducto);
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

    public EmpresaDTO convertirEmpresaDTO(Empresa empresa){
        EmpresaDTO dto = new EmpresaDTO();

        dto.setId(empresa.getId());
        dto.setNombre(empresa.getNombre());
        dto.setDescripcion(empresa.getDescripcion());
        //dto.setActivo(empresa.getActivo());

        return dto;
    }

    public VentaDTO convertirVentaDTO(Venta venta){
        VentaDTO dto = new VentaDTO();



        return dto;
    }

    public Venta convertirVenta(VentaDTO dto){
        Venta venta = new Venta();


        venta.setFechaTx(new Date());
        //venta.setInvoiceAddress(dto.getInvoic);
        //venta.setEmpresaId(new Empresa(dto.getBusinessId()));
        //venta.setPresentacion(dto.getPresentacion());
        //venta.setProveedor(dto.getProveedor());
        //venta.setProducto(dto.getProducto());

        return venta;

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

    public Empresa convertirEmpresa(EmpresaDTO empresaDTO){
        Empresa dto = new Empresa();

        dto.setId(empresaDTO.getId());
        dto.setNombre(empresaDTO.getNombre());
        dto.setDescripcion(empresaDTO.getDescripcion());
        //dto.setActivo(empresaDTO.getActivo());

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



}
