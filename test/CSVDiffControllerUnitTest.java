import com.bork.exceptions.FileNotSupportedException;
import com.bork.exceptions.NotSameTypeException;
import com.bork.main.CSVDiffController;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

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

public class CSVDiffControllerUnitTest {

    @Test
    public void processFiles_bothFilesAreNull_messageSaysFileIsNull() {
        File file1 = null;
        File file2 = null;
        CSVDiffController controller = new CSVDiffController();
        try {
            controller.processFiles(file1, file2, file2, Locale.GERMANY);
        } catch (NotSameTypeException e) {
            e.printStackTrace();
        } catch (FileNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processFiles_oneFileIsNull_messageSaysFileIsNull() {
        try {
            File file1 = null;
            File file2 = File.createTempFile("temp", ".tmp");
            CSVDiffController controller = new CSVDiffController();
            controller.processFiles(file1, file2, file2, Locale.GERMANY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotSupportedException e) {
            e.printStackTrace();
        } catch (NotSameTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processFiles_inputFilesAreNotOfSameType_messageSaysFilesHaveDifferentTypes() {
        try {
            File file1 = File.createTempFile("temp1", ".tm");
            File file2 = File.createTempFile("temp2", ".tmp");
            CSVDiffController controller = new CSVDiffController();
            controller.processFiles(file1, file2, file2, Locale.GERMANY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotSupportedException e) {
            e.printStackTrace();
        } catch (NotSameTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processFiles_inputFilesHaveUnsupportedType_messageSaysTypeIsUnsupported() {
        try {
            File file1 = File.createTempFile("temp1", ".tmp");
            File file2 = File.createTempFile("temp2", ".tmp");
            CSVDiffController controller = new CSVDiffController();
            controller.processFiles(file1, file2, file2, Locale.GERMANY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotSupportedException e) {
            e.printStackTrace();
        } catch (NotSameTypeException e) {
            e.printStackTrace();
        }
    }

}