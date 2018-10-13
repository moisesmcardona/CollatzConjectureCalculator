package msc.moisessoftwares.collatzconjecturecalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private double number;
    private double previousNumber;
    private ArrayList<String> ResultSteps = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.admob_app_id));

        final Button computeButton =  findViewById(R.id.button);
        final EditText NumberText = findViewById(R.id.editText);
        lv = findViewById(R.id.listView);
        final AdView mAdView = findViewById(R.id.mainactivityad);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        RateMeMaybe rmm = new RateMeMaybe(this);
        rmm.setPromptMinimums(3, 2, 3, 2);
        rmm.run();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.smalllistview, ResultSteps);
        lv.setAdapter(arrayAdapter);
        computeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NumberText.getText().toString().equals("")){
                  alertDialogs("You didn't entered a number. Please enter a number to compute its collatz sequence.");
                }
                else {
                    ResultSteps.clear();
                    number = Double.parseDouble(NumberText.getText().toString());
                    if (number == 0) {
                       alertDialogs("Cannot compute Collatz for 0. You must enter a positive number");
                    }
                    else if (number < 0){
                       alertDialogs("Cannot compute Collatz for negative numbers. You must enter a positive number");
                    }
                    else {
                        while (number != 1) {
                            previousNumber = number;
                            if (number % 2 == 0) {
                                number = number / 2;
                                ResultSteps.add(previousNumber + " ÷ 2 = " + number);
                            }
                            else {
                                number = number * 3 + 1;
                                ResultSteps.add(previousNumber + " × 3 + 1 = " + number);
                            }
                            arrayAdapter.notifyDataSetChanged();
                            lv.setAdapter(arrayAdapter);
                        }
                        ResultSteps.add("Final Result: " + Double.toString(number));
                        arrayAdapter.notifyDataSetChanged();
                        lv.setAdapter(arrayAdapter);
                    }
                }
            }
        });
    }
    private void alertDialogs(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Error");
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog notify = builder.create();
        notify.show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void aboutApp(MenuItem item){
        Intent main = new Intent(MainActivity.this, AboutApp.class);
        startActivity(main);
    }
    public void privacyPolicy(MenuItem item){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://moisescardona.me/CollatzConjecturePrivacyPolicy"));
        startActivity(browserIntent);
    }
}
