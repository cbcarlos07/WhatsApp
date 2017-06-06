package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Permissao;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {
    private EditText telefone;
    private EditText codArea;
    private EditText codPais;
    private EditText nome;
    private Button   cadatrar;
    private String[] permissoesNecessarias = new String[]{
        Manifest.permission.SEND_SMS,
       Manifest.permission.INTERNET

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias );

        telefone = (EditText)  findViewById(R.id.edit_telefone);
        codArea  = (EditText)  findViewById(R.id.edit_cod_area);
        codPais  = (EditText)  findViewById(R.id.edit_cod_pais);
        nome     = (EditText)  findViewById(R.id.edit_nome);
        cadatrar = (Button)    findViewById(R.id.btn_cadastrar);

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter simpleMaskCodArea     = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskCodPais     = new SimpleMaskFormatter("+NN");

        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        MaskTextWatcher maskCdArea = new MaskTextWatcher(codArea, simpleMaskCodArea);
        MaskTextWatcher maskCdPais = new MaskTextWatcher(codPais, simpleMaskCodPais);

        telefone.addTextChangedListener(maskTelefone);
        codArea.addTextChangedListener(maskCdArea);
        codPais.addTextChangedListener(maskCdPais);

        cadatrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString() +
                        codArea.getText().toString() +
                        telefone.getText().toString();
                String telefoneSemFormatacao = telefoneCompleto.replace("+","").replace("-","");
                Log.i("Telefone", "T: "+telefoneSemFormatacao);

                //GerarToken
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt( 9999 - 1000 ) + 1000;
                String token = String.valueOf( numeroRandomico );
                String mensagemEnvio = "WhatsApp Código de Confirmação: "+ token;



                //salvar dados para validação
                Preferencias preferencias = new Preferencias( LoginActivity.this );
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                //Envio do SMS
                boolean enviadoSMS = enviaSMS( "+"+telefoneSemFormatacao, mensagemEnvio );

                if( enviadoSMS ){



                }else{

                }


                //HashMap<String, String> usuario = preferencias.getDadosUsuario();



            }
        });


    }
    private boolean enviaSMS(String telefone, String mensagem){

        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        for( int resultado : grantResults ){

            if( resultado == PackageManager.PERMISSION_DENIED ){

                alertaValidacaoPermissao();

            }

        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
