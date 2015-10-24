package com.mpoo.ruralmaps.ruralmaps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import dao.UsuarioDAO;

//import com.mpoo.ruralmaps.ruralmaps.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edtUser;
    private EditText edtPassword;
    private Resources resources;
    public UsuarioDAO helper;
    private CheckBox checkbox_conectado;

    private static final String MANTER_CONECTADO = "manter_conectado";
    private static final String PREFERENCE_NAME = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        helper = new UsuarioDAO(this);

        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean conectado =  preferences.getBoolean(MANTER_CONECTADO, false);

        if(conectado){
            ChamarMapaActivity();
        }
    }

    /**
     * Recupera as views e configura os listeners para os campos editaveis e para o botao de entrar
     */
    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        edtUser = (EditText) findViewById(R.id.login_edt_user);
        edtUser.addTextChangedListener(textWatcher);

        edtPassword = (EditText) findViewById(R.id.login_edt_password);
        edtPassword.addTextChangedListener(textWatcher);

        checkbox_conectado = (CheckBox) findViewById(R.id.login_checkbox_conectado);

        Button btnEnter = (Button) findViewById(R.id.login_btn_enter);
        btnEnter.setOnClickListener(this);
    }

    /**
     * Chama o metodo para limpar erros
     *
     * @param s Editable
     */
    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtUser);
        }
    }
    public void logar(View view) {
        String usuario = edtUser.getText().toString().trim();
        String senha = edtPassword.getText().toString().trim();

        if (helper.logar(usuario, senha)) {
            if(checkbox_conectado.isChecked()){
                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean(MANTER_CONECTADO, true);
                editor.commit();
                }

                ChamarMapaActivity();

        }else {
            //Mensagem de erro
            Toast.makeText(this, resources.getString(R.string.login_auth_deny), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn_enter) {
            if (validateFields()) {
                //Toast.makeText(this, resources.getString(R.string.login_auth_ok), Toast.LENGTH_LONG).show();
                logar(v);
            }
        }
    }


    /**
     * Efetua a validacao dos campos.Nesse caso, valida se os campos nao estao vazios e se tem
     * tamanho permitido.
     * Nesse metodo voce poderia colocar outros tipos de validacoes de acordo com a sua necessidade.
     *
     * @return boolean que indica se os campos foram validados com sucesso ou nao
     */
    private boolean validateFields() {
        String user = edtUser.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass) && !noHasSpaceLogin(user));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            edtUser.requestFocus(); //seta o foco para o campo user
            edtUser.setError(resources.getString(R.string.login_user_required));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            edtPassword.requestFocus(); //seta o foco para o campo password
            edtPassword.setError(resources.getString(R.string.login_password_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {

        if (!(user.length() > 3)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        } else if (!(pass.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_pass_size_invalid));
            return false;
        }
        return true;
    }

    private boolean noHasSpaceLogin(String user) {
        int idx = user.indexOf(" ");
        if (idx != -1){
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_has_space));
            return true;
        }return false;
    }


    /**
     * Limpa os icones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }


    private void ChamarMapaActivity(){
        startActivity(new Intent(this, MapaActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}