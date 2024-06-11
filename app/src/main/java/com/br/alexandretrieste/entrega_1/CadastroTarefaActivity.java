package com.br.alexandretrieste.entrega_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CadastroTarefaActivity extends AppCompatActivity {

    private EditText tituloEditText, descricaoEditText;
    private RadioGroup prioridadeRadioGroup;
    private RadioButton radioButtonAlta, radioButtonMedia, radioButtonBaixa;
    private CheckBox concluidaCheckBox;
    private Spinner categoriaSpinner;
    private boolean isEditMode = false;
    private int position = -1;

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

        List<String> categorias = new ArrayList<>();
        categorias.add("Casa");
        categorias.add("Estudos");
        categorias.add("Trabalho");

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(categoriaAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("tarefa")) {
            Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");
            tituloEditText.setText(tarefa.getTitulo());
            descricaoEditText.setText(tarefa.getDescricao());
            switch (tarefa.getPrioridade()) {
                case "Alta":
                    radioButtonAlta.setChecked(true);
                    break;
                case "Média":
                    radioButtonMedia.setChecked(true);
                    break;
                case "Baixa":
                    radioButtonBaixa.setChecked(true);
                    break;
            }
            concluidaCheckBox.setChecked(tarefa.isConcluida());
            int categoriaPosition = categoriaAdapter.getPosition(tarefa.getCategoria());
            categoriaSpinner.setSelection(categoriaPosition);

            isEditMode = true;
            position = intent.getIntExtra("position", -1);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_salvar) {
            salvarTarefa();
            return true;
        } else if (id == R.id.menu_limpar) {
            limparCampos();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarTarefa() {
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
            Tarefa tarefa = new Tarefa(titulo, descricao, prioridade, concluida, categoria);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("tarefa", tarefa);
            if (isEditMode) {
                resultIntent.putExtra("position", position);
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void limparCampos() {
        tituloEditText.setText("");
        descricaoEditText.setText("");
        prioridadeRadioGroup.clearCheck();
        concluidaCheckBox.setChecked(false);
        categoriaSpinner.setSelection(0);
        Toast.makeText(getApplicationContext(), "Campos limpos", Toast.LENGTH_SHORT).show();
    }
}
