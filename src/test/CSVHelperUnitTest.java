import com.bork.interfaces.Helper;
import com.bork.util.csv.CSVHelper;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * CSVDiff
 *
 * @author Konstantin Bork
 * @version 0.1
 * @created 12/17/2015
 * <p>
 * §DESCRIPTION§
 */

public class CSVHelperUnitTest {

    @Test
    public void removeDuplicates_validFiles_duplicatesAreRemoved() {
        Helper helper = new CSVHelper();
        File file1 = new File("");
        File file2 = new File("");
        File file3 = new File("");
        String message = helper.removeDuplicates(file1, file2, file3);
        assertEquals("New file successfully shortened!", message);
    }

}