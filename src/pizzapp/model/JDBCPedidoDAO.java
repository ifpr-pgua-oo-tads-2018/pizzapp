package pizzapp.model;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCPedidoDAO implements PedidoDAO{

    private QueryRunner dbAccess = new QueryRunner();

    @Override
    public long insere(Pedido pedido) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        int idPedido = dbAccess.insert(con,"INSERT INTO Pedidos(idCliente,dataHora,valor) VALUES (?,?,? )",
                new ScalarHandler<Integer>(),pedido.getCliente().getId(),pedido.getDataHora(),pedido.getValor());

        for(Pizza p:pedido.getPedido()){
            int id = dbAccess.insert(con,"INSERT INTO PedidosPizza(idPedido,idPizza,valor) VALUES (?,?,? )",
                    new ScalarHandler<Integer>(),idPedido,p.getId(),p.getValor());
        }

        con.close();

        return idPedido;
    }

    @Override
    public boolean atualiza(Pedido p) throws SQLException {
        return false;
    }

    @Override
    public boolean deleta(Pedido p) throws SQLException {
        return false;
    }




    @Override
    public Pedido buscaId(int id) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        Pedido p = dbAccess.query(con,"SELECT * FROM Pedidos WHERE id=?",new PedidoHandler(new JDBCClienteDAO()),id);

        con.close();

        return p;
    }

    @Override
    public List<Pedido> buscaTodos() throws SQLException {
        Connection connection = FabricaConexao.getConnection();

        List<Pedido> lista = dbAccess.query(connection,"SELECT * FROM Pedidos",new PedidoListHandler(new JDBCClienteDAO()));

        return lista;
    }
}
