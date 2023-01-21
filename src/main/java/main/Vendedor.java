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
import com.github.javafaker.Faker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import resources.classes.*;
import tipoLocal.Armazem;
import tipoLocal.Mercado;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

/**
 * Classe de {@link Vendedor Vendedor}
 */
public class Vendedor implements Comparable<String>{

    /**
     * {@link #nome Nome} e {@link #id id} do {@link Vendedor Vendedor}
     */
    private String nome, id;

    /**
     * {@link #capacidade Capacidade Maxima} do {@link Vendedor Vendedor}
     */
    private int capacidade;

    /**
     * {@link ArrayUnorderedList Array} de {@link String String} que contém
     * os {@link Mercado#getNome() nome} dos {@link Mercado mercados} que visita
     */
    private ArrayUnorderedList<String> listmercado;

    /**
     * Constructor de {@link Vendedor Vendedor} que recebe o {@link #nome Nome},
     * a {@link #capacidade capacidade maxima} e o {@link #id id} do {@link Vendedor Vendedor}
     * @param nome {@link String String} com o {@link #nome Nome}
     * @param capacidade {@link Integer Integer} com a {@link #capacidade capacidade maxima}
     * @param id {@link String String} com o {@link #id id}
     */
    public Vendedor(String nome, int capacidade,String id) {
        Faker random= new Faker(new Locale("pt"));
        this.nome = nome;
        this.id = id;
        this.capacidade = capacidade;
        this.listmercado = new ArrayUnorderedList<String>();
    }

    /**
     * Constructor de {@link Vendedor Vendedor} que recebe o {@link #nome Nome},
     * a {@link #capacidade capacidade maxima} e gera um {@link #id id} {@link Faker aleatorio} para {@link Vendedor Vendedor}
     * @param nome {@link String String} com o {@link #nome Nome}
     * @param capacidade {@link Integer Integer} com a {@link #capacidade capacidade maxima}
     */
    public Vendedor(String nome, int capacidade) {
        Faker random= new Faker(new Locale("pt"));
        this.nome = nome;
        this.id = random.idNumber().valid();
        this.capacidade = capacidade;
        this.listmercado = new ArrayUnorderedList<String>();
    }

    /**
     * Getter para o {@link #nome Nome}
     * @return {@link String String} com o {@link #nome Nome}
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter para o {@link #nome Nome}
     * @param nome {@link String String} com o {@link #nome Nome}
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * toString de todos os {@link Mercado Mercados} que o {@link Vendedor Vendedor} visita
     * @return  {@link String String} com os {@link Mercado#getNome() nomes} dos {@link Mercado Mercados} que o {@link Vendedor Vendedor} visita
     */
    public String mercadostoString(){
        String temp = "";
        int count = 1;
        for (int i = 0; i <getListmercado().size(); i++) {
            temp+= count++ + "." + getListmercado().get(i)+"\n";
        }
        return temp;
    }

    /**
     * Getter para o {@link #id id}
     * @return {@link String String} com o {@link #id id}
     */
    public String getId() {
        return id;
    }

    /**
     * Getter para a {@link #capacidade capacidade maxima}
     * @return {@link Integer Integer} com a {@link #capacidade capacidade maxima}
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Setter para a {@link #capacidade capacidade maxima}
     * @param capacidade {@link Integer Integer} com a nova {@link #capacidade capacidade maxima}
     */
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    /**
     * {@link #export(String) Exporta} {@link Vendedor Vendedor} para um ficheiro .json
     * @param path  {@link String String} com o caminho do ficheiro
     * @throws IOException se nao escrever o ficheiro
     */
    public void export(String path) throws IOException {
        path=path.concat(".json");
        JSONObject vendedorJSON= new JSONObject();
        vendedorJSON.put("nome",this.nome);
        vendedorJSON.put("id",this.id);
        vendedorJSON.put("capacidade",this.capacidade);
        JSONArray exportMercados = exportMercados();
        vendedorJSON.put("mercados",exportMercados);
        FileWriter file = new FileWriter(path, false);
        file.write(vendedorJSON.toString());
        file.close();
    }

    /**
     * Devolve um {@link JSONArray JSONArray} dos {@link Mercado#getNome() nomes}
     * dos {@link Mercado Mercados} associados ao {@link Vendedor Vendedor}
     * @return  {@link JSONArray JSONArray} dos {@link Mercado#getNome() nomes} dos {@link Mercado Mercados}
     */
    public JSONArray exportMercados() {
        JSONArray exportMercados = new JSONArray();
        Iterator t = listmercado.iterator();
        while (t.hasNext()) {
            exportMercados.add(t.next());
        }
        return exportMercados;
    }

    /**
     * {@link ArrayUnorderedList#addToRear(Object) Adicona} um {@link Mercado#getNome() nome} de um {@link Mercado Mercado}
     * ao {@link #listmercado a lista de mercados} que o {@link Vendedor vendedor} visita
     * @param newmercado {@link String String} de um {@link Mercado#getNome() nome} de um {@link Mercado Mercado}
     */
    public void addMercado(String newmercado) {
        listmercado.addToRear(newmercado);
    }

    /**
     * {@link ArrayUnorderedList#remove(Object) Remove} um {@link Mercado#getNome() nome} de um {@link Mercado Mercado}
     * presente na {@link #listmercado lista de mercados} que o {@link Vendedor vendedor} visita
     * @param delmercado {@link String String} de um {@link Mercado#getNome() nome} de um {@link Mercado Mercado} presente na {@link #listmercado lista de mercados}
     * @return {@link Boolean#TRUE true} se for removido,{@link Boolean#FALSE false} caso contrario
     */
    public boolean removeMercado(String delmercado) {
        if(listmercado.contains(delmercado)) {
            listmercado.remove(delmercado);
            return true;
        }
        return false;
    }

    /**
     * Retorna a {@link #listmercado lista dos mercados}
     * @return {@link #listmercado lista dos mercados}
     */
    public ArrayUnorderedList<String> getListmercado() {
        return listmercado;
    }

    /**
     * compara o nome com a String enviada nos parametros
     * @param compare nome a comparar
     * @return  {@link Integer 0} se {@link #nome nome} for igual à string enviada
     * @return  {@link Integer >0} se {@link #nome nome} for maior alfabeticamente à string enviada
     * @return  {@link Integer <0} se {@link #nome nome} for menor alfabeticamente à string enviada
     */
    @Override
    public int compareTo(String compare) {
        return nome.compareTo(compare);
    }

    /**
     * toString da informação do {@link Vendedor Vendedor}
     * formato:"Vendedor\n nome:{@link #nome <nome>}\nid:{@link #id <id>}\ncapacidade:{@link #capacidade <capacidade>\nMercados:\n{@link #listmercado <Mercados Associados>}"}
     * @return {@link String String} com informação do {@link Vendedor Vendedor}
     */
    @Override
    public String toString() {
        return "Vendedor\n" +
                "nome:" + nome + '\n' +
                "id:" + id + '\n' +
                "capacidade:" + capacidade + '\n' +
                "Mercados:\n" + listmercado.toString() +
                '\n';
    }
}
