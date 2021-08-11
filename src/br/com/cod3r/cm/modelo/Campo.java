package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean marcado = false;
	private boolean minado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = this.linha != vizinho.linha;
		boolean colunaDiferente = this.coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(this.linha - vizinho.linha);
		int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
		
	}
	
	void alternarMarcacao() {
		if(!this.aberto) {
			marcado = !marcado; // essa é a forma de alternar, ou seja, de TROCAR valores
		}
	}
	
	boolean abrir() {
		if(!this.aberto && !this.marcado) {
			this.aberto = true;
			
			if(this.minado) {
				// TODO Implementar nova versão
			}
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		} else {
			return false;
			
		}
	}
	
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		this.minado = true;
	}
	
	public boolean isMarcado() {
		return this.marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public boolean isAberto() {
		return this.aberto;
	}
	
	public boolean isFechado() {
		return !this.aberto;
	}
	
	public boolean isMinado() {
		return this.minado;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	// dois objetivos no jogo: 1-clicar e não estar minado e 2-marcar um campo com certeza de bomba
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = !minado && marcado;
		return desvendado || protegido;
	}
	
	// saber a quantidade de minas na vizinhança
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count(); 
	}
	
	void reiniciar() {
		this.aberto = false;
		this.minado = false;
		this.marcado = false;
	}
	
}