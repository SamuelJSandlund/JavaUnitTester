import se.umu.cs.unittest.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Checks for and executes test methods from a given class
 *
 * @author Samuel Sandlund
 * @version 2.0
 * @since 2022-12-16
 */
public class TestRunnerModel{
    private int successes;
    private int fails;
    private int exceptionFails;

    public TestRunnerModel(){
        this.successes = 0;
        this.fails = 0;
        this.exceptionFails = 0;
    };

    /**
     * Attempts to run all test methods in the given Class
     * @return String: results from tests and summary, or an error message if one or more tests could not be performed
     */
    public String runTestClass(Class<?> testClass){
        Method[] methods = testClass.getMethods();
        Object classInstance;
        StringBuilder resultBuilder = new StringBuilder();
        //try getting an instance of the given class
        try{
            classInstance = getInstance(testClass);
        } catch (InvocationTargetException e) {
            return "ERROR: Class instantiation generated a " + e.getCause().getClass().getSimpleName();
        } catch (InstantiationException e) {
            return "ERROR: The class could not be instantiated";
        } catch (IllegalAccessException e) {
            return "ERROR: The class' constructor could not be accessed";
        } catch (NoSuchMethodException e) {
            return "ERROR: The class could not be instantiated because it lacks a constructor";
        }
        //check that the given class implements TestClass interface
        if(!(classInstance instanceof TestClass)){
            return "ERROR: Class does not implement the 'TestClass' interface";
        }
        //get setUp and tearDown methods. OBS! THESE ARE null IF NO SUCH METHODS ARE DEFINED
        Method setUp = checkForMethod(testClass, "setUp");
        Method tearDown = checkForMethod(testClass, "setUp");
        //run tests
        for(Method m : methods){
            //skip non-test methods
            if(!(m.getName().startsWith("test")
                    && m.getReturnType().getName().equals("boolean")
                    && m.getParameterCount() == 0
            )){continue;}
            //if test class has setup run it before each test
            if(setUp != null){
                try{
                    setUp.invoke(classInstance);
                } catch (InvocationTargetException e) {
                    return "ERROR: The method: 'setUp()' threw a " + e.getCause().getClass().getSimpleName();
                } catch (IllegalAccessException e) {
                    return "ERROR: The method 'setUp()' could not be accessed";
                }
            }
            //run the test method itself
            try{
                String methodResult = runTestMethod(m, classInstance);
                resultBuilder.append(methodResult);
            } catch (IllegalAccessException e) {
                return "ERROR: The method '"+ m.getName() + "' could not be accessed";
            }
            //if test class has teardown run it after each test
            if(tearDown != null){
                try{
                    tearDown.invoke(classInstance);
                } catch (InvocationTargetException e) {
                    return "ERROR: The method: 'tearDown()' threw a " + e.getCause().getClass().getSimpleName();
                } catch (IllegalAccessException e) {
                    return "ERROR: The method 'tearDown()' could not be accessed";
                } ;
            }
        }
        //all tests are done
        resultBuilder.append(getSummary());
        return resultBuilder.toString();
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
     * Called in runTest() to try to get a new instance of the given class object
     * if unsuccessful throws error so that an appropriate error message can be returned by the runTest() method
     * @param c Class to get instance of
     * @return an instance of the class c
     * @throws IllegalAccessException if the class' declared constructor could not be accessed
     * @throws InvocationTargetException if the class' constructor throws an exception
     * @throws NoSuchMethodException if the class lacks constructor
     * @throws InstantiationException if the class could for some other reason not be instantiated
     */
    private Object getInstance(Class<?> c) throws
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException{
        return c.getDeclaredConstructor().newInstance();
    }

    /**
     * Runs the test method "m" from the object "instance"
     * @param m Method to be executed
     * @param instance Object from with to invoke the method
     * @return A string showing the name of the method and result of the test
     * @throws IllegalAccessException if the given object can not execute the given method
     */
    private String runTestMethod(Method m, Object instance) throws IllegalAccessException {
        try {
            if((boolean)m.invoke(instance)){
                successes++;
                return m.getName() + ": SUCCESS\n";
            }
            else{
                fails++;
                return m.getName() + ": FAIL\n";
            }
        }
        catch (InvocationTargetException e) {
            exceptionFails++;
            return m.getName() + ": FAIL, generated " + e.getCause().getClass().getSimpleName() + "\n";
        }
    }

    /**
     * Builds a string summarizing results of the tests performed by this TestRunnerModel
     * @return String representation of test summary
     */
    private String getSummary(){
        StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder.append("\n");
        summaryBuilder.append("Tests passed: " + successes + "\n");
        summaryBuilder.append("Tests failed: " + fails + "\n");
        summaryBuilder.append("Tests failed due to exception: " + exceptionFails + "\n");
        return summaryBuilder.toString();
    }
}
