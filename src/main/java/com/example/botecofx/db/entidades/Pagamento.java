package com.example.botecofx.db.entidades;

public class Pagamento {
    private int id;
    private Comanda comanda;
    private double valor;
    private TipoPagamento tipoPagamento;

    // Construtor completo
    public Pagamento(int id, Comanda comanda, double valor, TipoPagamento tipoPagamento) {
        this.id = id;
        this.comanda = comanda;
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
    }

    // Construtor sem ID (para novos registros)
    public Pagamento(Comanda comanda, double valor, TipoPagamento tipoPagamento) {
        this(0, comanda, valor, tipoPagamento);
    }

    // Construtor para Pagamento com apenas valor e associações
    public Pagamento(double valor, Comanda comanda, TipoPagamento tipoPagamento) {
        this(0, comanda, valor, tipoPagamento);
    }

    // Construtor vazio
    public Pagamento() {
        this(0, null, 0.0, null);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", comanda=" + (comanda != null ? comanda.getId() : "null") +
                ", valor=" + valor +
                ", tipoPagamento=" + (tipoPagamento != null ? tipoPagamento.getNome() : "null") +
                '}';
    }
}
