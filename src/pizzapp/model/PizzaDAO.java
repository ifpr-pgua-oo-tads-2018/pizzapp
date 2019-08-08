package pizzapp.model;

import java.sql.SQLException;
import java.util.List;

public interface PizzaDAO {

    long insere(Pizza p) throws SQLException;
    boolean atualiza(Pizza p) throws SQLException;
    boolean deleta(Pizza p) throws SQLException;

    Pizza buscaId(int id) throws SQLException;
    List<Pizza> buscaAtributo(PizzaAtributoBusca atributo, Object valor) throws SQLException;
    List<Pizza> buscaTodos() throws SQLException;

}
