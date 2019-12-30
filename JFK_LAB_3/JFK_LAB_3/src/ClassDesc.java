import javassist.CtClass;
import javassist.CtMethod;

public class ClassDesc {
    private Integer idclass;
    private String classname;
    private Class<?> thisclass;
    private Integer numberofmethods;
    private CtClass ctClass;

    public ClassDesc(Integer idclass, String classname, Class<?> thisclass, Integer numberofmethods,CtClass ctClass) {
        this.idclass = idclass;
        this.classname = classname;
        this.thisclass = thisclass;
        this.numberofmethods = numberofmethods;
        this.ctClass=ctClass;
        CtMethod[] m=ctClass.getMethods();
        for(int i=0;i<m.length;i++)
        {
            System.out.println(m[i].getName());
        }
    }

    public CtClass getCtClass() {
        return ctClass;
    }

    public void setCtClass(CtClass ctClass) {
        this.ctClass = ctClass;
    }

    public Integer getIdclass() {
        return idclass;
    }

    public void setIdclass(Integer idclass) {
        this.idclass = idclass;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Class<?> getThisclass() {
        return thisclass;
    }

    public void setThisclass(Class<?> thisclass) {
        this.thisclass = thisclass;
    }

    public Integer getNumberofmethods() {
        return numberofmethods;
    }

    public void setNumberofmethods(Integer numberofmethods) {
        this.numberofmethods = numberofmethods;
    }
}
