package salesforce.prototipo.com.salesforce.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import salesforce.prototipo.com.salesforce.Auxiliares.Validacao;
import salesforce.prototipo.com.salesforce.Classes.Agendamento;
import salesforce.prototipo.com.salesforce.Classes.Cliente;
import salesforce.prototipo.com.salesforce.Classes.CondPagamento;
import salesforce.prototipo.com.salesforce.Classes.ItemPedido;
import salesforce.prototipo.com.salesforce.Classes.Pedido;
import salesforce.prototipo.com.salesforce.Classes.Preco;
import salesforce.prototipo.com.salesforce.Classes.TabPreco;
import salesforce.prototipo.com.salesforce.SQLiteControle.Delete;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforce.SQLiteControle.Update;
import salesforce.prototipo.com.salesforceprototipo.R;

public class CadPedidosActivity extends AppCompatActivity {

    private String sNumPed = "";
    private String sCodCli = "";
    private ImageView ivPedReal;
    private ImageView ivBuscCli;
    private ImageView ivBuscTabPreco;
    private ImageView ivBuscCondPag;
    private ImageView ivCadItem;
    private EditText etCodCli;
    private EditText etCodTabPreco;
    private EditText etDesTabPreco;
    private EditText etCondPag;
    private EditText etDesPag;
    private EditText etRazSocCli;

    private Button btnLimpar;
    private Button btnConcluir;
    private ListView lvItemPed;
    private SimpleAdapter adapter;
    private List<HashMap<String, String>> listItems;
    private List<ItemPedido> lista = new ArrayList<>();

    private Cliente cliente;
    private Pedido pedido = new Pedido();
    private AlertDialog alerta;

    static final int CONSULT_CLIENTE = 2;
    static final int CONSULT_TAB_PRECO = 3;
    static final int CONSULT_COD_PAG = 4;
    static final int CAD_ITEM = 6;
    static final int CONSULT_ITEM = 7;
    static final int CONSULT_PEDIDO = 5;

    private TextView tvValTotPed;
    private Locale ptBr = new Locale("pt", "BR");
    private NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);
    private Agendamento agendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadpedidos);

        ivPedReal = (ImageView) findViewById(R.id.btnPedReal);
        ivBuscCli = (ImageView) findViewById(R.id.btnBuscaCli);
        ivBuscTabPreco = (ImageView) findViewById(R.id.btnBuscaTabPreco);
        ivBuscCondPag = (ImageView) findViewById(R.id.btnBuscaCondPag);
        ivPedReal = (ImageView) findViewById(R.id.btnPedReal);
        ivCadItem = (ImageView) findViewById(R.id.btnCadProdutos);

        etCodCli = (EditText) findViewById(R.id.edtCodCli);
        etRazSocCli = (EditText) findViewById(R.id.edtRazSocCliPed);

        etCodTabPreco = (EditText) findViewById(R.id.edtCodTabPreco);
        etDesTabPreco = (EditText) findViewById(R.id.edtDescTab);

        etCondPag = (EditText) findViewById(R.id.edtCondPag);
        etDesPag = (EditText) findViewById(R.id.edtDescPag);

        lvItemPed = (ListView) findViewById(R.id.listViewItemPed);

        tvValTotPed = (TextView) findViewById(R.id.TextViewValTot);

        btnLimpar = (Button) findViewById(R.id.btnLimparPed);
        btnConcluir = (Button) findViewById(R.id.btnConcluirPed);

        ivBuscCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamada(CONSULT_CLIENTE, "0");
            }
        });
        ivBuscTabPreco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamada(CONSULT_TAB_PRECO, "0");
            }
        });
        ivBuscCondPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamada(CONSULT_COD_PAG, "0");
            }
        });
        ivPedReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamada(CONSULT_PEDIDO, "0");
            }
        });

        Intent intent = getIntent();
        agendamento = (Agendamento) intent.getSerializableExtra("agendamento");

        if (agendamento != null) {
            ArrayList<Pedido> pedidos = (ArrayList<Pedido>) intent.getSerializableExtra("pedidos");
            if (pedidos.size() > 0) {
                sCodCli = agendamento.getCliente().getsCodCli();
                Confirmacao();
            } else {
                cliente = agendamento.getCliente();
                etCodCli.setText(cliente.getsCodCli());
                etRazSocCli.setText(cliente.getsRazSocCli());

                if (cliente.getTabPreco() != null) {
                    etCodTabPreco.setText(cliente.getTabPreco().getsCodTabPreco());
                    etDesTabPreco.setText(cliente.getTabPreco().getsDesTabPreco());
                }

                if (cliente.getCondPagamento() != null) {
                    etCondPag.setText(cliente.getCondPagamento().getsCondPag());
                    etDesPag.setText(cliente.getCondPagamento().getsDesPag());
                }
            }
        }

        ivCadItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidaCampos()) {
                    if (pedido.getsNumPed().equals("")) {
                        DecimalFormat df = new DecimalFormat("000000");
                        Read read = new Read(getApplicationContext());

                        String maxNumped = read.getPedidos("WHERE A.STAEXISSERVER = 'N'", "MAX(A.NUMPED)").get(0).getsNumPed();
                        if (maxNumped == null)
                            pedido.setsNumPed(df.format(1));
                        else
                            pedido.setsNumPed(df.format(Integer.parseInt(maxNumped) + 1));

                        pedido.setCliente(cliente);
                    }

                    HashMap<String, Pedido> hashMap = new HashMap<>();
                    hashMap.put("Pedido", pedido);

                    Intent intent = new Intent(CadPedidosActivity.this, ProdutosConsultaActivity.class);
                    intent.putExtra("Pedido", pedido);
                    startActivityForResult(intent, CAD_ITEM);
                }
            }
        });
        AtualizaListItens();

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnLimpar.getText().equals("Excluir")) {
                    Delete delete = new Delete(getApplicationContext());
                    delete.deletePedido(pedido, "");
                    Toast.makeText(getApplicationContext(), "Pedido foi excluído!", Toast.LENGTH_SHORT).show();
                }
                LimparCampos();

            }
        });

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMensagem;
                if (pedido.getItemPedidos().size() != 0) {
                    if (pedido.getsExisRegServer() != null) {
                        Delete delete = new Delete(getApplicationContext());
                        delete.deletePedido(pedido, "");
                        sMensagem = "Edição concluída!";
                    } else {
                        sMensagem = "Pedido Cadastrado com Sucesso!";
                    }
                    Update insertPed = new Update(getApplicationContext());
                    insertPed.insertPedido(pedido);
                    if (agendamento != null){
                        Update update = new Update(getApplicationContext());
                        update.updateDadosAgend(agendamento.getsCodAgend());
                    }
                    LimparCampos();
                } else {
                    sMensagem = "Por favor, insira itens ao Pedido!";
                }
                Toast.makeText(getApplicationContext(), sMensagem, Toast.LENGTH_SHORT).show();

            }
        });
        lvItemPed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TextView txt1 = (TextView) parent.findViewById(R.id.text1);

                //if (!txt1.getText().equals("Nenhum Item cadastrado para este pedido!")) {
                if (lista.size() > 0){
                    ItemPedido itemPedido = lista.get(position);

                    Intent intent = new Intent(CadPedidosActivity.this, CadItemActivity.class);
                    intent.putExtra("index", position);
                    intent.putExtra("itemPedido", itemPedido);
                    intent.putExtra("Pedido", pedido);
                    intent.putExtra("chamada", CONSULT_ITEM);
                    startActivityForResult(intent, CAD_ITEM);
                }
            }
        });

    }

    private void AtualizaListItens() {
        listItems = new ArrayList<>();
        if (!sNumPed.isEmpty()) {
            lista = pedido.getItemPedidos();

            if ((lista.size() > 0)) {
                adapter = new SimpleAdapter(this, listItems, R.layout.list_item_agend,
                        new String[]{"First Line", "Second Line1", "Second Line2", "Third Line"},
                        new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4});
                for (int i = 0; i <= lista.size() - 1; i++) {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    resultsMap.put("First Line", lista.get(i).getProduto().getsDesmat());
                    resultsMap.put("Second Line1", "Qtde: " + lista.get(i).getdQtde());
                    resultsMap.put("Second Line2", "Valor Unit.: " + nf.format(lista.get(i).getPreco().getdPreco()));
                    resultsMap.put("Third Line", "Valor Total.:  " + nf.format(lista.get(i).getdValorTot()));
                    listItems.add(resultsMap);
                }
                tvValTotPed.setText(nf.format(pedido.getdValTotPed()));
            } else {
                ListaVazia();
            }
        } else {
            ListaVazia();
        }
        lvItemPed.setAdapter(adapter);
    }

    private boolean ValidaCampos() {

        boolean bvalido;
        Validacao validacao = new Validacao();

        bvalido = validacao.validateNotNull(etRazSocCli, "Escolha o Cliente!");

        if (bvalido) {
            bvalido = validacao.validateNotNull(etDesTabPreco, "Escolha a Tabela de Preço!");
        }
        if (bvalido) {
            bvalido = validacao.validateNotNull(etDesPag, "Escolha a Condição de Pagamento!");
        }
        return bvalido;
    }

    private void ListaVazia() {
        adapter = new SimpleAdapter(this, listItems, R.layout.simple_list_item,
                new String[]{"First Line"},
                new int[]{R.id.text1});
        HashMap<String, String> resultsMap = new HashMap<>();
        resultsMap.put("First Line", "Nenhum Item cadastrado para este pedido!");
        listItems.add(resultsMap);
        tvValTotPed.setText(nf.format(0));
    }

    private void Chamada(int parametro, String sdefault) {
        Intent i = new Intent(CadPedidosActivity.this, ConsultasActivity.class);
        Bundle params = new Bundle();
        params.putInt("chamada", parametro);
        params.putString("default", sdefault);
        i.putExtras(params);
        startActivityForResult(i, parametro);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //TODO TRAZER OBJETO
            case CONSULT_CLIENTE:
                if (resultCode == RESULT_OK) {
                    String sCodCli = data.getStringExtra("sCodigo");

                    //TODO melhorar desempenho trazendo o objeto
                    Read read = new Read(getApplicationContext());

                    List<Cliente> lista = read.getClientes("WHERE CODCLI = '" + sCodCli + "'", "DISTINCT  A.*, B.DESTABPRECO, C.DESCPAGTO");

                    cliente = lista.get(0);

                    etCodCli.setText(cliente.getsCodCli());
                    etRazSocCli.setText(cliente.getsRazSocCli());

                    if (cliente.getTabPreco().getsCodTabPreco() != null && !cliente.getTabPreco().getsCodTabPreco().isEmpty()) {
                        etCodTabPreco.setText(cliente.getTabPreco().getsCodTabPreco());
                        etDesTabPreco.setText(cliente.getTabPreco().getsDesTabPreco());
                    }

                    if (cliente.getCondPagamento().getsCondPag() != null && !cliente.getCondPagamento().getsCondPag().isEmpty()) {
                        etCondPag.setText(cliente.getCondPagamento().getsCondPag());
                        etDesPag.setText(cliente.getCondPagamento().getsDesPag());
                    }
                    etRazSocCli.setError(null);
                }
                break;
            case CONSULT_TAB_PRECO:
                if (resultCode == RESULT_OK) {
                    String sCodTabPreco = data.getStringExtra("sCodigo");

                    //TODO melhorar desempenho trazendo o objeto
                    Read read = new Read(getApplicationContext());

                    List<TabPreco> listaPreco = read.getTabPreco("WHERE CODTABPRECO = '" + sCodTabPreco + "'", "*");

                    TabPreco tabPreco = listaPreco.get(0);

                    cliente.setTabPreco(tabPreco);

                    etCodTabPreco.setText(tabPreco.getsCodTabPreco());
                    etDesTabPreco.setText(tabPreco.getsDesTabPreco());
                    etDesTabPreco.setError(null);

                    if ((pedido.getItemPedidos().size() > 0)) {
                        pedido.setdValTotPed(0);
                        double valTot, valTotPed = 0;

                        for (int i = 0; i <= pedido.getItemPedidos().size() - 1; i++) {

                            Read readPreco = new Read(getApplicationContext());
                            Preco preco = read.getPreco("WHERE CODMAT = '" + pedido.getItemPedidos().get(i).getProduto().getsCodMat() + "' " +
                                    "AND CODTABPRECO = '" + tabPreco.getsCodTabPreco() + "'", "*").get(0);

                            pedido.getItemPedidos().get(i).setPreco(preco);

                            double percAcresDesc = pedido.getItemPedidos().get(i).getdPercDesc();

                            valTot = pedido.getItemPedidos().get(i).getdQtde() * preco.getdPreco() * (1 + percAcresDesc/100);

                            pedido.getItemPedidos().get(i).setdValorTot(valTot);
                            valTotPed = valTotPed + valTot;
                        }
                        pedido.setdValTotPed(valTotPed);
                        //Carrega Itens do Pedido
                        AtualizaListItens();
                    }
                }
                break;
            case CONSULT_COD_PAG:
                if (resultCode == RESULT_OK) {
                    String sCodPag = data.getStringExtra("sCodigo");

                    //TODO melhorar desempenho trazendo o objeto
                    Read read = new Read(getApplicationContext());

                    List<CondPagamento> lista = read.getCondPag("WHERE CODCPAGTO = '" + sCodPag + "'", "*");

                    CondPagamento condPag = lista.get(0);

                    cliente.setCondPagamento(condPag);

                    etCondPag.setText(condPag.getsCondPag());
                    etDesPag.setText(condPag.getsDesPag());
                    etDesPag.setError(null);
                }
                break;
            case CAD_ITEM:
                if (resultCode == RESULT_OK) {
                    pedido = (Pedido) data.getSerializableExtra("Pedido");
                    sNumPed = pedido.getsNumPed();
                    AtualizaListItens();
                }
                break;
            case CONSULT_PEDIDO:
                if (resultCode == RESULT_OK) {
                    String maxNumped;
                    pedido = (Pedido) data.getSerializableExtra("Pedido");
                    sNumPed = pedido.getsNumPed();
                    cliente = pedido.getCliente();//Estava travando o sistema

                    if ((pedido.getsExisRegServer()!= null) && (pedido.getsExisRegServer().equals("S"))) {
                        pedido.setsExisRegServer(null);

                        DecimalFormat df = new DecimalFormat("000000");
                        Read read = new Read(getApplicationContext());
                        maxNumped = read.getPedidos("WHERE A.STAEXISSERVER = 'N'", "MAX(A.NUMPED)").get(0).getsNumPed();
                        if (maxNumped == null)
                            pedido.setsNumPed(df.format(1));
                        else
                            pedido.setsNumPed(df.format(Integer.parseInt(maxNumped) + 1));
                        Read readagend = new Read(getApplicationContext());
                        agendamento = readagend.getAgendamentos(" WHERE A.CODCLI = '"+pedido.getCliente().getsCodCli()+"' ", "A.CODAGEND").get(0);
                    }else{
                        btnLimpar.setText("Excluir");
                    }
                    Read readItens = new Read(getApplicationContext());
                    List<ItemPedido> listItemPed = readItens.getItemPed(" WHERE A.NUMPED = '" + sNumPed + "' ", " DISTINCT A.*," +
                            " C.PRECO, D.DESMAT");

                    for (int i = 0; i <= listItemPed.size() - 1; i++) {
                        pedido.addItemPedidos(listItemPed.get(i));
                    }

                    //Carrega Capa do Pedido
                    etCodCli.setText(pedido.getCliente().getsCodCli());
                    etRazSocCli.setText(pedido.getCliente().getsRazSocCli());
                    etCodTabPreco.setText(pedido.getCliente().getTabPreco().getsCodTabPreco());
                    etDesTabPreco.setText(pedido.getCliente().getTabPreco().getsDesTabPreco());
                    etCondPag.setText(pedido.getCliente().getCondPagamento().getsCondPag());
                    etDesPag.setText(pedido.getCliente().getCondPagamento().getsDesPag());

                    //Carrega Itens do Pedido
                    AtualizaListItens();


                }
                break;
        }
    }

    private void LimparCampos() {
        etCodCli.setText("");
        etRazSocCli.setText("");
        etCodTabPreco.setText("");
        etDesTabPreco.setText("");
        etCondPag.setText("");
        etDesPag.setText("");
        pedido = new Pedido();
        sNumPed = "";
        btnLimpar.setText("Limpar");
        AtualizaListItens();
    }
    private void Confirmacao() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Confirmação");
        //define a mensagem
        builder.setMessage("Deseja cadastrar um novo pedido a partir de um dos últimos pedidos deste cliente?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Chamada(CONSULT_PEDIDO, sCodCli);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                cliente = agendamento.getCliente();
                etCodCli.setText(cliente.getsCodCli());
                etRazSocCli.setText(cliente.getsRazSocCli());

                if (cliente.getTabPreco() != null) {
                    etCodTabPreco.setText(cliente.getTabPreco().getsCodTabPreco());
                    etDesTabPreco.setText(cliente.getTabPreco().getsDesTabPreco());
                }

                if (cliente.getCondPagamento() != null) {
                    etCondPag.setText(cliente.getCondPagamento().getsCondPag());
                    etDesPag.setText(cliente.getCondPagamento().getsDesPag());
                }
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}

