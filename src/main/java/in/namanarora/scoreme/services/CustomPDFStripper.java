package in.namanarora.scoreme.services;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomPDFStripper extends PDFTextStripper {

    private List<TextPosition> textPositions = new ArrayList<>();

    public CustomPDFStripper() throws IOException {
        super();
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        this.textPositions.addAll(textPositions);
        super.writeString(text, textPositions);
    }

    public List<TextPosition> getTextPositions() {
        return textPositions;
    }

    public void clearTextPositions() {
        textPositions.clear();
    }

    public List<List<TextPosition>> identifyTextSegments(double minWhitespaceHeight) {
        List<List<TextPosition>> segments = new ArrayList<>();
        List<TextPosition> currentSegment = new ArrayList<>();

        if (textPositions.isEmpty()) {
            return segments;
        }

        currentSegment.add(textPositions.get(0));

        for (int i = 1; i < textPositions.size(); i++) {
            TextPosition position = textPositions.get(i);
            TextPosition previousPosition = textPositions.get(i - 1);

            float spaceHeight = position.getY() - (previousPosition.getY() + previousPosition.getHeight());

            if (spaceHeight > minWhitespaceHeight) {
                segments.add(currentSegment);
                currentSegment = new ArrayList<>();
            }

            currentSegment.add(position);
        }

        if (!currentSegment.isEmpty()) {
            segments.add(currentSegment);
        }

        return segments;
    }
}
