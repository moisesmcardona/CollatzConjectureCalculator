package msc.moisessoftwares.collatzconjecturecalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by cardo on 12/19/2015.
 */
public class AboutApp extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutapp);
        final AdView mAdView = findViewById(R.id.mainactivityad);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void visitPage(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.website)));
        startActivity(browserIntent);
    }
    public void donateBitcoin(View v) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("bitcoin:" + getResources().getString(R.string.bitcoin_address)));
            startActivity(browserIntent);
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AboutApp.this);
            builder.setTitle("Download wallet?");
            builder.setMessage("It seems you do not have a wallet app installed. Do you want to download the Bitcoin Wallet app in the Play Store?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=de.schildbach.wallet"));
                                startActivity(browserIntent);
                            } catch (Exception e) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=de.schildbach.wallet"));
                                startActivity(browserIntent);
                            }

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog notify = builder.create();
            notify.show();
        }
    }
}
