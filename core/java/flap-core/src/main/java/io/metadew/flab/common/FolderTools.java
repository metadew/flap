package io.metadew.flab.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FolderTools {

    // Copy operations
    public static void copyFromFolderToFolder(String sourceFolder, String targetFolder, boolean createFolders) {
        if (createFolders) {
            copyFromFolderToFolderWithFolderCreation(sourceFolder, targetFolder);
        } else {
            copyFromFolderToFolderWithoutFolderCreation(sourceFolder, targetFolder);
        }

    }

    public static void copyFromFolderToFolderWithFolderCreation(String sourceFolder, String targetFolder) {
        File source = new File(sourceFolder);
        File target = new File(targetFolder);

        try {
            FileUtils.copyDirectory(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void copyFromFolderToFolderWithoutFolderCreation(String sourceFolder, String targetFolder) {
        final File folder = new File(sourceFolder);

        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                // Ignore
            } else {
                // Copy file
                File sourceFileName = new File(sourceFolder + File.separator + file.getName());
                File targetFileName = new File(targetFolder + File.separator + file.getName());
                FileChannel inputChannel = null;
                FileChannel outputChannel = null;
                try {
                    inputChannel = new FileInputStream(sourceFileName).getChannel();
                    outputChannel = new FileOutputStream(targetFileName).getChannel();
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        inputChannel.close();
                        outputChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    // Create Folder
    public static void createFolder(String folderPath) {
        FolderTools.createFolder(folderPath, false);
    }

    public static void createFolder(String folderPath, boolean errorIfExists) {
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                throw new RuntimeException("folder.create.error");
            }
        } else {
            if (errorIfExists) {
                throw new RuntimeException("folder.exists");
            }
        }
    }

    // Delete Folder
    public static void deleteFolder(String folderName, boolean deleteFolder) {
        File folder = new File(folderName);
        if (folder.exists()) {
            if (deleteFolder) {
                deleteRecursive(folderName);
            } else {
                for (File c : folder.listFiles())
                    deleteRecursive(c.getAbsolutePath());
            }
        } else {
        }
    }

    private static void deleteRecursive(String folder) {
        File del_folder = new File(folder);
        try {
            deleteAllFiles(del_folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAllFiles(File file) throws IOException {
        if (file.isDirectory()) {
            for (File c : file.listFiles())
                deleteRecursive(c.getAbsolutePath());
        }
        if (!file.delete())
            throw new FileNotFoundException("Failed to delete file: " + file);
    }

    // Copy Folder
    public static void copyFolderAndSubfolders(String sourceFolderName, String targetFolderName) {
        File folder = new File(sourceFolderName);
        if (folder.exists()) {
            for (File c : folder.listFiles())
                copyRecursive(c.getAbsolutePath(), sourceFolderName, targetFolderName);
        }
    }

    private static void copyRecursive(String folder, String sourceFolderName, String targetFolderName) {
        File copy_folder = new File(folder);
        try {
            copyAllFiles(copy_folder, sourceFolderName, targetFolderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyAllFiles(File file, String sourceFolderName, String targetFolderName) throws IOException {
        if (file.isDirectory()) {
            for (File c : file.listFiles())
                copyRecursive(c.getAbsolutePath(), sourceFolderName, targetFolderName);
        }
        if (!file.isDirectory()) {
            String sourceFile = file.getAbsolutePath().replace("\\", "/");
            String targetFolder = targetFolderName.replace("\\", "/");
            String sourceFolder = sourceFolderName.replace("\\", "/");
            String incrementPath = sourceFile.substring(sourceFolder.length());
            FileTools.copyFromFileToFile(sourceFile, targetFolder + incrementPath);
        }
    }

    public static boolean isFolder(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

}