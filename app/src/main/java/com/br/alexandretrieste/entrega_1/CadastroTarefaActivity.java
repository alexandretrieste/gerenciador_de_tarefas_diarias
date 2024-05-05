package com.br.alexandretrieste.entrega_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CadastroTarefaActivity extends AppCompatActivity {

    private EditText tituloEditText, descricaoEditText;
    private RadioGroup prioridadeRadioGroup;
    private RadioButton radioButtonAlta, radioButtonMedia, radioButtonBaixa;
    private CheckBox concluidaCheckBox;
    private Spinner categoriaSpinner;
    private Button limparButton, salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);

        tituloEditText = findViewById(R.id.tituloEditText);
        descricaoEditText = findViewById(R.id.descricaoEditText);
        prioridadeRadioGroup = findViewById(R.id.prioridadeRadioGroup);
        radioButtonAlta = findViewById(R.id.radioButtonAlta);
        radioButtonMedia = findViewById(R.id.radioButtonMedia);
        radioButtonBaixa = findViewById(R.id.radioButtonBaixa);
        concluidaCheckBox = findViewById(R.id.concluidaCheckBox);
        categoriaSpinner = findViewById(R.id.categoriaSpinner);
        limparButton = findViewById(R.id.limparButton);
        salvarButton = findViewById(R.id.salvarButton);

        List<String> categorias = new ArrayList<>();
        categorias.add("Casa");
        categorias.add("Estudos");
        categorias.add("Trabalho");

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(categoriaAdapter);

        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tituloEditText.setText("");
                descricaoEditText.setText("");
                prioridadeRadioGroup.clearCheck();
                concluidaCheckBox.setChecked(false);

                Toast.makeText(getApplicationContext(), "Campos limpos", Toast.LENGTH_SHORT).show();
            }
        });

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloEditText.getText().toString();
                String descricao = descricaoEditText.getText().toString();
                String prioridade = "";
                if (radioButtonAlta.isChecked()) {
                    prioridade = "Alta";
                } else if (radioButtonMedia.isChecked()) {
                    prioridade = "Média";
                } else if (radioButtonBaixa.isChecked()) {
                    prioridade = "Baixa";
                }
                boolean concluida = concluidaCheckBox.isChecked();
                String categoria = categoriaSpinner.getSelectedItem().toString();

                if (titulo.isEmpty() || prioridade.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
                    if (titulo.isEmpty()) {
                        tituloEditText.requestFocus();
                    } else if (prioridade.isEmpty()) {
                        radioButtonAlta.requestFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tarefa salva: " + titulo, Toast.LENGTH_SHORT).show();
                    tituloEditText.setText("");
                    descricaoEditText.setText("");
                    prioridadeRadioGroup.clearCheck();
                    concluidaCheckBox.setChecked(false);
                }
            }
        });
    }
}
