import com.bork.interfaces.Helper;
import com.bork.main.CSVDiffController;
import com.bork.util.excel.XLSHelper;
import org.junit.Test;

import java.io.File;
import java.util.Locale;

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

public class XLSHelperUnitTest {

    @Test
    public void removeDuplicates_validFiles_duplicatesAreRemoved() {
        Helper helper = new XLSHelper(new CSVDiffController());
        File file1 = new File("");
        File file2 = new File("");
        File file3 = new File("");
        helper.removeDuplicates(file1, file2, file3, Locale.GERMANY);
    }

}