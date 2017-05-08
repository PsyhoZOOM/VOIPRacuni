package classes;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.vandeseer.pdfbox.easytable.Cell;
import org.vandeseer.pdfbox.easytable.Row;
import org.vandeseer.pdfbox.easytable.Table;

import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/8/17.
 */
public class PDFDataUser {
    PDPageContentStream contentStream;
    PDDocument document;
    PDPage page;
    PDPage backPage;

    String imePrezime;
    String ulica;
    String mesto;
    String ugovor;
    String datumIzdavanja;
    String zaPeriod;
    String rokPlacanja;
    String pretplata;
    String potrosnja;
    String ukupno;
    String pdv;
    String prethodniDug;
    String ukupnoZauplatu;
    String brojTelefona;


    public PDFDataUser() {

    }

    PDPage getBackPage(ArrayList data) {
        backPage = new PDPage(PDRectangle.A4);

        return backPage;
    }

    private Table.TableBuilder getTableHeader() {
        Table.TableBuilder tableBuilder = new Table.TableBuilder()
                .addColumnOfWidth(30)
                .addColumnOfWidth(30)
                .addColumnOfWidth(30)
                .addColumnOfWidth(30)
                .addColumnOfWidth(30)
                .addColumnOfWidth(30)
                .setFontSize(8)
                .setFont(PDType1Font.HELVETICA);

        tableBuilder.addRow(new Row.RowBuilder()
                .add(Cell.withText("Destinacija"))
                .add(Cell.withText("Utrošeno minuta"))
                .add(Cell.withText("Gratis minuta"))
                .add(Cell.withText("Minuta za naplatu"))
                .add(Cell.withText("Cena po minutu"))
                .add(Cell.withText("Ukupno"))
                .build());

        return tableBuilder;

    }

    private Table.TableBuilder getTableBody(Table.TableBuilder tableBuilder, ArrayList data) {
        Row.RowBuilder rowBuilder;
        for (int i = 0; i < data.size(); i++) {
            rowBuilder = new Row.RowBuilder();
            rowBuilder
                    .add(Cell.withText(data.get(i)).withAllBorders())
                    .add(Cell.withText(data.get(i + 1)).withAllBorders())
                    .add(Cell.withText(data.get(i + 1)).withAllBorders())
                    .add(Cell.withText(data.get(i + 1)).withAllBorders())
                    .add(Cell.withText(data.get(i + 1)).withAllBorders())
                    .add(Cell.withText(data.get(i + 1)).withAllBorders());
            tableBuilder.addRow(rowBuilder.build());
        }

        //add UKUPNO to TAble
        rowBuilder = new Row.RowBuilder();
        rowBuilder.add(Cell.withText("UKUPNO").withAllBorders());
        rowBuilder.add(Cell.withText(ukupno).withAllBorders().setHorizontalAlignment(Cell.HorizontalAlignment.RIGHT));
        tableBuilder.addRow(rowBuilder.build());


        return tableBuilder;
    }


}
