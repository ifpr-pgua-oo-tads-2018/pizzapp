package pizzapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
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
        try{
            Connection con = FabricaConexao.getConnection();

            PreparedStatement stm = con.prepareStatement("INSERT INTO PIZZAS(SABOR,VALOR) VALUES (?,?)");

            stm.setString(1,p.getNome());
            stm.setDouble(2,p.getValor());

            stm.executeUpdate();

            stm.close();
            con.close();

            cadastro.add(p);


        }catch (SQLException e){
            e.printStackTrace();
        }


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

        cadastro.clear();

        try{

            Connection con = FabricaConexao.getConnection();

            Statement stm = con.createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM PIZZAS");

            while(res.next()){
                int id = res.getInt("ID");
                String sabor = res.getString("SABOR");
                Double valor = res.getDouble("VALOR");

                Pizza p = new Pizza(id,sabor,valor);

                cadastro.add(p);
            }

            res.close();
            stm.close();
            con.close();



        }catch (SQLException e){
            e.printStackTrace();
        }

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
