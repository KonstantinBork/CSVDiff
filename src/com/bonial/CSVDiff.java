package com.bonial;

import com.bonial.csv.CSVDiffController;
import com.bonial.csv.CSVDiffModel;
import com.bonial.csv.CSVDiffView;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/08/2015
 * <p/>
 * §DESCRIPTION§
 */
public class CSVDiff {

    public static void main(String[] args) {
        String startMessage =
                "Welcome to CSV Diff!\n" +
                        "Import two files and remove duplicates in the second one.";
        Logger.log(startMessage);
        Logger.log(System.getProperty("os.name"));
        CSVDiffModel model = new CSVDiffModel();
        CSVDiffController controller = new CSVDiffController(model);
        CSVDiffView view = new CSVDiffView(controller);
    }

}