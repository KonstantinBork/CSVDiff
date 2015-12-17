import com.bork.main.CSVDiffController;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
        String message = controller.processFiles(file1, file2, file2);
        assertEquals("Please import files!", message);
    }

    @Test
    public void processFiles_oneFileIsNull_messageSaysFileIsNull() {
        try {
            File file1 = null;
            File file2 = File.createTempFile("temp", ".tmp");
            CSVDiffController controller = new CSVDiffController();
            String message = controller.processFiles(file1, file2, file2);
            assertEquals("Please import files!", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processFiles_inputFilesAreNotOfSameType_messageSaysFilesHaveDifferentTypes() {
        try {
            File file1 = File.createTempFile("temp1", ".tm");
            File file2 = File.createTempFile("temp2", ".tmp");
            CSVDiffController controller = new CSVDiffController();
            String message = controller.processFiles(file1, file2, file2);
            assertEquals("Please check your files, they are not of the same type!", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processFiles_inputFilesHaveUnsupportedType_messageSaysTypeIsUnsupported() {
        try {
            File file1 = File.createTempFile("temp1", ".tmp");
            File file2 = File.createTempFile("temp2", ".tmp");
            CSVDiffController controller = new CSVDiffController();
            String message = controller.processFiles(file1, file2, file2);
            assertEquals("File type is not supported!", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}