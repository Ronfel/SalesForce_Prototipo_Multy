package salesforce.prototipo.com.salesforce.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.os.Handler;
import android.widget.Toast;

import java.util.HashMap;

import java.util.logging.LogRecord;

import salesforce.prototipo.com.salesforce.Classes.Sincronizacao;
import salesforce.prototipo.com.salesforce.Classes.Vendedor;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

public class SincronizacaoActivity extends AppCompatActivity {

    private Switch swtAgend;
    private Switch swtCli;
    private Switch swtTabPre;
    private Switch swtCondPag;
    private Switch swtUltPedCliAgend;
    private Switch swtCliCad;
    private Switch swtPedRealiz;
    private Switch swtProdutos;
    private Button btnSincronizar;
    private HashMap<String, Boolean> resultsMap;
    public ProgressDialog dialog;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizacao);

        swtAgend = (Switch) findViewById(R.id.switchDownAgend);
        swtCli   = (Switch) findViewById(R.id.switchClientes);
        swtTabPre = (Switch) findViewById(R.id.switchTabPreco);
        swtCondPag = (Switch) findViewById(R.id.switchCondPag);
        swtUltPedCliAgend = (Switch) findViewById(R.id.switchUltPed);
        swtCliCad = (Switch) findViewById(R.id.switchCliCadast);
        swtPedRealiz = (Switch) findViewById(R.id.switchPedRealiz);
        swtProdutos = (Switch) findViewById(R.id.switchProdutos);
        btnSincronizar = (Button) findViewById(R.id.btnSinc);

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sincronizar();
            }
        });
        //dialog = ProgressDialog.show(getApplicationContext(), "Aguarde...","Sincronizando Registros!");

        //dialog.dismiss();
        //Toast.makeText(getApplicationContext(), "Sincronização Efetuada", Toast.LENGTH_LONG).show();
    }
    private void Sincronizar(){
        dialog = ProgressDialog.show(SincronizacaoActivity.this, "Aguarde...","Sincronizando Registros!");
        new Thread(){
            @Override
            public void run(){
                resultsMap = new HashMap<>();
                resultsMap.put("swtAgend", swtAgend.isChecked());
                resultsMap.put("swtCli", swtCli.isChecked());
                resultsMap.put("swtTabPre", swtTabPre.isChecked());
                resultsMap.put("swtCondPag", swtCondPag.isChecked());
                resultsMap.put("swtUltPedCliAgend", swtUltPedCliAgend.isChecked());
                resultsMap.put("swtCliCad", swtCliCad.isChecked());
                resultsMap.put("swtPedRealiz", swtPedRealiz.isChecked());
                resultsMap.put("swtProdutos", swtProdutos.isChecked());

                Read read = new Read(getApplicationContext());
                Vendedor vendedor = read.getVendedor();

                Sincronizacao sincronizacao = new Sincronizacao(getApplicationContext(), resultsMap, vendedor);
                FinalizaDialogo(sincronizacao.isbStatus(), sincronizacao.getsMensagem());
            }
        }.start();
    }
    private void FinalizaDialogo(final boolean bStatus, final String Mensagem){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (bStatus){
                        Toast.makeText(getApplicationContext(), "Sincronização Efetuada!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Houve algum problema na sincronização!", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
    }
}
