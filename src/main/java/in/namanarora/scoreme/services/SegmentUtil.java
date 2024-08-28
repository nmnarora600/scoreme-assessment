package in.namanarora.scoreme.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SegmentUtil {

    public static List<Integer> determineCuts(List<TextPosition> textPositions, int numberOfCuts) {
        // Analyze text positions and calculate whitespace gaps
        List<Float> yPositions = textPositions.stream()
                                                .map(TextPosition::getY)
                                                .distinct()
                                                .sorted()
                                                .toList();

        List<Integer> gaps = new ArrayList<>();
        for (int i = 1; i < yPositions.size(); i++) {
            gaps.add((int) (yPositions.get(i) - yPositions.get(i - 1)));
        }

        return gaps.stream()
                .sorted(Collections.reverseOrder())
                .limit(numberOfCuts)
                .collect(Collectors.toList());
    }


    private static void clearDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    } else if (file.isDirectory()) {
                        clearDirectory(file);
                        file.delete();
                    }
                }
            }
        }
    }
    public static void createSegmentedPDFs(PDDocument document, List<Integer> cutPositions) throws IOException {
        // Ensure the cut positions are sorted and unique
        String outputDirectory = "oops";
        cutPositions = cutPositions.stream().distinct().sorted().collect(Collectors.toList());
        int pageCount = document.getNumberOfPages();

        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        } else {
            clearDirectory(directory);

        }
        for (int i = 0; i < cutPositions.size(); i++) {
            int cutPosition = cutPositions.get(i);

            // Ensure cutPosition is within bounds
            if (cutPosition >= pageCount) {
                cutPosition = pageCount - 1;
            }

            PDDocument newDoc = new PDDocument();
            PDPage page = document.getPage(i);
            newDoc.addPage(page);

            // Use append mode to avoid overwriting existing content
            try (PDPageContentStream contentStream = new PDPageContentStream(newDoc, page, PDPageContentStream.AppendMode.APPEND, true)) {
                // Here you can add additional content if needed
                System.out.println("Content Stream=" + contentStream);
            }

            String outputFileName = directory + File.separator + "Segment_" + i + ".pdf";
            newDoc.save(outputFileName);
            newDoc.close();
        }

        // Handle the last segment if necessary
        if (cutPositions.size() > 0 && cutPositions.get(cutPositions.size() - 1) < pageCount - 1) {
        PDDocument lastSegmentDoc = new PDDocument();
        int startPage = cutPositions.get(cutPositions.size() - 1) + 1;
        for (int pageIndex = startPage; pageIndex < pageCount; pageIndex++) {
            PDPage page = document.getPage(pageIndex);
            lastSegmentDoc.addPage(page);
        }


        String lastSegmentFileName = directory + File.separator + "Segment_" + cutPositions.size() + ".pdf";
        lastSegmentDoc.save(lastSegmentFileName);
        lastSegmentDoc.close();
    }

    }
}
