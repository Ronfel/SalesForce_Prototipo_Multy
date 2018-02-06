package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 10/08/2017.
 */

public class Cliente implements Serializable{
    private String sCodCli;
    private String sRazSocCli;
    private String sTipo;
    private String sCnpjCpf;
    private String sInscrEst;
    private String sTelefone;
    private String sExisRegServer;
    private String sEndCli;
    private String sContatoCli;
    private TabPreco tabPreco;
    private CondPagamento condPagamento;
    private ArrayList<Pedido> Pedidos;

    public Cliente() {
        this.sCodCli = "";
        this.sRazSocCli = "";
        this.sTipo = "";
        this.sInscrEst = "";
        this.sTelefone = "";
        this.sCnpjCpf = "";
    }

    public String getsCodCli() {
        return sCodCli;
    }

    public void setsCodCli(String sCodCli) {
        this.sCodCli = sCodCli;
    }

    public String getsRazSocCli() {
        return sRazSocCli;
    }

    public void setsRazSocCli(String sRazSocCli) {
        this.sRazSocCli = sRazSocCli;
    }

    public String getsTipo() {
        return sTipo;
    }

    public void setsTipo(String sTipo) {
        this.sTipo = sTipo;
    }

    public String getsCnpjCpf() {
        return sCnpjCpf;
    }

    public void setsCnpjCpf(String sCnpjCpf) {
        this.sCnpjCpf = sCnpjCpf;
    }

    public String getsInscrEst() {
        return sInscrEst;
    }

    public void setsInscrEst(String sInscrEst) {
        this.sInscrEst = sInscrEst;
    }

    public String getsTelefone() {
        return sTelefone;
    }

    public void setsTelefone(String sTelefone) {
        this.sTelefone = sTelefone;
    }

    public TabPreco getTabPreco() {
        return tabPreco;
    }

    public void setTabPreco(TabPreco tabPreco) {
        this.tabPreco = tabPreco;
    }

    public CondPagamento getCondPagamento() {
        return condPagamento;
    }

    public void setCondPagamento(CondPagamento condPagamento) {
        this.condPagamento = condPagamento;
    }

    public String getsExisRegServer() {
        return sExisRegServer;
    }

    public void setsExisRegServer(String sExisRegServer) {
        this.sExisRegServer = sExisRegServer;
    }

    public String getsEndCli() {
        return sEndCli;
    }

    public void setsEndCli(String sEndCli) {
        this.sEndCli = sEndCli;
    }

    public String getsContatoCli() {
        return sContatoCli;
    }

    public void setsContatoCli(String sContatoCli) {
        this.sContatoCli = sContatoCli;
    }

    public ArrayList<Pedido> getPedidos() {
        return Pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        Pedidos = pedidos;
    }
}
