package com.bork.interfaces;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/08/2015
 * <p/>
 * §DESCRIPTION§
 */

public interface Controller {

    /**
     * Improves the content of both files, removes duplicates between both files and saves the result into {@param saveFile}.
     * @param oldFile The older file containing data.
     * @param newFile The newer file containing data.
     * @param saveFile The file to save the result in.
     * @return
     */
    String processFiles(File oldFile, File newFile, File saveFile);

}