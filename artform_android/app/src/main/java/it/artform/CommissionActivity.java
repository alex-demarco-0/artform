package it.artform;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;

import it.artform.web.ArtformApiEndpointInterface;

public class CommissionActivity extends Activity {
    AFGlobal app = null;
    ArtformApiEndpointInterface apiService = null;
    Button confirmCommission = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);
    }
}