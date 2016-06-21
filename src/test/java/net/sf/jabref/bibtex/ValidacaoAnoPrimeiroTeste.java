ckage net.sf.jabref.bibtex;


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

public class ValidacaoAnoPrimeiroTeste {

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

    // teste para o tipo article com data maior que a atual
    @Test
    public void testeValidacaoAnoMaiorArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2027");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " year = {2027}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));
    }

    //teste para o tipo article com data no limite esperado
    @Test
    public void testeValidacaoAnoEsperadoArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2010");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " year = {2010}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));
    }

    //teste para o tipo article com data menor que 0
    @Test
    public void testeValidacaoAnoMenorArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "-10");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " year = {-10}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));

    }

    // teste para o tipo book com data maior que a atual
    @Test
    public void testeValidacaoAnoMaiorBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2027");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + " year = {2027}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));
    }

    //teste para o tipo book com data no limite esperado
    @Test
    public void testeValidacaoAnoEsperadoBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2010");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + " year = {2010}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));
    }

    //teste para o tipo book com data menor que 0
    @Test
    public void testeValidacaoAnoMenorBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "-10");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + " year = {-10}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));

    }
}
