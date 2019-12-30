import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javassist.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class JarView {
    SceneControl sceneControl;
    ClassTable classTable;
    MethodTable methodTable;
    VBox vbox;
    String jarpath;
    Object object;
    Method selectedmethod;

    JarView(SceneControl sceneControl, String jarpath) {
        this.jarpath = jarpath;
        this.sceneControl = sceneControl;
        vbox = new VBox();
        sceneControl.getStage().setScene(new Scene(vbox, 1000, 700));
        classTable = new ClassTable(jarpath);
        methodTable = new MethodTable(jarpath);
        vbox.getChildren().addAll(classTable.getTableofclasses(), methodTable.getTableofmethods());
        Button back = new Button("Cofnij");
        Button methods = new Button("Metody");
        Button changemethod = new Button("Zmień metodę");
        Button usemethod = new Button("Wywołaj metode");
        Button instance = new Button("Powołaj obiekt");
        Button addmethod = new Button("Dodaj metode");
        changemethod.setOnAction(event -> changemethod());
        methods.setOnAction(event -> methodbutton());
        back.setOnAction(event -> backbutton());
        usemethod.setOnAction(event -> usebutton());
        instance.setOnAction(event -> makeinstance());
        addmethod.setOnAction(event -> addmethod());
        vbox.getChildren().addAll(back, methods, changemethod, usemethod, instance, addmethod);
    }

    public void addmethod() {
        CtClass ctClass = this.classTable.getTableofclasses().getSelectionModel().getSelectedItem().getCtClass();
    }

    public void makeinstance() {
        Class c = this.classTable.tableofclasses.getSelectionModel().getSelectedItem().getThisclass();
        try {
            setObject(c.newInstance());
            System.out.println("Obiekt");

        } catch (Exception ex) {
            Stage stage = new Stage();

        }
    }

    public void usebutton() {
        MethodDesc methodDesc = this.methodTable.tableofmethods.getSelectionModel().getSelectedItem();
        try {
            ClassPool classPool=ClassPool.getDefault();
            classPool.insertClassPath(jarpath);
            CtClass ctClass=this.classTable.tableofclasses.getSelectionModel().getSelectedItem().getCtClass();

            Class c = this.classTable.tableofclasses.getSelectionModel().getSelectedItem().getThisclass();
    Method[] methods=c.getMethods();
            String a = methodDesc.getMethod().getName();
            String b;
            ClassLoader classLoader=classPool.getClassLoader();
            Method method = null;
            for (int i = 0; i < methods.length; i++) {
                b = methods[i].getName();
                if (b.equals(a))
                    method = methods[i];

            }
            this.selectedmethod = method;
            Class<?>[] types = method.getParameterTypes();
            Stage stage = new Stage();
            VBox vBox = new VBox();
            Scene scene = new Scene(vBox, 200, 200);
            ArrayList<TextArea> areas = new ArrayList<>();
            stage.setScene(scene);
            stage.show();
            for (int i = 0; i < types.length; i++) {
                Label label = new Label(types[i].getName());
                TextArea textArea = new TextArea();
                areas.add(textArea);
                textArea.setMaxSize(100, 20);
                vBox.getChildren().addAll(label, textArea);
            }
            Button accept = new Button("Wykonaj");
            vBox.getChildren().addAll(accept);
            accept.setOnAction(event -> usemethod(types, areas)


            );
        } catch (Exception ex) {
            System.out.println(ex.getCause().getMessage());
        }


    }

    public int usemethod(Class<?>[] types, ArrayList<TextArea> areas) {

        Object[] args = new Object[this.selectedmethod.getParameterCount()];
        if (this.object == null) {
            try {
                Class c = this.classTable.tableofclasses.getSelectionModel().getSelectedItem().getThisclass();
                setObject(c.newInstance());
            } catch (Exception ex) {
                System.out.println("AAA");
            }
        }


        for (int i = 0; i < this.selectedmethod.getParameterCount(); i++) {
            if (types[i].toString().equals("int")) {
                args[i] = Integer.parseInt(areas.get(i).getText());
            }
        }

        try {

            this.selectedmethod.invoke(this.object, args);
        } catch (Exception ex) {

        }


        return 0;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getSelectedmethod() {
        return selectedmethod;
    }

    public void setSelectedmethod(Method selectedmethod) {
        this.selectedmethod = selectedmethod;
    }

    public void methodbutton() {
        methodTable.printmethods(classTable.tableofclasses.getSelectionModel().getSelectedItems().get(0).getThisclass());
        methodTable.getTableofmethods().refresh();
    }

    public void backbutton() {
        Menu menu = new Menu(sceneControl);
        sceneControl.getStage().setScene(menu.getScene());
    }

    public void changemethod() {
        TextArea textArea = new TextArea();
        Button accept = new Button("Zmień");
        accept.setOnAction(event -> acceptnewmethod(textArea, accept));
        this.vbox.getChildren().addAll(textArea, accept);
    }

    public void acceptnewmethod(TextArea textArea, Button accept) {
        ClassPool classPool = ClassPool.getDefault();
        CtMethod method = methodTable.tableofmethods.getSelectionModel().getSelectedItem().method;
        CtMethod old = method;
        CtClass ctClass = this.classTable.getTableofclasses().getSelectionModel().getSelectedItem().getCtClass();
        ClassDesc classDesc = this.classTable.getTableofclasses().getSelectionModel().getSelectedItem();
        try {
            ctClass.defrost();
            ctClass.removeMethod(method);
            method.setName("aaa");
            method.setBody(textArea.getText());
            ctClass.addMethod(method);
            ctClass.writeFile(".");
            ctClass.toClass();
            ctClass.defrost();
            classTable.getTableofclasses().refresh();
            methodTable.getTableofmethods().refresh();



        } catch (Exception ex) {

            System.out.println("Nie ma pliku");
            methodbutton();
            vbox.getChildren().removeAll(textArea, accept);
        }
        methodbutton();
        vbox.getChildren().removeAll(textArea, accept);
    }


}
