package com.mpoo.ruralmaps.ruralmaps;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.mpoo.ruralmaps.ruralmaps.R;jjj


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edtUser;
    private EditText edtPassword;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    /**
     * Recupera as views e configura os listeners para os campos edit�veis e para o bot�o de entrar
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
        Button btnEnter = (Button) findViewById(R.id.login_btn_enter);
        btnEnter.setOnClickListener(this);
    }

    /**
     * Chama o m�todo para limpar erros
     *
     * @param s Editable
     */
    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtUser);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn_enter) {
            if (validateFields()) {
                /**
                 * Nesse ponto voc� poderia chamar um servi�o de autentica��o do usu�rio.
                 * Por quest�es de simplicidade e entendimento emitiremos somente um alerta
                 */
                Toast.makeText(this, resources.getString(R.string.login_auth_ok), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Efetua a valida��o dos campos.Nesse caso, valida se os campos n�o est�o vazios e se tem
     * tamanho permitido.
     * Nesse m�todo voc� poderia colocar outros tipos de valida��es de acordo com a sua necessidade.
     *
     * @return boolean que indica se os campos foram validados com sucesso ou n�o
     */
    private boolean validateFields() {
        String user = edtUser.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass));
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

    /**
     * Limpa os �cones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }
}