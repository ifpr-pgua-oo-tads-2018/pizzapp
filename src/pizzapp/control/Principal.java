package pizzapp.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pizzapp.model.Pizza;
import pizzapp.model.Repositorio;

public class Principal {


    @FXML
    private TextField tfSabor;

    @FXML
    private TextField tfValor;


    @FXML
    private ListView<Pizza> ltvPizzas;

    @FXML
    private ListView<Pizza> ltvPedido;

    @FXML
    private Text txtValorTotal;

    @FXML
    private Button btAdiciona;

    @FXML
    private Button btFecha;


    public void initialize(){

        ltvPizzas.setItems(Repositorio.getInstance().listaCadastro());
        btAdiciona.setDisable(true);
        btFecha.setDisable(true);
        ltvPedido.setDisable(true);


    }

    @FXML
    public void cadastra(){
        String sabor = tfSabor.getText();
        double valor = Double.valueOf(tfValor.getText());

        Pizza p = new Pizza(sabor,valor);

        Repositorio.getInstance().cadastra(p);

    }

    @FXML
    public void abrePedido(){
        Repositorio.getInstance().abrePedido();

        ltvPedido.setItems(Repositorio.getInstance().listaPedido());
        btAdiciona.setDisable(false);
        ltvPedido.setDisable(false);
        btFecha.setDisable(false);
    }

    @FXML
    public void fechaPedido(){
        ltvPedido.setItems(null);

        double total = Repositorio.getInstance().fechaPedido();

        btAdiciona.setDisable(true);
        ltvPedido.setDisable(true);
        btFecha.setDisable(true);

        txtValorTotal.setText("Valor Total: R$ 0,00");
    }

    @FXML
    public void adicionaPizzaPedido(){
        Pizza p = ltvPizzas.getSelectionModel().getSelectedItem();
        if(p != null){
            Repositorio.getInstance().adicionaPizza(p);
        }

        txtValorTotal.setText("Valor Total: R$"+ Repositorio.getInstance().valorPedido());


    }


}
