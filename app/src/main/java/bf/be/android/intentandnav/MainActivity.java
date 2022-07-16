package bf.be.android.intentandnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;
    private EditText phoneNr, url, email, password;
    private TextView preferences;
    private CheckBox remember;
    private Button callBtn, searchBtn, loginBtn, nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNr = findViewById(R.id.et_number);
        callBtn = findViewById(R.id.callBtn);

        url = findViewById(R.id.et_search);
        searchBtn = findViewById(R.id.searchBtn);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        remember = findViewById(R.id.cb_remember);
        loginBtn = findViewById(R.id.loginBtn);
        nextBtn = findViewById(R.id.nextBtn);
        preferences = findViewById(R.id.tv_preferences);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWeb();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        preferences.setText(getPrefs());

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });
    }

    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNr.getText().toString()));

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        } else {
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(this, "You have no permission to make a call on this device",Toast.LENGTH_SHORT).
                show();
            }
        }
    }

    private void searchWeb() {
        Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
        searchIntent.putExtra(SearchManager.QUERY, url.getText().toString());
        startActivity(searchIntent);
    }

    private void login() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.putBoolean("remember", remember.isChecked());
        editor.apply();
        preferences.setText(getPrefs());
    }

    private String getPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String retrievedEmail = prefs.getString("email", "");
        String retrievedPassword = prefs.getString("password", "");
        String retrievedRemember = prefs.getBoolean("remember", false) ? "true" : "false";

        return "Email: " + retrievedEmail + "\nPassword: " + retrievedPassword + "\nRemember me: " + retrievedRemember;
    }

    private void goNext() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}