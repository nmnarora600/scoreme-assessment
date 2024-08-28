package in.namanarora.scoreme.controller;
import in.namanarora.scoreme.services.PdfSegmentationService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.io.FileOutputStream;


@RestController
public class PdfSegmenterController {

    @Autowired
    private PdfSegmentationService pdfSegmentationService;

    @PostMapping("/segment")
    public ResponseEntity<String> segmentPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("cuts") int cuts) {
        try {

            File pdfFile = convertMultipartFileToFile(file);

            try(PDDocument document = PDDocument.load(pdfFile)) {
                if (cuts > document.getNumberOfPages()){

                    pdfSegmentationService.segmentPDF(pdfFile, document.getNumberOfPages());}
                else pdfSegmentationService.segmentPDF(pdfFile, cuts-1);
            }

            return new ResponseEntity<>("PDF segmented successfully.", HttpStatus.OK);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Error processing PDF.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to convert MultipartFile to File
    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file with the same name as the original file
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());

        // Transfer the content of the multipart file to the newly created file
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }

        return convFile;
    }
}
