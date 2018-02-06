package salesforce.prototipo.com.salesforce.Activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import salesforce.prototipo.com.salesforce.Auxiliares.Mask;
import salesforce.prototipo.com.salesforce.Auxiliares.Validacao;
import salesforce.prototipo.com.salesforce.Classes.Cliente;
import salesforce.prototipo.com.salesforce.Classes.CondPagamento;
import salesforce.prototipo.com.salesforce.Classes.TabPreco;
import salesforce.prototipo.com.salesforce.SQLiteControle.Delete;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforce.SQLiteControle.Update;
import salesforce.prototipo.com.salesforceprototipo.R;

public class CadClientesActivity extends AppCompatActivity {

    private ImageView btnClientes;
    private Button btnInserir;
    private Button btnLimpar;
    private RadioGroup rdgpTipo;
    private EditText edtRazSocCli;
    private EditText edtCnpjCpf;
    private EditText edtInsEst;
    private EditText edtTel;
    private EditText edtCont;
    private TextView tvCPFouCNPJ;
    private TextView tvNomeRazSoc;
    private RadioButton rbCPF;
    private RadioButton rbCNPJ;
    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;
    private Cliente edtcliente = null;
    static final int CONSULTA_CLIENTE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadclientes);

        btnClientes = (ImageView) findViewById(R.id.imageViewCliCad);
        btnInserir = (Button) findViewById(R.id.btnInsEdiCli);
        btnLimpar = (Button) findViewById(R.id.btnlimpar);
        rdgpTipo = (RadioGroup) findViewById(R.id.radioGroupTipo);
        edtRazSocCli = (EditText) findViewById(R.id.editTextRazSocCli);
        edtCnpjCpf = (EditText) findViewById(R.id.editTextCpfCnpj);
        edtCont = (EditText) findViewById(R.id.editTextCont);

        // Armazene seus TextWatcher para posterior uso
        cpfMask = Mask.insert("###.###.###-##", edtCnpjCpf);
        edtCnpjCpf.addTextChangedListener(cpfMask);
        cnpjMask = Mask.insert("##.###.###/####-##", edtCnpjCpf);

        rbCPF = (RadioButton) findViewById(R.id.rbFisica);
        rbCNPJ = (RadioButton) findViewById(R.id.rbJuridica);

        tvCPFouCNPJ = (TextView) findViewById(R.id.textViewCPFouCNPJ);
        tvNomeRazSoc = (TextView) findViewById(R.id.textViewRazSocCli);

        edtInsEst = (EditText) findViewById(R.id.editTextInsEst);
        edtTel = (EditText) findViewById(R.id.editTextCont);

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CadClientesActivity.this, ConsultasActivity.class);
                Bundle params = new Bundle();
                params.putInt("chamada", CONSULTA_CLIENTE);
                i.putExtras(params);
                startActivityForResult(i, CONSULTA_CLIENTE);
            }
        });


        rdgpTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int opcao = rdgpTipo.getCheckedRadioButtonId();
                if (rbCPF.getId() == opcao) {
                    tvCPFouCNPJ.setText("CPF");
                    tvNomeRazSoc.setText("Nome");
                    edtCnpjCpf.removeTextChangedListener(cnpjMask);
                    edtCnpjCpf.addTextChangedListener(cpfMask);
                } else {
                    tvCPFouCNPJ.setText("CNPJ");
                    tvNomeRazSoc.setText("Razão Social");
                    edtCnpjCpf.removeTextChangedListener(cpfMask);
                    edtCnpjCpf.addTextChangedListener(cnpjMask);
                }
                //edtTel.addTextChangedListener(Mask.insert("", edtCnpjCpf));
            }
        });

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTipo, sMensagem = "";
                int iOpcao;
                iOpcao = rdgpTipo.getCheckedRadioButtonId();
                if (iOpcao == rbCPF.getId()) {
                    sTipo = "F";
                } else {
                    sTipo = "J";
                }

                boolean bValido = ValidaCampos(sTipo);

                if (bValido) {
                    if (edtcliente == null) {
                        Update insert = new Update(getApplicationContext());
                        insert.insertDadosCli(UpdateCliente(sTipo, false));
                        sMensagem = "Cadastro Concluído!";
                    } else {
                        Update editarCli = new Update(getApplicationContext());
                        editarCli.updateDadosCli(UpdateCliente(sTipo, true));
                        sMensagem = "Edição Concluída!";
                    }
                    LimparCampos();
                    Toast.makeText(getApplicationContext(), sMensagem, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtcliente != null) {
                    Delete deleteCli = new Delete(getApplicationContext());
                    deleteCli.deleteCliente(edtcliente);
                    Toast.makeText(getApplicationContext(), "Cadastro Concluído", Toast.LENGTH_SHORT).show();
                }
                LimparCampos();
            }
        });
    }

    private Cliente UpdateCliente(String sTipo, boolean bEdit) {
        Cliente cliente = new Cliente();
        CondPagamento condPagamento = new CondPagamento();
        TabPreco tabPreco = new TabPreco();
        if (bEdit) {
            cliente.setsCodCli(edtcliente.getsCodCli().toString());
        }
        cliente.setsTipo(sTipo);
        cliente.setsRazSocCli(edtRazSocCli.getText().toString());
        cliente.setsCnpjCpf(Mask.unmask(edtCnpjCpf.getText().toString()));
        cliente.setsInscrEst(edtInsEst.getText().toString());
        cliente.setsTelefone(edtTel.getText().toString());
        cliente.setsContatoCli(edtCont.getText().toString());
        cliente.setCondPagamento(condPagamento);
        cliente.setTabPreco(tabPreco);
        return cliente;
    }


    private boolean ValidaCampos(String sTipo) {

        boolean bvalido;
        Validacao validacao = new Validacao();

        bvalido = validacao.validateNotNull(edtRazSocCli, "Preencha o campo " + tvNomeRazSoc.getText().toString() + "!");

        if (bvalido) {
            bvalido = validacao.validateNotNull(edtCnpjCpf, "Preencha o campo " + tvCPFouCNPJ.getText().toString() + "!");
        }
        if ((sTipo.equals("J")) && (bvalido)) {
            bvalido = validacao.validateNotNull(edtInsEst, "Preencha o campo Inscrição Estadual!");
        }

        if (bvalido) {
            bvalido = validacao.validateNotNull(edtTel, "Preencha o campo Telefone!");
        }
        if (bvalido){
            bvalido = validacao.validateNotNull(edtTel, "Preencha o campo Contato!");
        }
        if (bvalido) {
            bvalido = validacao.ValidaCpfCnpj(sTipo, edtCnpjCpf.getText().toString());
            if (!bvalido) {
                edtCnpjCpf.setError(tvCPFouCNPJ.getText().toString() + " inválido");
                edtCnpjCpf.setFocusable(true);
                edtCnpjCpf.requestFocus();
            }
        }

                /*boolean email_valido = Validacao.validateEmail(campo_email.getText().toString());
                if(!email_valido){
                    campo_email.setError("Email inválido");
                    campo_email.setFocusable(true);
                    campo_email.requestFocus();
                }*/
        return bvalido;
    }

    private void LimparCampos() {
        rbCPF.setChecked(true);
        //rbCNPJ.setChecked(false);
        edtRazSocCli.setText("");
        edtCnpjCpf.setText("");
        edtInsEst.setText("");
        edtTel.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONSULTA_CLIENTE) {
            if (resultCode == RESULT_OK) {
                String sCodCli = data.getStringExtra("sCodigo");

                Read read = new Read(getApplicationContext());
                List<Cliente> lista = read.getClientes("WHERE CODCLI = '" + sCodCli + "'", "DISTINCT A.*");

                edtcliente = lista.get(0);

                String sTipo = edtcliente.getsTipo();

                if (sTipo.equals("J")) {
                    rbCNPJ.setChecked(true);
                }
                edtRazSocCli.setText(edtcliente.getsRazSocCli());
                edtCnpjCpf.setText(edtcliente.getsCnpjCpf());
                edtInsEst.setText(edtcliente.getsInscrEst());
                edtTel.setText(edtcliente.getsTelefone());

                btnLimpar.setText("Excluir");
            }
        }
    }
}
