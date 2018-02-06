package salesforce.prototipo.com.salesforce.Classes;

import java.io.Serializable;

/**
 * Created by Rodrigo on 06/08/2017.
 */

public class Vendedor implements Serializable {
    private String sCodVend;
    private String sUser_id;
    private String sUser_web;
    private String sSenha;
    private String sHost;

    public Vendedor(){
        this.sUser_web = "";
        this.sSenha = "";
        this.sCodVend = "";
        this.sUser_id = "";
        this.sHost = "";
    }

    public String getsUser_id() {
        return sUser_id;
    }

    public void setsUser_id(String sUser_id) {
        this.sUser_id = sUser_id;
    }

    public String getsSenha() {
        return sSenha;
    }

    public void setsSenha(String sSenha) {
        this.sSenha = sSenha;
    }

    public String getsCodVend() {
        return sCodVend;
    }

    public void setsCodVend(String sCodVend) {
        this.sCodVend = sCodVend;
    }

    public String getsUser_web() {
        return sUser_web;
    }

    public void setsUser_web(String sUser_web) {
        this.sUser_web = sUser_web;
    }

    public String getsHost() {
        return sHost;
    }

    public void setsHost(String sHost) {
        this.sHost = sHost;
    }
}
