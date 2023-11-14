import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

@QuarkusTest
public class TikaParserTest {


    @Test
    void parsingPDFworks() throws Exception {

        URL resourceUrl = getClass().getResource("/files/test.pdf");
        Assertions.assertNotNull(resourceUrl);
        File file = new File(resourceUrl.getFile());
        Assertions.assertNotNull(file);

        FileParserResult result = FileParser.parseFile(file);
        Assertions.assertNotNull(result);

    }

}
