package com.porempresa.jwt.cliente.comando;

import com.porempresa.jwt.cliente.ManejadorPeticiones;

public class Consola extends ManejadorPeticiones {
    private IComando operacion1;
    private IComando operacion2;
    private IComando operacion3;
    private IComando operacion4;
    public Consola() {

    }
    public void inicializar(IComando comando){
        this.operacion1 = comando;
    }
    public void continuar(IComando comando){
        this.operacion2 = comando;
    }
    public void ejecutarSiguiente(IComando comando){
        this.operacion3 = comando;
    }
    public void finalizar(IComando comando){
        this.operacion4 = comando;
    }
    public void ejecutarOperaciones(){
        if(operacion1 instanceof IComando){
            operacion1.ejecutar();
        }if(operacion2 instanceof IComando){
            operacion2.ejecutar();
        }
        if(operacion3 instanceof IComando){
            operacion3.ejecutar();
        }if(operacion4 instanceof IComando){
            operacion4.ejecutar();
        }
    }
}