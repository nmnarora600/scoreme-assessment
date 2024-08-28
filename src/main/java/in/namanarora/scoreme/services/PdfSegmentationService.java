package in.namanarora.scoreme.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PdfSegmentationService {

    public void segmentPDF(File pdfFile, int numberOfCuts) throws IOException {

        try (PDDocument document = PDDocument.load(pdfFile)) {

            CustomPDFStripper stripper = new CustomPDFStripper();
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());
            stripper.getText(document); // Trigger text extraction

            // Extract text positions
            List<TextPosition> textPositions = stripper.getTextPositions();
            // Determine cut positions based on text positions
            List<Integer> cutPositions = SegmentUtil.determineCuts(textPositions, numberOfCuts);

            System.out.println("Determined cut positions: " + cutPositions);

            // Create segmented PDFs based on the cut positions
            SegmentUtil.createSegmentedPDFs(document, cutPositions);
        }
    }
}
