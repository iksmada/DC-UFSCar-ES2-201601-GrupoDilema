package net.sf.jabref.bibtex;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefPreferences;
import net.sf.jabref.logic.bibtex.BibEntryWriter;
import net.sf.jabref.logic.bibtex.LatexFieldFormatter;
import net.sf.jabref.model.database.BibDatabaseMode;
import net.sf.jabref.model.entry.BibEntry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidacaoBibtexKeyPrimeiroTeste {

    private BibEntryWriter writer;
    private static JabRefPreferences backup;


    @BeforeClass
    public static void setUp() {
        Globals.prefs = JabRefPreferences.getInstance();
        backup = Globals.prefs;
    }

    @AfterClass
    public static void tearDown() {
        Globals.prefs.overwritePreferences(backup);
    }

    @Before
    public void setUpWriter() {
        writer = new BibEntryWriter(new LatexFieldFormatter(), true);
    }

    // teste de uma bibtexkey somente com números
    @Test
    public void testeBibtexkeyNumerica() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("123567");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste de bibtexkey com só 1 caracter
    @Test
    public void testeBibtexkeyTamanho() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("a");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste de bibtexkey com numeral no início
    @Test
    public void testeBibtexkeyNumeralInicio() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("1a");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    // teste de uma bibtexkey qualquer
    @Test
    public void testeBibtexkeyQualquer() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("a2E1");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //*** para categoria article
    // teste de uma bibtexkey somente com números
    @Test
    public void testeBibtexkeyNumericaArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setCiteKey("123567");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste de bibtexkey com só 1 caracter
    @Test
    public void testeBibtexkeyTamanhoArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setCiteKey("a");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste de bibtexkey com numeral no início
    @Test
    public void testeBibtexkeyNumeralInicioArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setCiteKey("1a");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    // teste de uma bibtexkey qualquer
    @Test
    public void testeBibtexkeyValidaArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setCiteKey("a2E1");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

}
