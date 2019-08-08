package pizzapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Pizzaria {

    private static String ARQ = "pizzas.bin";

    private ObservableList<Pizza> cadastro;
    private ObservableList<Cliente> clientes;

    private Pedido pedidoAtual = null;


    private static Pizzaria instance = new Pizzaria();


    private Pizzaria(){
        cadastro = FXCollections.observableArrayList();
        clientes = FXCollections.observableArrayList();
    }

    public static Pizzaria getInstance(){
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

    public void cadastraCliente(Cliente c){
        try{
            Connection con = FabricaConexao.getConnection();

            PreparedStatement stm = con.prepareStatement("INSERT INTO CLIENTES(NOME,TELEFONE,ANO_NASCIMENTO) VALUES (?,?,?)");

            stm.setString(1,c.getNome());
            stm.setString(2,c.getTelefone());
            stm.setInt(3,c.getAnoNascimento());

            stm.executeUpdate();

            stm.close();
            con.close();


            clientes.add(c);

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

    public ObservableList buscaPizza(String texto){
        cadastro.clear();

        try{

            Connection con = FabricaConexao.getConnection();

            PreparedStatement stm = con.prepareStatement("SELECT * FROM PIZZAS where SABOR like ?");

            stm.setString(1,"%"+texto+"%");

            ResultSet res = stm.executeQuery();

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

    public ObservableList buscaCliente(String texto){
        clientes.clear();

        try{

            Connection con = FabricaConexao.getConnection();

            PreparedStatement stm = con.prepareStatement("SELECT * FROM CLIENTES where NOME like ?");

            stm.setString(1,"%"+texto+"%");

            ResultSet res = stm.executeQuery();

            while(res.next()){
                int id = res.getInt("ID");
                String nome = res.getString("NOME");
                String telefone = res.getString("NOME");
                int anoNascimento = res.getInt("ANO_NASCIMENTO");

                Cliente c = new Cliente(id,nome,telefone,anoNascimento);

                clientes.add(c);
            }

            res.close();
            stm.close();
            con.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return clientes;
    }



    public ObservableList listaClientes(){
        clientes.clear();

        try{

            Connection con = FabricaConexao.getConnection();

            Statement stm = con.createStatement();

            ResultSet res = stm.executeQuery("SELECT * FROM CLIENTES");

            while(res.next()){
                int id = res.getInt("ID");
                String nome = res.getString("NOME");
                String telefone = res.getString("TELEFONE");
                int anoNascimento =res.getInt("ANO_NASCIMENTO");

                Cliente c = new Cliente(id,nome,telefone,anoNascimento);

                clientes.add(c);
            }

            res.close();
            stm.close();
            con.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return clientes;
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
