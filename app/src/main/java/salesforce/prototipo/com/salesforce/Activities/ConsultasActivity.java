package salesforce.prototipo.com.salesforce.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import salesforce.prototipo.com.salesforce.Classes.Cliente;
import salesforce.prototipo.com.salesforce.Classes.CondPagamento;
import salesforce.prototipo.com.salesforce.Classes.Pedido;
import salesforce.prototipo.com.salesforce.Classes.TabPreco;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

public class ConsultasActivity extends AppCompatActivity {

    private MultiAutoCompleteTextView macTextViewCli;
    private ListView lvClientes;
    private SimpleAdapter adapter;
    private List<HashMap<String, String>> listItems;
    private List<Pedido> listaPed = new ArrayList<>();
    private List<Cliente> listaCli = new ArrayList<>();
    private List<TabPreco> listaTab = new ArrayList<>();
    private List<CondPagamento> listaCond = new ArrayList<>();
    private Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        Intent intent = getIntent();
        params = intent.getExtras();

        macTextViewCli = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
        lvClientes = (ListView) findViewById(R.id.ListViewConsulta);
        TextView tvConsultas = (TextView) findViewById(R.id.tvConsultas);

        Read readCli = new Read(getApplicationContext());
        Read readTabPreco = new Read(getApplicationContext());
        Read readCond = new Read(getApplicationContext());
        Read readPed = new Read(getApplicationContext());
        String sMsg = "";
        String sHint = "";
        if (params != null) {
            switch (params.getInt("chamada")) {
                case 3:
                    listaTab = readTabPreco.getTabPreco("", "*");
                    sMsg = "Sincronize as Tab. de Preço!";
                    tvConsultas.setText("Tabelas de Preço");
                    sHint = "Insira o Código ou a Tabela de Preço";
                    break;
                case 4:
                    listaCond = readCond.getCondPag("", "*");
                    sMsg = "Sincronize as Cond. Pagamento!";
                    tvConsultas.setText("Condições de Pagamento");
                    sHint = "Insira o Código ou a Cond. Pagamento";
                    break;
                case 5:
                    String where;
                    String sDefault = params.getString("default");
                    if (sDefault.equals("0")){
                        where = "WHERE A.STAEXISSERVER = 'N'";
                    }else{
                        where = "WHERE A.STAEXISSERVER = 'S' " +
                                "  AND A.CODCLI = '"+sDefault+"'";
                    }
                    listaPed = readPed.getPedidos(where, "DISTINCT A.*, B.RAZSOCCLI, " +
                            "C.DESTABPRECO, D.DESCPAGTO");
                    sMsg = "Insira novos Pedidos!";
                    tvConsultas.setText("Consulta de Pedidos");
                    sHint = "Insira o Código ou a Razão Social";
                    break;
                default:
                    listaCli = readCli.getClientes("", "DISTINCT A.*, B.DESTABPRECO, C.DESCPAGTO");
                    sMsg = "Sincronize os Clientes!";
                    tvConsultas.setText("Consulta de Clientes");
                    sHint = "Insira o Código ou Razão Social";
                    break;
            }
        }
        macTextViewCli.setHint(sHint);
        listItems = new ArrayList<>();

        /*for (int i = 0; i <= lista.size() - 1; i++) {
            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("First Line", lista.get(i).getsCodCli());
            resultsMap.put("Second Line", lista.get(i).getsRazSocCli().toUpperCase());
            listItems.add(resultsMap);
        }*/

        if ((listaTab.size() > 0) || (listaCond.size() > 0) || (listaCli.size() > 0) ||(listaPed.size() > 0)) {
            adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                    new String[]{"First Line", "Second Line"},
                    new int[]{R.id.text1, R.id.text2});
            Pesquisar();
        } else {
            adapter = new SimpleAdapter(this, listItems, R.layout.simple_list_item,
                    new String[]{"First Line"},
                    new int[]{R.id.text1});
            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("First Line", sMsg);
            listItems.add(resultsMap);
        }

        lvClientes.setAdapter(adapter);

        macTextViewCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Vazio
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 6 && Character.isDigit(s.charAt(0))) {
                    macTextViewCli.setText(macTextViewCli.getText().toString().substring(0, 6));
                    Toast.makeText(getApplicationContext(), "A pesquisa por código aceita apenas 6 caracteres!", Toast.LENGTH_LONG).show();
                }
                Pesquisar();
                lvClientes.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Vazio
            }
        });
        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Retornar(view, position);
            }

       });

    }

    private void Retornar(View view, int position) {
        //Quando tiver o resultado pronto para ser devolvido à primeira activity
        String sCodigo = ((TextView) (view.findViewById(R.id.text1))).getText().toString();

        if (params != null) {
            Intent returnIntent = new Intent();
            switch (params.getInt("chamada")) {
                case 1:
                    Read read = new Read(getApplicationContext());
                    List<Cliente> listCli = read.getClientes(" WHERE CODCLI = '" + sCodigo + "'", "DISTINCT A.*");
                    String sExisRegServer = listCli.get(0).getsExisRegServer();
                    if (sExisRegServer.equals("N")) {
                        returnIntent.putExtra("sCodigo", sCodigo);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Este Cliente não pode ser excluído ou alterado!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    returnIntent.putExtra("sCodigo", sCodigo);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                    break;
                case 3:
                    returnIntent.putExtra("sCodigo", sCodigo);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                    break;
                case 4:
                    returnIntent.putExtra("sCodigo", sCodigo);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                    break;
                case 5:
                    returnIntent.putExtra("Pedido", listaPed.get(position));
                    setResult(RESULT_OK, returnIntent);
                    finish();
                    break;

            }
        }
    }

    private void Pesquisar() {
        int textlength = macTextViewCli.getText().length();
        listItems.clear();

        char c;
        if (!macTextViewCli.getText().toString().isEmpty()) {
            String s = macTextViewCli.getText().toString().substring(0, 1);
            c = s.charAt(0);
        } else {
            c = 'c';
        }
        switch (params.getInt("chamada")) {
            case 3:
                for (int i = 0; i <= listaTab.size() - 1; i++) {
                    if (textlength <= listaTab.get(i).getsDesTabPreco().length()) {
                        if (Character.isDigit(c)) {
                            if (macTextViewCli.getText().toString().contains(listaTab.get(i).getsCodTabPreco().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", listaTab.get(i).getsCodTabPreco());
                                resultsMap.put("Second Line", listaTab.get(i).getsDesTabPreco());
                                listItems.add(resultsMap);
                            }
                        } else {
                            if (macTextViewCli.getText().toString().toUpperCase().contains(listaTab.get(i).getsDesTabPreco().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", listaTab.get(i).getsCodTabPreco());
                                resultsMap.put("Second Line", listaTab.get(i).getsDesTabPreco());
                                listItems.add(resultsMap);
                            }
                        }
                    }
                }
            case 4:
                for (int i = 0; i <= listaCond.size() - 1; i++) {
                    if (textlength <= listaCond.get(i).getsDesPag().length()) {
                        if (Character.isDigit(c)) {
                            if (macTextViewCli.getText().toString().contains(listaCond.get(i).getsCondPag().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", listaCond.get(i).getsCondPag());
                                resultsMap.put("Second Line", listaCond.get(i).getsDesPag());
                                listItems.add(resultsMap);
                            }
                        } else {
                            if (macTextViewCli.getText().toString().toUpperCase().contains(listaCond.get(i).getsDesPag().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", listaCond.get(i).getsCondPag());
                                resultsMap.put("Second Line", listaCond.get(i).getsDesPag());
                                listItems.add(resultsMap);
                            }
                        }
                    }
                }
            case 5:
                for (int i = 0; i <= listaPed.size() - 1; i++) {
                    if (textlength <= listaPed.get(i).getCliente().getsRazSocCli().length()) {
                        if (Character.isDigit(c)) {
                            if (macTextViewCli.getText().toString().contains(listaPed.get(i).getsNumPed().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line","Pedido: " + listaPed.get(i).getsNumPed());
                                resultsMap.put("Second Line", "Cliente: " + listaPed.get(i).getCliente().getsRazSocCli());
                                listItems.add(resultsMap);
                            }
                        } else {
                            if (macTextViewCli.getText().toString().toUpperCase().contains(listaPed.get(i).getCliente().getsRazSocCli().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", "Pedido: " + listaPed.get(i).getsNumPed());
                                resultsMap.put("Second Line", "Cliente: " + listaPed.get(i).getCliente().getsRazSocCli());
                                listItems.add(resultsMap);
                            }
                        }
                    }
                }
            default:
                for (int i = 0; i <= listaCli.size() - 1; i++) {
                    if (textlength <= listaCli.get(i).getsRazSocCli().length()) {
                        if (Character.isDigit(c)) {
                            if (macTextViewCli.getText().toString().contains(listaCli.get(i).getsCodCli().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", listaCli.get(i).getsCodCli());
                                resultsMap.put("Second Line", listaCli.get(i).getsRazSocCli());
                                listItems.add(resultsMap);
                            }
                        } else {
                            if (macTextViewCli.getText().toString().toUpperCase().contains(listaCli.get(i).getsRazSocCli().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", listaCli.get(i).getsCodCli());
                                resultsMap.put("Second Line", listaCli.get(i).getsRazSocCli());
                                listItems.add(resultsMap);
                            }
                        }
                    }
                }
        }
    }
}
