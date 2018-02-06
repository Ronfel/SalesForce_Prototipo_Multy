package salesforce.prototipo.com.salesforce.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import salesforce.prototipo.com.salesforce.Classes.Agendamento;
import salesforce.prototipo.com.salesforce.Classes.Pedido;
import salesforce.prototipo.com.salesforce.SQLiteControle.Create;
import salesforce.prototipo.com.salesforce.SQLiteControle.Delete;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

public class MainActivity extends AppCompatActivity {

    private ImageView btnClientes;
    private ImageView btnPedidos;
    private ImageView btnAgendamentos;
    private ImageView btnSincronizar;
    private ImageView deletartabelas;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClientes = (ImageView) findViewById(R.id.imageViewClientes);
        btnPedidos = (ImageView) findViewById(R.id.imageViewPedidos);
        btnAgendamentos = (ImageView) findViewById(R.id.imageViewAgendamentos);
        btnSincronizar = (ImageView) findViewById(R.id.imageViewSincronizar);
        deletartabelas = (ImageView) findViewById(R.id.imageView10);

        deletartabelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Confirmacao();
            }
        });

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadClientesActivity.class));
            }
        });
        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadPedidosActivity.class));
            }
        });
        btnAgendamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AgendamentosActivity.class));
            }
        });
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SincronizacaoActivity.class));
            }
        });
    }
    private void Confirmacao() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Confirmação");
        //define a mensagem
        builder.setMessage("Deseja sair da aplicação?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Delete delete = new Delete(getApplicationContext());
                delete.deletaTabelas();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
