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
import main.Cliente;
import main.Vendedor;
import org.json.simple.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe {@link Armazem Armazem} que extende {@link Local Local}
 */
public class Armazem extends Local implements Comparable<Double> {
    /**
     * {@link #capacidade Capacidade Maxima}  do {@link Armazem Armazem}
     */
    private int capacidade;
    /**
     * {@link #stock stock} do {@link Armazem Armazem}
     */
    private double stock;
    /**
     * {@link #capacidade Capacidade Maxima} {@link Math#random() aleatoria} de um {@link Armazem Armazem}
     */
    public static final int CAP_MAX=3000;
    /**
     * {@link #capacidade Capacidade minima} {@link Math#random() aleatoria} de um {@link Armazem Armazem}
     */
    public static final int CAP_MIN=500;

    /**
     * Construtor de {@link Armazem}
     * @param nome {@link String String} para o {@link #getNome() Nome}
     * @param capacidade {@link Integer Integer} para o {@link #capacidade capacidade}
     * @param stock {@link Integer Integer}  para o {@link #stock stock}
     */
    public Armazem(String nome,int capacidade, int stock) {
        super(nome,"Armazém");
        this.capacidade = capacidade;
        this.stock = stock;
    }
    /**
     * Construtor de {@link Armazem} com {@link #capacidade capacidade}
     *  e {@link #stock stock} {@link Math#random() aleatorio}
     * @param nome {@link #getNome() Nome} para o {@link Armazem Armazem}
     */
    public Armazem(String nome) {
        super(nome,"Armazém");
        this.capacidade=(int) ((Math.random() * (CAP_MAX- CAP_MIN)) + CAP_MIN);
        this.stock=(int) ((Math.random() * (capacidade)) + 0);
    }

    /**
     * Getter para a {@link #capacidade capacidade}
     * @return {@link #capacidade capacidade}
     */
    public int getCapMax() {
        return capacidade;
    }
    /**
     * Getter para a {@link #stock stock}
     * @return {@link #stock stock}
     */
    public double getStock() {
        return stock;
    }
    /**
     * Setter para a {@link #capacidade capacidade}
     * @param capMax {@link Integer Integer} para {@link #capacidade capacidade}
     */
    public void setCapMax(int capMax) {
        if(capMax>=stock)this.capacidade = capMax;
                else throw new IllegalArgumentException("Valor da Capacidade menor que o stock ");
    }
    /**
     * Setter para a {@link #stock stock}
     * @param stock {@link Double Double} para {@link #stock stock}
     */
    public void setStock(double stock) {
        if(stock<=capacidade)this.stock=stock;
        else throw new IllegalArgumentException("Valor da Capacidade menor que o stock ");
    }
    /**
     * Setter {@link Math#random() aleatorio}  para a {@link #capacidade capacidade}
     */
    public void setCapMaxrandom() {
        this.capacidade=(int) ((Math.random() * (CAP_MAX- stock)) + stock);
    }

    /**
     * Setter {@link Math#random() aleatorio}  para a {@link #stock stock}
     */
    public void setStockrandom() {
        this.stock=(int) ((Math.random() * (capacidade)) + 0);
    }

    /**
     * Stocka o {@link Vendedor vendedor} de acordo com o {@link #stock stock} disponivel
     * @param vendedor  {@link Vendedor vendedor} que irá receber o {@link #stock stock}
     * @param mercadoria {@link Double Double} com a mercadoria atual do {@link Vendedor vendedor}
     * @return  {@link Integer Integer} com o stock que foi colocoda no {@link Vendedor vendedor}
     */
    public double stockarVendedor(Vendedor vendedor,double mercadoria){
        double stockcolocado=0;
        if(vendedor==null)return stockcolocado;
        if(this.getStock()==0)return stockcolocado;
        if((vendedor.getCapacidade()-mercadoria)>=this.getStock()){
            stockcolocado=this.getStock();
        }else{
            stockcolocado= vendedor.getCapacidade()-mercadoria;
        }
        mercadoria= mercadoria+stockcolocado;
        this.setStock(this.getStock()-(int)stockcolocado);
        return mercadoria;
    }

    /**
     * {@link Armazem Armazem} toString
     * @return {@link String String} com informação do {@link Armazem Armazem}
     */
    @Override
    public String toString() {
        return getTipo() + " "+'\u0022' +getNome() +'\u0022'+":"+
                "	Capacidade Max: " + getCapMax() + "\n" +
                        "	Stock : " + getStock() + "\n";
    }

    /**
     * {@link #export(String) Exporta} {@link Armazem Armazem} para um ficheiro .json
     * @param path  {@link String String} com o caminho do ficheiro
     * @throws IOException se nao escrever o ficheiro
     */
    public void export(String path) throws IOException {
        path=path.concat(".json");
        JSONObject armazemJSON= new JSONObject();
        armazemJSON.put("nome",this.getNome());
        armazemJSON.put("tipo",this.getTipo());
        armazemJSON.put("capacidade",this.capacidade);
        armazemJSON.put("stock",this.stock);
        FileWriter file = new FileWriter(path, false);
        file.write(armazemJSON.toString());
        file.close();
    }


    /**
     * Retorna um {@link JSONObject} com as informações do {@link Armazem}
     * @return {@link JSONObject} com as informações do {@link Armazem}
     */
    public JSONObject toString_exportJson() {
        JSONObject j = new JSONObject();
        j.put("capacidade", capacidade);
        j.put("stock", stock);
        return j;
    }

    /**
     *
     * @param o {@link Integer} a comparar
     * @return diferença entre {@link #stock stock} e {@link Integer} recebido
     */
    @Override
    public int compareTo(Double o) {
        if(stock>o)return 1;
        if(stock<0)return -1;
        return 0;
    }
}
