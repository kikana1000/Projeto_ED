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
package main;


import com.github.javafaker.Faker;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import resources.classes.*;
import tipoLocal.*;

import java.io.*;
import java.util.*;

import static resources.ConsoleColors.*;
import static resources.Input.*;

/**
 * Classe da {@link Empresa}
 */
public class Empresa {
    private final Network<Local> locais;
    private final ArrayUnorderedList<Vendedor> vendedores;
    private final String nome;
    private static final String FILE = "C:\\Users\\andre\\Desktop\\ED\\ed-projeto-final\\export.json";

    /**
     * Construtor da {@link Empresa} que inicializa um {@link #nome Nome} {@link Faker aleatorio},
     * a {@link Network network} de {@link #locais locais} e a {@link ArrayUnorderedList lista} de {@link Vendedor vendedores}
     */
    public Empresa() {
        this.locais = new Network<>();
        this.nome = (new Faker(new Locale("pt")).company()).name();
        this.vendedores = new ArrayUnorderedList<>();
    }

    /**
     * Retorna {@link String String} com a {@link ArrayUnorderedList lista} de {@link Vendedor vendedores}
     *
     * @return {@link String String} com a {@link ArrayUnorderedList lista} de {@link Vendedor vendedores}
     */
    public String listarVendedores() {
        String temp = "";
        if (vendedores.isEmpty()) return temp;
        int index = 1;

        for (Vendedor vendedor : vendedores) {
            temp += (index++) + "-" + vendedor.getNome();
            if (index % 3 == 0) temp += "\n";
            else temp += "\t";
        }
        return temp;
    }

    /**
     * Retorna a {@link ArrayUnorderedList lista} de {@link Vendedor vendedores}
     *
     * @return {@link String String}  com a {@link ArrayUnorderedList lista} de {@link Vendedor vendedores}
     */
    public ArrayUnorderedList<Vendedor> getVendedores() {
        return vendedores;
    }

    /**
     * Retorna a {@link Network network} de {@link #locais locais}
     *
     * @return {@link Network network} de {@link #locais locais}
     */
    public Network<Local> getLocais() {
        return locais;
    }

    /**
     * Limpa o Output;
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Retorna {@link ArrayUnorderedList lista} de {@link Local locais} por {@link Local#getTipo() tipo}
     *
     * @param type {@link String String} com o {@link Local#getTipo() tipo} dos {@link Local locais}
     * @return {@link ArrayUnorderedList lista} de {@link Local locais} de acordo com o {@link Local#getTipo() tipo}
     */
    public ArrayUnorderedList<Local> localbyType(String type) {
        ArrayUnorderedList<Local> locals = new ArrayUnorderedList<>();
        for (Object vertex : locais.getVertices()) {
            if (type.equals("Mercado")) {
                if (vertex instanceof Mercado) {
                    locals.addToRear((Local) vertex);
                }
            } else {
                if (vertex instanceof Armazem) {
                    locals.addToRear((Local) vertex);
                }
            }
        }
        return locals;
    }

    /**
     * Return {@link Iterator} to the closest {@link Armazem} with {@link Armazem#getStock()}
     * @param currently {@link Local}
     * @return  {@link Iterator} to the closest {@link Armazem} with {@link Armazem#getStock()}
     */
    public Iterator closestArmazem(Local currently) {
        Double menor = Double.MAX_VALUE;
        ArrayUnorderedList<Integer> resultList =
                new ArrayUnorderedList<>();
        Iterator iter = resultList.iterator();
        if (!locais.isConnected() || locais.isEmpty()) return iter;
        Object[] o = this.locais.getVertices();
        for (Object vertex : o) {
            if (vertex instanceof Armazem && ((Armazem) vertex).getStock() > 0) {
                if (locais.shortestPathWeight(currently, (Armazem) vertex) < menor) {
                    iter = locais.iteratorShortestPath(currently, (Armazem) vertex);
                }
            }
        }
        return iter;

    }

    /**
     * Percorre a Menor Rota para o {@link Vendedor vendedor}
     * @param vendedor {@link Vendedor vendedor} que vai percorrer a sua rota
     */
    public String percRota(Vendedor vendedor) throws Exception {
        String output = "Rota do " + '\u0022' + vendedor.getNome() + '\u0022' + " :\n";
        Local index;
        Iterator iter;
        double mercadoria = 0;
        Object[] vertex = locais.getVertices();
        ArrayUnorderedList<String> mercadolist = vendedor.getListmercado();
        ArrayUnorderedList<Local> mercados_visit = new ArrayUnorderedList<Local>();
        LinkedQueue<Local> traversalQueue = new LinkedQueue<Local>();
        double weight = 0;

        if (!locais.isConnected())
            throw new IllegalArgumentException(RED + "Rede de Locais tem de ser conexa!" + RESET);
        if (locais.isEmpty()) throw new IllegalArgumentException(RED + "Rede de Locais está vazia!" + RESET);
        if (vendedor == null) throw new IllegalArgumentException(RED + "Vendedor nao pode ser nulo!" + RESET);
        if (vendedor.getListmercado().isEmpty())
            throw new IllegalArgumentException(RED + "Vendedor tem que ser mercados associados!" + RESET);

        //geting mercados in ArrayUnordered List
        for (int i = 0; i < mercadolist.size(); i++) {
            mercados_visit.addToFront((Mercado) vertex[getVerticebyName(this,mercadolist.get(i))]);
        }

        traversalQueue.enqueue(getSede());
        output+=getSede().getNome();
        while (!traversalQueue.isEmpty() && !mercados_visit.isEmpty()) {
            index = traversalQueue.dequeue();
            Local atual = mercados_visit.last();
            output += "-" + locais.shortestPathWeight(index, atual) + "KM->" + atual.getNome();
            weight += locais.shortestPathWeight(index, atual);
            iter = locais.iteratorShortestPath(index, atual);
            while (iter.hasNext()) {
                Local temp = (Local) iter.next();
                if(temp instanceof Mercado) {
                if (mercados_visit.contains(temp)) {
                    mercadoria=((Mercado) temp).satisfazerClientes(mercadoria);
                    if(((Mercado) temp).valorclients()==0){
                        mercados_visit.removeLast();
                    }
                    update_data(temp);
                        while (mercadoria==0) {
                            if (mercados_visit.isEmpty()) break;
                            Iterator iter2 = closestArmazem(temp);
                            if (!iter2.hasNext()) throw new Exception(RED + "Rota Concluida por falta de Stock em Armazem!\n" + RESET + output + "\nPeso Total:" + weight + "\n");
                            while (iter2.hasNext()) {
                                Local temparma= (Local) iter2.next();
                                if (temparma instanceof Armazem && ((Armazem) temparma).getStock() > 0) {
                                    mercadoria= ((Armazem) temparma).stockarVendedor(vendedor, mercadoria);
                                    System.out.println(mercadoria);
                                    update_data(temparma);
                                    traversalQueue.enqueue(temparma);
                                    break;
                                }
                            }
                        }

                    }else if(temp instanceof Armazem){
                        mercadoria=((Armazem) temp).stockarVendedor(vendedor,mercadoria);
                    }
                }
            }
            if (mercadolist.size() > 0) {
                traversalQueue.enqueue(atual);
            }
        }

        return GREEN + "Rota Concluida!" + RESET + "\n" + output+"\n"+"Peso Total:"+weight;
    }

    /**
     * Retorna a {@link Sede sede} da {@link #locais lista}
     * @return a {@link Sede sede} da {@link #locais lista}
     */
    public Local getSede(){
        if(locais.size()==0)return null;
        for(Object vertex:locais.getVertices()){
            if(vertex instanceof Sede){
                return (Local) vertex;
            }
        }
        return null;
    }

    /**
     * Lista o {@link Armazem Armazens} por {@link Armazem#getStock() Armazens}
     */
    public void listarArmazemPorStock() {
        ArrayOrderedList<Local> l = new ArrayOrderedList<Local>();
        for(Object temp:locais.getVertices()){
            if (temp instanceof Armazem) {
                l.add((Local) temp);
            }
        }
        System.out.println(l.toString());
    }

    /**
     * Ordena {@link #vendedores vendedores} por nome (Ordem alfabética)
     * @return {@link Vendedor network} de {@link #vendedores vendedores}
     */
    public ArrayOrderedList<Vendedor> sortyByName(){
        ArrayOrderedList<Vendedor> tmp = new ArrayOrderedList<Vendedor>();
        for (int i = 0; i < vendedores.size(); i++) {
            tmp.add(vendedores.get(i));
        }
        return tmp;
    }

    /**
     * {@link Network#addVertex(Object) Adiciona} local a {@link Network network} de {@link #locais locais}
     * @return {@link Boolean#TRUE True} se adicionou, {@link Boolean#FALSE False} caso contrario
     */
    public Boolean addLocal() {
        boolean bool = false;
        int menu;
        System.out.println(" Opção adicionar local!");
        System.out.println("Escolha o Tipo do local:");
        do {
            menu = readInt("1.Armazém\t2.Mercado");
            if (menu != 1 && menu != 2) {
                System.out.println(RED + "Escolha Invalida!" + RESET + "\nEscolha novamente o Tipo do local:");
            }
        } while (menu != 1 && menu != 2);
        String nome;
        do {
            System.out.print("Insira um nome para o ");
            if (menu == 1) {
                System.out.print("Armazem:");
            } else {
                System.out.print("Mercado:");
            }
            nome = readString("");
            if (nome.length() < 4) {
                System.out.println("Insira um nome com mais de 3 carateres!");
            }
        } while (nome.length() < 4);
        if (menu == 1) {
            String menu2;
            int stock;
            int capacidade;
            do {
                menu2 = readString("Deseja adicionar Aleatoriamente ou Manualmente a capacidade e stock?(Auto/Manual)");
                if (menu2.equals("Auto") || menu2.equals("auto")) {
                    locais.addVertex(new Armazem(nome));
                    bool = true;
                } else if (menu2.equals("Manual") || menu2.equals("manual")) {
                    do {
                        capacidade = readInt("Insira a capacidade(KG):");
                        stock = readInt("Insira o stock(KG):");
                        if (capacidade < stock || capacidade <= 0 || stock < 0) {
                            System.out.println("Valores invalidos!");
                        }
                    } while (capacidade < stock || capacidade <= 0 || stock < 0);
                    locais.addVertex(new Armazem(nome, capacidade, stock));
                    bool = true;
                }
                if (!menu2.equals("Auto") && !menu2.equals("Manual") && !menu2.equals("auto") && !menu2.equals("manual"))
                    System.out.println("Escolha Invalida!");
            } while (!menu2.equals("Auto") && !menu2.equals("Manual") && !menu2.equals("auto") && !menu2.equals("manual"));
        } else {
            String menu2;
            do {
                menu2 = readString("Deseja adicionar Clientes aleatoriamente?(Yes/No)");
                if (!menu2.equals("Yes") && !menu2.equals("No") && !menu2.equals("yes") && !menu2.equals("no"))
                    System.out.println("Escolha Invalida!");
            } while (!menu2.equals("Yes") && !menu2.equals("No") && !menu2.equals("yes") && !menu2.equals("no"));
            if (menu2.equals("Yes") || menu2.equals("yes")) {
                Mercado temp = new Mercado(nome);
                temp.addrandomClientes();
                locais.addVertex(temp);
                bool = true;
            } else {
                locais.addVertex(new Mercado(nome));
                bool = true;
            }
        }
        return bool;
    }

    /**
     * Retorna {@link String String} com a informação dos {@link #locais locais}
     *
     * @return {@link String String} com a informação dos {@link #locais locais}
     */
    public String locaistoString() {
        String temp = "";
        if (locais.size() > 0) {
            Object[] locais = this.locais.getVertices();
            for (int i = 0; i < locais.length; i++) {
                if (locais[i] instanceof Mercado)
                    temp += (i) + "." + '\u0022' + ((Mercado) locais[i]).getNome() + '\u0022' + "-Mercado" + ":" + ((Mercado) locais[i]).valorclients() + "\n";
                else if (locais[i] instanceof Armazem)
                    temp += (i) + "." + '\u0022' + ((Armazem) locais[i]).getNome() + '\u0022' + "-Armazem" + ":" + ((Armazem) locais[i]).getStock() + "/" + ((Armazem) locais[i]).getCapMax() + "\n";
                else temp += (i) + "." + '\u0022' + ((Sede) locais[i]).getNome() + '\u0022' + "-Sede" + "\n";
            }
        }
        return temp;
    }

    /**
     * Retorna {@link String String} com a informação dos {@link Mercado mercados} em {@link #locais locais}
     * @return {@link String String} com a informação dos {@link Mercado mercados} em {@link #locais locais}
     */
    public String mercadostoString() {
        String temp = "";
        int i = 0;
        if (locais.size() > 0) {
            Object[] locais = this.locais.getVertices();
            for (i = 0; i < locais.length; i++) {
                if (locais[i] instanceof Mercado)
                    temp += "\t" + (i + 1) + "." + '\u0022' + ((Mercado) locais[i]).getNome() + '\u0022' + "-Mercado" + ":" + ((Mercado) locais[i]).valorclients() + "\n";
            }
        }
        if (i == 0) temp += "\tLista Vazia";
        return temp;
    }

    /**
     * Menu da Gestão de {@link #locais locais}
     */
    public void gestaolocais() {
        int menu;
        int opc;
        do {
            System.out.println("Menu locais:");
            System.out.println(locaistoString());
            System.out.println("Escolha uma das seguintes opções!:");
            System.out.print("1 - Adicionar Local\t");
            System.out.println("2 - Editar Local");
            System.out.print("3 - Eliminar Local\t");
            System.out.println("4 - Fazer caminho entre dois Locais");
            System.out.print("5 - Listar caminhos\t");
            System.out.println("6 - Listar armazéns por Stock");
            System.out.println("0 - Voltar ao menu anterior!");
            menu = readInt();
            switch (menu) {
                case 1:
                    if (this.locais.size() > 0) {
                        addLocal();
                    } else this.locais.addVertex(new Sede(this.nome));
                    System.out.println(GREEN + "Adicionado com sucesso!" + RESET);

                    break;
                case 2:
                    opc = (readInt("Qual local quer alterar?") - 1);
                    if (locais.size() >= opc && opc > 0) {
                        Local escolhido = (Local) locais.getVertices()[opc];
                        if (escolhido instanceof Armazem) gestaoarmazem((Armazem) escolhido);
                        else if (escolhido instanceof Mercado) gestaomercado((Mercado) escolhido);
                        else if (escolhido instanceof Sede) {
                            System.out.println(RED + "A sede é impossivel de editar!" + RESET);
                        }
                    } else System.out.println("Local Não existente");
                    break;
                case 3:
                    opc = (readInt("Qual local quer apagar?") - 1);
                    if (locais.size() > opc && opc > 0) {
                        locais.removeVertex(opc);
                        System.out.println(GREEN + "Removido com sucesso!" + RESET);
                    } else System.out.println("Local Não existente");
                    break;
                case 4:
                    opc = (readInt("Onde começa o caminho?") - 1);
                    if (locais.size() > opc && opc > 0) {
                        int opc2 = (readInt("Onde acaba o caminho?") - 1);
                        if (locais.size() > opc2 && opc2 > 0) {
                            double val = readDouble("Quanto é a distancia do caminho(KM)?");
                            if (val > 0) {
                                locais.addEdge(opc, opc2, val);
                                System.out.println(GREEN + "Caminho feito com sucesso!" + RESET);
                            } else System.out.println(RED + "Distancia Invalida!" + RESET);
                        } else System.out.println(RED + "Local Não existente" + RESET);
                    } else System.out.println(RED + "Local Não existente" + RESET);
                    break;
                case 5:
                    System.out.println(locais.EdgetoString());
                    break;
                case 6:

                    listarArmazemPorStock();
                    break;
                case 0:
                    clearScreen();
                    System.out.println("Saiu do menu anterior!");
                    break;

                default:
                    System.out.println(RED + "Opção Invalida!" + RESET);
                    break;
            }
        } while (menu != 0);
    }

    /**
     * Menu da Gestão do {@link Armazem Armazem} recebido no parametro
     * @param local {@link Armazem Armazem} que sera gerido
     */
    public void gestaoarmazem(Armazem local) {
        int menu;
        String menu2;
        do {
            System.out.println("Armazem " + '\u0022' + local.getNome() + '\u0022' + ":");
            System.out.println(local.toString());
            System.out.println("Escolha uma das seguintes opções!:");
            System.out.print("1 - Adicionar Stock\t");
            System.out.println("2 - Editar Capacidade");
            System.out.print("3 - Editar Nome do Armazem\t");
            System.out.println("4 - Export");
            System.out.println("0 -  Voltar ao menu anterior!");
            menu = readInt();
            switch (menu) {
                case 1:
                    int stock;
                    menu2 = readString("Deseja adicionar stock Aleatoriamente ou Manualmente?(Auto/Manual)");
                    if (menu2.equals("Auto") || menu2.equals("auto")) {
                        stock = (int) ((Math.random() * (local.getCapMax() - local.getStock())) + local.getStock());
                        local.setStock(stock);
                    } else {
                        stock = readInt("Insira o stock(KG):");
                        if (local.getCapMax() > (stock + local.getStock()) || stock >= 0)
                            local.setStock(local.getStock() + stock);
                        if (local.getCapMax() < (stock + local.getStock()) || stock < 0)
                            System.out.println(RED + "Valores invalidos!" + RESET);

                    }
                    if (!menu2.equals("Auto") && !menu2.equals("Manual") && !menu2.equals("auto") && !menu2.equals("manual"))
                        System.out.println(RED + "Escolha Invalida!" + RESET);
                    break;
                case 2:
                    int cap = readInt("Insira a capacidade(KG):");
                    if (cap < local.getStock() || cap < 0) {
                        System.out.println(RED + "Valores invalidos!" + RESET);
                    } else if (cap >= local.getStock() || cap >= 0) {
                        local.setCapMax(cap);
                    }
                    break;
                case 3:
                    String nome = readString("Insira o novo nome(mais de 3 carateres):");
                    if (nome.length() <= 3) {
                        System.out.println(RED + "Nome tem de ter mais de 3 carateres!" + RESET);
                    } else {
                        local.setNome(nome);
                    }
                    break;
                case 4:

                    String path = readString("Insira o caminho do ficheiro onde deseja export:");
                    try {
                        local.export(path);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    clearScreen();
                    System.out.println("Saiu do menu anterior!");
                    break;
                default:
                    System.out.println(RED + "Opção Invalida!" + RESET);
                    break;
            }
        } while (menu != 0);
    }

    /**
     * Menu da Gestão do {@link Mercado Mercado} recebido no parametro
     *
     * @param local {@link Mercado Mercado}  que sera gerido
     */
    public void gestaomercado(Mercado local) {
        int menu;
        String menu2;
        int opc;
        do {
            System.out.println("\tMercado " + '\u0022' + local.getNome() + '\u0022' + ":");
            System.out.println(local.clientstoString());
            System.out.println("Escolha uma das seguintes opções!:");
            System.out.print("1 - Adicionar Cliente\t");
            System.out.println("2 - Editar Nome do mercado");
            System.out.println("3 - Export\t");
            System.out.println("0 - Voltar ao menu anterior!");
            menu = readInt();
            switch (menu) {
                case 1:
                    menu2 = readString("Deseja adicionar um cliente Aleatoriamente ou Manualmente?(Auto/Manual)");
                    if (menu2.equals("Auto") || menu2.equals("auto")) {
                        local.addCliente();
                    } else if (menu2.equals("Manual") || menu2.equals("manual")) {
                        menu2 = readString("Insira o nome (+3 carateres):");
                        if (menu2.length() > 3) {
                            double valor = readDouble("Insira a quantidade que o cliente deseja:");
                            if (valor > 0) {
                                local.addCliente(menu2, valor);
                            } else System.out.println(RED + "Insira um valor positivo!" + RESET);
                        } else System.out.println(RED + "Escolha um nome com mais de 3 carateres!" + RESET);
                    } else System.out.println(RED + "Escolha Invalida!" + RESET);
                    break;
                case 2:
                    menu2 = readString("Insira o novo nome:");
                    local.setNome(menu2);
                    break;
                case 3:
                    String path = readString("Insira o caminho do ficheiro onde deseja export:");
                    try {
                        local.export(path);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    clearScreen();
                    System.out.println("Saiu do menu anterior!");
                    break;
                default:
                    System.out.println(RED + "Opção Invalida!" + RESET);
                    break;
            }
        } while (menu != 0);
    }

    /**
     * Menu da Gestão do {@link Vendedor Vendedor} recebido no parametro
     *
     * @param vendedor {@link Vendedor Vendedor}   que sera gerido
     */
    public void gestaoVendedor(Vendedor vendedor) {
        int menu, opc;
        Object[] local = locais.getVertices();
        do {
            System.out.println("Menu Vendedor - " + '\u0022' + vendedor.getId() + '\u0022' + ":");
            System.out.print("\tNome:" + vendedor.getNome() + "\t");
            System.out.println("\tCapacidade: " + vendedor.getCapacidade());
            System.out.println("\tMercados" + vendedor.getListmercado().size());
            if (!vendedor.getListmercado().isEmpty()) {
                System.out.println("Mercados Associados: \n");
                System.out.println(vendedor.mercadostoString());
            }
            System.out.println("Escolha uma das seguintes opções!:");
            System.out.print("1 - Associar Mercado\t");
            System.out.println("2 - Listar Todos os Mercados Disponiveis");
            System.out.print("3 - Desassociar Mercado\t");
            System.out.println("4 - Editar Capacidade");
            System.out.print("5 - Exportar Vendedor\t");
            System.out.println("0 -  Voltar ao menu anterior!");
            menu = readInt();
            switch (menu) {
                case 1:
                    System.out.println("Escolha um dos Mercados!");
                    mercadostoString();
                    opc = readInt(mercadostoString());
                    if (opc > 0 && opc <= locais.size()) {
                        System.out.println("Nome:");
                        vendedor.addMercado(((Mercado) local[opc - 1]).getNome());
                    } else System.out.println(RED + "Não foi escolhido um mercado da lista" + RESET);

                    break;
                case 2:
                    System.out.println("Mercados\n");
                    mercadostoString();
                    break;
                case 3:
                    if (!vendedor.getListmercado().isEmpty()) {
                        System.out.println("Escolha um dos Mercados para desassociar:");
                        opc = readInt();
                        if (opc > 0 && opc <= vendedor.getListmercado().size()) {
                            vendedor.removeMercado(vendedor.getListmercado().get(opc - 1));
                        } else {
                            System.out.println(RED + "Não foi escolhido um mercado da lista" + RESET);
                        }
                    } else {
                        System.out.println(RED + "Lista de Mercados associados vazia!" + RESET);
                    }
                    break;
                case 4:
                    vendedor.setCapacidade(readInt("Selecionar Capacidade: ", 0, Integer.MAX_VALUE, RED + "Valor Invalido!" + RESET));
                    break;
                case 5:
                    String path = readString("Insira o caminho do ficheiro onde deseja export:");
                    try {
                        vendedor.export(path);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    clearScreen();
                    System.out.println("Saiu do menu anterior!");
                    break;
                default:
                    System.out.println(RED + "Opção Invalida!" + RESET);
                    break;
            }
        } while (menu != 0);
    }

    /**
     * Adicionar {@link Vendedor Vendedor} com os parametros a {@link #vendedores lista }
     *
     * @param nome       {@link String String} com o nome do {@link Vendedor Vendedor}
     * @param capacidade {@link Integer Integer} com a capacidade maxima do {@link Vendedor Vendedor}
     */
    public void addVendedor(String nome, int capacidade) {
        vendedores.addToRear(new Vendedor(nome, capacidade));
    }

    /**
     * Menu da Gestão dos {@link Vendedor Vendedores}
     */
    public void gestaoVendedores() {
        int menu;
        int opc;
        do {
            System.out.println("Menu Vendedores:");
            System.out.println(listarVendedores());
            System.out.println("Escolha uma das seguintes opções!:");
            System.out.print("1 - Adicionar Vendedor\t");
            System.out.println("2 - Editar Vendedor");
            System.out.print("3 - Eliminar Vendedor\t");
            System.out.println("4 - Fazer Rota");
            System.out.print("5 - Listar Vendedor por Ordem alfabetica\t");
            System.out.println("0 - Voltar ao menu anterior!");
            menu = readInt();
            switch (menu) {
                case 1:
                    System.out.println("Adicionar Vendedor:");
                    String nome = readString("Insira o Nome:");
                    int capacidade = readInt("Capacidade Maxima(em Kg):");
                    addVendedor(nome, capacidade);
                    break;
                case 2:
                    if (!vendedores.isEmpty()) {
                        opc = readInt("Qual vendedor deseja editar?");
                        gestaoVendedor(vendedores.get(opc - 1));
                    } else System.out.println(RED + "Lista de Vendedores Vazia!" + RESET);
                    break;
                case 3:
                    if (!vendedores.isEmpty()) {
                        opc = readInt("Qual vendedor deseja eliminar?");
                        vendedores.remove(vendedores.get(opc - 1));
                    } else System.out.println(RED + "Lista de Vendedores Vazia!" + RESET);
                    break;
                case 4:
                    if (!vendedores.isEmpty()) {
                        opc = readInt("Para qual vendedor deseja fazer a rota?");
                        try {
                            System.out.println(percRota(vendedores.get(opc - 1)));
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }
                    } else System.out.println(RED + "Lista de Vendedores Vazia!" + RESET);
                    break;
                case 5:
                    System.out.println("Listagem Vendedores (ordenandos por Ordem alfabetica):");
                    for(Vendedor vendedor:sortyByName()) {
                        System.out.println("\t\t"+vendedor.getNome());
                    }
                case 0:
                    clearScreen();
                    System.out.println("Saiu do menu anterior!");
                    break;
                default:
                    System.out.println(RED + "Opção Invalida!" + RESET);
                    break;
            }
        } while (menu != 0);
    }

    /**
     * {@link #exportJson(String) String) Exporta} a informação toda relativa a {@link Empresa Empresa} para um ficheiro .json
     * @throws IOException se não escrever o ficheiro
     */
    public void exportJson(String path) throws IOException {
        path=path.concat(".json");
        JSONObject export = new JSONObject();
        JSONArray vendedoresJson = new JSONArray();
        JSONArray locaisJson = new JSONArray();
        JSONArray caminhosJSON = new JSONArray();
        //Vendedores To Json

        for (Vendedor v : vendedores) {
            JSONObject vendedoresJsonTMP = new JSONObject();
            vendedoresJsonTMP.put("id", v.getId());
            vendedoresJsonTMP.put("nome", v.getNome());
            vendedoresJsonTMP.put("capacidade", v.getCapacidade());
            vendedoresJsonTMP.put("mercados_a_visitar", v.exportMercados());
            vendedoresJson.add(vendedoresJsonTMP);
        }

        //Locais
        Object[] tmp = locais.getVertices();
        Local[] j = new Local[tmp.length];
        for (int i = 0; i < j.length; i++) {
            j[i] = (Local) tmp[i];
        }
        //Locais To Json
        if (j.length != 0) {
            for (Local local : j) {
                JSONObject locaisJSONTMP = new JSONObject();
                locaisJSONTMP.put("nome", local.getNome());
                switch (local.getTipo()) {
                    case "Armazém":
                        locaisJSONTMP.put("tipo", local.getTipo());
                        Armazem a = (Armazem) local;
                        locaisJSONTMP.put("stock", a.getStock());
                        locaisJSONTMP.put("capacidade", a.getCapMax());
                        break;
                    case "Mercado":
                        locaisJSONTMP.put("tipo", local.getTipo());
                        Mercado m = (Mercado) local;
                        locaisJSONTMP.put("clientes", m.exportClientes());
                        break;
                    case "Sede":
                        locaisJSONTMP.put("tipo", local.getTipo());
                }
                locaisJson.add(locaisJSONTMP);
            }


            Scanner scanner = new Scanner(locais.EdgetoString());
            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            while (scanner.hasNextInt()) {
                JSONObject edges = new JSONObject();
                int de = scanner.nextInt();
                scanner.next("para");
                int para = scanner.nextInt();
                scanner.skip("\t");
                String valor = scanner.next();
                edges.put("de", j[de].getNome());
                edges.put("para", j[para].getNome());
                edges.put("distancia", Double.parseDouble(valor));
                caminhosJSON.add(edges);
                scanner.skip("\n");
            }

        }
        export.put("vendedores", vendedoresJson);
        export.put("locais", locaisJson);
        export.put("caminhos", caminhosJSON);
        FileWriter file = new FileWriter(path, false);
        file.write(export.toString());
        file.close();
    }

    /**
     * {@link #importJson( String)}  Importa} a informação toda relativa a {@link Empresa Empresa} de um ficheiro ".json"
     * @param path    caminho do ficheiro ".json"
     * @throws IOException se nao ler o ficheiro ".json"
     */
    public static Empresa importJson(String path) throws IOException {
        path=path.concat(".json");
        Empresa empresa_temp = new Empresa();
        FileReader file = new FileReader(path);
        JSONParser parser = new JSONParser();
        JSONObject empresajson;
        try {
            empresajson = (JSONObject) parser.parse(file);
        } catch (ParseException ex) {
            throw new FileNotFoundException("Ficheiro Invalido!");
        }

        JSONArray vendedoresJSON = (JSONArray) empresajson.get("vendedores");
        JSONArray locaisJSON = (JSONArray) empresajson.get("locais");
        JSONArray caminhosJSON = (JSONArray) empresajson.get("caminhos");
        Vendedor seller_temp;
        JSONObject objecttemp;
        for (int i = 0; i < vendedoresJSON.size(); i++) {
            objecttemp = (JSONObject) vendedoresJSON.get(i);
            String id_temp = (String) objecttemp.get("id");
            String nome_temp = (String) objecttemp.get("nome");
            long cap_temp = (long) objecttemp.get("capacidade");
            JSONArray mercadosVisitArray = (JSONArray) objecttemp.get("mercados_a_visitar");
            seller_temp = new Vendedor(nome_temp, (int) cap_temp, id_temp);
            for (int j = 0; j < mercadosVisitArray.size(); j++) {
                seller_temp.addMercado((String) mercadosVisitArray.get(j));
            }
            empresa_temp.vendedores.addToRear(seller_temp);
        }
        Local local_temp;
        for (int i = 0; i < locaisJSON.size(); i++) {
            objecttemp = (JSONObject) locaisJSON.get(i);
            long cap_temp;
            double stock_temp;
            String nome_temp = (String) objecttemp.get("nome");
            String tipo_temp = (String) objecttemp.get("tipo");
            if (tipo_temp.equals("Mercado")) {
                local_temp = new Mercado(nome_temp);
                JSONArray clientsJSON = (JSONArray) objecttemp.get("clientes");
                for (int j = 0; j < clientsJSON.size(); j++) {
                    Cliente cliente_temp = new Cliente((int) ((double) clientsJSON.get(j)));
                    ((Mercado) local_temp).addCliente(cliente_temp);
                }
            } else if (tipo_temp.equals("Armazém")) {
                cap_temp = (long) objecttemp.get("capacidade");
                stock_temp = (double) objecttemp.get("stock");
                local_temp = new Armazem(nome_temp, (int) cap_temp, (int) stock_temp);
            } else local_temp = new Sede(nome_temp);
            empresa_temp.locais.addVertex(local_temp);
        }
        for (int i = 0; i < caminhosJSON.size(); i++) {
            objecttemp = (JSONObject) caminhosJSON.get(i);
            String de_temp = (String) objecttemp.get("de");
            String para_temp = (String) objecttemp.get("para");
            double distancia_temp = (double) objecttemp.get("distancia");
            empresa_temp.locais.addEdge(getVerticebyName(empresa_temp, de_temp), getVerticebyName(empresa_temp, para_temp), distancia_temp);
        }
        return empresa_temp;
    }

    /**
     * Retorna um index do {@link Local } da {@link #locais Lista} de acordo com o seu {@link Local#getNome()}
     *
     * @param empresa {@link Empresa} que vai buscar o {@link Local}
     * @param str     {@link String} do {@link Local#getNome()}
     * @return {@link Local} da {@link #locais Lista} de acordo com o seu {@link Local#getNome()}
     */
    public static int getVerticebyName(Empresa empresa, String str) {
        Object[] newobjtmp = empresa.locais.getVertices();
        for (int j = 0; j < newobjtmp.length; j++) {
            if (((Local) newobjtmp[j]).equals(new Local(str))) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Atualiza a data de um {@link Local local} na {@link #locais Network}
     * @param update {@link Local local} que irá atualizar
     */
    public void update_data(Local update){
        LinkedQueue<Local> adj= new LinkedQueue<>();
        LinkedQueue<Double> weight= new LinkedQueue<>();
        int count=0;
        for(Object vertex: locais.getVertices()){
            if(!vertex.equals(update)){
                if(locais.shortestPathLength(update,(Local) vertex)==1){
                    adj.enqueue((Local) vertex);
                    count++;
                    weight.enqueue(locais.shortestPathWeight(update, (Local) vertex));
                }
            }
        }
        locais.removeVertex(update);
        locais.addVertex(update);
        while(!(count==0)){
            count--;
            locais.addEdge(update,adj.dequeue(),weight.dequeue());
        }
    }

    public static Class getClassLocal(String obj) {
        switch (obj) {
            case "Armazém":
                return Armazem.class;
            case "Mercado":
                return Mercado.class;
            case "Sede":
                return Sede.class;
        }
        return null;
    }

    /**
     * Adiciona Informação a empresa para testar
     */
    private void testValues() {
        locais.addVertex(new Sede("Sede Norte"));
        Mercado m1 = new Mercado("Mercado 1");
        Mercado m2 = new Mercado("Mercado 2");
        Mercado m3 = new Mercado("Mercado 3");
        Mercado m4 = new Mercado("Mercado 4");
        addVendedor("Tomé", 400);
        addVendedor("Joana", 500);
        addVendedor("Zé Primo", 300);
        addVendedor("Américo", 200);
        m1.addCliente();
        m1.addCliente();
        m1.addCliente();
        m2.addCliente();
        m2.addCliente();
        m2.addCliente();
        m3.addCliente();
        m3.addCliente();
        m3.addCliente();
        m4.addCliente();
        m4.addCliente();
        m4.addCliente();
        locais.addVertex(new Armazem("Armazem Retunda"));
        locais.addVertex(new Armazem("Armazem Nacional"));
        locais.addVertex(new Armazem("Armazem 3"));
        locais.addVertex(m1);
        locais.addVertex(m2);
        locais.addVertex(m3);
        locais.addVertex(m4);
        locais.addEdge(0, 1, 30);
        locais.addEdge(0, 2, 15);
        locais.addEdge(0, 3, 20);
        locais.addEdge(2, 4, 30);
        locais.addEdge(3, 5, 10);
        locais.addEdge(4, 7, 15);
        locais.addEdge(1, 6, 35);
        vendedores.get(2).addMercado(m3.getNome());
        vendedores.get(0).addMercado(m1.getNome());
        vendedores.get(0).addMercado(m2.getNome());
        vendedores.get(1).addMercado(m3.getNome());
        vendedores.get(1).addMercado(m2.getNome());
        vendedores.get(1).addMercado(m4.getNome());
        vendedores.get(1).addMercado(m1.getNome());
    }

    /**
     * Menu Principal
     */
    public static void main(String[] args) {
        Empresa e = new Empresa();
        int menu;

        String path;
        do {
            System.out.println("Menu Principal:");
            System.out.println("Escolha uma das seguintes opções!:");
            System.out.print("1 - Menu Vendedor\t");
            System.out.println("2 - Menu Locais");
            System.out.print("3 - Export\t\t\t");
            System.out.println("4 - Import");
            System.out.println("0 - Sair Programa");
            menu = readInt();
            switch (menu) {
                case 1:
                    e.gestaoVendedores();
                    break;
                case 2:
                    e.gestaolocais();
                    break;
                case 3:
                    path = readString("Insira o caminho do ficheiro em que quer guarda a informação:");
                    try {
                        e.exportJson(path);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    path = readString("Insira o caminho do ficheiro em que buscar a informação:");
                    try {
                        e=importJson(path);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 0:
                    clearScreen();
                    if (!readString("Deseja sair do programa?(Sim/Nao)").equals("Sim")) menu = 1;
                    break;
                default:
                    System.out.println("Opção Invalida!");
                    break;
            }
        } while (menu != 0);
    }
}