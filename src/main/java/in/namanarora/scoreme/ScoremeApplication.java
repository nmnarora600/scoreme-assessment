package in.namanarora.scoreme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
@SpringBootApplication
public class ScoremeApplication {

	public static void main(String[] args) throws IOException {


		SpringApplication.run(ScoremeApplication.class, args);
	}

}
