package pizzapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Pedido {

    private ObservableList<Pizza> pedido;

    public Pedido() {
        pedido = FXCollections.observableArrayList();
    }

    public void add(Pizza p){
        pedido.add(p);
    }

    public double getValor(){
        double total=0;

        for(Pizza p:pedido){
            total += p.getValor();
        }

        return total;
    }

    public ObservableList getPedido(){
        return pedido;
    }
}
