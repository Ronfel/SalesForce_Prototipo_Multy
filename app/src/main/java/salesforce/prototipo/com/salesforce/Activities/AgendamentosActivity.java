package salesforce.prototipo.com.salesforce.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import salesforce.prototipo.com.salesforce.Classes.Agendamento;
import salesforce.prototipo.com.salesforce.Classes.Pedido;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

import static android.widget.AdapterView.*;

public class AgendamentosActivity extends AppCompatActivity {

    private ListView lvAgendamentos;
    private List<Agendamento> lista;
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter adapter;
    private AlertDialog alerta;
    private int iPosicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos);

        lvAgendamentos = (ListView) findViewById(R.id.listViewAgendamentos);

        Atualizar();

        lvAgendamentos.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lista.size() > 0) {
                    iPosicao = position;
                    Confirmacao();
                }
            }
        });
    }

    public void Atualizar() {
        Read read = new Read(getApplicationContext());
        lista = read.getAgendamentos(" WHERE STAAGEND = 'N' ", "A.*, " +
                                                               "B.ENDCLI, " +
                                                               "B.RAZSOCCLI, " +
                                                               "C.CODTABPRECO, " +
                                                               "C.DESTABPRECO," +
                                                               "D.CODCPAGTO, " +
                                                               "D.DESCPAGTO");
        listItems = new ArrayList<>();
        if (lista.size() > 0) {
            adapter = new SimpleAdapter(this, listItems, R.layout.list_item_agend,
                    new String[]{"First Line", "Second Line1", "Second Line2", "Third Line"},
                    new int[]{R.id.text1, R.id.text2, R.id.text3, R.id.text4});
            for (int i = 0; i <= lista.size() - 1; i++) {
                HashMap<String, String> resultsMap = new HashMap<>();
                resultsMap.put("First Line", lista.get(i).getCliente().getsRazSocCli());
                resultsMap.put("Second Line1", "Data: " + lista.get(i).getsDatAgend());
                resultsMap.put("Second Line2", "Hora: " + lista.get(i).getsHorAgend());
                resultsMap.put("Third Line", "End.: " + lista.get(i).getCliente().getsEndCli());
                listItems.add(resultsMap);
            }
        } else {
            adapter = new SimpleAdapter(this, listItems, R.layout.simple_list_item,
                    new String[]{"First Line"},
                    new int[]{R.id.text1});
            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("First Line", "Você não possui agendamentos!");
            listItems.add(resultsMap);
        }
        lvAgendamentos.setAdapter(adapter);
    }

    private void Confirmacao() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Confirmação");
        //define a mensagem
        builder.setMessage("Deseja incluir um novo pedido para este Cliente?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(AgendamentosActivity.this, CadPedidosActivity.class);
                Agendamento agendamento = lista.get(iPosicao);

                Read read = new Read(getApplicationContext());
                ArrayList<Pedido> pedidos = read.getPedidos("WHERE A.CODCLI = '"+agendamento.getCliente().getsCodCli()+"'", "A.NUMPED");

                intent.putExtra("agendamento", agendamento);
                intent.putExtra("pedidos", pedidos);
                startActivity(intent);

                finish();
                //String sCodAgend = lista.get(iPosicao).getsCodAgend();
                //Update update = new Update(getApplicationContext());
                //update.updateDadosAgend(sCodAgend);
                //Atualizar();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //não faz nada
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
