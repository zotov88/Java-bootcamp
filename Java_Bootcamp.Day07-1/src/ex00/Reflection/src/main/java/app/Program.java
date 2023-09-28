package app;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Program {

    private final static Scanner SC = new Scanner(System.in);
    private static Set<Class<?>> classes;
    private static Class selectedClass;
    private static Method[] methods;
    private static Field[] fields;
    private static Object obj;
    private static String className;
    private final static String LINE = "-------------------------------------------";

    public static void main(String[] args) {
        scanClasses();
        printNameClasses();
        chooseClass();
        printInfoAboutClass();
        createInstance();
        fillInstanceFields();
        methodInterception();
        SC.close();
    }

    private static void scanClasses() {
        Reflections reflections = new Reflections("classes", new SubTypesScanner(false));
        classes = reflections.getSubTypesOf(Object.class);
    }

    private static void printNameClasses() {
        System.out.println("Classes:");
        for (Class<?> tmp : classes) {
            System.out.println(tmp.getSimpleName());
        }
        System.out.println(LINE);
    }

    private static void chooseClass() {
        System.out.println("Enter class name:");
        className = SC.next();
        System.out.println(LINE);
    }

    private static void printInfoAboutClass() {
        for (Class<?> oneClassName : classes) {
            if (oneClassName.getSimpleName().equals(className)) {
                selectedClass = oneClassName;
                System.out.println("fields:");
                fields = oneClassName.getDeclaredFields();
                for (Field field : fields) {
                    System.out.printf("\t%s %s\n", field.getType().getSimpleName(), field.getName());
                }
                System.out.println("methods:");
                methods = oneClassName.getDeclaredMethods();
                for (Method method : methods) {
                    System.out.printf("\t%s\n", executeMethod(method));
                }
                break;
            }
        }
        if (Objects.isNull(selectedClass)) {
            chooseClass();
            printInfoAboutClass();
        }
        System.out.println(LINE);
    }

    private static void createInstance() {
        System.out.println("Create an object");
        try {
            obj = Class.forName(selectedClass.getName()).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.exit(-1);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName() + ":");
            setValueFields(field);
        }
        System.out.printf("New instance: %s\n", obj);
        System.out.println(LINE);
    }

    private static void fillInstanceFields() {
        System.out.println("Enter the name of the field you want to change:");
        String tmp = SC.next();
        for (Field field : fields) {
            if (field.getName().equals(tmp)) {
                System.out.printf("Enter value for %s\n", field.getType().getSimpleName());
                setValueFields(field);
                System.out.printf("Updated instance: %s\n", obj);
                SC.nextLine();
                break;
            }
        }
        System.out.println(LINE);
    }

    private static void setValueFields(Field field) {
        try {
            switch (field.getType().getSimpleName()) {
                case "Integer": {
                    Integer tmp = SC.nextInt();
                    field.set(obj, tmp);
                    break;
                }
                case "String": {
                    String tmp = SC.next();
                    field.set(obj, tmp);
                    break;
                }
                case "Long": {
                    Long tmp = SC.nextLong();
                    field.set(obj, tmp);
                    break;
                }
                case "Double": {
                    Double tmp = SC.nextDouble();
                    field.set(obj, tmp);
                    break;
                }
                case "Boolean": {
                    Boolean tmp = SC.nextBoolean();
                    field.set(obj, tmp);
                    break;
                }
                default:
                    System.err.println("No such field!");
                    System.exit(-1);
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void methodInterception() {
        System.out.println("Enter name of the method for interception:");
        String chosenMethod = SC.nextLine();
        for (Method method : methods) {
            if (executeMethod(method).endsWith(chosenMethod)) {
                Class<?>[] parametersTypes = method.getParameterTypes();
                Object[] parameters = new Object[parametersTypes.length];
                for (int i = 0; i < parametersTypes.length; i++) {
                    System.out.printf("Enter %s value:\n", parametersTypes[i].getSimpleName());
                    switch (parametersTypes[i].getSimpleName()) {
                        case "Integer":
                            parameters[i] = SC.nextInt();
                            break;
                        case "String":
                            parameters[i] = SC.next();
                            break;
                        case "Long":
                            parameters[i] = SC.nextLong();
                            break;
                        case "Double":
                            parameters[i] = SC.nextDouble();
                            break;
                        case "Boolean":
                            parameters[i] = SC.nextBoolean();
                            break;
                        default:
                            System.err.println("No such parameter!");
                            System.exit(-1);
                    }
                }
                try {
                    System.out.println("Returned value:\n" + method.invoke(obj, parameters));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private static String executeMethod(Method method) {
        StringBuilder result = new StringBuilder(String.format("%s %s(", method.getReturnType().getSimpleName(), method.getName()));
        Class<?>[] parameters = method.getParameterTypes();
        for (Class<?> parameter : parameters) {
            result.append(parameter.getSimpleName()).append(", ");
        }
        if (parameters.length > 0) {
            result.delete(result.length() - 2, result.length());
        }
        return result.append(")").toString();
    }

}
