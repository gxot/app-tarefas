package br.equipe07.ui;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private long id;
    private String titulo;
    private String descricao;

    public Tarefa() {
    }

      public Tarefa(long id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

}