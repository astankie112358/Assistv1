import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneControl sceneControl=new SceneControl(primaryStage);
        Menu menu=new Menu(sceneControl);
        sceneControl.setScene(menu.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
	launch(args);
    }
}
