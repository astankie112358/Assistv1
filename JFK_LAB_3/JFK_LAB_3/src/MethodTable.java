import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;


public class MethodTable {
    String jarpath;
    ObservableList<MethodDesc> getmethods= FXCollections.observableArrayList();
    TableView<MethodDesc> tableofmethods;

    public MethodTable(String jarpath) {
        this.jarpath = jarpath;
        tableofmethods=new TableView<>();
        TableColumn<MethodDesc,Integer> methodid=new TableColumn<>("ID");
        methodid.setCellValueFactory(new PropertyValueFactory<MethodDesc,Integer>("methodid"));
        TableColumn<MethodDesc,String> methodname=new TableColumn<>("Nazwa Metody");
        methodname.setCellValueFactory(new PropertyValueFactory<MethodDesc,String>("methodname"));
        TableColumn<MethodDesc,Integer> numberofparams=new TableColumn<>("Liczba parametrów metody");
        numberofparams.setCellValueFactory(new PropertyValueFactory<MethodDesc,Integer>("numberofparams"));

        tableofmethods.getColumns().addAll(methodid,methodname,numberofparams);
    }



    public void printmethods(Class c)
    {
        getmethods.clear();
        ClassPool classPool=ClassPool.getDefault();
        try {
            classPool.appendClassPath(jarpath);
            CtClass ctClass = classPool.get(c.getName());
            //CtMethod[]declaredMethods = ctClass.getDeclaredMethods();
            for (int i = 0; i < c.getMethods().length; i++) {
                getmethods.add(new MethodDesc(i + 1, ctClass.getMethods()[i]));
            }
        }catch (Exception ex){;}
        tableofmethods.setItems(getmethods);

    }
    public String getJarpath() {
        return jarpath;
    }

    public void setJarpath(String jarpath) {
        this.jarpath = jarpath;
    }

    public ObservableList<MethodDesc> getGetmethods() {
        return getmethods;
    }

    public void setGetmethods(ObservableList<MethodDesc> getmethods) {
        this.getmethods = getmethods;
    }

    public TableView<MethodDesc> getTableofmethods() {
        return tableofmethods;
    }

    public void setTableofmethods(TableView<MethodDesc> tableofmethods) {
        this.tableofmethods = tableofmethods;
    }
}
