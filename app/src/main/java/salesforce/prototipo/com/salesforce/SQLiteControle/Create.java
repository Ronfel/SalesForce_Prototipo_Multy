package salesforce.prototipo.com.salesforce.SQLiteControle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rodrigo on 11/08/2017.
 */

public class Create extends SQLiteOpenHelper {
    private final static String DATABASE_NOME = "sqliteControle.db";
    private final static int DATABASE_VERSAO = 1;
    private static final String PATH_DB = "/data/user/0/salesforce.prototipo.com.salesforceprototipo/databases/sqliteControle.db";

    private String sTabela;
    private Context context;
    private SQLiteDatabase db;

    public Create(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
        this.context = context;
        this.db = getWritableDatabase();
        this.sTabela = "TBFATU006sf";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //logica para atualizar o BD;
    }

    public boolean CriarTabelas(){
        openDB();
        String createTable;
        try {
            createTable = "CREATE TABLE IF NOT EXISTS TBSENH006sf (" +
                          //"CODEMP CHAR(2),     " +
                          "CODVEND CHAR(6),      " +
                          "USER_ID CHAR(10),     " +
                          "USER_IDWEB CHAR(10),  " +
                          "SENHA CHAR(14),       " +
                          "HOST  CHAR(30))       " ;

            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBFATU006sf (" +
                          "CODCLI CHAR(9),       " +
                          "RAZSOCCLI CHAR(70),   " +
                          "TIPFISJUR CHAR(1),    " +
                          "NUMCGCCPFCLI CHAR(14)," +
                          "INSCRESTA CHAR(20),   " +
                          "TELCLI CHAR(12),      " +
                          "CODCPAGTO CHAR(3),    " +
                          "CODTABPRECO CHAR(3),  " +
                          "STAEXISSERVER CHAR(1)," +
                          "ENDCLI CHAR(200),     " +
                          "CONTATOCLI CHAR(20))  " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBFATU222sf (" +
                          "CODAGEND CHAR(3),     " +
                          "CODCLI CHAR(9),       " +
                          "CODVEND CHAR(6),      " +
                          "DATAGEND CHAR(10),    " +
                          "HORAGEND CHAR(5),     " +
                          "STAAGEND CHAR(1))     " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBFATU060sf (" +
                          "CODTABPRECO CHAR(3),  " +
                          "DESTABPRECO CHAR(20)) " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBFATU057sf (" +
                          "CODMAT CHAR(15),      " +
                          "PRECO DECIMAL(28,14), " +
                          "CODTABPRECO CHAR(3))  " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBCOMP009sf (" +
                          "CODCPAGTO CHAR(3),    " +
                          "DESCPAGTO CHAR(30))   " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBCOMP002sf (" +
                          "CODMAT CHAR(15),      " +
                          "DESMAT CHAR(70),      " +
                          "CODUNIMED CHAR(2))    " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBFATU010sf (" +
                          "NUMPED CHAR(6),       " +
                          "CODCLI CHAR(9),       " +
                          "CODCPAGTO CHAR(10),   " +
                          "STAEXISSERVER CHAR(1)," +
                          "CODTABPRECO CHAR(5))  " ;
                         // "CODEMP CHAR(2),       " ;
            db.execSQL(createTable);

            createTable = "CREATE TABLE IF NOT EXISTS TBFATU013sf (" +
                          "NUMPED CHAR(9),                         " +
                          "NUMITEMPED CHAR(3),                     " +
                          "CODMAT CHAR(6),                         " +
                          "QTDE DECIMAL(28,14),                    " +
                          "PERCACRESDESCITEM DECIMAL(28,14),       " +
                          "DATNEC CHAR(10),                        " +
                          "STAEXISSERVER CHAR(1))                  " ;
                       // "CODEMP CHAR(2),       " ;
            db.execSQL(createTable);

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
