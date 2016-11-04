package suwuttipoj.nantapak.takienfloatingmarket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private static final String urlJSON = "http://swiftcodingthai.com/ton/get_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText12);
        passwordEditText = (EditText) findViewById(R.id.editText13);

    } //Main Method

    public void clickMyLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Have Space",
                    "Please Fill All Every Blank");

        } else {

            SynMember synMember = new SynMember(this);
            synMember.execute(urlJSON);

        }

    }   //clickMyLogin

    private class SynMember extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;
        private boolean aBoolean = true;
        private String[] columnStrings = new String[]{
                "MEM_ID",
                "USER_NAME",
                "USER_PASSWORD",
                "MEM_FIRSTNAME",
                "MEM_LASTNAME",
                "MEM_ADDRESS",
                "MEM_TEL",
                "MEM_EMAIL"};
        private String[] loginStrings;

        public SynMember(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("2octV1", "e doInBack ==> " + e.toString());
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("2octV1", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                loginStrings = new String[columnStrings.length];

                for (int i=0;i<jsonArray.length();i+=1) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (userString.equals(jsonObject.getString(columnStrings[1]))) {

                        aBoolean = false;
                        for (int i1=0;i1 < columnStrings.length;i1+=1) {

                            loginStrings[i1] = jsonObject.getString(columnStrings[i1]);
                            Log.d("2octV2", "loginString(" + i1 + ") = " + loginStrings[i1]);

                        }   // for

                    }   // if
                }   // for

                //Check User
                if (aBoolean) {
                    //User False
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "User ผิด",
                            "ไม่มี " + userString + " ในฐานข้อมูล ของเรา");
                } else if (!passwordString.equals(loginStrings[2])) {
                    //Password False
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "Password False",
                            "กรุณาลองใหม่ Password ผิด");
                } else {
                    //Password True
                    Toast.makeText(context, "Welcome " + loginStrings[3] + " " + loginStrings[4],
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();

                }


            } catch (Exception e) {
                Log.d("2octV1", "e onPost ==> " + e.toString());
            }

        }   // onPost

    }   // SynMember Class



    public void clickCancel(View view) {
        finish();
    }

    public void clickNewRegis(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

}     // Main Class