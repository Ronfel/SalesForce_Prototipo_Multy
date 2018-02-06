package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 25/08/2017.
 */

public class TabPreco implements Serializable {
    private String sCodTabPreco;
    private String sDesTabPreco;

    public TabPreco() {
        this.sCodTabPreco = "";
        this.sDesTabPreco = "";
    }

    public String getsCodTabPreco() {
        return sCodTabPreco;
    }

    public void setsCodTabPreco(String sCodTabPreco) {
        this.sCodTabPreco = sCodTabPreco;
    }

    public String getsDesTabPreco() {
        return sDesTabPreco;
    }

    public void setsDesTabPreco(String sDesTabPreco) {
        this.sDesTabPreco = sDesTabPreco;
    }
}
