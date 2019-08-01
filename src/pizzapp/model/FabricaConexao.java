package pizzapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static int MAX_CONNECTIONS=5;
    private static String CON_STRING="jdbc:sqlite:pizzapp.sqlite";

    private static Connection[] pool;

    private static FabricaConexao instance = new FabricaConexao();

    private FabricaConexao(){
        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException{

        for(int i=0;i<MAX_CONNECTIONS;i++){
            if(instance.pool[i]==null || instance.pool[i].isClosed()){
                instance.pool[i] = DriverManager.getConnection(CON_STRING);
                return instance.pool[i];
            }
        }

        throw new SQLException("Máximo de conexões");
    }


}
