package br.com.ucsal.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassFinder 
{
    public static List<Class<?>> find(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        final File directory = getFile(packageName);
        for (String file : Objects.requireNonNull(directory.list())) {
            if (file.endsWith(".class")) {
                String className = packageName + '.' + file.substring(0, file.length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

    private static File getFile(String packageName) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new ClassNotFoundException("Pacote não encontrado: " + packageName);
        }
        File directory = new File(resource.getFile());
        if (!directory.exists()) {
            throw new ClassNotFoundException("Diretório não encontrado: " + resource.getFile());
        }
        return directory;
    }
}
