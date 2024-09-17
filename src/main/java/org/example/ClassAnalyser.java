package org.example;

import org.eclipse.jdt.core.dom.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassAnalyser {

    private String classSourceCode;

    private String className;

    private final List<String> fields = new ArrayList<>();

    private final List<String> methods = new ArrayList<>();

    private final List<String> superClasses = new ArrayList<>();

    public ClassAnalyser(String classPath) {
        this.classSourceCode = readFile(classPath);
        this.run();
    }

    private void run() {
        if (classSourceCode != null) {
            ASTParser parser = ASTParser.newParser(AST.JLS4);
            parser.setSource(classSourceCode.toCharArray());
            parser.setKind(ASTParser.K_COMPILATION_UNIT);

            CompilationUnit cu = (CompilationUnit) parser.createAST(null);

            cu.accept(new ASTVisitor() {
                @Override
                public boolean visit(FieldDeclaration node) {
                    String res = "";
                    String modifiers = ((List<IExtendedModifier>) node.modifiers()).stream()
                            .filter(IExtendedModifier::isModifier)
                            .map(m -> ((Modifier) m).toString())
                            .collect(Collectors.joining());
                    res += modifiers + " ";
                    String variables = node.fragments().stream()
                            .map(f -> ((VariableDeclarationFragment) f).getName().getIdentifier())
                            .collect(Collectors.joining(", ")).toString();
                    res += variables;
                    fields.add(res);
                    return super.visit(node);
                }

                @Override
                public boolean visit(TypeDeclaration node) {
                    className = node.getName().toString();
                    superClasses.add(node.getSuperclassType().toString());
                    return super.visit(node);
                }

                @Override
                public boolean visit(MethodDeclaration node) {
                    String res = node.getName().toString();
                    methods.add(res);
                    return super.visit(node);
                }
            });
        }
    }

    public String getClassName() {
        return className;
    }

    public List<String> getFields() {
        return fields;
    }

    public List<String> getMethods() {
        return methods;
    }

    public List<String> getSuperClasses() {
        return superClasses;
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
