package com.bonial.interfaces;

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

    String processFiles(File oldFile, File newFile);

}