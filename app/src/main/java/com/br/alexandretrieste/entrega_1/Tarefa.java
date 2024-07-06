package com.br.alexandretrieste.entrega_1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "tarefas")
public class Tarefa implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private boolean concluida;
    private String categoria;
    private String data;

    // Construtor padrão necessário para o Room
    public Tarefa() {
    }

    // Novo construtor com parâmetros
    public Tarefa(String titulo, String descricao, String prioridade, boolean concluida, String categoria, String data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.concluida = concluida;
        this.categoria = categoria;
        this.data = data;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.data = sdf.format(new Date());
    }
}
