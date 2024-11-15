package com.porempresa.jwt.modelos.service;

import com.porempresa.jwt.modelos.dto.*;
import com.porempresa.jwt.modelos.repositorios.*;
import com.porempresa.jwt.modelos.tablas.*;
import com.porempresa.jwt.util.TipoAsociadoEnums;
import com.porempresa.jwt.util.ResponsEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class VentaService {

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    DistribucionPropinaRepository distPropRepo;
    @Autowired
    DistribucionProductoRepository distProdRepo;
    @Autowired
    DistribucionPropinaService distPropService;

    @Autowired
    ColaboradorProductoRepository colabProdRepo;
    @Autowired
    EmpresaRepository empresaRepo;

    @Autowired
    ComisionComercioRepository comisionComercioRepo;

    @Autowired
    TorogozPayRepository torogozPayRepository;

    public Optional<Venta> buscarPorUid(String uid) {
        return ventaRepository.findByUid(uid);

    }
    public Optional<VentaDTO> consultarDTOPorUid(String uid){
        Optional<Venta> vopc = ventaRepository.findByUid(uid);
        if(vopc.isPresent()){
            Venta v = vopc.get();
            VentaDTO vDto = new VentaDTO();
            vDto.uid = v.getUid();
            vDto.invoiceAddress = v.getInvoiceAddress()!=null?v.getInvoiceAddress():"";
            vDto.invoiceDate = v.getInvoiceDate();
            vDto.status = v.getEstado().equals("Q")?0:1;
            Optional<Empresa> emp = empresaRepo.findById(v.getEmpresaId().getId());
            EmpresaDTO eDto = new EmpresaDTO();
            eDto.setApiId("");
            eDto.setSecretId("");
            if(emp.isPresent()){
                Empresa e = emp.get();
                eDto.setId(e.getId());
                eDto.setNombre(e.getNombre());
                eDto.setDescripcion(e.getDescripcion());
                eDto.setActivo(e.getActivo());
            }else{
                eDto.setId(0);
                eDto.setNombre("NO ENCONTRADO");
                eDto.setDescripcion("");
                eDto.setActivo(0);
            }
            vDto.setTotalSats(v.getTotalSats());
            vDto.empresa = eDto;
            vDto.applySplit = v.isApplySplit();
            vDto.currency = v.getCurrency();
            vDto.totalAmount = v.getTotalAmount();
            vDto.orderId = v.getOrderId();
            vDto.totalAmountMSats = 0;
            if(v.getAmountSat()!=null)
                vDto.amountSat = BigDecimal.valueOf(v.getAmountSat());
            else vDto.amountSat = BigDecimal.ZERO;
            vDto.createdAt = Date.from(v.getFechaCreacion().toInstant(ZoneOffset.UTC));
            if(v.getFechaModificacion()!=null)
                vDto.updatedAt = Date.from(v.getFechaModificacion().toInstant(ZoneOffset.UTC));
            else vDto.updatedAt = Date.from(v.getFechaCreacion().toInstant(ZoneOffset.UTC));//fecha de creacion por defecto

            vDto.customerName = "";
            vDto.customerEmail = "";
            vDto.shipping = v.getShipping()!=null?v.getShipping():BigDecimal.ZERO;
            vDto.details = new ArrayList<>();
            vDto.distributed = v.isDistributed();
            vDto.subTotal = v.getSubtotal();
            vDto.taxes = v.getTaxes();


            //logica de distribucion de pago
            Optional<List<DistribucionPropina>> distribucionPropina = distPropRepo.findByVentaId(v);
            ArrayList<VentaDistribucionDto> paymentSplit = new ArrayList<VentaDistribucionDto>() ;
            if(distribucionPropina.isPresent()) {
                for (DistribucionPropina dist : distribucionPropina.get()) {
                    VentaDistribucionDto vdd = new VentaDistribucionDto();
                    vdd.invoiceUid = v.getUid();

                    //if(dist.getColabProd()!=null)
                        vdd.ldAddress = dist.getInvoiceAddress(); //dist.getColabProd().getId().getColaborador().getLnAddress()!=null?dist.getColabProd().getId().getColaborador().getLnAddress():"";//"temporal_" + Calendar.getInstance().getTimeInMillis();
                    //else
                      //  vdd.ldAddress = v.getEmpresaId().getLnAddress();
                    vdd.amountSat = dist.getAmountSat().intValue();
                    vdd.status = dist.getStatus()?1:0;
                    vdd.invoiceAddress = "no-definido";
                    vdd.attempts = 0;
                    vdd.tipoAsociado = dist.getTipoAsociado();
                    paymentSplit.add(vdd);
                }
            }
            vDto.paymentSplit = paymentSplit;
            return Optional.of(vDto);
        }
        return null;
    }
    public ResponseEntity<ApiResponse> consultarVenta(){
        ArrayList<Venta> lista = null;
        ArrayList<VentaDTO> listaDTO = null;
        lista = (ArrayList<Venta>) ventaRepository.findAll();
        if (lista != null){
            listaDTO = convertirLista(lista);
            return ResponsEntityUtil.responseApiOk("Registro encontrado.",listaDTO,true);
        }else {
            return ResponsEntityUtil.responseApi400("Registro no encontrado.",null,false, HttpStatus.BAD_REQUEST);
        }



    }


    /**
     * @return inicializa una venta retornando un selloRecibido como comprobante que la transaccion se ha guardado en la bd local
     * */
    public ResponseEntity<ApiResponse> agregar(VentaDTO dto){

        try {
            UUID uuid = UUID.randomUUID();

            Venta venta = convertir(dto);
            venta.setUid(UUID.randomUUID().toString().toLowerCase());
            venta.setEstado("I");
            dto.setUid(venta.getUid());

            Optional<List<ComisionComercio>> comisionComercio = comisionComercioRepo.findByEmpresa(venta.getEmpresaId());

            BigDecimal tarifaPlataforma = BigDecimal.ZERO;
            BigDecimal porcentajeTarifaPlataforma = BigDecimal.ZERO;
            BigDecimal diferenciaParaElComercio = BigDecimal.ZERO;
            BigDecimal satsDistribuidosColaboradores = BigDecimal.ZERO;
            if(comisionComercio.isPresent()){
                List<ComisionComercio> comisiones = comisionComercio.get();
                for(ComisionComercio c : comisiones)
                    porcentajeTarifaPlataforma = porcentajeTarifaPlataforma.add(c.getValorComision());
                porcentajeTarifaPlataforma = porcentajeTarifaPlataforma.divide(new BigDecimal(100), 6, RoundingMode.HALF_UP);
            }

            if (dto.getApplySplit())
            {
                ventaRepository.save(venta);

                for (FacturaDetalleDTO invoiceItem : dto.details) {
                    //realizar la distribucion en base a la configuracion de cada producto
                    Optional<DistribucionProducto> distProdOpc = distProdRepo.findByIdProducto(new Producto(invoiceItem.getProductId()));
                    if (invoiceItem.getTotalSats() == null)
                        return ResponsEntityUtil.responseApi400(String.format("No se ha definido el totalMSats para el producto %d", invoiceItem.getProductId()), null, false, HttpStatus.NOT_ACCEPTABLE);


                    if (distProdOpc.isPresent()) {
                        DistribucionProducto distProd = distProdOpc.get();
                        Optional<List<ColaboradorProducto>> colabProd = colabProdRepo.findByIdProducto(distProd.getId().getProducto());
                        if (distProd.getPorcentaje() != null && distProd.getPorcentaje().compareTo(BigDecimal.ZERO) > 0) {

                            BigDecimal montoADistribuirMsats = BigDecimal.valueOf(invoiceItem.getTotalSats()).multiply(distProd.getPorcentaje().divide(new BigDecimal(100), 6, RoundingMode.HALF_UP));
                            ////++ diferenciaParaElComercio = BigDecimal.valueOf(invoiceItem.getTotalSats()).subtract(montoADistribuirMsats);
                            if (colabProd.isPresent()) {//si hay colaboradores a quienes distribuirles..
                                List<ColaboradorProducto> colaboradores = colabProd.get();
                                int numColaboradores = colaboradores.size();//de donde sacar esta cantidad ya q actualmente esta configurado a nivel de presentacion y no de producto directamente
                                satsDistribuidosColaboradores = satsDistribuidosColaboradores.add(montoADistribuirMsats);

                                BigDecimal msatsCadaColab = montoADistribuirMsats.divide(BigDecimal.valueOf(numColaboradores), 0, RoundingMode.DOWN);
                                for (ColaboradorProducto colab : colaboradores) {
                                    //porcentaje que le corresponde a cada colaborador sera equitativo para permitir q la distrb sea dinamica
                                    //sin importar la adicion de nuevos colaboradores
                                    //agregar una distribucion propina por cada colaborador
                                    //diferenciaParaElComercio = diferenciaParaElComercio.subtract(msatsCadaColab);
                                    registrarDistribucionPorColaborador(colab, venta, msatsCadaColab, TipoAsociadoEnums.COLABORADOR);
                                }
                            }

                            //registrar pago para el comercio y para torogoz
                            //BigDecimal porcentajeTarifaPlataforma = BigDecimal.ZERO; //configurable: % a cobrar por el uso de la plataforma de pago (torogoz)
                            ////++ tarifaPlataforma = tarifaPlataforma.add( diferenciaParaElComercio.multiply(porcentajeTarifaPlataforma)); //valor de tarifa a cobrar por el uso de la plataforma
                            ////++ diferenciaParaElComercio = diferenciaParaElComercio.subtract(tarifaPlataforma); //pago al comercio: despues de restar la distribucion de propias y restar la comision por el uso de la plataforma
                            //registrarDistribucionPorColaborador(null, venta, tarifaPlataforma, TipoAsociadoEnums.TOROGOZ);
                            ////++ registrarDistribucionPorColaborador(null, venta, diferenciaParaElComercio, TipoAsociadoEnums.COMERCIO);

                        } else if(distProd.getMonto()!=null && distProd.getMonto().compareTo(BigDecimal.ZERO)>0) {
                            if (colabProd.isPresent()) {//si hay colaboradores a quienes distribuirles..
                                List<ColaboradorProducto> colaboradores = colabProd.get();
                                int numColaboradores = colaboradores.size();
                                BigDecimal montoADistribuir = BigDecimal.valueOf(numColaboradores).multiply(distProd.getMonto());
                                satsDistribuidosColaboradores = satsDistribuidosColaboradores.add(montoADistribuir);
                                ////++ diferenciaParaElComercio = BigDecimal.valueOf(invoiceItem.getTotalSats()).subtract(montoADistribuir);
                                //if(proporcionDist.compareTo(BigDecimal.ONE)>=0 ){
                                  //  return ResponsEntityUtil.responseApi400(String.format("La configuracion permite la distribucion total del monto vendido producto:%d %s", invoiceItem.getProductId(), invoiceItem.getProductName()), null, false, HttpStatus.NOT_ACCEPTABLE);
                                //}else
                                {
                                    for (ColaboradorProducto colab : colaboradores) {
                                        //aplica una distribucion fija y la GUI es la encargada de validar que la distribucion
                                        //no supere el monto total de lo pagado. esto depende de la logica del negocio.

                                        registrarDistribucionPorColaborador(colab, venta, distProd.getMonto(), TipoAsociadoEnums.COLABORADOR);
                                    }
                                }

                                //registrar pago para el comercio y para torogoz
                                ////++ tarifaPlataforma = tarifaPlataforma.add( diferenciaParaElComercio.multiply(porcentajeTarifaPlataforma)); //valor de tarifa a cobrar por el uso de la plataforma
                                ////++ diferenciaParaElComercio = diferenciaParaElComercio.subtract(tarifaPlataforma); //pago al comercio: despues de restar la distribucion de propias y restar la comision por el uso de la plataforma
                                //registrarDistribucionPorColaborador(null, venta, tarifaPlataforma, TipoAsociadoEnums.TOROGOZ);
                                ////++ registrarDistribucionPorColaborador(null, venta, diferenciaParaElComercio, TipoAsociadoEnums.COMERCIO);
                            }
                        }else{
                            return ResponsEntityUtil.responseApi400(String.format("Se ha detectado una configuracion erronea de distribucion para el producto: %d %s", invoiceItem.getProductId(), invoiceItem.getProductName()), null, false, HttpStatus.NOT_ACCEPTABLE);
                        }
                    } else {
                        return ResponsEntityUtil.responseApi400(String.format("No se ha encontrado una configuracion de distribucion de propina para el producto: %d %s", invoiceItem.getProductId(), invoiceItem.getProductName()), null, false, HttpStatus.NOT_ACCEPTABLE);
                    }
                }
            }else{
                ventaRepository.save(venta);
                ////++ tarifaPlataforma =  BigDecimal.valueOf(venta.getTotalSats()).multiply(porcentajeTarifaPlataforma); //valor de tarifa a cobrar por el uso de la plataforma

            }

            diferenciaParaElComercio = BigDecimal.valueOf(venta.getTotalSats()).subtract(satsDistribuidosColaboradores);
            //calculamos la comision a cobrar al comercio, tomandolo de su parte despues de haber hecho la distribucion:
            tarifaPlataforma = diferenciaParaElComercio.multiply(porcentajeTarifaPlataforma);
            registrarDistribucionPorColaborador(null, venta, tarifaPlataforma, TipoAsociadoEnums.TOROGOZ);

            //actualizamos la parte que le toca al comercio, restandole la comision de torogoz
            diferenciaParaElComercio = diferenciaParaElComercio.subtract(tarifaPlataforma);
            registrarDistribucionPorColaborador(null, venta, diferenciaParaElComercio, TipoAsociadoEnums.COMERCIO);


            venta.setEstado("0");//estado 0 es detectado por LNAPI para ser procesado efectuando el pago
            ventaRepository.save(venta);
            //if(dto.getApplySplit()==null)
                //venta.setApplySplit(false);

            if(dto.invoiceAddress==null)
                dto.invoiceAddress="";
            Optional<Empresa> emp = empresaRepo.findById(dto.getBusinessId());
            EmpresaDTO eDto = new EmpresaDTO();
            eDto.setApiId("");
            eDto.setSecretId("");
            if(emp.isPresent()){
                Empresa e = emp.get();
                eDto.setId(e.getId());
                eDto.setNombre(e.getNombre());
                eDto.setDescripcion(e.getDescripcion());
                eDto.setActivo(e.getActivo());
            }else{
                eDto.setId(0);
                eDto.setNombre("NO ENCONTRADO");
                eDto.setDescripcion("");
                eDto.setActivo(0);
            }
            dto.empresa = eDto;
            dto.totalAmountMSats = dto.totalAmountMSats!=null?dto.totalAmountMSats:0;
            dto.amountSat = dto.amountSat !=null?dto.amountSat :BigDecimal.ZERO;
            dto.createdAt = venta.getFechaCreacion()!=null?Date.from(venta.getFechaCreacion().toInstant(ZoneOffset.UTC)):Date.from(Instant.from(LocalDateTime.now(ZoneOffset.UTC)));
            if(venta.getFechaModificacion()!=null)
                dto.updatedAt = Date.from(venta.getFechaModificacion().toInstant(ZoneOffset.UTC));
            else dto.updatedAt = dto.createdAt;//fecha de creacion por defecto

            dto.customerName = "";
            dto.customerEmail = "";
            dto.shipping = venta.getShipping()!=null?venta.getShipping():BigDecimal.ZERO;
            dto.details = new ArrayList<>();
            dto.paymentSplit = new ArrayList<>();
            dto.totalSats = venta.getTotalSats();
            return ResponsEntityUtil.responseApi201("Registro Creado.",dto,true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }

    private void registrarDistribucionPorColaborador(ColaboradorProducto colab, Venta venta, BigDecimal msatsCadaColab, TipoAsociadoEnums tipoAsociado) {
        DistribucionPropina dp = new DistribucionPropina();
        dp.setVentaId(venta);
        //dp.setProducto(distProd.getId().getProducto());
        dp.setColabProd(colab);
        dp.setAttempts(1);
        dp.setStatus(false);
        dp.setAmountSat(msatsCadaColab);
        dp.setTipoAsociado(tipoAsociado.getValue());
        switch(tipoAsociado){
            case COLABORADOR -> dp.setInvoiceAddress(colab.getId().getColaborador().getLnAddress());
            case COMERCIO -> dp.setInvoiceAddress(venta.getEmpresaId().getLnAddress());
            case TOROGOZ -> {
                TorogozPay torogozPay = torogozPayRepository.findById(1);
                dp.setInvoiceAddress(torogozPay.getLnAddress());//no es necesario conocerla a este punto puesto que el nodo ya realizo dicho cobro
            }
        }

        dp.setEmpresa(venta.getEmpresaId());
        distPropService.agregarDistribucionPropina(dp);
    }

    public ResponseEntity<ApiResponse> actualizar(VentaDTO dto){

        try {

            Venta venta = convertir(dto);
            venta = ventaRepository.saveAndFlush(venta);
            if (venta != null){
                dto = convertirDTO(venta);
            }
            return ResponsEntityUtil.responseApiOk("Registro Actualizado.",dto,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public ResponseEntity<ApiResponse> eliminar(VentaDTO dto){

        try {
            Venta venta = convertir(dto);
            ventaRepository.delete(venta);
            return ResponsEntityUtil.responseApiOk("Registro Eliminado.",null,true);
        }catch (Exception e){
            return ResponsEntityUtil.responseApi400(e.getMessage(),null,false, HttpStatus.BAD_REQUEST);
        }


    }


    public Venta convertir(VentaDTO dto) throws Exception {
        Venta venta = new Venta();


        //venta.setU_id((dto.getSelloRecibido() != null) ? dto.getSelloRecibido():null);
        venta.setEstado("Q");//nacen en cola //dto.getEstado());

        Optional<Empresa> empresaOpc = empresaRepo.findById(dto.getBusinessId());
        if(empresaOpc.isEmpty()){
            throw new Exception(String.format("Empresa no encontrada id:%d",dto.getBusinessId()));
        }
        venta.setEmpresaId(empresaOpc.get());
        venta.setOrderId(dto.getOrderId());
        venta.setCustomerEmail(dto.getCustomerEmail());
        venta.setCustomerName(dto.getCustomerName());
        venta.setCurrency(dto.getCurrency());
        venta.setSubtotal(dto.getSubTotal());
        venta.setTaxes(dto.getTaxes());
        venta.setTotalAmount(dto.getTotalAmount());
        venta.setAmountSat(dto.getTotalAmountMSats());
        venta.setInvoiceDate(Calendar.getInstance().getTime());
        venta.setInvoiceStamp(UUID.randomUUID().toString().toUpperCase());
        venta.setApplySplit(dto.getApplySplit());
        venta.setTotalSats(dto.getTotalSats());
        Timestamp ts = Timestamp.from((Instant.now()));
        venta.setFechaCreacion(LocalDateTime.now(ZoneOffset.UTC));
        return venta;

    }

    public ArrayList<VentaDTO> convertirLista(ArrayList<Venta> lista){
        ArrayList<VentaDTO> ventas = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            VentaDTO dto = convertirDTO(lista.get(i));
            ventas.add(dto);
        }

        return ventas;

    }

    public VentaDTO convertirDTO(Venta venta){
        VentaDTO dto = new VentaDTO();



        return dto;
    }


    public Empresa convertirEmpresa(EmpresaDTO empresaDTO){
        Empresa dto = new Empresa();

        dto.setId(empresaDTO.getId());
        dto.setNombre(empresaDTO.getNombre());
        dto.setDescripcion(empresaDTO.getDescripcion());


        return dto;
    }

    public EmpresaDTO convertirEmpresaDTO(Empresa empresa){
        EmpresaDTO dto = new EmpresaDTO();

        dto.setId(empresa.getId());
        dto.setNombre(empresa.getNombre());
        dto.setDescripcion(empresa.getDescripcion());


        return dto;
    }


}
