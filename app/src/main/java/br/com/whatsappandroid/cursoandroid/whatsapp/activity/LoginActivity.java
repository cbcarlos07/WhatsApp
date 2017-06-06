package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {
    private EditText telefone;
    private EditText codArea;
    private EditText codPais;
    private EditText nome;
    private Button   cadatrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                //salvar dados para validação
                Preferencias preferencias = new Preferencias( LoginActivity.this );
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuario();



            }
        });


    }
}
