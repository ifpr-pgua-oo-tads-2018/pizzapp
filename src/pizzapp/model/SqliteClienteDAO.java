package pizzapp.model;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteClienteDAO implements ClienteDAO {

    private static String TABELA="clientes";
    private static String CAMPO_NOME="nome";
    private static String CAMPO_NASCIMENTO="ano_nascimento";
    private static String CAMPO_TELEFONE="telefone";
    private static String CAMPO_ID="id";

    private static String INSERT="INSERT INTO "+TABELA+"("+CAMPO_NOME+","+CAMPO_TELEFONE+","+CAMPO_NASCIMENTO+") VALUES (?,?,?)";
    private static String UPDATE="UPDATE "+TABELA+" SET "+CAMPO_NOME+"=?,"+CAMPO_TELEFONE+"=?,"+CAMPO_NASCIMENTO+"=? WHERE"+CAMPO_ID+"=?";
    private static String DELETE="DELETE FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";
    private static String SELECT="SELECT * FROM "+TABELA;
    private static String SELECT_ID="SELECT * FROM "+TABELA+" WHERE "+CAMPO_ID+"=?";


    private QueryRunner dbAccess = new QueryRunner();


    public Map<String,String> getColumnsToFieldsMap(){
        Map<String, String> columnsToFieldsMap = new HashMap<>();
        columnsToFieldsMap.put("ano_nascimento", "anoNascimento");

        return columnsToFieldsMap;
    }

    @Override
    public long insere(Cliente cliente) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        long id = dbAccess.insert(con,INSERT,
                new ScalarHandler<BigInteger>(),cliente.getNome(),cliente.getTelefone(),cliente.getAnoNascimento()).longValue();

        cliente.setId((int)id);

        con.close();

        return id;
    }

    @Override
    public boolean atualiza(Cliente p) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        dbAccess.update(con,UPDATE
                ,p.getNome(),p.getTelefone(),p.getAnoNascimento(),p.getId());

        con.close();
        return true;
    }

    @Override
    public boolean deleta(Cliente p) throws SQLException {
        Connection con = FabricaConexao.getConnection();

        dbAccess.update(con,DELETE,p.getId());

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
                where = CAMPO_NOME+" like ?";
                wValor = "%"+valor.toString()+"%";
                break;
            case TELEFONE:
                where = CAMPO_TELEFONE+" like ?";
                wValor = valor.toString();
                break;
            case ANO_NASCIMENTO:
                where = CAMPO_NASCIMENTO+" = ?";
                wValor = valor.toString();
                break;
        }

        List<Cliente> list = dbAccess.query(con,SELECT+" WHERE "+where,handler,wValor);

        con.close();

        return list;

    }

    @Override
    public Cliente buscaId(int id) throws SQLException {

        Connection con = FabricaConexao.getConnection();

        BeanHandler<Cliente> handler = new BeanHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        Cliente c = dbAccess.query(con,SELECT_ID,handler,id);

        con.close();

        return c;
    }

    @Override
    public List<Cliente> buscaTodos() throws SQLException {

        Connection con = FabricaConexao.getConnection();

        BeanListHandler<Cliente> handler = new BeanListHandler<Cliente>(Cliente.class,new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));

        List<Cliente> list = dbAccess.query(con,SELECT,handler);

        con.close();

        return list;
    }
}
