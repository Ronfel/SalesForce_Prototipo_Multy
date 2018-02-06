package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 25/08/2017.
 */

public class CondPagamento implements Serializable {
    private String sCondPag;
    private String sDesPag;

    public CondPagamento() {
        this.sCondPag = "";
        this.sDesPag = "";
    }

    public String getsCondPag() {
        return sCondPag;
    }

    public void setsCondPag(String sCondPag) {
        this.sCondPag = sCondPag;
    }

    public String getsDesPag() {
        return sDesPag;
    }

    public void setsDesPag(String sDesPag) {
        this.sDesPag = sDesPag;
    }
}
