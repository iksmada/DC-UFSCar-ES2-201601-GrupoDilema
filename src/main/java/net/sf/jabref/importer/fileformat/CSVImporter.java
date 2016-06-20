package net.sf.jabref.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.jabref.importer.ImportFormatReader;
import net.sf.jabref.importer.OutputPrinter;
import net.sf.jabref.model.entry.BibEntry;

/**
 * Importer for CSV format.
 *
 * Documentation can be found online at:
 *
 *
 */

public class CSVImporter extends ImportFormat {

    private static final Pattern CSV_PATTERN = Pattern.compile("((\"([^\"]*),+([^\"]*)\")||([^\"]*),([^\"]*))\n?");


    /**
     * Return the name of this import format.
     */
    @Override
    public String getFormatName() {
        return "CSV";
    }

    @Override
    public String getCLIId() {
        return "csv";
    }

    @Override
    public boolean isRecognizedFormat(InputStream stream) throws IOException {

        BufferedReader in = new BufferedReader(ImportFormatReader.getReaderDefaultEncoding(stream));

        String str;
        boolean status = false;
        int oldpieces = -1, newpieces;
        String[] aux;
        while ((str = in.readLine()) != null) {
            aux = preProcessamento(new StringBuilder(str)).split(",");
            newpieces = aux.length;
            if (oldpieces != -1) {
                if (((oldpieces != newpieces) && (newpieces > 0)) || !CSVImporter.CSV_PATTERN.matcher(str).find()) {
                    return false;
                }
            } else if (!CSVImporter.CSV_PATTERN.matcher(str).find()) {
                return false;
            }

            oldpieces = newpieces;
            //for now its true
            status = true;
        }
        return status;
    }

    @Override
    public List<BibEntry> importEntries(InputStream stream, OutputPrinter status) throws IOException {
        List<BibEntry> bibitems = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(ImportFormatReader.getReaderDefaultEncoding(stream))) {
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
                sb.append('\n');
            }
        }
        //verifica se nao é vazio
        if (sb.length() > 0) {
            //processamento de aspas com virgulas dentro
            String ready = preProcessamento(sb);
            //divide a entrada em cabeçalho e corpo
            String[] HeaderBody = ready.split("\n", 2);
            //divide o corpo em linhas, cada um é uma entrada
            String[] lines = HeaderBody[1].split("\n");

            //a primeira é o cabeçalho indicando os campos, o resto são as entradas.
            //divide o cabeçalho nas virgulas
            String[] header = HeaderBody[0].split(",");

            //header[0]=tipo da entrada-- unica exigencia

            for (String line : lines) {
                //divide a linha em virgulas, cada uma um campo
                String[] fields = line.split(",");

                BibEntry b = new BibEntry(DEFAULT_BIBTEXENTRY_ID, fields[0]);


                for (int i = 1; i < header.length; i++) {
                    b.setField(header[i], fields[i].replace('#', ','));
                    System.out.print(header[i] + "=" + fields[i].replace('#', ',') + ", ");
                    //setOrAppend(b, header[i], fields[i], ", ");
                }
                System.out.println(b);
                bibitems.add(b);
            }
        }
        return bibitems;
    }

    private static String preProcessamento(StringBuilder sb) {
        //processamento de aspas com virgulas dentro

        //separa nas aspas
        String[] quote = sb.toString().split("\"");
        String ready = "";
        //todos os valores impares da variavel quote serao as strings entre aspas
        for (int i = 1; i < quote.length; i += 2) {
            //coloca cerquilha no lugar da virgula
            quote[i] = quote[i].replace(',', '#');
            ready += quote[i - 1] + quote[i];
        }
        //se tiver um numero par de comprimento tem que pegar o final
        if ((quote.length % 2) == 1) {
            ready += quote[quote.length - 1];
        }
        return ready;
    }

    private static void setOrAppend(BibEntry b, String field, String value, String separator) {
        if (b.hasField(field)) {
            b.setField(field, b.getField(field) + separator + value);
        } else {
            b.setField(field, value);
        }
    }

}