package salesforce.prototipo.com.salesforce.Classes;


import android.content.Context;

import com.embarcadero.javaandroid.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import salesforce.prototipo.com.salesforce.SQLiteControle.Delete;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforce.SQLiteControle.Update;

/**
 * Created by Rodrigo on 07/08/2017.
 */

public class Sincronizacao /*implements Runnable*/ {
    private String sMensagem;
    private boolean bStatus;
    private DSRESTConnection cConnection;
    //private String sHost; //"192.168.99.135";
    private int iPorta = 1032;
    private Context context;
    private HashMap<String, Boolean> hashMap;
    private Vendedor vendedor;
    /*private String sUserWeb;
    private String sUser;
    private String sSenha;
    private String sCodVend;*/

    public Sincronizacao(Context context, HashMap<String, Boolean> hashMap, Vendedor vendedor) {
        this.sMensagem = "";
        this.bStatus = true;
        this.context = context;
        this.hashMap = hashMap;
        //this.sUser = vendedor.getsUser_id();
        //this.sUserWeb = vendedor.getsUser_web();
        //this.sSenha = vendedor.getsSenha();
        //this.sCodVend = vendedor.getsCodVend();
        //this.sHost = vendedor.getsHost();
        this.vendedor = vendedor;

        this.conecta();
        this.disconecta();
    }

    private void DownVendedor() {
        try {
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insVend = new Update(context);
            insVend.sincronizarVendedor(proxy.GetDados_Vendedor(vendedor.getsUser_web()), vendedor.getsSenha(), vendedor.getsHost());
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void UpPedidos() {
        try {
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);

            Update upPedidoServer = new Update(context);
            Update upItemPedidoServer = new Update(context);
            Read readPed = new Read(context);
            Read readItemPed = new Read(context);

            proxy.PutDados_Pedidos(upPedidoServer.uploadPedidosServidor(readPed.getPedidos(" WHERE A.STAEXISSERVER = 'N'", "*")),
                                   upItemPedidoServer.uploadItemPedServidor(readItemPed.getItemPed(" WHERE A.STAEXISSERVER = 'N'", "*")),
                                   vendedor.getsCodVend(),
                                   vendedor.getsUser_id());

            Delete excluiPed = new Delete(context);
            excluiPed.deletePedido(null, "STAEXISSERVER = 'N'");
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void DownPedidos() {
        try {
            Delete delete = new Delete(context);
            delete.deletePedido(null, "STAEXISSERVER = 'S'");
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insPedidos = new Update(context);
            insPedidos.sincronizarPedido(proxy.GetDados_Pedidos(vendedor.getsCodVend()));
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void DownProdutos() {
        try {
            Delete delete = new Delete(context);
            delete.deleteProdutos();
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insProdutos = new Update(context);
            insProdutos.sincronizarProdutos(proxy.GetDados_Produtos());
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void DownAgendamentos() {
        try {
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insAgend = new Update(context);
            insAgend.sincronizarAgendamentos(proxy.GetDados_Agendamentos());
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void DownCondPag() {
        try {

            Delete delete = new Delete(context);
            delete.deleteCondPag();
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insCondPag = new Update(context);
            insCondPag.sincronizarCondPag(proxy.GetDados_CondPag());
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void DownTabPreco() {
        try {
            Delete delete = new Delete(context);
            delete.deleteTabPreco();
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insTabPreco = new Update(context);
            insTabPreco.sincronizarTabPreco(proxy.GetDados_TabPreco(), "TBFATU060sf");

            Update insPreco = new Update(context);
            insPreco.sincronizarTabPreco(proxy.GetDados_Precos(), "TBFATU057sf");
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void DownCliente() {
        try {
            Read readCli = new Read(context);
            DecimalFormat df = new DecimalFormat("000000");
            List<Cliente> lista = readCli.getClientes(" WHERE STAEXISSERVER <> 'N' ", "MAX(A.CODCLI)");

            String sCodCli;
            if (lista.get(0).getsCodCli() != null) {
                sCodCli = df.format(Integer.parseInt(lista.get(0).getsCodCli()));
            } else {
                sCodCli = "";

            }

            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);
            Update insCli = new Update(context);
            insCli.sincronizarCliente(proxy.GetDados_Clientes(sCodCli));
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void UpAgendamentos() {
        try {
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);

            Update upAgendServer = new Update(context);
            Read readCli = new Read(context);
            proxy.PutDados_Agendamentos(upAgendServer.uploadAgendServidor(readCli.getAgendamentos(" WHERE STAAGEND = 'S'", "*")));

            Delete excluiAgend = new Delete(context);
            excluiAgend.deleteAgend();//Deleta Clientes Cadastrados no Mobile, pois eles serão buscados no método GetDados_Clientes
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void UpCliente() {
        try {
            DSProxy.TServerMethods1 proxy = new DSProxy.TServerMethods1(cConnection);

            Update insCliServer = new Update(context);
            Read readCli = new Read(context);
            proxy.PutDados_Clientes(insCliServer.uploadCliServidor(readCli.getClientes(" WHERE STAEXISSERVER = 'N' ", "*")),vendedor.getsCodVend());

            Delete deleteCli = new Delete(context);
            deleteCli.deleteCliente(null);//Deleta Clientes Cadastrados no Mobile, pois eles serão buscados no método GetDados_Clientes
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    /*@Override
    public void run() {
        try {
            this.cConnection = new DSRESTConnection();
            this.cConnection.setHost(sHost);
            this.cConnection.setPort(iPorta);
            this.cConnection.setProtocol("http");
            this.cConnection.setUserName(sUserWeb);
            this.cConnection.setPassword(sSenha);

            if (hashMap == null) {
                DownVendedor();
                Read read = new Read(context);
                Vendedor vendedor = read.getVendedor();
                if (vendedor.getsCodVend().equals("")) {
                    this.sMensagem = "Usuário não Cadastrado!";
                    this.bStatus = false;
                    return;
                }
                sCodVend = vendedor.getsCodVend();
            }

            //Atualiza Servidor com dados do mobile
            if ((hashMap != null) && (hashMap.get("swtCliCad")))
                UpCliente();
            //O Upload dos Agendamentos não necessita de validação
            //Pois, uma vez visitado a visita deve ser assinalada no Servidor
            UpAgendamentos();

            //Atualiza mobile com dados do Servidor
            if ((hashMap == null) || hashMap.get("swtCli"))
                DownCliente();
            if ((hashMap == null) || hashMap.get("swtAgend"))
                DownAgendamentos();

            if ((hashMap == null) || hashMap.get("swtTabPre"))
                DownTabPreco();
            if ((hashMap == null) || hashMap.get("swtCondPag"))
                DownCondPag();

            if ((hashMap == null) || hashMap.get("swtUltPedCliAgend"))
                DownPedidos();

            if ((hashMap == null) || hashMap.get("swtProdutos"))
                DownProdutos();

            if ((hashMap != null) && hashMap.get("swtPedRealiz"))
                UpPedidos();

        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }*/

    private void conecta() {
       /* Thread thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }*/
        try {
            this.cConnection = new DSRESTConnection();
            this.cConnection.setHost(vendedor.getsHost());
            this.cConnection.setPort(iPorta);
            this.cConnection.setProtocol("http");
            this.cConnection.setUserName(vendedor.getsUser_web());
            this.cConnection.setPassword(vendedor.getsSenha());

            if (hashMap == null) {
                DownVendedor();
                Read read = new Read(context);
                Vendedor vend = read.getVendedor();
                if (vend.getsCodVend().equals("")) {
                    this.sMensagem = "Usuário não Cadastrado!";
                    this.bStatus = false;
                    return;
                }
                vendedor.setsCodVend(vend.getsCodVend());
            }

            //Atualiza Servidor com dados do mobile
            if ((hashMap != null) && (hashMap.get("swtCliCad")))
                UpCliente();
            //O Upload dos Agendamentos não necessita de validação
            //Pois, uma vez visitado a visita deve ser assinalada no Servidor
            UpAgendamentos();

            //Atualiza mobile com dados do Servidor
            if ((hashMap == null) || hashMap.get("swtCli"))
                DownCliente();
            if ((hashMap == null) || hashMap.get("swtAgend"))
                DownAgendamentos();

            if ((hashMap == null) || hashMap.get("swtTabPre"))
                DownTabPreco();
            if ((hashMap == null) || hashMap.get("swtCondPag"))
                DownCondPag();

            if ((hashMap == null) || hashMap.get("swtUltPedCliAgend"))
                DownPedidos();

            if ((hashMap == null) || hashMap.get("swtProdutos"))
                DownProdutos();

            if ((hashMap != null) && hashMap.get("swtPedRealiz"))
                UpPedidos();

        } catch (Exception e) {
            this.sMensagem = e.getMessage();
            this.bStatus = false;
        }
    }

    private void disconecta() {
        if (this.cConnection != null) {
            try {
                this.cConnection.CloseSession();
            } catch (Exception e) {

            } finally {
                this.cConnection = null;
            }
        }
    }

    public String getsMensagem() {
        return sMensagem;
    }

    public boolean isbStatus() {
        return bStatus;
    }
}
