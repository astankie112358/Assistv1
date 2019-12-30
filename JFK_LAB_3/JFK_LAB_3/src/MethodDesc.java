import javassist.CtMethod;

import java.lang.reflect.Method;

public class MethodDesc {
    int methodid,numberofparams;
    String methodname;
    CtMethod method;



    public MethodDesc(int methodid, CtMethod method) {
        try {
            this.methodid = methodid;
            this.method = method;
            this.methodname = method.getName();
            this.numberofparams = method.getParameterTypes().length;
        }catch (Exception ex) {
        System.out.println("Brak ścieżki do pliku");
        }
    }

    public int getMethodid() {
        return methodid;
    }

    public void setMethodid(int methodid) {
        this.methodid = methodid;
    }

    public int getNumberofparams() {
        return numberofparams;
    }

    public void setNumberofparams(int numberofparams) {
        this.numberofparams = numberofparams;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public CtMethod getMethod() {
        return method;
    }

    public void setMethod(CtMethod method) {
        this.method = method;
    }
}
