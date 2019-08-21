package pizzapp.model;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SqlitePizzaDAO implements PizzaDAO {

    private static String TABELA="pizzas";
    private static String CAMPO_SABOR="sabor";
    private static String CAMPO_VALOR="valor";
    private static String CAMPO_ID="id";

    private static String INSERT="INSERT INTO "+TABELA+"("+CAMPO_SABOR+","+CAMPO_VALOR+") VALUES (?,?)";
    private static String UPDATE="UPDATE "+TABELA+" SET "+CAMPO_SABOR+"=?,"+CAMPO_VALOR+"=? WHERE"+CAMPO_ID+"=?";
    private static String DELETE="DELETE FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";
    private static String SELECT="SELECT * FROM "+TABELA;
    private static String SELECT_ID="SELECT * FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";


    private QueryRunner dbAccess = new QueryRunner();

    @Override
    public long insere(Pizza p) throws SQLException {



        Connection connection = FabricaConexao.getConnection();

        long id = dbAccess.insert(connection,INSERT,
                new ScalarHandler<BigInteger>(),p.getSabor(),p.getValor()).longValue();

        connection.close();
        return id;
    }

    @Override
    public boolean atualiza(Pizza p) throws SQLException {
        Connection connection = FabricaConexao.getConnection();
        dbAccess.update(connection,UPDATE,
                p.getSabor(),p.getValor(),p.getId());
        connection.close();
        return true;
    }

    @Override
    public boolean deleta(Pizza p) throws SQLException {
        Connection connection = FabricaConexao.getConnection();
        dbAccess.update(connection,DELETE,p.getId());
        connection.close();
        return true;
    }

    @Override
    public Pizza buscaId(int id) throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        Pizza p =dbAccess.query(connection,SELECT_ID,
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
                where = " where "+CAMPO_SABOR+" like ?";
                valorWhere = "%"+valor.toString()+"%";
                break;
        }

        Connection connection = FabricaConexao.getConnection();
        List<Pizza> lista = dbAccess.query(connection,SELECT+where,
                new BeanListHandler<Pizza>(Pizza.class),valorWhere);

        connection.close();

        return lista;


    }

    @Override
    public List<Pizza> buscaTodos() throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        List<Pizza> lista = dbAccess.query(connection,SELECT,
                new BeanListHandler<Pizza>(Pizza.class));

        connection.close();

        return lista;
    }
}
