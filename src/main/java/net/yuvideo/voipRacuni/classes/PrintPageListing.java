/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.voipRacuni.classes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 * @author PsyhoZOOM
 */
public class PrintPageListing {

  Font fotNorma = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-Regular.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 8);
  Font font = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-Regular.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 10);
  Font fontBold = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-ExtraBold.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 10);
  Font fontLargeBold = FontFactory
      .getFont(getClass().getResource("/Fonts/OpenSans-ExtraBold.ttf").toExternalForm(),
          BaseFont.IDENTITY_H, 20);
  DecimalFormat df = new DecimalFormat("#,###,##0.00");

  private Document document = new Document(new Rectangle(PageSize.A4), 14, 14, 60, 14);
  private ObservableList<izvestajPotrosnje> listing;
  private Users user;
  private String period;

  public PrintPageListing(ObservableList<izvestajPotrosnje> items, Users user, String period) {
    this.user = user;
    this.listing = items;
    this.period = period;
  }


  public void createListing() {

    document.open();
    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);

    //HEADERS
    PdfPCell cell = new PdfPCell(new Phrase("IZVEŠTAJ-LISTING", fontBold));
    cell.setColspan(5);
    cell.setBorder(0);
    table.addCell(cell);

    //HEADER KORISNIK
    String korisnik;
    if (user.isFirma()) {
      korisnik = user.getNazivFirme();
    } else {
      korisnik = user.getIme();
    }
    cell = new PdfPCell(
        new Phrase(String.format("%s - %s - %s", korisnik, user.getBrojTelefona(), this.period),
            font));

    cell.setColspan(5);
    cell.setBorder(0);
    table.addCell(cell);

    //HEADER TABLE
    PdfPCell cellH1 = new PdfPCell(new Phrase("br.", fontBold));
    cellH1.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH1).setHorizontalAlignment(Element.ALIGN_CENTER);

    PdfPCell cellH2 = new PdfPCell(new Phrase("Pozivani broj", fontBold));
    cellH2.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH2).setHorizontalAlignment(1);

    PdfPCell cellH3 = new PdfPCell(new Phrase("Destinacija", fontBold));
    cellH3.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH3).setHorizontalAlignment(1);

    PdfPCell cellH4 = new PdfPCell(new Phrase("Vreme", fontBold));
    cellH4.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH4).setHorizontalAlignment(1);

    PdfPCell cellH5 = new PdfPCell(new Phrase("Trajanje poziva", fontBold));
    cellH5.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(cellH5).setHorizontalAlignment(1);

    PdfPCell cellIzvestaj;
    int ukupno = 0;

    for (izvestajPotrosnje izvestaj : listing) {
      //potrosnja ukupno

      //Redni broj
      cellIzvestaj = new PdfPCell(new Phrase(String.valueOf(izvestaj.br), font));
      cellIzvestaj.setHorizontalAlignment(1);
      cellIzvestaj.setVerticalAlignment(5);
      table.addCell(cellIzvestaj);

      //Pozivani broj
      cellIzvestaj = new PdfPCell(new Phrase(izvestaj.getSource(), font));
      cellIzvestaj.setHorizontalAlignment(1);
      cellIzvestaj.setVerticalAlignment(5);
      table.addCell(cellIzvestaj);

      //Destinacija
      cellIzvestaj = new PdfPCell(new Phrase(izvestaj.getDestination(), font));
      cellIzvestaj.setHorizontalAlignment(1);
      cellIzvestaj.setVerticalAlignment(5);
      table.addCell(cellIzvestaj);

      //Vreme
      cellIzvestaj = new PdfPCell(new Phrase(izvestaj.getDatumPoziva(), font));
      cellIzvestaj.setHorizontalAlignment(1);
      cellIzvestaj.setVerticalAlignment(5);
      table.addCell(cellIzvestaj);

      //trajanje poziva
      cellIzvestaj = new PdfPCell(new Phrase(izvestaj.getTrajanje(), font));
      cellIzvestaj.setHorizontalAlignment(1);
      cellIzvestaj.setVerticalAlignment(5);
      table.addCell(cellIzvestaj);

      ukupno = izvestaj.getUkupno_poziv();
    }

    PdfPCell footer = new PdfPCell(new Phrase(String.format("Ukupno: %s",
        LocalTime.MIN.plusSeconds(ukupno).toString()
    ), fontLargeBold));
    footer.setColspan(5);
    footer.setHorizontalAlignment(2);
    footer.setBorder(0);
    table.addCell(footer);

    try {
      document.add(table);
    } catch (DocumentException ex) {
      Logger.getLogger(PrintPageListing.class.getName()).log(Level.SEVERE, null, ex);
    }

    document.close();

  }


  public Document getDocument() {
    return this.document;
  }


}
