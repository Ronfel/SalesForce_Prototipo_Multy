package salesforce.prototipo.com.salesforce.SQLiteControle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import salesforce.prototipo.com.salesforce.Classes.Cliente;
import salesforce.prototipo.com.salesforce.Classes.Pedido;


/**
 * Created by Rodrigo on 11/08/2017.
 */

public class Delete extends SQLiteOpenHelper {
    private final static String DATABASE_NOME = "sqliteControle.db";
    private final static int DATABASE_VERSAO = 1;
    private static final String PATH_DB = "/data/user/0/salesforce.prototipo.com.salesforceprototipo/databases/sqliteControle.db";

    private Context context;
    private SQLiteDatabase db;

    public Delete(Context context) {
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

    public boolean deletaTabelas(){
        openDB();
        String deleteTable;

        try {
            //Tabela de Vendedores
            deleteTable = "DROP TABLE IF EXISTS TBSENH006sf";
            db.execSQL(deleteTable);

            //Tabela de Clientes
            deleteTable = "DROP TABLE IF EXISTS TBFATU006sf";
            db.execSQL(deleteTable);

            //Tabela de Agendamentos
            deleteTable = "DROP TABLE IF EXISTS TBFATU222sf";
            db.execSQL(deleteTable);

            //Tabela de Preços
            deleteTable = "DROP TABLE IF EXISTS TBFATU060sf";
            db.execSQL(deleteTable);

            //Tabela de Preços
            deleteTable = "DROP TABLE IF EXISTS TBFATU057sf";
            db.execSQL(deleteTable);

            //Tabela de Condições de Pagamento
            deleteTable = "DROP TABLE IF EXISTS TBCOMP009sf";
            db.execSQL(deleteTable);

            //Tabela de Produtos
            deleteTable = "DROP TABLE IF EXISTS TBCOMP002sf";
            db.execSQL(deleteTable);

            //Tabela de Pedidos
            deleteTable = "DROP TABLE IF EXISTS TBFATU010sf";
            db.execSQL(deleteTable);

            //Tabela de Itens do Pedido
            deleteTable = "DROP TABLE IF EXISTS TBFATU013sf";
            db.execSQL(deleteTable);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deleteProdutos(){
        openDB();
        //String where;
        try {
            db.delete("TBCOMP002sf", null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deleteTabPreco(){
        openDB();
        //String where;
        try {
            db.delete("TBFATU057sf", null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deleteCondPag(){
        openDB();
        //String where;
        try {
            db.delete("TBCOMP009sf", null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deleteAgend(){
        openDB();
        //String where = "STAAGEND = 'S'";
        try {
            db.delete("TBFATU222sf", null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deletePedido(Pedido pedido, String sStaExisServer){
        openDB();
        String where;
        if (pedido != null) {
            where = "NUMPED = '" + pedido.getsNumPed() + "'";
        }else{
            where = sStaExisServer;
        }
        try {
            db.delete("TBFATU013sf", where, null);
            db.delete("TBFATU010sf", where, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deleteCliente(Cliente cliente){
        openDB();
        String where;
        if (cliente != null) {
            where = "CODCLI = '" + cliente.getsCodCli() + "'";
        }else{
            where = "STAEXISSERVER = 'N'";
        }
        try {
            db.delete("TBFATU006sf", where, null);
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
