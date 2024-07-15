package ren.lawliet.f2kDoor_Plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Coaixy
 * @createTime 2024-07-15
 * @packageName ren.lawliet.f2kDoor_Plugin
 */

public class FileHelper {
    private static final String pluginDirectoryPath = System.getProperty("user.dir") + "\\plugins";

    public record pluginClasses(String pluginName, ArrayList<String> classNameList) {
    }

    public static ArrayList<String> getPluginPathList() {
        Path dicPathObj = Paths.get(pluginDirectoryPath);
        ArrayList<String> pluginPathArray = new ArrayList<>();
        try {
            Files.walk(dicPathObj).filter(Files::isRegularFile).filter(Files::exists)
                    .filter(path -> path.toString().endsWith(".jar")).forEach(path -> {
                        pluginPathArray.add(path.toString());
                    });
        } catch (IOException e) {
            System.err.println("Plugin Directory is not find of " + e.getMessage());
        }
        return pluginPathArray;
    }

    public static ArrayList<pluginClasses> getPluginClasses() {
        // initial variable
        ArrayList<String> pluginPathList = getPluginPathList();
        ArrayList<pluginClasses> pluginClassesArrayList = new ArrayList<>();

        String pluginName = "";
        Plugin tempPlugin = null;
        PluginManager pluginManager = Bukkit.getPluginManager();
        String packageName = null;

        for (String pluginPath : pluginPathList) {
            try (JarFile jarFile = new JarFile(pluginPath)) {

                ArrayList<String> classNameList = new ArrayList<>();

                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory()) {
                        continue;
                    }
                    if (entry.getName().equalsIgnoreCase("plugin.yml")) {
                        InputStream is = jarFile.getInputStream(entry);
                        BufferedReader bis = new BufferedReader(new InputStreamReader(is));
                        pluginName = bis.readLine().replace("name: ","");
                        tempPlugin = pluginManager.getPlugin(pluginName);
                        packageName = tempPlugin != null ? tempPlugin.getClass().getPackageName() : null;
                        if (packageName != null) {
                            packageName = packageName.replaceAll("\\.","/");
                        }
                    }
                    // find class
                    if (packageName != null && entry.getName().endsWith(".class") && entry.getName().contains(packageName)) {
                        classNameList.add(entry.getName());
                    }
                }
                pluginClassesArrayList.add(new pluginClasses(pluginName, classNameList));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return pluginClassesArrayList;
    }
}