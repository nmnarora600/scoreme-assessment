package in.namanarora.scoreme;

import in.namanarora.scoreme.services.PdfSegmentationService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PdfServiceTest {

    @InjectMocks
    private PdfSegmentationService pdfService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSplitPdf() throws IOException {
        File testFile = ResourceUtils.getFile("classpath:test.pdf"); // Ensure this file exists in your test resources


        // Call the method to test with the mockDocument
        pdfService.segmentPDF(testFile, 5); // Pass the mockDocument



        // Check the output directory
        Path outputDirectory = Path.of("oops"); // Replace with actual path used in segmentPDF
        List<Path> files = Files.list(outputDirectory).toList();

        // Check if the number of files matches the expected number of segments
        int expectedFileCount = 5;
        assertEquals(expectedFileCount, files.size(), "The number of segmented PDF files is incorrect");
    }
}
