package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        ClassAnalyser ca = new ClassAnalyser("src/main/java/org/example/Necessity.java");
        System.out.println("Class name: " + ca.getClassName());
        System.out.println("Class attributes:\n" + String.join("\n", ca.getFields()));
        System.out.println("Class methods:\n" + String.join("\n", ca.getMethods()));
        System.out.println("Super class names:\n" + String.join("\n", ca.getSuperClasses()));
    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
