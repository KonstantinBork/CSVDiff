package com.bork.interfaces;

import com.bork.exceptions.FileNotSupportedException;
import com.bork.exceptions.NotSameTypeException;

import java.io.File;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/08/2015
 * <p>
 * §DESCRIPTION§
 */

public interface Controller {

    /**
     * Improves the content of both files, removes duplicates between both files and saves the result into {@param saveFile}.
     *
     * @param oldFile
     * @param newFile
     * @param saveFile
     * @throws NullPointerException
     * @throws NotSameTypeException
     */
    void processFiles(File oldFile, File newFile, File saveFile) throws NullPointerException, NotSameTypeException, FileNotSupportedException;

    /**
     * Sets the current progress to {@param value}.
     *
     * @param value
     */
    void setProgress(double value);

}