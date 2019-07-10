package io.metadew.flap.core;

import java.io.File;

import io.metadew.flab.common.FolderTools;
import io.metadew.flap.common.AssemblerTools;

/**
 * Assembler
 *
 */
public class Assembler {
	public static void main(String[] args) {
		String repositoryHome = "c:/Data/metadew/flap";
		String sandboxHome = "c:/Data/metadew/sandbox/flap";
		String instanceHome = sandboxHome + File.separator + "dev";
		String versionHome = instanceHome + File.separator + "alpha";

		// Delete version directory
		FolderTools.deleteFolder(versionHome, true);

		// Recreate version directory
		FolderTools.createFolder(instanceHome);
		FolderTools.createFolder(versionHome);

		// Loop the file system configuration
		String fileSystemStructure = repositoryHome + File.separator + "core" + File.separator + "assembly"
				+ File.separator + "folder-assembly.conf";
		AssemblerTools.createSolutionStructure(fileSystemStructure, versionHome);
		
		// Copy all modules
		FolderTools.copyFromFolderToFolder(repositoryHome + File.separator + "modules", versionHome + File.separator + "modules", true);
		
	}
}
