package pizzapp.model;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteClienteDAO implements ClienteDAO {

    private QueryRunner dbAccess = new QueryRunner();


    public Map<String,String> getColumnsToFieldsMap(){
        Map<String, String> columnsToFieldsMap = new HashMap<>();
        columnsToFieldsMap.put("ano_nascimento", "anoNascimento");

        return columnsToFieldsMap;
    }

    @Override
    public long insere(Cliente cliente) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        int id = dbAccess.insert(con,"INSERT INTO CLIENTES(NOME,TELEFONE,ANO_NASCIMENTO) VALUES (?,?,?)",
                new ScalarHandler<Integer>(),cliente.getNome(),cliente.getTelefone(),cliente.getTelefone(),cliente.getAnoNascimento());

        cliente.setId(id);

        con.close();

        return id;
    }

    @Override
    public boolean atualiza(Cliente p) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        dbAccess.update(con,"UPDATE Clientes SET NOME=?,TELEFONE=?,ANO_NASCIMENTO=? WHERE id=?"
                ,p.getNome(),p.getTelefone(),p.getAnoNascimento(),p.getId());

        con.close();
        return true;
    }

    @Override
    public boolean deleta(Cliente p) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        dbAccess.update(con,"DELETE FROM Clientes WHERE id=?",p.getId());

        con.close();
        return true;
    }

    @Override
    public List<Cliente> buscaAtributo(ClienteAtributoBusca atributo, Object valor) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        BeanListHandler<Cliente> handler = new BeanListHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        String where = "";
        String wValor="";

        switch (atributo){
            case NOME:
                where = "nome like ?";
                wValor = "%"+valor.toString()+"%";
                break;
            case TELEFONE:
                where = "telefone like ?";
                wValor = valor.toString();
                break;
            case ANO_NASCIMENTO:
                where = "ano_nascimento = ?";
                wValor = valor.toString();
                break;
        }

        List<Cliente> list = dbAccess.query(con,"SELECT * FROM Clientes WHERE "+where,handler,wValor);

        con.close();

        return list;

    }

    @Override
    public Cliente buscaId(int id) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        BeanHandler<Cliente> handler = new BeanHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        Cliente c = dbAccess.query(con,"SELECT * FROM Clientes WHERE id=?",handler,id);

        con.close();

        return c;
    }

    @Override
    public List<Cliente> buscaTodos() throws SQLException {

        Connection con = FabricaConexao.getConnection();

        BeanListHandler<Cliente> handler = new BeanListHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        List<Cliente> list = dbAccess.query(con,"SELECT * FROM Clientes",handler);

        con.close();

        return list;
    }
}
