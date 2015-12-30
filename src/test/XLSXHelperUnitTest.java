import com.bork.interfaces.Helper;
import com.bork.main.CSVDiffController;
import com.bork.util.excel.XLSXHelper;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/17/2015
 * <p>
 * §DESCRIPTION§
 */

public class XLSXHelperUnitTest {

    @Test
    public void removeDuplicates_validFiles_duplicatesAreRemoved() {
        Helper helper = new XLSXHelper(new CSVDiffController());
        String path = "/Users/konstantin/Documents/IDEA Projects/CSVDiff/files/";
        File file1 = new File(path + "Arbeitszeiten Konstantin Bork August 2015.xlsx");
        File file2 = new File(path + "Arbeitszeiten Konstantin Bork August 2016.xlsx");
        File file3 = new File(path + "test.xlsx");
        helper.removeDuplicates(file1, file2, file3);
        path += "numeric/";
        file1 = new File(path + "test1.xlsx");
        file2 = new File(path + "test2.xlsx");
        file3 = new File(path + "test.xlsx");
        helper.removeDuplicates(file1, file2, file3);
    }

}