package com.br.alexandretrieste.entrega_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.TextView;

import java.util.Locale;

public class ConfiguracoesActivity extends AppCompatActivity {

    private Switch switchModoNoturno;
    private Spinner spinnerOrdenarPor, spinnerLanguage;

    private SharedPreferences sharedPreferences;
    private Locale currentLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        switchModoNoturno = findViewById(R.id.switchModoNoturno);
        spinnerOrdenarPor = findViewById(R.id.spinnerOrdenarPor);
        spinnerLanguage = findViewById(R.id.spinnerIdioma);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isModoNoturno = sharedPreferences.getBoolean("modo_noturno", false);
        String ordenarPor = sharedPreferences.getString("ordenar_por", "alfabetica");
        String language = sharedPreferences.getString("language", "en");
        currentLocale = getResources().getConfiguration().locale;

        switchModoNoturno.setChecked(isModoNoturno);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ordenar_por_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrdenarPor.setAdapter(adapter);

        if (ordenarPor.equals("alfabetica")) {
            spinnerOrdenarPor.setSelection(0);
        } else {
            spinnerOrdenarPor.setSelection(1);
        }

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.idioma_options, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(languageAdapter);

        if (language.equals("en")) {
            spinnerLanguage.setSelection(0);
        } else {
            spinnerLanguage.setSelection(1);
        }

        switchModoNoturno.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("modo_noturno", isChecked);
            editor.apply();
            aplicarModoNoturno(isChecked);
        });

        spinnerOrdenarPor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String ordenarPor = position == 0 ? "alfabetica" : "data";
                editor.putString("ordenar_por", ordenarPor);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String language = position == 0 ? "en" : "pt";
                editor.putString("language", language);
                editor.apply();
                aplicarIdioma(language);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void aplicarModoNoturno(boolean isModoNoturno) {
        if (isModoNoturno) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void aplicarIdioma(String language) {
        Locale locale = new Locale(language);
        if (!currentLocale.equals(locale)) {
            currentLocale = locale;
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
