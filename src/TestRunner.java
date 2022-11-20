import se.umu.cs.unittest.TestClass;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Checks for and executes test methods from a given class and prints results to a given GUI
 *
 * @author Samuel Sandlund
 * @version 1.0
 * @since 2022-11-21
 */
public class TestRunner extends SwingWorker<String, String> {
    private final Class<?> testClass;
    private final GuiWindow outputGui;
    private int successes;
    private int fails;
    private int exceptionFails;
    private boolean failed;

    public TestRunner(Class<?> testClass, GuiWindow outputGui){
        this.testClass = testClass;
        this.outputGui = outputGui;
        this.successes = 0;
        this.fails = 0;
        this.exceptionFails = 0;
        this.failed = false;
    }

    /**
     * Attempts to run all test methods in the class given to this TestRunner
     * @return String: "success" if all tests could be completed, else "failed"
     */
    @Override
    protected String doInBackground() {
        Method[] methods = testClass.getMethods();
        Object classInstance = getInstance(testClass);
        //check that the class could be instantiated
        if(classInstance == null){
            failed = true;
            return "failed";
        }
        //check that the class implements TestClass interface
        if(!(classInstance instanceof TestClass)){
            publish("ERROR: Class does not implement the 'TestClass' interface");
            failed = true;
            return "failed";
        }
        //check if the class has setUp and tearDown functions
        Method setUp = checkForMethod(testClass, "setUp");
        Method tearDown = checkForMethod(testClass, "tearDown");
        //loop through all methods in class
        for(Method m : methods){
            //test only methods that follow our definition of a test
            if(!(m.getName().startsWith("test")
                    && m.getReturnType().getName().equals("boolean")
                    && m.getParameterCount() == 0
            )){continue;}
            try{
                    if(setUp != null){setUp.invoke(classInstance);}
                    runTest(m, classInstance);
                    if(tearDown != null){tearDown.invoke(classInstance);}
                }
                catch (IllegalAccessException e){
                    publish("ERROR: The method '" + m.getName() + "' could not be accessed");
                    failed = true;
                    return "failed";
                }
                catch (InvocationTargetException e)
                {
                    publish("ERROR: setUp or tearDown threw a " + e.getCause().getClass().getSimpleName());
                    failed = true;
                    return "failed";
                }
        }
        return "success";
    }

    /**
     * Prints strings to GUI
     * @param chunks intermediate results to process
     */
    @Override
    protected void process(List<String> chunks){
        for(String s : chunks){
            outputGui.output(s + "\n");
        }
    }

    /**
     * If all test were completed correctly prints test summary to GUI
     */
    @Override
    protected void done(){
        if(!failed){
            printResults();
        }
    }

    /**
     * Prints test summary to GUI
     */
    private void printResults(){
        outputGui.output("\n");
        outputGui.output("Tests passed: " + successes + "\n");
        outputGui.output("Tests failed: " + fails + "\n");
        outputGui.output("Tests failed due to exception: " + exceptionFails + "\n");
        outputGui.output("\n");
    }

    /**
     * Searches for a method of name "methodName" in the class "c" and returns a Method object
     * if it is found.
     * @param c Class in which to look for the method
     * @param methodName Name of the method to be looked for
     * @return If the class has the named method returns the method, else returns null
     */
    private Method checkForMethod(Class<?> c, String methodName){
        try{
            return c.getMethod(methodName);
        }
        catch (NoSuchMethodException e){
            return null;
        }
    }

    /**
     * Tries to create an object of the class "c" using its declared constructor.
     * @param c Class to be instantiated
     * @return An object of the class given as parameter or null if the class could not be instantiated
     */
    private Object getInstance(Class<?> c) {
        try {
            return c.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException e) {
            publish("ERROR: The class could not be instantiated");
            return null;
        }
        catch (IllegalAccessException e) {
            publish("ERROR: The class' constructor could not be accessed");
            return null;
        }
        catch (InvocationTargetException e) {
            publish("ERROR: Class instantiation generated a " + e.getCause().getClass().getSimpleName());
            return null;
        }
        catch (NoSuchMethodException e) {
            publish("ERROR: The class could not be instantiated because it lacks a constructor");
            return null;
        }
    }

    /**
     * Runs the test method "m" from the object "instance"
     * @param m: Method to be executed
     * @param instance: Object from with to invoke the method
     * @throws IllegalAccessException if the given object can not execute the given method
     */
    private void runTest(Method m, Object instance) throws IllegalAccessException {
        try {
            if((boolean) m.invoke(instance)){
                publish(m.getName() + ": SUCCESS");
                successes++;
            }
            else{
                publish(m.getName() + ": FAIL");
                fails++;
            }
        }
        catch (InvocationTargetException e) {
            publish(m.getName() + ": FAIL, generated " + e.getCause().getClass().getSimpleName());
            exceptionFails++;
        }
    }
}
