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
import salesforce.prototipo.com.salesforce.Classes.Produto;
import salesforce.prototipo.com.salesforce.Classes.TabPreco;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

public class ProdutosConsultaActivity extends AppCompatActivity {

    private MultiAutoCompleteTextView macTextViewCli;
    private ListView lvProdutos;
    private TextView tvProdutos;
    private SimpleAdapter adapter;
    private List<HashMap<String, String>> listItems;
    private List<Produto> lista;
    private Pedido pedido;
    static final int CAD_ITEM = 6;

    /*public ProdutosConsultaActivity(HashMap<String, Pedido> hashPed) {
        this.hashPed = hashPed;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        Intent intent = getIntent();
        pedido = (Pedido) intent.getSerializableExtra("Pedido");

        macTextViewCli = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
        lvProdutos = (ListView) findViewById(R.id.ListViewConsulta);
        tvProdutos = (TextView) findViewById(R.id.tvConsultas);

        tvProdutos.setText("Consulta de Produtos");

        Read read = new Read(getApplicationContext());
        lista = read.getProdutos("", "*");
        listItems = new ArrayList<>();

        if ((lista.size() > 0)) {
            adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                    new String[]{"First Line", "Second Line"},
                    new int[]{R.id.text1, R.id.text2});
            Pesquisar();
        } else {
            adapter = new SimpleAdapter(this, listItems, R.layout.simple_list_item,
                    new String[]{"First Line"},
                    new int[]{R.id.text1});
            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("First Line", "Sincronize os Produtos!");
            listItems.add(resultsMap);
        }
        lvProdutos.setAdapter(adapter);
        macTextViewCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Vazio
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 15 && Character.isDigit(s.charAt(0))) {
                    macTextViewCli.setText(macTextViewCli.getText().toString().substring(0, 15));
                    Toast.makeText(getApplicationContext(), "A pesquisa por código aceita apenas 15 caracteres!", Toast.LENGTH_LONG).show();
                }
                Pesquisar();
                lvProdutos.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Vazio
            }
        });
        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = lista.get(position);

                Intent intent = new Intent(ProdutosConsultaActivity.this, CadItemActivity.class);
                intent.putExtra("Produto", produto);
                intent.putExtra("Pedido", pedido);
                intent.putExtra("chamada", CAD_ITEM);
                startActivityForResult(intent, CAD_ITEM);
            }
        });
    }

    private void
    Pesquisar() {
        int textlength = macTextViewCli.getText().length();
        listItems.clear();

        char c;
        if (!macTextViewCli.getText().toString().isEmpty()) {
            String s = macTextViewCli.getText().toString().substring(0, 1);
            c = s.charAt(0);
        } else {
            c = 'c';
        }
        int i = 0;
        try {

            for (i = 0; i <= lista.size() - 1; i++) {
                    if (Character.isDigit(c)) {
                        if (textlength <= lista.get(i).getsCodMat().length()) {
                            if (macTextViewCli.getText().toString().contains(lista.get(i).getsCodMat().subSequence(0, textlength))) {
                                HashMap<String, String> resultsMap = new HashMap<>();
                                resultsMap.put("First Line", lista.get(i).getsCodMat());
                                resultsMap.put("Second Line", lista.get(i).getsDesmat());
                                listItems.add(resultsMap);
                            }
                        }
                    } else {
                            if (textlength <= lista.get(i).getsDesmat().length()) {
                                if (macTextViewCli.getText().toString().toUpperCase().contains(lista.get(i).getsDesmat().subSequence(0, textlength))) {
                                    HashMap<String, String> resultsMap = new HashMap<>();
                                    resultsMap.put("First Line", lista.get(i).getsCodMat());
                                    resultsMap.put("Second Line", lista.get(i).getsDesmat());
                                    listItems.add(resultsMap);
                                }
                            }
                    }
                }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO TRAZER OBJETO
        if (requestCode == CAD_ITEM) {
            if (resultCode == RESULT_OK) {
                Intent returnIntent = new Intent();
                pedido = (Pedido) data.getSerializableExtra("Pedido");
                returnIntent.putExtra("Pedido", pedido);
                setResult(RESULT_OK, returnIntent);
            }
        }
    }
}
