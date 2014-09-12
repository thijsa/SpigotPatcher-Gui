package org.spigotmc.patcher.utils;

import java.io.File;

import javafx.stage.FileChooser;

public class filechooser {
	
	public static void configureFileChooser(
            final FileChooser fileChooser) {      
                fileChooser.setTitle("Search jars");
                fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.dir"))
                );                 
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Jar", "*.jar")
                );
      }
	
	public static void configurepatchFileChooser(
            final FileChooser fileChooser) {      
                fileChooser.setTitle("Search patch files");
                fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.dir"))
                );                 
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Patch file", "*.bps")
                );
      }

}
