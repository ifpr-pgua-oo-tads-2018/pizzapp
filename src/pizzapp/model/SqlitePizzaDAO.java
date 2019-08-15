package pizzapp.model;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SqlitePizzaDAO implements PizzaDAO {

    private QueryRunner dbAccess = new QueryRunner();

    @Override
    public long insere(Pizza p) throws SQLException {



        /*Connection connection = FabricaConexao.getConnection();

        long id = dbAccess.insert(connection,"INSERT INTO Pizzas(sabor,valor) VALUES (?,?)",
                            new ScalarHandler<BigDecimal>(),p.getSabor(),p.getValor()).longValue();

        connection.close();*/
        throw new SQLException("SQL Inválido...");
        //return id;
    }

    @Override
    public boolean atualiza(Pizza p) throws SQLException {
        Connection connection = FabricaConexao.getConnection();
        dbAccess.update(connection,"UPDATE Pizzas SET sabor=?, valor=? WHERE id=?",
                p.getSabor(),p.getValor(),p.getId());
        connection.close();
        return true;
    }

    @Override
    public boolean deleta(Pizza p) throws SQLException {
        Connection connection = FabricaConexao.getConnection();
        dbAccess.update(connection,"DELETE FROM Pizzas where id=?",p.getId());
        connection.close();
        return true;
    }

    @Override
    public Pizza buscaId(int id) throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        Pizza p =dbAccess.query(connection,"SELECT * FROM Pizzas WHERE id=?",
                new BeanHandler<Pizza>(Pizza.class),id);

        connection.close();
        return p;

    }

    @Override
    public List<Pizza> buscaAtributo(PizzaAtributoBusca atributo, Object valor) throws SQLException {
        String where = "";
        String valorWhere = "";

        switch (atributo){
            case SABOR:
                where = "where SABOR like ?";
                valorWhere = "%"+valor.toString()+"%";
                break;
        }

        Connection connection = FabricaConexao.getConnection();
        List<Pizza> lista = dbAccess.query(connection,"SELECT * FROM Pizzas "+where,
                new BeanListHandler<Pizza>(Pizza.class),valorWhere);

        connection.close();

        return lista;


    }

    @Override
    public List<Pizza> buscaTodos() throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        List<Pizza> lista = dbAccess.query(connection,"SELECT * FROM Pizzas",
                new BeanListHandler<Pizza>(Pizza.class));

        connection.close();

        return lista;
    }
}
