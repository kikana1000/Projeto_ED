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
package tipoLocal;
import com.github.javafaker.Faker;
import main.Cliente;
import main.Vendedor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import resources.classes.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
/**
 * Classe {@link Mercado Mercado} que extende {@link Local Local}
 */
public class Mercado extends Local {
    /**
     * {@link LinkedQueue Queue} de {@link Cliente clientes}
     */
    private final LinkedQueue<Cliente> clientes;
    /**
     * {@link #clientes Clientes maximos} que podem ser adicionados {@link Math#random() aleatoriamente} em um {@link Mercado Mercado}
     */
    public static final int MAX_CLIENTS= 8;
    /**
     * {@link #clientes Clientes minimos} que podem ser adicionados {@link Math#random() aleatoriamente} em um {@link Mercado Mercado}
     */
    public static final int MIN_CLIENTS=2;

    /**
     * Constructor para o {@link Mercado Mercado} que recebe o {@link #getNome() Nome}
     * e inicializa a {@link LinkedQueue Queue} de {@link Cliente clientes}
     * @param nome {@link #getNome() Nome} do {@link Mercado Mercado}
     */
    public Mercado(String nome) {
        super(nome,"Mercado");
        this.clientes = new LinkedQueue<>();
    }

    /**
     * Constructor para o {@link Mercado Mercado} que inicializa com um {@link #getNome() Nome} {@link Faker aleatorio  }
     *  a {@link LinkedQueue Queue} de {@link Cliente clientes} {@link Faker aleatorios}
     */
    public Mercado(){
        super((new Faker(new Locale("pt")).company()).name(),"Mercado");
        this.clientes = new LinkedQueue<>();
        addrandomClientes();
    }
    /**
     * Retorna a  {@link LinkedQueue Queue} de {@link Cliente clientes}
     * @return  {@link LinkedQueue Queue} de {@link Cliente clientes}
     */
    public LinkedQueue<Cliente> getClientes() {
        return clientes;
    }

    /**
     * {@link LinkedQueue#enqueue(Object) Adiciona} um {@link Cliente cliente} a {@link #clientes Queue}
     * @param newCliente Novo {@link Cliente cliente}
     */
    public void addCliente(Cliente newCliente) {
        clientes.enqueue(newCliente);
    }

    /**
     * {@link LinkedQueue#enqueue(Object) Adiciona} um novo {@link Cliente cliente} de acordo com os parametros a {@link #clientes Queue}
     * @param nome {@link Cliente#getNome() Nome} do novo {@link Cliente cliente}
     * @param valor {@link Cliente#getValor() Valor} do novo {@link Cliente cliente}
     */
    public void addCliente(String nome, double valor) {
        clientes.enqueue(new Cliente("nome",valor));
    }
    /**
     * {@link LinkedQueue#enqueue(Object) Adiciona} um novo {@link Cliente cliente} {@link Faker aleatorio} a {@link #clientes Queue}
     */
    public void addCliente() {
        clientes.enqueue(new Cliente());
    }
    /**
     * {@link LinkedQueue#enqueue(Object) Adiciona} novos {@link Cliente cliente} {@link Faker aleatoriamente} a {@link #clientes Queue}
     */
    public void addrandomClientes(){
        int nrclientes=(int) ((Math.random() * (MAX_CLIENTS- MIN_CLIENTS)) + MIN_CLIENTS);
        for(int i=0;i<nrclientes;i++){
            clientes.enqueue(new Cliente());
        }
    }

    /**
     * Satisfaz os {@link #clientes clientes} com a mercadoria
     * @param mercadoria   {@link Double} da mercadoria
     * @return {@link Boolean#TRUE true} se foram satisfeitos todos os {@link #clientes clientes}, {@link Boolean#FALSE false} caso contrario
     */
    public Double satisfazerClientes(double mercadoria){
        while(mercadoria>0&&!clientes.isEmpty()){
            Cliente cliente=clientes.dequeue();
            if(cliente.getValor()<=mercadoria){
                mercadoria= mercadoria-cliente.getValor();
            }else{
                cliente.setValor(cliente.getValor()-mercadoria);
                clientes.enqueue(cliente);
                mercadoria=0;
            }
        }
        return mercadoria;
    }
    /**
     * Retorna o {@link Cliente#getValor() valor} de todos os {@link Cliente clientes} na {@link #clientes Queue}
     * @return {@link Integer Integer} com o {@link Cliente#getValor() valor} de todos os {@link Cliente clientes} na  {@link #clientes Queue}
     */
    public int valorclients(){
        Iterator<Cliente> iter=this.clientes.iterator();
        int valtotal=0;
        while(iter.hasNext()){
        valtotal+= iter.next().getValor();
        }
        return valtotal;
    }

    /**
     * toString de todos os {@link Cliente clientes} na {@link #clientes Queue} ordenados por ordem de entrada
     * @return String de todos os {@link Cliente clientes} na {@link #clientes Queue} ordenados por ordem de entrada
     */
    public String clientstoString() {
        String temp="Clientes=\n";
        for (Cliente cliente : this.clientes) {
            temp += "\t" + cliente.toString();
        }
        return temp;
    }
    /**
     * toString de todos os {@link Cliente clientes} na {@link #clientes Queue} ordenados por {@link Cliente#getValor()}
     * @return String de todos os {@link Cliente clientes} na {@link #clientes Queue} ordenados por {@link Cliente#getValor()}
     */
    public String clientsbyvaluetoString() {
        String temp="Clientes=";
        ArrayOrderedList<Cliente> ordered= new ArrayOrderedList<>();
        for (Cliente cliente : this.clientes) {
            ordered.add(cliente);
        }
        for (Cliente cliente : ordered) {
            temp += cliente.toString();
        }
        return temp;
    }

    /**
     * Devolve um {@link JSONArray JSONArray} dos {@link Cliente clientes} do {@link Mercado Mercado}
     * @return  {@link JSONArray JSONArray} dos {@link Cliente clientes} dos {@link Mercado Mercados}
     */
    public JSONArray exportClientes() {
        JSONArray jsonClientesTMP = new JSONArray();
        Iterator clienteIterator = clientes.iterator();
        while (clienteIterator.hasNext()){
            jsonClientesTMP.add(((Cliente) clienteIterator.next()).getValor());
        }
        return jsonClientesTMP;
    }
    /**
     * {@link #export(String) Exporta} {@link Mercado Mercado} para um ficheiro .json
     * @param path  {@link String String} com o caminho do ficheiro
     * @throws IOException se nao escrever o ficheiro
     */
    public void export(String path) throws IOException {
        path=path.concat(".json");
        JSONObject mercadoJSON= new JSONObject();
        JSONArray clientesJSON= new JSONArray();
        mercadoJSON.put("nome",this.getNome());
        mercadoJSON.put("tipo",this.getTipo());
        for (Cliente temp:clientes){
            JSONObject client = new JSONObject();
            client.put("id",temp.getId());
            client.put("nome",temp.getNome());
            client.put("valor",temp.getValor());
            clientesJSON.add(client);
        }
        mercadoJSON.put("clientes",clientesJSON);
        FileWriter file = new FileWriter(path, false);
        file.write( mercadoJSON.toString());
        file.close();
    }
    /**
     * toString da informação do {@link Local local}
     * formato:"{@link #getTipo() <tipo>} "{@link #getTipo() <nome>}":\n\t{@link #clientstoString() <clientes>}"
     * @return {@link String String} com informação do {@link Vendedor Vendedor}
     */
    @Override
    public String toString() {
        return getTipo() + " "+'\u0022' +getNome()
                +'\u0022'+":"+
                "\n\t" + clientstoString() ;
    }
}
