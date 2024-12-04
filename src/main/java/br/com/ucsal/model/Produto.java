package br.com.ucsal.model;

public class Produto {

	private Integer id; 
	private String nome;
	private double preco;


	public Produto(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}

	public Produto(Integer id, String nome, double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "Produto{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", preco=" + preco +
				'}';
	}
}
