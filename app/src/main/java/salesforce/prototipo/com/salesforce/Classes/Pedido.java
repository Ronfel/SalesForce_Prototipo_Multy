package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 25/08/2017.
 */

public class Pedido implements Serializable {
    private String sNumPed;
    private Cliente cliente;
    private ArrayList<ItemPedido> itemPedidos;
    private String sExisRegServer;
    private double dValTotPed;

    public Pedido() {
        this.sNumPed = "";
        this.cliente = new Cliente();
        this.itemPedidos = new ArrayList<>();
        this.dValTotPed = 0;
        this.sExisRegServer = null;
    }

    public String getsNumPed() {
        return sNumPed;
    }

    public void setsNumPed(String sNumPed) {
        this.sNumPed = sNumPed;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void addItemPedidos(ItemPedido itemPed) {
        this.itemPedidos.add(itemPed);
        this.dValTotPed = this.dValTotPed + itemPed.getdValorTot();
    }

    public void removeItemPedido (ItemPedido itemPed, int index){
        this.itemPedidos.remove(index);
        this.dValTotPed = this.dValTotPed - itemPed.getdValorTot();
    }

    public void setItemPedidos(ArrayList<ItemPedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public double getdValTotPed() {
        return dValTotPed;
    }

    public void setdValTotPed(double dValTotPed) {
        this.dValTotPed = dValTotPed;
    }

    public String getsExisRegServer() {
        return sExisRegServer;
    }

    public void setsExisRegServer(String sExisRegServer) {
        this.sExisRegServer = sExisRegServer;
    }
}
