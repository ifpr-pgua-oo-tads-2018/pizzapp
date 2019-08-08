package pizzapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pizzapp.model.Pizzaria;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();

        try {
            Pizzaria.getInstance().carrega();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void stop() throws Exception {
        super.stop();


        try {
            Pizzaria.getInstance().salva();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));


        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.show();



    }
}
