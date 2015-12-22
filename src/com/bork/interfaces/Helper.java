package com.bork.interfaces;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/10/2015
 * <p>
 * §DESCRIPTION§
 */

public interface Helper {

    /**
     * Removes duplicate information from newFile. It is assumed that both input files have the same file extension.
     * @param oldFile the first input file which is the base for the operation
     * @param newFile the second input file which contains edited and new data only
     * @return a String containing information about the result
     */
    void removeDuplicates(File oldFile, File newFile, File saveFile);

}
