package suwuttipoj.nantapak.takienfloatingmarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {


    //Explicit
    private EditText userEditText, passwordEditText, password2EditText,
            nameEditText, surnameEditText, addressEditText,
            telEditText, emailEditText;

    private RadioGroup radioGroup;
    private RadioButton prefix1RadioButton, prefix2RadioButton, prefix3RadioButton;

    private Spinner districtSpinner, amphurSpinner, privinceSpinner;

    private String userString, passwordString, password2String,
            nameString, surnameString, addressString,
            telString, emailString, prefixString, districtString,
            amphurString, provinceString;

    private static final String urlPHP = "http://swiftcodingthai.com/ton/php_add_user.php";

    private MyConstante myConstante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myConstante = new MyConstante();

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        password2EditText = (EditText) findViewById(R.id.editText3);
        nameEditText = (EditText) findViewById(R.id.editText4);
        surnameEditText = (EditText) findViewById(R.id.editText14);
        addressEditText = (EditText) findViewById(R.id.editText7);
        telEditText = (EditText) findViewById(R.id.editText8);
        emailEditText = (EditText) findViewById(R.id.editText9);
        districtSpinner = (Spinner) findViewById(R.id.spinner);
        amphurSpinner = (Spinner) findViewById(R.id.spinner1);
        privinceSpinner = (Spinner) findViewById(R.id.spinner2);
        radioGroup = (RadioGroup) findViewById(R.id.ragPrefix);

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.radioButton6:
                        prefixString = "1";
                        break;
                    case R.id.radioButton3:
                        prefixString = "2";
                        break;
                    case R.id.radioButton5:
                        prefixString = "3";
                        break;

                }   // switch

            }
        });


        //Create Province Spinner
        SynProvince synProvince = new SynProvince(SignUpActivity.this);
        synProvince.execute(myConstante.getUrlGetProvinceString());




    } // Main Method

    //For Load String Array Create Province Spinner
    private class SynProvince extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;
        private String[] provinceStrings;

        public SynProvince(Context context) {
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
                Log.d("4novV2", "e doInBack ==> " + e.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("4novV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                provinceStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    provinceStrings[i] = jsonObject.getString("PROVINCE_NAME");
                    Log.d("4novV2", "procinve[" + i + "] ==> " + provinceStrings[i]);

                }   // for

                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, provinceStrings);
                privinceSpinner.setAdapter(stringArrayAdapter);



            } catch (Exception e) {
                Log.d("4novV2", "e onPost ==> " + e.toString());
            }

        }
    }   // SynProvince Class



    public void clickBackSignUp(View view) {
        finish();
    }


    public void clickSaveData(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        password2String = password2EditText.getText().toString().trim();
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        addressString = addressEditText.getText().toString().trim();
        telString = telEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();
        radioGroup = (RadioGroup) findViewById(R.id.ragPrefix);
        prefix1RadioButton = (RadioButton) findViewById(R.id.radioButton6);
        prefix2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        prefix3RadioButton = (RadioButton) findViewById(R.id.radioButton5);


        // Check Space
        if (checkSpace()) {
            // Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกให้ครบทุกช่องครับ");
        } else if (!passwordString.equals(password2String)) {

            //Password Not Mat
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "รหัสผ่านไม่ตรงกัน",
                    "กรุณากรอกรหัสผ่านให้ตรงกันครับ");

        } else if (!(prefix1RadioButton.isChecked() ||
                prefix2RadioButton.isChecked() ||
                prefix3RadioButton.isChecked())) {

            //Non Choose Prefix
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่เลือกคำนำหน้า",
                    "กรุณาเลือกคำนำหน้านามด้วย คะ");

        }


    }   // clickSaveData

    private void confirmData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.nobita48);
        builder.setTitle("โปรดตรวจสอบข้อมูล");
        builder.setMessage("ชื่อผู้ใช้งาน = " + userString + "\n" +
                "รหัสผ่าน = " + passwordString + "\n" +
                "ชื่อ = " + nameString + "\n" +
                "นามสกุล = " + surnameString + "\n" +
                "ที่อยู่ = " + addressString + "\n" +
                "เบอร์โทรศัพท์ = " + telString + "\n" +
                "อีเมลล์ = " + emailString + "\n");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadToServer();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void uploadToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("USER_NAME", userString)
                .add("USER_PASSWORD", passwordString)
                .add("MEM_FIRSTNAME", nameString)
                .add("MEM_LASTNAME", surnameString)
                .add("MEM_ADDRESS", addressString)
                .add("MEM_TEL", telString)
                .add("MEM_EMAIL", emailString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("4SepV1", "e ==> " + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("4SepV1", "Result ==> " + response.body().string());
                finish();
            }
        });

    } // uploadToServer

    private boolean checkSpace() {
        return userString.equals("") ||
                passwordString.equals("") ||
                password2String.equals("") ||
                nameString.equals("") ||
                addressString.equals("") ||
                telString.equals("") ||
                emailString.equals("");
    }


} // Main Class