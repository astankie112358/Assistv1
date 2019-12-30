import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;

public class Menu {
    Scene scene;
    String jarpath;
    SceneControl sceneControl;
    Menu(SceneControl sceneControl)
    {
        this.sceneControl=sceneControl;
        VBox pane=new VBox();
        Button getjar=new Button("Podaj ścieżkę do jar");
        getjar.setOnAction(event -> setJar());
        Button wyjscie=new Button("Wyjdź");
        wyjscie.setOnAction(event -> sceneControl.getStage().close());
        pane.getChildren().addAll(getjar,wyjscie);
        this.scene=new Scene(pane,1000,700);
        double placex=((scene.getWidth()/2)-80);
        double placey=((scene.getHeight()/2)-10);
        getjar.setTranslateX(placex);
        wyjscie.setTranslateX(placex);
        getjar.setTranslateY(placey);
        wyjscie.setTranslateY(placey+80);
    }
    public void setJar()
    {
        FileChooser jfc=new FileChooser();
        JButton click=new JButton();
        File selection=jfc.showOpenDialog(sceneControl.getStage());
        jarpath=selection.getPath();
        JarView jv=new JarView(sceneControl,jarpath);
    }

    public Scene getScene() {
        return scene;
    }

    public String getJarpath() {
        return jarpath;
    }
    public SceneControl getsceneControl()
    {
        return this.getsceneControl();
    }
}
