package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 25/08/2017.
 */

public class ItemPedido implements Serializable {
    private Pedido pedido;
    private String sNumItem;
    private Produto produto;
    private double dQtde;
    private Preco preco;
    private double dValorTot;
    private String sDataNecess;
    private double dPercDesc;
    private String sExisRegServer;

    public ItemPedido() {
        this.pedido = new Pedido();
        this.sNumItem = "";
        this.produto = new Produto();
        this.dQtde = 0;
        this.preco = new Preco();
        this.dValorTot = 0;
        this.sDataNecess = "";
        this.dPercDesc = 0;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getsNumItem() {
        return sNumItem;
    }

    public void setsNumItem(String sNumItem) {
        this.sNumItem = sNumItem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getdQtde() {
        return dQtde;
    }

    public void setdQtde(double dQtde) {
        this.dQtde = dQtde;
    }

    public Preco getPreco() {
        return preco;
    }

    public void setPreco(Preco preco) {
        this.preco = preco;
    }

    public double getdValorTot() {
        return dValorTot;
    }

    public void setdValorTot(double dValorTot) {
        this.dValorTot = dValorTot;
    }

    public String getsDataNecess() {
        return sDataNecess;
    }

    public void setsDataNecess(String sDataNecess) {
        this.sDataNecess = sDataNecess;
    }

    public double getdPercDesc() {
        return dPercDesc;
    }

    public void setdPercDesc(double dPercDesc) {
        this.dPercDesc = dPercDesc;
    }

    public String getsExisRegServer() {
        return sExisRegServer;
    }

    public void setsExisRegServer(String sExisRegServer) {
        this.sExisRegServer = sExisRegServer;
    }
}
