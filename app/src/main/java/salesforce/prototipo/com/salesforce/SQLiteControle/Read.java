package salesforce.prototipo.com.salesforce.SQLiteControle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import salesforce.prototipo.com.salesforce.Classes.Agendamento;
import salesforce.prototipo.com.salesforce.Classes.Cliente;
import salesforce.prototipo.com.salesforce.Classes.CondPagamento;
import salesforce.prototipo.com.salesforce.Classes.ItemPedido;
import salesforce.prototipo.com.salesforce.Classes.Pedido;
import salesforce.prototipo.com.salesforce.Classes.Preco;
import salesforce.prototipo.com.salesforce.Classes.Produto;
import salesforce.prototipo.com.salesforce.Classes.TabPreco;
import salesforce.prototipo.com.salesforce.Classes.Vendedor;

/**
 * Created by Rodrigo on 11/08/2017.
 */

public class Read extends SQLiteOpenHelper {
    private final static String DATABASE_NOME = "sqliteControle.db";
    private final static int DATABASE_VERSAO = 1;
    private static final String PATH_DB = "/data/user/0/salesforce.prototipo.com.salesforceprototipo/databases/sqliteControle.db";

    private Context context;
    private SQLiteDatabase db;

    public Read(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
        this.context = context;
        this.db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //logica para atualizar o BD;
    }


    public Vendedor getVendedor() {
        openDB();
        Vendedor vendedor = new Vendedor();

        String getVendedor = "SELECT * FROM TBSENH006sf ";
        try {
            Cursor c = db.rawQuery(getVendedor, null);

            if (c.moveToFirst()) {
                vendedor.setsCodVend(c.getString(0));
                vendedor.setsUser_id(c.getString(1));
                vendedor.setsUser_web(c.getString(2));
                vendedor.setsSenha(c.getString(3));
                vendedor.setsHost(c.getString(4));

                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return vendedor;
    }

    public ArrayList<CondPagamento> getCondPag(String sWhere, String sCampos) {
        openDB();
        ArrayList<CondPagamento> cArray = new ArrayList<>();

        String getCondPag = "SELECT " + sCampos + " FROM  TBCOMP009sf " + sWhere;
        try {
            Cursor c = db.rawQuery(getCondPag, null);

            if (c.moveToFirst()) {
                do {
                    CondPagamento condPagamento = new CondPagamento();

                    condPagamento.setsCondPag(c.getString(0));
                    condPagamento.setsDesPag(c.getString(1));

                    cArray.add(condPagamento);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    public ArrayList<Preco> getPreco(String sWhere, String sCampos) {
        openDB();
        ArrayList<Preco> cArray = new ArrayList<>();

        String getPreco = "SELECT " + sCampos + " FROM  TBFATU057sf " + sWhere;
        try {
            Cursor c = db.rawQuery(getPreco, null);

            if (c.moveToFirst()) {
                do {
                    Preco preco = new Preco();
                    //TODO CRIAR TBFATU060
                    preco.setsCodMat(c.getString(0));
                    preco.setdPreco(c.getDouble(1));

                    cArray.add(preco);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    public ArrayList<Produto> getProdutos(String sWhere, String sCampos) {
        openDB();
        ArrayList<Produto> cArray = new ArrayList<>();

        String getProdutos = "SELECT " + sCampos + " FROM  TBCOMP002sf " + sWhere;
        try {
            Cursor c = db.rawQuery(getProdutos, null);

            if (c.moveToFirst()) {
                do {
                    Produto produto = new Produto();
                    produto.setsCodMat(c.getString(0));
                    produto.setsDesmat(c.getString(1));
                    produto.setsCodUnimed(c.getString(2));

                    cArray.add(produto);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }


    public ArrayList<TabPreco> getTabPreco(String sWhere, String sCampos) {
        openDB();
        ArrayList<TabPreco> cArray = new ArrayList<>();

        String getTabPreco = "SELECT " + sCampos + " FROM  TBFATU060sf " + sWhere;
        try {
            Cursor c = db.rawQuery(getTabPreco, null);

            if (c.moveToFirst()) {
                do {
                    TabPreco tabPreco = new TabPreco();
                    tabPreco.setsCodTabPreco(c.getString(0));
                    tabPreco.setsDesTabPreco(c.getString(1));

                    cArray.add(tabPreco);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    public ArrayList<ItemPedido> getItemPed(String sWhere, String sCampos) {
        openDB();
        ArrayList<ItemPedido> cArray = new ArrayList<>();
        String getItemPed = "SELECT " + sCampos + " FROM TBFATU013sf A" +
                "  LEFT JOIN TBFATU010sf B ON " +
                "       A.NUMPED = B.NUMPED" +
                "  LEFT JOIN TBFATU057sf C ON " +
                "       B.CODTABPRECO = C.CODTABPRECO" +
                "   AND A.CODMAT = C.CODMAT" +
                "  LEFT JOIN TBCOMP002sf D ON " +
                "       A.CODMAT = D.CODMAT" + sWhere;

        try {
            Cursor c = db.rawQuery(getItemPed, null);

            if (c.moveToFirst()) {
                do {
                    ItemPedido itemPedido = new ItemPedido();
                    Pedido pedido = new Pedido();
                    Produto produto = new Produto();
                    Preco preco = new Preco();

                    //TODO MELHORAR ROTINA
                    pedido.setsNumPed(c.getString(0));

                    if (c.getColumnCount() >= 2)
                        itemPedido.setsNumItem(c.getString(1));
                    if (c.getColumnCount() >= 3)
                        produto.setsCodMat(c.getString(2));
                    if (c.getColumnCount() >= 4)
                        itemPedido.setdQtde(c.getDouble(3));
                    if (c.getColumnCount() >= 5)
                        itemPedido.setdPercDesc(c.getDouble(4));
                    if (c.getColumnCount() >= 6)
                        itemPedido.setsDataNecess(c.getString(5));
                    if (c.getColumnCount() >= 7)
                        itemPedido.setsExisRegServer(c.getString(6));
                    if (c.getColumnCount() >= 8) {
                        preco.setdPreco(c.getDouble(7));
                        itemPedido.setdValorTot(c.getDouble(3) * c.getDouble(7) * (1 + c.getDouble(4)/100));
                    }
                    if (c.getColumnCount() >= 9)
                        produto.setsDesmat(c.getString(8));

                    itemPedido.setPedido(pedido);
                    itemPedido.setPreco(preco);
                    itemPedido.setProduto(produto);

                    cArray.add(itemPedido);
                } while (c.moveToNext());
                c.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    public ArrayList<Pedido> getPedidos(String sWhere, String sCampos) {
        openDB();
        ArrayList<Pedido> cArray = new ArrayList<>();
        String getPedidos = "SELECT " + sCampos + " FROM TBFATU010sf A " +
                "  LEFT JOIN TBFATU006sf B ON              " +
                "       A.CODCLI = B.CODCLI                " +
                "  LEFT JOIN TBFATU060sf C ON              " +
                "       A.CODTABPRECO = C.CODTABPRECO      " +
                "  LEFT JOIN TBCOMP009sf D ON              " +
                "       A.CODCPAGTO = D.CODCPAGTO          " + sWhere;

        try {
            Cursor c = db.rawQuery(getPedidos, null);

            if (c.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    Cliente cliente = new Cliente();
                    CondPagamento condPagamento = new CondPagamento();
                    TabPreco tabPreco = new TabPreco();

                    //TODO MELHORAR ROTINA
                    pedido.setsNumPed(c.getString(0));

                    if (c.getColumnCount() >= 2)
                        cliente.setsCodCli(c.getString(1));
                    if (c.getColumnCount() >= 3)
                        condPagamento.setsCondPag(c.getString(2));
                    if (c.getColumnCount() >= 4)
                        pedido.setsExisRegServer(c.getString(3));
                    if (c.getColumnCount() >= 5)
                        tabPreco.setsCodTabPreco(c.getString(4));
                    if (c.getColumnCount() >= 6)
                        cliente.setsRazSocCli(c.getString(5));
                    if (c.getColumnCount() >= 7)
                        tabPreco.setsDesTabPreco(c.getString(6));
                    if (c.getColumnCount() >= 8)
                        condPagamento.setsDesPag(c.getString(7));

                    cliente.setCondPagamento(condPagamento);
                    cliente.setTabPreco(tabPreco);
                    pedido.setCliente(cliente);

                    cArray.add(pedido);
                } while (c.moveToNext());
                c.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    public ArrayList<Cliente> getClientes(String sWhere, String sCampos) {
        openDB();
        ArrayList<Cliente> cArray = new ArrayList<>();
        String getClientes = "SELECT " + sCampos + " FROM TBFATU006sf A" +
                "  LEFT JOIN TBFATU060sf B ON " +
                "       A.CODTABPRECO = B.CODTABPRECO" +
                "  LEFT JOIN TBCOMP009sf C ON " +
                "       A.CODCPAGTO = C.CODCPAGTO " + sWhere;

        try {
            Cursor c = db.rawQuery(getClientes, null);

            if (c.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    CondPagamento condPagamento = new CondPagamento();
                    TabPreco tabPreco = new TabPreco();

                    //TODO MELHORAR ROTINA
                    cliente.setsCodCli(c.getString(0));

                    if (c.getColumnCount() >= 2)
                        cliente.setsRazSocCli(c.getString(1));
                    if (c.getColumnCount() >= 3)
                        cliente.setsTipo(c.getString(2));
                    if (c.getColumnCount() >= 4)
                        cliente.setsCnpjCpf(c.getString(3));
                    if (c.getColumnCount() >= 5)
                        cliente.setsInscrEst(c.getString(4));
                    if (c.getColumnCount() >= 6)
                        cliente.setsTelefone(c.getString(5));
                    if (c.getColumnCount() >= 7)
                        condPagamento.setsCondPag(c.getString(6));
                    if (c.getColumnCount() >= 8)
                        tabPreco.setsCodTabPreco(c.getString(7));
                    if (c.getColumnCount() >= 9)
                        cliente.setsExisRegServer(c.getString(8));
                    if (c.getColumnCount() >= 10)
                        cliente.setsEndCli(c.getString(9));
                    if (c.getColumnCount() >= 11)
                        cliente.setsContatoCli(c.getString(10));
                    if (c.getColumnCount() >= 12)
                        tabPreco.setsDesTabPreco(c.getString(11));
                    if (c.getColumnCount() == 13)
                        condPagamento.setsDesPag(c.getString(12));

                    cliente.setCondPagamento(condPagamento);
                    cliente.setTabPreco(tabPreco);

                    cArray.add(cliente);
                } while (c.moveToNext());
                c.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    public ArrayList<Agendamento> getAgendamentos(String sWhere, String sCampos) {
        openDB();
        ArrayList<Agendamento> cArray = new ArrayList<>();

        String getAgendamentos = "SELECT " + sCampos + " FROM  TBFATU222sf A" +
                " INNER JOIN TBFATU006sf B ON " +
                "       A.CODCLI = B.CODCLI   " +
                "  LEFT JOIN TBFATU060sf C ON " +
                "       B.CODTABPRECO = C.CODTABPRECO" +
                "  LEFT JOIN TBCOMP009sf D ON " +
                "       B.CODCPAGTO = D.CODCPAGTO " + sWhere;
        try {
            Cursor c = db.rawQuery(getAgendamentos, null);

            if (c.moveToFirst()) {
                do {
                    Agendamento agendamento = new Agendamento();
                    Cliente cliente = new Cliente();
                    Vendedor vendedor = new Vendedor();
                    CondPagamento condPagamento = new CondPagamento();
                    TabPreco tabPreco = new TabPreco();

                    agendamento.setsCodAgend(c.getString(0));
                    if (c.getColumnCount() >= 2)
                        cliente.setsCodCli(c.getString(1));
                    if (c.getColumnCount() >= 3)
                        vendedor.setsCodVend(c.getString(2));
                    if (c.getColumnCount() >= 4)
                        agendamento.setsDatAgend(c.getString(3));
                    if (c.getColumnCount() >= 5)
                        agendamento.setsHorAgend(c.getString(4));
                    if (c.getColumnCount() >= 6)
                        agendamento.setsStaAgend(c.getString(5));
                    if (c.getColumnCount() >= 7)
                        cliente.setsEndCli(c.getString(6));
                    if (c.getColumnCount() >= 8)
                        cliente.setsRazSocCli(c.getString(7));
                    if (c.getColumnCount() >= 9)
                        tabPreco.setsCodTabPreco(c.getString(8));
                    if (c.getColumnCount() >= 10)
                        tabPreco.setsDesTabPreco(c.getString(9));
                    if (c.getColumnCount() >= 11)
                        condPagamento.setsCondPag(c.getString(10));
                    if (c.getColumnCount() >= 12)
                        condPagamento.setsDesPag(c.getString(11));

                    cliente.setTabPreco(tabPreco);
                    cliente.setCondPagamento(condPagamento);
                    agendamento.setVendedor(vendedor);
                    agendamento.setCliente(cliente);

                    cArray.add(agendamento);
                } while (c.moveToNext());
                c.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return cArray;
    }

    private void openDB() {
        if (!db.isOpen()) {
            db = context.openOrCreateDatabase(PATH_DB, SQLiteDatabase.OPEN_READWRITE, null);
        }
    }
}
