package salesforce.prototipo.com.salesforce.SQLiteControle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.embarcadero.javaandroid.TDataSet;
import com.embarcadero.javaandroid.TJSONArray;
import com.embarcadero.javaandroid.TJSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import salesforce.prototipo.com.salesforce.Classes.Agendamento;
import salesforce.prototipo.com.salesforce.Classes.Cliente;
import salesforce.prototipo.com.salesforce.Classes.ItemPedido;
import salesforce.prototipo.com.salesforce.Classes.Pedido;

/**
 * Created by Rodrigo on 11/08/2017.
 */

public class Update extends SQLiteOpenHelper {
    private final static String DATABASE_NOME = "sqliteControle.db";
    private final static int DATABASE_VERSAO = 1;
    private static final String PATH_DB = "/data/user/0/salesforce.prototipo.com.salesforceprototipo/databases/sqliteControle.db";

    private Context context;
    private SQLiteDatabase db;

    public Update(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
        this.context = context;
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //logica para atualizar o BD;
    }

    public TJSONObject uploadPedidosServidor(ArrayList<Pedido> aryltPed){
        TJSONObject jo = new TJSONObject();
        TJSONArray jsonArray = new TJSONArray();
        //TDataSet ds = null;
        try {
            for(int i = 0; i<= aryltPed.size()-1;i++) {
                TJSONObject jsonObject = new TJSONObject();
                jsonObject.addPairs("NUMPED",  aryltPed.get(i).getsNumPed());
                jsonObject.addPairs("CODCLI", aryltPed.get(i).getCliente().getsCodCli());
                jsonObject.addPairs("CODCPAGTO", aryltPed.get(i).getCliente().getCondPagamento().getsCondPag());
                jsonObject.addPairs("STAEXISSERVER", aryltPed.get(i).getsExisRegServer());
                jsonObject.addPairs("CODTABPRECO", aryltPed.get(i).getCliente().getTabPreco().getsCodTabPreco());

                jsonArray.add(jsonObject);
            }
            jo.addPairs("TBFATU010",jsonArray);

            //ds = TDataSet.createFrom(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    public TJSONObject uploadItemPedServidor(ArrayList<ItemPedido> aryltItemPed){
        TJSONObject jo = new TJSONObject();
        TJSONArray jsonArray = new TJSONArray();
        //TDataSet ds = null;
        try {
            for(int i = 0; i<= aryltItemPed.size()-1;i++) {
                TJSONObject jsonObject = new TJSONObject();
                jsonObject.addPairs("NUMPED"    , aryltItemPed.get(i).getPedido().getsNumPed());
                jsonObject.addPairs("NUMITEMPED", aryltItemPed.get(i).getsNumItem());
                jsonObject.addPairs("CODMAT"    , aryltItemPed.get(i).getProduto().getsCodMat());
                jsonObject.addPairs("QTDE"      , aryltItemPed.get(i).getdQtde());
                jsonObject.addPairs("PERCACRESDESCITEM", aryltItemPed.get(i).getdPercDesc());
                jsonObject.addPairs("DATNEC"    , aryltItemPed.get(i).getsDataNecess());
                jsonObject.addPairs("STAEXISSERVER", aryltItemPed.get(i).getsExisRegServer());

                jsonArray.add(jsonObject);
            }
            jo.addPairs("TBFATU013",jsonArray);

            //ds = TDataSet.createFrom(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    public TJSONObject uploadAgendServidor(ArrayList<Agendamento> aryltAgend){
        TJSONObject jo = new TJSONObject();
        TJSONArray jsonArray = new TJSONArray();
        //TDataSet ds = null;
        try {
            for(int i = 0; i<= aryltAgend.size()-1;i++) {
                TJSONObject jsonObject = new TJSONObject();
                jsonObject.addPairs("CODAGEND", aryltAgend.get(i).getsCodAgend());
                jsonObject.addPairs("CODCLI", aryltAgend.get(i).getCliente().getsCodCli());
                jsonObject.addPairs("CODVEND", aryltAgend.get(i).getVendedor().getsCodVend());
                jsonObject.addPairs("DATAGEND",aryltAgend.get(i).getsDatAgend());

                jsonArray.add(jsonObject);
            }
            jo.addPairs("TBFATU222",jsonArray);

            //ds = TDataSet.createFrom(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    public TJSONObject uploadCliServidor(ArrayList<Cliente> aryltClientes){
        TJSONObject jo = new TJSONObject();
        TJSONArray jsonArray = new TJSONArray();
        //TDataSet ds = null;
        try {
            for(int i = 0; i<= aryltClientes.size()-1;i++) {
                TJSONObject jsonObject = new TJSONObject();
                jsonObject.addPairs("RAZSOCCLI", aryltClientes.get(i).getsRazSocCli());
                jsonObject.addPairs("TIPFISJUR", aryltClientes.get(i).getsTipo());
                jsonObject.addPairs("NUMCGCCPFCLI",aryltClientes.get(i).getsCnpjCpf());
                jsonObject.addPairs("INSCRESTA", aryltClientes.get(i).getsInscrEst());
                jsonObject.addPairs("TELCLI", aryltClientes.get(i).getsTelefone());
                jsonObject.addPairs("CONTATOCLI", aryltClientes.get(i).getsContatoCli());
                jsonArray.add(jsonObject);
            }
            jo.addPairs("TBFATU006",jsonArray);

            //ds = TDataSet.createFrom(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    public boolean sincronizarPedido(TDataSet ds){
        openDB();
        try{
            String sNumped;
            while (ds.next()) {
                ContentValues cv = new ContentValues();

                sNumped = ds.getValue("NUMPED").GetAsString();
                cv.put("NUMPED"     , sNumped);
                cv.put("CODCLI"     , ds.getValue("CODCLI").GetAsString());
                cv.put("CODCPAGTO"  , ds.getValue("CODCPAGTO").GetAsString());
                cv.put("CODTABPRECO", ds.getValue("CODTABPRECO").GetAsString());
                cv.put("STAEXISSERVER", "S");

                db.insert("TBFATU010sf", null, cv);

                do{
                    if (!sNumped.equals(ds.getValue("NUMPED").GetAsString())) {
                        ds.prior();
                        break;
                    }

                    cv = new ContentValues();
                    cv.put("NUMPED"    , ds.getValue("NUMPED").GetAsString());
                    cv.put("NUMITEMPED", ds.getValue("NUMITEMPED").GetAsString());
                    cv.put("CODMAT"    , ds.getValue("CODMAT").GetAsString());
                    cv.put("QTDE"      , ds.getValue("QTDE").GetAsDouble());
                    cv.put("PERCACRESDESCITEM", ds.getValue("PERCACRESDESCITEM").GetAsDouble());
                    cv.put("DATNEC"    , ds.getValue("DATNEC").GetAsString());
                    cv.put("STAEXISSERVER", "S");

                    db.insert("TBFATU013sf", null, cv);
                }while (ds.next());
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }


    public boolean sincronizarProdutos(TDataSet ds){
        openDB();
        try{
            while (ds.next()) {
                ContentValues cv = new ContentValues();
                cv.put("CODMAT", ds.getValue("CODMAT").GetAsString());
                cv.put("DESMAT", ds.getValue("DESMAT").GetAsString());
                cv.put("CODUNIMED", ds.getValue("CODUNIMED").GetAsString());

                db.insert("TBCOMP002sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean sincronizarVendedor(TDataSet ds, String sSenha, String sHost){
        openDB();
        try{
            while (ds.next()) {
                ContentValues cv = new ContentValues();
                cv.put("CODVEND"   , ds.getValue("CODVEND").GetAsString());
                cv.put("USER_ID"   , ds.getValue("USER_ID").GetAsString());
                cv.put("USER_IDWEB", ds.getValue("USER_IDWEB").GetAsString());
                cv.put("SENHA"     , sSenha);
                cv.put("HOST"      , sHost);

                db.insert("TBSENH006sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean sincronizarCondPag(TDataSet ds){
        openDB();
        try{
            while (ds.next()) {
                ContentValues cv = new ContentValues();
                cv.put("CODCPAGTO", ds.getValue("CODCPAGTO").GetAsString());
                cv.put("DESCPAGTO", ds.getValue("DESCPAGTO").GetAsString());

                db.insert("TBCOMP009sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean sincronizarTabPreco(TDataSet ds, String sTabela){
        openDB();
        try{
            while (ds.next()) {
                ContentValues cv = new ContentValues();
                cv.put("CODTABPRECO", ds.getValue("CODTABPRECO").GetAsString());
                if (sTabela.equals("TBFATU057sf")) {
                    cv.put("CODMAT", ds.getValue("CODMAT").GetAsString());
                    cv.put("PRECO", ds.getValue("PRECO").GetAsDouble());
                }else {
                    cv.put("DESTABPRECO", ds.getValue("DESTABPRECO").GetAsString());
                }
                db.insert(sTabela, null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean sincronizarAgendamentos(TDataSet ds){
        openDB();
        try{
            while (ds.next()) {
                ContentValues cv = new ContentValues();
                cv.put("CODAGEND", ds.getValue("CODAGEND").GetAsString());
                cv.put("CODCLI"  , ds.getValue("CODCLI").GetAsString());
                cv.put("CODVEND" , ds.getValue("CODVEND").GetAsString());
                cv.put("DATAGEND", ds.getValue("DATAGEND").GetAsString());
                cv.put("HORAGEND", ds.getValue("HORAGEND").GetAsString());
                cv.put("STAAGEND", ds.getValue("STAAGEND").GetAsString());

                db.insert("TBFATU222sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }


    public boolean sincronizarCliente(TDataSet ds){
        openDB();
        try{
            while (ds.next()) {
                ContentValues cv = new ContentValues();
                cv.put("CODCLI", ds.getValue("CODCLI").GetAsString());
                cv.put("RAZSOCCLI", ds.getValue("RAZSOCCLI").GetAsString());
                cv.put("TIPFISJUR", ds.getValue("TIPFISJUR").GetAsString());
                cv.put("NUMCGCCPFCLI", ds.getValue("NUMCGCCPFCLI").GetAsString());
                cv.put("INSCRESTA", ds.getValue("INSCRESTA").GetAsString());
                cv.put("TELCLI", ds.getValue("TELCLI").GetAsString());
                cv.put("CODCPAGTO", ds.getValue("CODCPAGTO").GetAsString());
                cv.put("CODTABPRECO", ds.getValue("CODTABPRECO").GetAsString());
                cv.put("ENDCLI", ds.getValue("ENDCLI").GetAsString());
                cv.put("STAEXISSERVER", "S");

                db.insert("TBFATU006sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean insertPedido(Pedido pedido){
        openDB();
        try{
            ContentValues cv = new ContentValues();
            cv.put("NUMPED",  pedido.getsNumPed());
            cv.put("CODCLI", pedido.getCliente().getsCodCli());
            cv.put("CODCPAGTO", pedido.getCliente().getCondPagamento().getsCondPag());
            cv.put("STAEXISSERVER", "N");
            cv.put("CODTABPRECO", pedido.getCliente().getTabPreco().getsCodTabPreco());

            db.insert("TBFATU010sf", null, cv);

            cv = new ContentValues();

            for (int i = 0; i <= pedido.getItemPedidos().size()-1 ;i++) {
                cv.put("NUMPED", pedido.getsNumPed());
                cv.put("NUMITEMPED", pedido.getItemPedidos().get(i).getsNumItem());
                cv.put("CODMAT", pedido.getItemPedidos().get(i).getProduto().getsCodMat());
                cv.put("QTDE", pedido.getItemPedidos().get(i).getdQtde());
                cv.put("PERCACRESDESCITEM", pedido.getItemPedidos().get(i).getdPercDesc());
                cv.put("DATNEC", pedido.getItemPedidos().get(i).getsDataNecess());
                cv.put("STAEXISSERVER", "N");
                db.insert("TBFATU013sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean insertDadosCli(Cliente cliente){
        openDB();
        try{
            Read read = new Read(context);
            List<Cliente> lista = read.getClientes("","MAX(CODCLI)");

            ContentValues cv = new ContentValues();
            DecimalFormat df = new DecimalFormat("000000");
            cv.put("CODCLI",  df.format(Integer.parseInt(lista.get(0).getsCodCli()) + 1));
            cv.put("RAZSOCCLI", cliente.getsRazSocCli());
            cv.put("TIPFISJUR", cliente.getsTipo());
            cv.put("NUMCGCCPFCLI", cliente.getsCnpjCpf());
            cv.put("INSCRESTA", cliente.getsInscrEst());
            cv.put("TELCLI", cliente.getsTelefone());
            cv.put("CONTATOCLI", cliente.getsContatoCli());
            //cv.put("CODCPAGTO", clientes.getsCodPag());
            //cv.put("CODTABPRECO", clientes.getsCodTabPreco());
            cv.put("STAEXISSERVER", "N");
            db.insert("TBFATU006sf", null, cv);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

/*    public boolean updatePedido(Pedido pedido){
        openDB();
        try{
            String where = "NUMPED = '" + pedido.getsNumPed() + "'";
            ContentValues cv = new ContentValues();
            cv.put("NUMPED",  pedido.getsNumPed());
            cv.put("CODCLI", pedido.getCliente().getsCodCli());
            cv.put("CODCPAGTO", pedido.getCliente().getCondPagamento().getsCondPag());
            cv.put("STAEXISSERVER", "N");
            cv.put("CODTABPRECO", pedido.getCliente().getTabPreco().getsCodTabPreco());

            db.update("TBFATU010sf", cv, where, null);

            cv = new ContentValues();


            for (int i = 0; i <= pedido.getItemPedidos().size()-1 ;i++) {
                cv.put("NUMPED", pedido.getsNumPed());
                cv.put("NUMITEMPED", pedido.getItemPedidos().get(i).getsNumItem());
                cv.put("CODMAT", pedido.getItemPedidos().get(i).getProduto().getsCodMat());
                cv.put("QTDE", pedido.getItemPedidos().get(i).getdQtde());
                db.insert("TBFATU013sf", null, cv);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }*/
    public boolean updateDadosCli(Cliente cliente){
        openDB();
        try{
            String where = "CODCLI = '" + cliente.getsCodCli() + "'";
            ContentValues cv = new ContentValues();
            cv.put("CODCLI", cliente.getsCodCli());
            cv.put("RAZSOCCLI", cliente.getsRazSocCli());
            cv.put("TIPFISJUR", cliente.getsTipo());
            cv.put("NUMCGCCPFCLI", cliente.getsCnpjCpf());
            cv.put("INSCRESTA", cliente.getsInscrEst());
            cv.put("TELCLI", cliente.getsTelefone());
            cv.put("CONTATOCLI", cliente.getsContatoCli());
            //cv.put("CODCPAGTO", clientes.getsCodPag());
            //cv.put("CODTABPRECO", clientes.getsCodTabPreco());
            cv.put("STAEXISSERVER", "N");
            db.update("TBFATU006sf", cv, where, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean updateDadosAgend(String sCodAgend){
        openDB();
        try{
            String where = "CODAGEND = '" + sCodAgend + "'";
            ContentValues cv = new ContentValues();
            cv.put("STAAGEND", "S");
            db.update("TBFATU222sf", cv, where, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    private void openDB(){
        if(!db.isOpen()){
            db = context.openOrCreateDatabase(PATH_DB, SQLiteDatabase.OPEN_READWRITE, null);
        }
    }
}
