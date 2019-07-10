package io.metadew.flap.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.metadew.flab.common.FolderTools;

public final class AssemblerTools {

    @SuppressWarnings("resource")
    public static void createSolutionStructure(String configurationFilePath, String targetFolderPath) {
        try {
            File file = new File(configurationFilePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null) {
                String innerpart = readLine.trim();
                String[] parts = innerpart.split(";");
                FolderTools.createFolder(targetFolderPath + parts[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Issue creating solution structure", e);
        }
    }

}