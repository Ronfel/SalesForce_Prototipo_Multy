package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 25/08/2017.
 */

public class Produto implements Serializable {
     private String sCodMat;
     private String sDesmat;
     private String sCodUnimed;

    /*public Produto(String sCodMat, String sDesmat) {
        this.sCodMat = sCodMat;
        this.sDesmat = sDesmat;
    }*/

    public String getsCodMat() {
        return sCodMat;
    }

    public void setsCodMat(String sCodMat) {
        this.sCodMat = sCodMat;
    }

    public String getsDesmat() {
        return sDesmat;
    }

    public void setsDesmat(String sDesmat) {
        this.sDesmat = sDesmat;
    }

    public String getsCodUnimed() {
        return sCodUnimed;
    }

    public void setsCodUnimed(String sCodUnimed) {
        this.sCodUnimed = sCodUnimed;
    }
}
