package classes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTextArray;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/8/17.
 */
public class PrintPage {
    Document doc;
    Font font = FontFactory.getFont(getClass().getResource("/Fonts/OpenSans-Regular.ttf").toExternalForm(), BaseFont.IDENTITY_H, 10);
    Paragraph p;
    PdfWriter writer;


    public PrintPage(Users user, ArrayList<CSVData> csvData, String startDate, String stopDate, Paketi userPaket) {
        doc = new Document(new Rectangle(PageSize.A4), 14, 14, 60, 14);
        try {
            writer = PdfWriter.getInstance(doc, new FileOutputStream("/home/zoom/test.pdf"));
            doc.open();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createFrontPage(user, csvData, startDate, stopDate, userPaket);

    }

    private void createFrontPage(Users user, ArrayList<CSVData> csvData, String startDate, String stopDate, Paketi userPaket) {
        String startD = LocalDate.parse(startDate).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String endD = LocalDate.parse(stopDate).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        String topR = String.format("" +
                "Ugovor br.: %s\n" +
                "Datum izdavanja: %s\n" +
                "Za period od %s do %s\n" +
                "Rok Plaćanja %s", user.getBrUgovora(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), startD, endD, "25.05.2007");
        Paragraph p = new Paragraph(topR, font);
        p.setAlignment(Element.ALIGN_RIGHT);

        Phrase phraseImeAdresa = new Phrase(user.getIme() + "\n" + user.getAdresa() + "\n" + user.getPostBr() + " " + user.getMesto(), font);
        PdfContentByte canvas = writer.getDirectContent();
        PdfTextArray textArray = new PdfTextArray();
        int X = 320;
        int Y = 665;


        canvas.saveState();

        canvas.setFontAndSize(font.getBaseFont(), 13);
        canvas.beginText();
        canvas.moveText(X, Y);
        canvas.showText(user.getIme());
        canvas.endText();
        canvas.beginText();
        canvas.moveText(X, Y - 20);
        canvas.showText(user.getAdresa());
        canvas.endText();
        canvas.beginText();
        canvas.moveText(X, Y - 40);
        canvas.showText(user.getMesto() + " " + user.getPostBr());
        canvas.endText();

        canvas.restoreState();


        try {
            doc.add(p);
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    private void createBackPage() {
    }

}
