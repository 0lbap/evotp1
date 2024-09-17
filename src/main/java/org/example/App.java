package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        ClassAnalyser ca = new ClassAnalyser("src/main/java/org/example/Product.java");
        System.out.println("Class name: " + ca.getClassName());
        List<String> fields = ca.getFields().stream().map(f -> " - " + f).collect(Collectors.toList());
        if (fields.isEmpty()) {
            System.out.println("No class fields");
        } else {
            System.out.println("Class fields:\n" + String.join("\n", fields));
        }
        List<String> methods = ca.getMethods().stream().map(f -> " - " + f).collect(Collectors.toList());
        if (methods.isEmpty()) {
            System.out.println("No class methods");
        } else {
            System.out.println("Class methods:\n" + String.join("\n", methods));
        }
        List<String> superClasses = ca.getSuperClasses().stream().map(f -> " - " + f).collect(Collectors.toList());
        System.out.println("Super class names:\n" + String.join("\n", superClasses));
    }

}
