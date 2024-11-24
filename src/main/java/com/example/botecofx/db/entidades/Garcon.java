package com.example.botecofx.db.entidades;

import java.awt.image.BufferedImage;
import java.io.File;

public class Garcon {
    private int id;
    private String nome;
    private String cpf;
    private String cep;
    private String endereco;
    private String numero;
    private String cidade;
    private String uf;
    private String fone;
    private BufferedImage foto;

    // Construtor completo
    public Garcon(int id, String nome, String cpf, String cep, String endereco, String numero, String cidade, String uf, String fone, BufferedImage foto) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.fone = fone;
        this.foto = foto;
    }

    // Construtor sem foto (foto é opcional)
    public Garcon(int id, String nome, String cpf, String cep, String endereco, String numero, String cidade, String uf, String fone) {
        this(id, nome, cpf, cep, endereco, numero, cidade, uf, fone, null);
    }

    // Construtor para novo registro (id padrão 0)
    public Garcon(String nome, String cpf, String cep, String endereco, String numero, String cidade, String uf, String fone) {
        this(0, nome, cpf, cep, endereco, numero, cidade, uf, fone, null);
    }

    // Construtor vazio para inicializações flexíveis
    public Garcon() {
        this(0, "", "", "", "", "", "", "", "", null);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public BufferedImage getFoto() {
        return foto;
    }

    public void setFoto(BufferedImage foto) {
        this.foto = foto;
    }

    // Método toString para exibir o nome do garçom
    @Override
    public String toString() {
        return nome;
    }
}
