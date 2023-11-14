import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;


@ApplicationScoped
public class FileParser {

    public static FileParserResult parseFile(File file) throws Exception {
        TesseractOCRConfig config = new TesseractOCRConfig();
        config.setSkipOcr(true);
        ParseContext context = new ParseContext();
        context.set(TesseractOCRConfig.class, config);

        FileInputStream stream = new FileInputStream(file);
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();

        parser.parse(stream, handler, metadata, context);
        StringWriter writer = new StringWriter();
        JsonMetadata.toJson(metadata, writer);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonMetadata = objectMapper.readValue(writer.toString(), JsonNode.class);

        String content = handler.toString();

        return new FileParserResult(content, jsonMetadata);
    }

    public static String detectLanguage(String text) {
        LanguageDetector detector = new OptimaizeLangDetector().loadModels();
        LanguageResult result = detector.detect(text);
        return result.getLanguage();
    }
}
