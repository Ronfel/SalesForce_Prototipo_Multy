package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 17/08/2017.
 */

public class Agendamento implements Serializable {
    private String sCodAgend;
    private String sDatAgend;
    private String sHorAgend;
    private String sStaAgend;
    private Cliente cliente;
    private Vendedor vendedor;


    public Agendamento(){
        super();
    }

    public String getsCodAgend() {
        return sCodAgend;
    }

    public void setsCodAgend(String sCodAgend) {
        this.sCodAgend = sCodAgend;
    }

    public String getsDatAgend() {
        return sDatAgend;
    }

    public void setsDatAgend(String sDatAgend) {
        this.sDatAgend = sDatAgend;
    }

    public String getsHorAgend() {
        return sHorAgend;
    }

    public void setsHorAgend(String sHorAgend) {
        this.sHorAgend = sHorAgend;
    }

    public String getsStaAgend() {
        return sStaAgend;
    }

    public void setsStaAgend(String sStaAgend) {
        this.sStaAgend = sStaAgend;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
}
