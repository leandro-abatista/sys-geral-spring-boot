package project.curso.springboot.enums;

public enum Cargo {
	/*
	 * pode fazer assim, ou como o código abaixo
	JUNIOR,
	PLENO, 
	SENIOR;
	*/
	
	JUNIOR("Júnior"),
	PLENO("Pleno"), 
	SENIOR("Sênior");
	
	private String nome;
	
	/**
	 * Construtor
	 * @param nome
	 */
	private Cargo(String nome) {
		this.nome = nome;
	} 
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
