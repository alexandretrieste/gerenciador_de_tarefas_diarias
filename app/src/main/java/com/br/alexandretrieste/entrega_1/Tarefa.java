package com.br.alexandretrieste.entrega_1;

public class Tarefa {
    private String titulo;
    private String descricao;
    private String prioridade;
    private boolean concluida;
    private String categoria; // Novo atributo

    // Construtor atualizado para incluir categoria
    public Tarefa(String titulo, String descricao, String prioridade, boolean concluida, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.concluida = concluida;
        this.categoria = categoria; // Inicializar novo atributo
    }

    // Getters e setters para todos os atributos
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
}
