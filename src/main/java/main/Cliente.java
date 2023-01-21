/**
 * @Author:
 *  Nome: André Figueiredo Almeida
 * Número: 8200289
 * Turma: Lei2T2
 * @Author:
 * Nome: José Francisco Marques Lima
 * Número: 8200472
 * Turma: Lei2T2
 */
package main;
import com.github.javafaker.*;
import java.util.Locale;

/**
 * Classe {@link Cliente Cliente} que implementa {@link Comparable}
 */
public class Cliente implements Comparable {
    private final String nome, id;
    private double valor;
    public static final int VALOR_MAX=90;
    public static final int VALOR_MIN=20;

    /**
     *
     * Constructor de {@link Cliente Cliente} que recebe {@link #nome Nome},
     * {@link #valor Valor} e iniciliza {@link #id Id} com um valor {@link Faker aleatorios}
     * @param nome String para o {@link #nome Nome}
     * @param valor Double para o {@link #valor Valor}
     */
    public Cliente(String nome,double valor) {
        Faker random=new Faker(new Locale("pt"));
        this.nome = nome;
        this.id = random.idNumber().valid();
        this.valor = valor;
    }

    /**
     *Constructor de {@link #Cliente Cliente} que recebe {@link #nome Nome}
     * e iniciliza {@link #id Id},{@link #valor Valor} com um valores {@link Faker aleatorios}
     * @param nome {@link String string} para o {@link #nome Nome}
     */
    public Cliente(String nome) {
        Faker random=new Faker(new Locale("pt"));
        this.nome = nome;
        this.id = random.idNumber().valid();
        this.valor=(int) ((Math.random() * (VALOR_MAX- VALOR_MIN)) + VALOR_MIN);
    }

    /**
     *Constructor de {@link #Cliente Cliente} que iniciliza {@link #nome Nome},
     * {@link #id Id} e {@link #valor Valor} com um valores {@link Faker aleatorios}
     */
    public Cliente() {
        Faker random=new Faker(new Locale("pt"));
        this.nome= random.name().firstName()+" "+random.name().lastName();
        this.id = random.idNumber().valid();
        this.valor=(int) ((Math.random() * (VALOR_MAX- VALOR_MIN)) + VALOR_MIN);
    }

    /**
     * Constructor de {@link #Cliente Cliente} que recebe {@link #valor Valor} e
     * iniciliza {@link #nome Nome}, {@link #id Id} com um valores {@link Faker aleatorios}
     * @param valor  Double para o {@link #valor Valor}
     */
    public Cliente(int valor) {
        Faker random=new Faker(new Locale("pt"));
        this.nome= random.name().firstName()+" "+random.name().lastName();
        this.id = random.idNumber().valid();
        this.valor=valor;
    }

    /**
     * Setter para o {@link #valor Valor}
     * @return {@link Integer Integer} {@link #valor Valor}
     */
    public void setValor(double v) {
        this.valor=v;
    }

    /**
     * Getter para o {@link #nome Nome}
     * @return {@link #nome Nome}
     */
    public String getNome() {
        return nome;
    }
    /**
     * Getter para o {@link #id id}
     * @return {@link #id id}
     */
    public String getId() {
        return id;
    }

    /**
     * Getter para o {@link #valor Valor}
     * @return {@link #valor Valor}
     */
    public double getValor() {
        return valor;
    }

    /**
     * {@link Cliente Cliente} toString
     * formato:"Cliente "{@link #id <id>}":\n\tnome  : {@link #nome <nome>}\n\tvalor: {@link #valor <valor>}"
     * @return {@link String String} com informação do {@link Cliente Cliente}
     */
    @Override
    public String toString() {
        return "Cliente " +'\u0022'+ id +'\u0022'+" :"+ "\n" +
                "      nome   : " + nome + "\n" +
                "      valor: " + valor + "\n";
    }

    /**
     * CompareTo de {@link Cliente Cliente} que compara o {@link #valor valor} do {@link Cliente Cliente}
     * @param o Objeto para {@link Comparable comparação}
     * @return -1 se o {@link #valor valor} for menor,1 se o {@link #valor valor} for maior e 0 se o {@link #valor valor} for igual
     */
    @Override
    public int compareTo(Object o) {
        if(o==null){
            throw new NullPointerException();
        }
        if (!(o instanceof Cliente)){
            throw new IllegalArgumentException();
        }
        if(((Cliente) o).valor>valor){
            return -1;
        }else if(((Cliente) o).valor<valor){
            return 1;
        }
        return 0;
    }

}
