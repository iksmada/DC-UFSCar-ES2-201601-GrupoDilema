package net.sf.jabref.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
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

}