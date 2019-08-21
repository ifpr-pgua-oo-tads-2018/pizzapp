package pizzapp.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pizzapp.model.Pedido;
import pizzapp.model.Pizza;
import pizzapp.model.Pizzaria;

import java.sql.SQLException;
import java.time.LocalDateTime;


public class DetalhesPedidos {

    @FXML
    private TableView<Pedido> tbPedidos;

    @FXML
    private TableColumn<Pedido, LocalDateTime> tcPedidoData;

    @FXML
    private TableColumn<Pedido, String> tcPedidoCliente;

    @FXML
    private TableColumn<Pedido,Double> tcPedidoValor;

    @FXML
    private TableView<Pizza> tbDetalhesPedido;

    @FXML
    private TableColumn<Pizza,String> tcPizzaSabor;

    @FXML
    private TableColumn<Pizza,Double> tcPizzaValor;


    public void initialize(){

        tcPizzaSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        tcPizzaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tcPedidoData.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        tcPedidoCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tcPedidoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tbPedidos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        try{
            tbPedidos.setItems(Pizzaria.getInstance().listaPedidos());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Problema ao buscar pizzas \n"+e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void mostraDetalhe(){
        Pedido pedido = tbPedidos.getSelectionModel().getSelectedItem();



        if(pedido != null){
            tbDetalhesPedido.setItems(pedido.getPedido());
        }


    }


}
