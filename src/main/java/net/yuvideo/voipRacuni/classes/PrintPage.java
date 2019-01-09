package net.yuvideo.voipRacuni.classes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/8/17.
 */
public class PrintPage {

  Font fotNorma = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-Regular.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 8);
  Font font = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-Bold.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 10);
  Font fontBold = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-ExtraBold.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 10);
  Font fontLargeBold = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-ExtraBold.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 20);
  DecimalFormat df = new DecimalFormat("#,###,##0.00");
  private PdfWriter writer;
  private Document doc;

  public PrintPage(userRacun racun, Document doc, PdfWriter pdfWriter) {
    this.doc = doc;
    this.writer = pdfWriter;

    createFrontPage(racun);
    doc.newPage();
    //createBackPage(racun);
    //doc.newPage();
  }

  private void createFrontPage(userRacun racun) {

    //TOP PODACI
    font.setSize(8);
    String brRacuna;
    LocalDate racunGodinaUgoovor = LocalDate
        .parse(racun.getPeriodDo(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    brRacuna =
        racun.getUser().getBrUgovora().replace("/", "") + "-" + racunGodinaUgoovor.getMonthValue();

    String topR = String.format(""
            + "Račun br.: %s\n"
            + "Ugovor br.: %s\n"
            + "Datum izdavanja: %s\n"
            + "Za period od %s do %s\n"
            + "Rok plaćanja %s", brRacuna, racun.getUser().getBrUgovora(),
        LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), racun.getPeriodOd(),
        racun.getPeriodDo(), racun.getRokPlacanja());
    Paragraph p = new Paragraph(topR, font);
    p.setAlignment(Element.ALIGN_RIGHT);

    PdfContentByte canvas = writer.getDirectContent();
    LocalDate zaPerodDo = LocalDate
        .parse(racun.getPeriodDo(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    LocalDate zaPeriodOd = LocalDate
        .parse(racun.getPeriodOd(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    String modelIPozivNaBroj =
        racun.getUser().getPozivNaBroj() + zaPeriodOd.getYear() + "/" + zaPerodDo.getMonthValue();

    int X = 320;
    int Y = 665;

    canvas.saveState();

    //KORISNICKI PODACI
    if (!racun.getUser().isFirma()) {
      canvas.setFontAndSize(fontLargeBold.getBaseFont(), 11);
      canvas.beginText();
      canvas.moveText(X, Y);
      canvas.showText(racun.getUser().getIme());
      canvas.endText();
      canvas.beginText();
      canvas.moveText(X, Y - 20);
      canvas.showText(racun.getUser().getAdresa());
      canvas.endText();
      canvas.beginText();
      canvas.moveText(X, Y - 40);
      canvas.showText(racun.getUser().getMesto() + " " + racun.getUser().getPostBr());
      canvas.endText();
    } else {
      canvas.setFontAndSize(fontLargeBold.getBaseFont(), 11);
      canvas.beginText();
      canvas.moveText(X, Y);
      canvas.showText(racun.getUser().getNazivFirme());
      canvas.endText();
      canvas.beginText();
      canvas.moveText(X, Y - 20);
      canvas.showText(racun.getUser().getAdresa());
      canvas.endText();
      canvas.beginText();
      canvas.moveText(X, Y - 40);
      canvas.showText(racun.getUser().getMesto() + " " + racun.getUser().getPostBr());
      canvas.endText();
      canvas.beginText();
      canvas.moveText(X, Y - 60);
      canvas.showText("PIB: " + racun.getUser().getPib());
      canvas.endText();
      canvas.beginText();
      canvas.moveText(X, Y - 80);
      canvas.showText("Matični broj: " + racun.getUser().getMbr());
      canvas.endText();
    }

    //CENTAR PRETPLATA LEFT
    X = 100;
    Y = 500;

    canvas.setFontAndSize(font.getBaseFont(), 13);
    canvas.beginText();
    canvas.moveText(X, Y);
    canvas.showText("Pretplata:");
    canvas.endText();
    canvas.beginText();
    canvas.moveText(X, Y - 20);

    canvas.showText("Potrošnja:");
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, -40);
    canvas.showText("Ukupno:");
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 80);
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 100);
    canvas.showText("PDV: " + racun.getPDV() + "%");
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 120);
    canvas.showText("Prethodni dug:");
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 140);
    canvas.showText("Ukupno za uplatu:");
    canvas.endText();

    //CENTAR PRETPLATA RIGHT
    X = 300;
    Y = 500;

    String din = " din.";

    canvas.beginText();
    canvas.moveText(X, Y);
    canvas.showText(df.format(racun.getPretplata()) + din);
    canvas.endText();
    canvas.beginText();
    canvas.moveText(X, Y - 20);

    canvas.showText(df.format(racun.getPotrosnja()) + din);
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, -40);
    canvas.showText(df.format(racun.getZaUplatu()) + din);
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 80);
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 100);
    canvas.showText(df.format(
        valueToPercent.getValue(racun.getPretplata() + racun.getPotrosnja(), racun.getPDV()))
        + "din.");
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 120);
    canvas.showText(df.format(racun.getPrethodniDug()) + din);
    canvas.endText();

    canvas.beginText();
    canvas.moveText(X, Y - 140);
    canvas.showText(df.format(racun.getZaUplatu()) + din);
    canvas.endText();

    //BOTTOM RACUN LEFT USER DATA
    canvas.setFontAndSize(fotNorma.getBaseFont(), 8);
    canvas.beginText();
    canvas.moveText(52, 215);
    if (racun.getUser().isFirma()) {
      canvas.showText(racun.getUser().getNazivFirme());
    } else {
      canvas.showText(racun.getUser().getIme());
    }
    canvas.endText();

    canvas.beginText();
    canvas.moveText(48, 205);
    canvas.showText(String.format("%s, %s", racun.getUser().getAdresa(), racun.getUser().mesto));
    canvas.endText();

    canvas.beginText();
    canvas.moveText(164, 195);
    canvas.showText(String
        .format("%s-%s.%s.%s", zaPeriodOd.getDayOfMonth(), zaPerodDo.getDayOfMonth(),
            zaPerodDo.getMonthValue(), zaPerodDo.getYear()));
    canvas.endText();

    canvas.beginText();
    canvas.moveText(280, 195);
    canvas.showText(String.format("%s", racun.getRokPlacanja()));
    canvas.endText();

    //BOTTOM RACUN RIGHT USER DATA
    canvas.setFontAndSize(fotNorma.getBaseFont(), 8);
    canvas.beginText();
    canvas.moveText(388, 217);
    if (racun.getUser().isFirma()) {
      canvas.showText(racun.getUser().getNazivFirme());
    } else {
      canvas.showText(racun.getUser().getIme());
    }
    canvas.endText();

    canvas.beginText();
    canvas.moveText(385, 207);
    canvas.showText(String.format("%s, %s", racun.getUser().getAdresa(), racun.getUser().mesto));
    canvas.endText();

    canvas.beginText();
    canvas.moveText(500, 197);
    canvas.showText(String
        .format("%s-%s.%s.%s", zaPeriodOd.getDayOfMonth(), zaPerodDo.getDayOfMonth(),
            zaPerodDo.getMonthValue(), zaPerodDo.getYear()));
    canvas.endText();

    //BOTTTOM RACUN LEFT
    canvas.setFontAndSize(fontBold.getBaseFont(), 10);
    canvas.beginText();
    canvas.moveText(230, 170);
    canvas.showText(df.format(racun.getZaUplatu()));
    canvas.endText();

    canvas.beginText();
    canvas.moveText(205, 105);
    canvas.showText(modelIPozivNaBroj);
    canvas.endText();

    //BOTOM RACUN RIGHT
    canvas.beginText();
    canvas.moveText(490, 170);
    canvas.showText(df.format(racun.getZaUplatu()));
    canvas.endText();

    canvas.beginText();
    canvas.moveText(460, 105);
    canvas.showText(modelIPozivNaBroj);
    canvas.endText();

    canvas.restoreState();

    try {
      doc.add(p);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

  }

  private void createBackPage(userRacun racun) {
    Font fontregular = FontFactory
        .getFont(getClass().getResource("/Fonts/OpenSans-Bold.ttf").toExternalForm(),
            BaseFont.IDENTITY_H, 7);
    Font fontBold = FontFactory
        .getFont(getClass().getResource("/Fonts/OpenSans-ExtraBold.ttf").toExternalForm(),
            BaseFont.IDENTITY_H, 8);

    ArrayList<destination> destinationArrayList = racun.getDestinacija();
    destination destination;
    doc.newPage();

    Rectangle small = new Rectangle(200, 100);
    PdfPTable table = new PdfPTable(6);
    table.setWidthPercentage(100);

    PdfPCell pcell = new PdfPCell(new Phrase("SPECIFIKACIJA POTROŠNJE:", fontBold));
    pcell.setColspan(6);
    pcell.setBorder(0);
    table.addCell(pcell);

    pcell = new PdfPCell(new Phrase("BROJ: " + racun.getUser().getBrojTelefona(), fontBold));
    pcell.setColspan(6);
    pcell.setBorder(0);
    table.addCell(pcell);

    PdfPCell cellH1 = new PdfPCell(new Phrase("Destinacija", fontBold));
    cellH1.setBackgroundColor(BaseColor.LIGHT_GRAY);

    table.addCell(cellH1).setHorizontalAlignment(Element.ALIGN_CENTER);
    PdfPCell cellH2 = new PdfPCell(new Phrase("Utrošeno minuta", fontBold));
    cellH2.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH2).setHorizontalAlignment(Element.ALIGN_CENTER);
    PdfPCell cellH3 = new PdfPCell(new Phrase("Gratis minuta", fontBold));
    cellH3.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH3).setHorizontalAlignment(Element.ALIGN_CENTER);
    PdfPCell cellH4 = new PdfPCell(new Phrase("Minuta za naplatu", fontBold));
    cellH4.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH4).setHorizontalAlignment(Element.ALIGN_CENTER);
    PdfPCell cellH5 = new PdfPCell(new Phrase("Cena po minutu", fontBold));
    cellH5.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH5).setHorizontalAlignment(Element.ALIGN_CENTER);
    PdfPCell cellH6 = new PdfPCell(new Phrase("Ukupno", fontBold));
    cellH6.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH6).setHorizontalAlignment(Element.ALIGN_CENTER);

    PdfPCell cll;

    //ako korisnik nema nikakvu potrosnju staviti "-" umesto praznig polja (cisto zbog izgleda :))
    if (destinationArrayList.size() == 0) {
      destination = new destination();
      destination.setMinutaZaNaplatu(0);
      destination.setGratisMinuta(0);
      destination.setUkupno(0.00);
      destination.setNazivDestinacijeZone("-");
      destination.setUtrosenoMinuta(0);
      destination.setCenaPoMinutu(0);
      destinationArrayList.add(destination);

    }
    for (int i = 0; i < destinationArrayList.size(); i++) {
      destination = destinationArrayList.get(i);

      cll = new PdfPCell(new Phrase(destination.getNazivDestinacijeZone(), fontregular));
      cll.setHorizontalAlignment(Element.ALIGN_CENTER);
      cll.setVerticalAlignment(Element.ALIGN_MIDDLE);

      table.addCell(cll);

      cll = new PdfPCell(new Phrase(String.valueOf(destination.getUtrosenoMinuta()), fontregular));
      cll.setHorizontalAlignment(Element.ALIGN_CENTER);
      cll.setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.addCell(cll);

      cll = new PdfPCell(new Phrase(String.valueOf(destination.getGratisMinuta()), fontregular));
      cll.setHorizontalAlignment(Element.ALIGN_CENTER);
      cll.setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.addCell(cll);

      cll = new PdfPCell(new Phrase(String.valueOf(destination.getMinutaZaNaplatu()), fontregular));
      cll.setHorizontalAlignment(Element.ALIGN_CENTER);
      cll.setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.addCell(cll);

      cll = new PdfPCell(new Phrase(df.format(destination.getCenaPoMinutu()), fontregular));
      cll.setHorizontalAlignment(Element.ALIGN_CENTER);
      cll.setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.addCell(cll);

      cll = new PdfPCell(
          new Phrase(String.valueOf(df.format(destination.getUkupno())), fontregular));
      cll.setVerticalAlignment(Element.ALIGN_MIDDLE);
      cll.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cll);

    }

    cellH1 = new PdfPCell(
        new Phrase(String.format("UKUPNO: %s din.", df.format(racun.getPotrosnja())), fontBold));
    cellH1.setColspan(6);
    cellH1.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cellH1.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH1);

    try {
      doc.add(table);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    //createBackPageLog(racun);
  }

  private void createBackPageLog(userRacun racun) {
    ArrayList<destination> destinacija = racun.getDestinacija();
    Double ukupn = 0.00;

    System.out.println(
        "====================================================================================================================================================================================");
    System.out.println(String
        .format("Korisnik: %s, Broj tel: %s Model i poziv na broj: %s", racun.getUser().getIme(),
            racun.getUser().getBrojTelefona(), racun.getUser().pozivNaBroj));
    System.out.println(
        "====================================================================================================================================================================================");

    System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s\n", "Destinacija", "Utroseno minuta",
        "Gratis minuta", "Minuta za naplatu", "Cena po minutu", "Ukupno");
    for (int i = 0; i < destinacija.size(); i++) {
      System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s\n",
          destinacija.get(i).getNazivDestinacije(),
          destinacija.get(i).getUtrosenoMinuta(),
          destinacija.get(i).getGratisMinuta(),
          destinacija.get(i).getMinutaZaNaplatu(),
          destinacija.get(i).getCenaPoMinutu(),
          destinacija.get(i).getUkupno()
      );
      ukupn += destinacija.get(i).getUkupno();
    }
    System.out.println(
        "====================================================================================================================================================================================");
    System.out.printf("%180s", "UKUPNO Saobracaj: " + df.format(ukupn) + "din.\n");
    System.out.printf("%180s", "DUG:" + df.format(racun.getZaUplatu()) + "din.\n\n");

  }

}
