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
 * Importer for COPAC format.
 *
 * Documentation can be found online at:
 *
 * http://copac.ac.uk/faq/#format
 */

public class CSVImporter extends ImportFormat {

    private static final Pattern CSV_PATTERN = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");


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

        while ((str = in.readLine()) != null) {
            if (CSVImporter.CSV_PATTERN.matcher(str).find()) {
                return true;
            }
        }

        return false;
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
        //divide a entrada em 2 Strings, a primeira é o cabeçalho indicando os campos, o resto são as entradas
        String[] entries = sb.toString().split("\n", 2);

        //divide o cabeçalho nas virgulas
        String[] fields = entries[0].split(",");

        //fields[0]=tipo da entrada-- unica exigencia

        for (String entry : entries) {
            BibEntry b = new BibEntry(DEFAULT_BIBTEXENTRY_ID, fields[0]);

        }

        return bibitems;
    }

}