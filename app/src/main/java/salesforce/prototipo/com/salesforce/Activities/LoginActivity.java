package salesforce.prototipo.com.salesforce.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import salesforce.prototipo.com.salesforce.Auxiliares.Validacao;
import salesforce.prototipo.com.salesforce.Classes.Sincronizacao;
import salesforce.prototipo.com.salesforce.Classes.Vendedor;
import salesforce.prototipo.com.salesforce.SQLiteControle.Create;
import salesforce.prototipo.com.salesforce.SQLiteControle.Delete;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmp;
    private EditText etUser;
    private EditText etSenha;
    private EditText etHost;
    public ProgressDialog dialog;
    private Handler handler = new Handler();
    private Read read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Create create = new Create(getApplicationContext());
        create.CriarTabelas();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        etEmp = (EditText) findViewById(R.id.EditTextEmp);
        etUser = (EditText) findViewById(R.id.EditTextUser);
        etHost = (EditText) findViewById(R.id.EditTextHost);
        etSenha = (EditText) findViewById(R.id.EditTextSenha);
        etSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etSenha.setTypeface(etUser.getTypeface());

        read = new Read(getApplicationContext());

        if (!read.getVendedor().getsCodVend().equals("")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidaCampos()) {
                    dialog = ProgressDialog.show(LoginActivity.this, "Aguarde...", "Verificando Dados!");

                    new Thread() {
                        @Override
                        public void run() {
                            Vendedor vendedor = new Vendedor();
                            vendedor.setsUser_web(etUser.getText().toString());
                            vendedor.setsSenha(etSenha.getText().toString());
                            vendedor.setsHost(etHost.getText().toString());

                            Sincronizacao sincronizacao = new Sincronizacao(getApplicationContext(), null, vendedor);
                            FinalizaDialogo(sincronizacao.isbStatus(), sincronizacao.getsMensagem());
                        }
                    }.start();
                }
            }
        });

    }

    private void FinalizaDialogo(final boolean bStatus, final String Mensagem){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bStatus){
                    Toast.makeText(getApplicationContext(), "Login Efetuado!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Verifique os dados informados ou a conexão com a internet!", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
    }

    private boolean ValidaCampos() {

        boolean bvalido;
        Validacao validacao = new Validacao();

        bvalido = validacao.validateNotNull(etEmp, "Insira a Empresa!");

        if (bvalido) {
            bvalido = validacao.validateNotNull(etUser, "Insira o Usuário!");
        }
        if (bvalido) {
            bvalido = validacao.validateNotNull(etSenha, "Insira!");
        }
        return bvalido;
    }
}
