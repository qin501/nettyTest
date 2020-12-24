package cn.wsq.nettyServer;

/**
 * Created by qin on 2020-12-23
 */
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * java执行javaScript代码的工具类
 *
 * @author weizj
 */
public class JavaScriptUtil {

    /** 单例的JavaScript解析引擎 */
    private static ScriptEngine javaScriptEngine;

    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        if (scriptEngine == null) {
            throw new RuntimeException("获取JavaScript解析引擎失败");
        }
        javaScriptEngine = scriptEngine;
    }


    /**
     * 执行一段JavaScript代码
     *
     * @param script JavaScript的代码
     * @return JavaScript代码运行结果的值
     * @throws ScriptException JavaScript代码运行异常
     */
    public static Object execute(String script) throws ScriptException {
        return javaScriptEngine.eval(script);
    }


    /**
     * 运行一个JavaScript代码段,并获取指定变量名的值
     *
     * @param script        代码段
     * @param attributeName 已知的变量名
     * @return 指定变量名对应的值
     * @throws ScriptException JavaScript代码运行异常
     */
    public static Object executeForAttribute(String script, String attributeName) throws ScriptException {
        javaScriptEngine.eval(script);
        return javaScriptEngine.getContext().getAttribute(attributeName);
    }

    /**
     * 获取当前语句运行后第一个有值变量的值
     *
     * @param script 代码段
     * @return 第一个有值变量的值
     * @throws ScriptException JavaScript代码运行异常
     */
    public static Object executeForFirstAttribute(String script) throws ScriptException {

        //这里重新获取一个JavaScript解析引擎是为了避免代码中有其他调用工具类的地方的变量干扰
        //重新获取后,这个JavaScript解析引擎只执行了这次传入的代码,不会保存其他地方的变量
        //全局的解析器中会保存最大200个变量,JavaScript解析引擎本身最大保存100个变量
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        if (scriptEngine == null) {
            throw new RuntimeException("获取JavaScript解析引擎失败");
        }

        scriptEngine.eval(script);
        ScriptContext context = scriptEngine.getContext();
        if (context == null) {
            return null;
        }
        Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
        if (bindings == null) {
            return null;
        }
        Set<Map.Entry<String, Object>> entrySet = bindings.entrySet();
        if (entrySet == null || entrySet.isEmpty()) {
            return null;
        }
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() != null) {
                return entry.getValue();
            }
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        Integer testExecute = (Integer) execute("2*3");
//        String testExecuteForAttribute = (String) executeForAttribute("var value = 'a'+ 'dc'", "value");
//        Boolean testExecuteForFirstAttribute = (Boolean) executeForFirstAttribute("var a = 6==2*3");
//
//        System.out.println(testExecute);
//        System.out.println(testExecuteForAttribute);
//        System.out.println(testExecuteForFirstAttribute);
//
//        System.out.println("test over ....");


        File f = new File("D:\\qin\\WebstormProjects\\nettyTest\\CLodopfuncs.js");
        InputStream in = new FileInputStream(f);
        byte b[]=new byte[(int)f.length()];     //创建合适文件大小的数组
        in.read(b);    //读取文件中的内容到b[]数组
        in.close();
        System.out.println(new String(b));

        Object execute = execute(new String(b));
        System.out.println(execute);

    }
}
