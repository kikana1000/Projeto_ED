/**
 * @Authors:
 *  Nome: André Figueiredo Almeida
 * Número: 8200289
 * Turma: Lei2T2
 *  @Author:
 * Nome: José Francisco Marques Lima
 * Número: 8200472
 * Turma: Lei2T2
 */
package tipoLocal;

import java.util.Objects;

/**
 * Classe para {@link Local local} que é extendida por {@link Armazem Armazem},{@link Mercado Mercado} e {@link Sede Sede}
 */
public class Local {
	private String nome;
	private final String tipo;

	/**
	 * Constructor para um {@link Local local}
	 * @param nome {@link String String} para o {@link #nome nome}
	 * @param tipo {@link String String} para o {@link #tipo tipo}
	 */
	public Local(String nome,String tipo) {
		this.nome = nome;
		this.tipo = tipo;
	}
	/**
	 * Constructor para um {@link Local local}
	 * @param nome {@link String String} para o {@link #nome nome}
	 */
	public Local(String nome) {
		this.nome = nome;
		this.tipo = null;
	}

	/**
	 * Getter para o {@link #tipo tipo}
	 * @return {@link String String} com o {@link #tipo tipo}
	 */
	public String getTipo() {
		return this.tipo;
	}

	/**
	 * Getter para o {@link #nome nome}
	 * @return {@link String String} com o {@link #nome nome}
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Setter para o {@link #nome}
	 * @param nome {@link String String} com o novo {@link #nome nome}
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Verificar de o {@link Object Object} o é igual ao {@link Local local} por o elemento {@link #nome}
	 * @param o {@link Object Object} que ira igualar ao {@link Local local}
	 * @return {@link Boolean#TRUE true} se for igual, {@link Boolean#FALSE false} caso contrario
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Local local = (Local) o;
		return nome.equals(local.nome);
	}

	/**
	 * to String do {@link Local Local}
	 * @return	{@link String String} com a informação do {@link Local Local}
	 */
	@Override
	public String toString() {
		return tipo + " "+'\u0022' +nome
				+'\u0022'+":";
	}
}
