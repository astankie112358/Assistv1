import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneControl {
    private Scene scene;
    private Stage stage;
    SceneControl(Stage stage)
    {
        this.stage=stage;
    }
    Scene getScene()
    {
        return this.scene;
    }
    Stage getStage()
    {
        return stage;
    }
    void setScene(Scene scene)
    {
        stage.setScene(scene);
        this.scene=scene;
    }

}
