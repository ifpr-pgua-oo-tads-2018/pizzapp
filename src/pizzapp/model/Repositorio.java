package pizzapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Repositorio {

    private static String ARQ = "pizzas.bin";

    private ObservableList<Pizza> cadastro;
    private Pedido pedidoAtual = null;


    private static Repositorio instance = new Repositorio();


    private Repositorio(){
        cadastro = FXCollections.observableArrayList();
    }

    public static Repositorio getInstance(){
        return instance;
    }

    public void cadastra(Pizza p){
        cadastro.add(p);
    }


    public void abrePedido(){
        if(pedidoAtual == null){
            pedidoAtual = new Pedido();
        }

    }

    public double fechaPedido(){
        if(pedidoAtual != null){
            double total = pedidoAtual.getValor();
            pedidoAtual = null;

            return total;
        }
        return -1;
    }

    public void adicionaPizza(Pizza p){
        if(pedidoAtual != null){
            pedidoAtual.add(p);
        }
    }

    public ObservableList listaPedido(){
        return pedidoAtual.getPedido();
    }

    public double valorPedido(){
        return pedidoAtual.getValor();
    }

    public ObservableList listaCadastro(){
        return cadastro;
    }

    public void salva() throws IOException {

        File f = new File(ARQ);
        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(new ArrayList<Pizza>(cadastro));

        oos.close();
        fos.close();

    }

    public void carrega() throws IOException,ClassNotFoundException{

        File f = new File(ARQ);
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);


        ArrayList pizzas = (ArrayList) ois.readObject();

        cadastro.addAll(pizzas);


        ois.close();
        fis.close();

    }





}
