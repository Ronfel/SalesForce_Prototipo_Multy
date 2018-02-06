package salesforce.prototipo.com.salesforce.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import salesforce.prototipo.com.salesforce.Auxiliares.Validacao;
import salesforce.prototipo.com.salesforce.Classes.ItemPedido;
import salesforce.prototipo.com.salesforce.Classes.Pedido;
import salesforce.prototipo.com.salesforce.Classes.Preco;
import salesforce.prototipo.com.salesforce.Classes.Produto;
import salesforce.prototipo.com.salesforce.SQLiteControle.Read;
import salesforce.prototipo.com.salesforceprototipo.R;

public class CadItemActivity extends AppCompatActivity {

    private EditText etCodMat;
    private EditText etDesMat;
    private EditText etQtde;
    private EditText etDataNecess;
    private EditText etDescont;

    private TextView tvPreco;
    private TextView tvCodUnimed;
    private TextView tvPrecoTot;
    private TextView tvDesc;
    private TextView tvPorc;

    private ImageView btnDesconto;
    private Button btnIncluirItem;
    private Button btnLimpar;
    private ItemPedido itemPedido;
    private Pedido pedido;

    private Locale ptBr = new Locale("pt", "BR");
    private NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);
    private double valTot;
    private int iChamada;
    private int iIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_item);

        Intent intent = getIntent();
        iChamada = intent.getIntExtra("chamada", 0);
        pedido = (Pedido) intent.getSerializableExtra("Pedido");
        itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);

        etCodMat = (EditText) findViewById(R.id.etCodMat);
        etDesMat = (EditText) findViewById(R.id.etDesmat);
        etQtde = (EditText) findViewById(R.id.etQtde);
        etDataNecess = (EditText) findViewById(R.id.etData);
        etDescont = (EditText) findViewById(R.id.etDesc);

        tvPreco = (TextView) findViewById(R.id.TextViewPreco);
        tvCodUnimed = (TextView) findViewById(R.id.TextViewUM);
        tvPrecoTot = (TextView) findViewById(R.id.tvPrecoTot);
        tvDesc = (TextView) findViewById(R.id.tvDesconto);
        tvPorc = (TextView) findViewById(R.id.tvPercetual);

        btnIncluirItem = (Button) findViewById(R.id.btnConcluir);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnDesconto = (ImageView) findViewById(R.id.ivItens);
        Date hoje = new Date();
        updateEdit(hoje);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEdit(myCalendar.getTime());
            }

        };

        etDataNecess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CadItemActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DecimalFormat df = new DecimalFormat("000");
        switch (iChamada) {
            case 6:
                Produto produto = (Produto) intent.getSerializableExtra("Produto");
                Read read = new Read(getApplicationContext());
                final Preco preco = read.getPreco("WHERE CODMAT = '" + produto.getsCodMat() + "' " +
                        "AND CODTABPRECO = '" + pedido.getCliente().getTabPreco().getsCodTabPreco() + "'", "*").get(0);
                itemPedido.setPreco(preco);
                itemPedido.setProduto(produto);

                String sNumItem;

                if (!pedido.getItemPedidos().isEmpty()) {
                    sNumItem = df.format(pedido.getItemPedidos().size() + 1);
                } else {
                    sNumItem = df.format(1);
                }
                itemPedido.setsNumItem(sNumItem);
                break;
            case 7:
                df = new DecimalFormat("0.##");
                iIndex = intent.getIntExtra("index", 0);
                itemPedido = (ItemPedido) intent.getSerializableExtra("itemPedido");
                etQtde.setText(String.valueOf(itemPedido.getdQtde()).replace(".",","));
                etDataNecess.setText(itemPedido.getsDataNecess());

                double valPercAcresDesc = 0;

                valTot = Double.parseDouble(etQtde.getText().toString().replaceAll(",", ".")) * itemPedido.getPreco().getdPreco();

                if (itemPedido.getdPercDesc() != 0) {
                    valPercAcresDesc = itemPedido.getdPercDesc();

                    etDescont.setText(df.format(valPercAcresDesc).replace(".", ","));
                    valTot = valTot * (1 + valPercAcresDesc/100);

                }

                tvPrecoTot.setText(nf.format(valTot));
                btnLimpar.setText("Excluir");
                break;
        }
        etCodMat.setText(itemPedido.getProduto().getsCodMat());
        etDesMat.setText(itemPedido.getProduto().getsDesmat());
        tvCodUnimed.setText(itemPedido.getProduto().getsCodUnimed());

        tvPreco.setText(nf.format(itemPedido.getPreco().getdPreco()));

        etDescont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double percDescAcresc = 0;
                String c = "";
                if (!s.toString().equals("")){
                    percDescAcresc = Double.parseDouble(etDescont.getText().toString().replaceAll(",", "."));
                    c = s.toString().substring(s.length()-1, s.length());
                }
                if (c.matches("-?\\d+(\\,\\d+)?") || s.toString().equals("")) {
                    if (!etQtde.getText().toString().equals(""))
                        valTot = Double.parseDouble(etQtde.getText().toString().replaceAll(",", ".")) * itemPedido.getPreco().getdPreco() * (1 + percDescAcresc/100);

                        tvPrecoTot.setText(nf.format(valTot));
                }

            }
        });

        etQtde.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Não Aplicável
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    String c = s.toString().substring(s.length()-1, s.length());
                    if (c.matches("-?\\d+(\\,\\d+)?")) {
                        double percDescAcresc = 0;
                        if (!etDescont.getText().toString().equals(""))
                           percDescAcresc = Double.parseDouble(etDescont.getText().toString().replaceAll(",", "."));

                        valTot = Double.parseDouble(etQtde.getText().toString()) * itemPedido.getPreco().getdPreco() * (1 + percDescAcresc/100);

                        tvPrecoTot.setText(nf.format(valTot));
                    }
                }
            }
        });

        btnDesconto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDescont.getVisibility() == v.INVISIBLE) {
                    tvDesc.setVisibility(v.VISIBLE);
                    tvPorc.setVisibility(v.VISIBLE);
                    etDescont.setVisibility(v.VISIBLE);
                }
            }
        });

        btnIncluirItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidaCampos()) {
                    if (iChamada == 7)
                        pedido.removeItemPedido(itemPedido, iIndex);

                    double valPercAcresDesc = 0;

                    if (!etDescont.getText().toString().equals("")){
                        valPercAcresDesc = Double.parseDouble(etDescont.getText().toString().replaceAll(",", "."));
                        itemPedido.setdPercDesc(valPercAcresDesc);
                    }
                    itemPedido.setdQtde(Double.parseDouble(etQtde.getText().toString().replaceAll(",", ".")));

                    itemPedido.setdValorTot(valTot);

                    itemPedido.setsDataNecess(etDataNecess.getText().toString());
                    pedido.addItemPedidos(itemPedido);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Pedido", pedido);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iChamada == 6) {
                    etDataNecess.setText("");
                    etQtde.setText("");
                    if (!etDescont.getText().toString().equals(""))
                        etDescont.setText("");
                }else{
                    pedido.removeItemPedido(itemPedido, iIndex);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Pedido", pedido);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    private boolean ValidaCampos() {

        boolean bvalido;
        Validacao validacao = new Validacao();

        bvalido = validacao.validateNotNull(etQtde, "Insira a Quantidade!");

        return bvalido;
    }

    private void updateEdit(Date data) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, ptBr);

        etDataNecess.setText(sdf.format(data));
    }
}
