package com.friendfill.foodifie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.friendfill.foodifie.model.Login;
import com.friendfill.foodifie.network.APIClient;
import com.friendfill.foodifie.network.APIInterface;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends Activity {
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Nullable
    @BindView(R.id.login_btn_login)
    Button btn_login_login;
    @Nullable
    @BindView(R.id.login_btn_direct_enter)
    Button btn_login_direct_enter;
    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.activity_login)
    FrameLayout activityLogin;
    @BindView(R.id.show_password_button)
    ImageView showPasswordButton;
    int passowrd_visible = 1;
    ProgressDialog progressDialog;

    public void MoveToDashBoard() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        new SecurePrefManagerInit.Initializer(getApplicationContext())
                .useEncryption(true)
                .initialize();

        boolean isLoggedIn = SecurePrefManager.with(this).get("isLoggedIn").defaultValue(false).go();
        if (isLoggedIn) {
            MoveToDashBoard();
        }

        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passowrd_visible == 0) {
                    loginPassword.setTransformationMethod(new PasswordTransformationMethod());
                    passowrd_visible = 1;

                } else {
                    loginPassword.setTransformationMethod(null);
                    passowrd_visible = 0;
                }
                loginPassword.setSelection(loginPassword.getText().length());

            }
        });

        loginEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!validEmail(loginEmail.getText().toString().trim()) || s.length() <= 0) {
                    loginEmail.setError("Enter valid username");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        btn_login_direct_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MoveToDashBoard();
            }
        });
        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

    }


    private boolean validEmail(String text) {
        return (text.length() >= 6);
    }

    private boolean validPassword(String text) {
        return !text.equals("");
    }


    public void Login() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        if (validEmail(email)) {
            if (validPassword(password)) {
                DoLogin(email, password);
            } else {
                loginPassword.setError("Enter valid password");
            }

        } else {
            loginEmail.setError("Enter valid username");
        }

    }

    private void DoLogin(String username, String password) {
        try {
            ApplicationConfig.setProgressDialog(LoginActivity.this, "Authenticating..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Login login_detail = new Login(username, password);

            Call<ResponseBody> call = request.Login("application/json", login_detail);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        if (json.getString("status").equals("1")) {
                            JSONObject result = json.getJSONObject("result");
                            SecurePrefManager.with(getApplicationContext())
                                    .set("id")
                                    .value("" + result.getString("id"))
                                    .go();
                            SecurePrefManager.with(getApplicationContext())
                                    .set("name")
                                    .value("" + result.getString("name"))
                                    .go();
                            SecurePrefManager.with(getApplicationContext())
                                    .set("isLoggedIn")
                                    .value(true)
                                    .go();
                            MoveToDashBoard();

                        } else {
                            Snackbar.make(activityLogin, json.getString("message"), BaseTransientBottomBar.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(activityLogin, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ApplicationConfig.progressDialog.dismiss();
                    System.out.print("error" + t.getMessage());
                    Snackbar.make(activityLogin, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            ApplicationConfig.progressDialog.dismiss();
            e.printStackTrace();
            Snackbar.make(activityLogin, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }
}
