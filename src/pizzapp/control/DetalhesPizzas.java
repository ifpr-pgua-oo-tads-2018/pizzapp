package pizzapp.control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pizzapp.model.Pizza;
import pizzapp.model.Pizzaria;

import java.sql.SQLException;


public class DetalhesPizzas {

    @FXML
    private TableView<Pizza> tbPizzas;

    @FXML
    private TableColumn<Pizza,String> tcPizzaSabor;

    @FXML
    private TableColumn<Pizza,Double> tcPizzaValor;

    public void initialize(){

        tcPizzaSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        tcPizzaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tbPizzas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        try{
            tbPizzas.setItems(Pizzaria.getInstance().listaCadastro());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Problema ao buscar pizzas \n"+e.getMessage());
            alert.showAndWait();
        }
    }
}
