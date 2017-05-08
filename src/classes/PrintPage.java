package classes;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/8/17.
 */
public class PrintPage {
    PDPageContentStream pdPageContentStream;
    PDDocument document;
    PDPage frontPage = new PDPage();
    PDPage backPage;

    public PrintPage(Users user, ArrayList<CSVData> csvData, String starDate, String stopDate) {
        document = new PDDocument();
        frontPage = new PDPage(PDRectangle.A4);
        backPage = new PDPage(PDRectangle.A4);

        document.addPage(frontPage);
        try {


            pdPageContentStream = new PDPageContentStream(document, frontPage);
            //topRight ugovor datumizdavanja;
            pdPageContentStream.beginText();
            pdPageContentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            pdPageContentStream.newLineAtOffset(frontPage.getMediaBox().getUpperRightX() - 200, frontPage.getMediaBox().getUpperRightY() - 50);
            pdPageContentStream.showText("Ugovor br.: " + user.getBrUgovora());
            pdPageContentStream.endText();
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset(frontPage.getMediaBox().getUpperRightX() - 200, frontPage.getMediaBox().getUpperRightY() - 60);
            pdPageContentStream.showText("Datum izdavanja: " + LocalDate.now().toString());
            pdPageContentStream.endText();
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset(frontPage.getMediaBox().getUpperRightX() - 200, frontPage.getMediaBox().getUpperRightY() - 70);
            pdPageContentStream.showText("Za period od: " + starDate + " do " + stopDate);
            pdPageContentStream.endText();
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset(frontPage.getMediaBox().getUpperRightX() - 200, frontPage.getMediaBox().getUpperRightY() - 80);
            pdPageContentStream.endText();
            pdPageContentStream.beginText();
            pdPageContentStream.showText("Rok placanja: " + LocalDate.parse(stopDate).plusMonths(1));
            pdPageContentStream.endText();
            pdPageContentStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            document.save(new File("/home/zoom/test.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void createFrontPage() {
        frontPage = new PDPage();

    }
}
