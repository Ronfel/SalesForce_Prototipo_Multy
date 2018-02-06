package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 01/09/2017.
 */

public class Preco extends TabPreco implements Serializable {
    private double dPreco;
    private String sCodMat;

    public double getdPreco() {
        return dPreco;
    }

    public void setdPreco(double dPreco) {
        this.dPreco = dPreco;
    }

    public String getsCodMat() {
        return sCodMat;
    }

    public void setsCodMat(String sCodMat) {
        this.sCodMat = sCodMat;
    }
}
