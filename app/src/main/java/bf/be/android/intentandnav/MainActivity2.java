package bf.be.android.intentandnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView prefs2;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        prefs2 = findViewById(R.id.tv2_preferences);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        prefs2.setText(getPrefs());
    }

    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String getPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String retrievedEmail = prefs.getString("email", "");
        String retrievedPassword = prefs.getString("password", "");
        String retrievedRemember = prefs.getBoolean("remember", false) ? "true" : "false";

        return "Email: " + retrievedEmail + "\nPassword: " + retrievedPassword + "\nRemember me: " + retrievedRemember;
    }
}