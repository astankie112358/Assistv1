import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassTable {
    String jarpath;
    TableView<ClassDesc> tableofclasses;
    ObservableList<ClassDesc> getclassess= FXCollections.observableArrayList();
    public ClassTable(String jarpath) {

        this.jarpath = jarpath;
        TableColumn<ClassDesc,Integer> idclass=new TableColumn<>("Id Klasy");
        TableColumn<ClassDesc,String> classname=new TableColumn<>("Nazwa klasy");
        idclass.setCellValueFactory(new PropertyValueFactory<ClassDesc,Integer>("idclass"));
        classname.setCellValueFactory(new PropertyValueFactory<ClassDesc,String>("classname"));
        TableColumn<ClassDesc,Integer> numberofmethods=new TableColumn<>("Liczba metod w klasie");
        numberofmethods.setCellValueFactory(new PropertyValueFactory<ClassDesc,Integer>("numberofmethods"));
        tableofclasses=new TableView<>();
        tableofclasses.getColumns().addAll(idclass,classname,numberofmethods);
        try {
            JarFile jarFile = new JarFile(jarpath+"//");
            JarEntry jarEntry=null;
            Enumeration<JarEntry> entryEnumeration=jarFile.entries();
            URL[] url = { new URL("jar:file:" + jarpath+"!/") };
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(url);
            int idclassq=1;
            while(entryEnumeration.hasMoreElements()) {
                jarEntry = entryEnumeration.nextElement();
                if(jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")){
                    continue;
                }
                String className = jarEntry.getName().substring(0,jarEntry.getName().length()-6);
                className = className.replace('/', '.');
                Class c = urlClassLoader.loadClass(className);

                ClassPool classPool=ClassPool.getDefault();
                classPool.insertClassPath(jarpath);
                CtClass ctClass=classPool.get(className);
                getclassess.add(new ClassDesc(idclassq,c.getName(),c,c.getMethods().length,ctClass));
                idclassq++;
            }
            tableofclasses.setMaxHeight(200);
            tableofclasses.setMaxWidth(400);
            tableofclasses.setItems(getclassess);
        }catch (Exception ex){System.out.println("Zła ścieżka do pliku");
    }}


    public TableView<ClassDesc> getTableofclasses() {
        return tableofclasses;
    }

    public void setTableofclasses(TableView<ClassDesc> tableofclasses) {
        this.tableofclasses = tableofclasses;
    }

    public ObservableList<ClassDesc> getGetclassess() {
        return getclassess;
    }

    public void setGetclassess(ObservableList<ClassDesc> getclassess) {
        this.getclassess = getclassess;
    }

    public String getJarpath() {
        return jarpath;
    }

    public void setJarpath(String jarpath) {
        this.jarpath = jarpath;
    }


}
