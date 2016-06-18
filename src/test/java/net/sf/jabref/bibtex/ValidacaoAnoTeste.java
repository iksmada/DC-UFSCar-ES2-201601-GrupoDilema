package net.sf.jabref.bibtex;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefPreferences;
import net.sf.jabref.exporter.LatexFieldFormatter;
import net.sf.jabref.model.database.BibDatabaseMode;
import net.sf.jabref.model.entry.BibEntry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidacaoAnoTeste {

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

    // teste para o tipo article com data inválida maior que a atual, deve exibir um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoAnoInvalidoMaiorArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2017");
    }

    //teste para o tipo article com data válida no limite, deve exibir o ano
    @Test
    public void testeValidacaoAnoLimiteMaiorArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2016");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " year = {2016}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste para o tipo article com data inválida menor que o limite, deve exibir um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoAnoInvalidoMenorArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "-1");

    }

    //teste para o tipo article com data válida no limite menor, deve ser exibido o ano informado
    @Test
    public void testeValidacaoAnoLimiteMenorArticle() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "0");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " year = {0}," + Globals.NEWLINE
                + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    // teste para o tipo book com data inválida maior que a atual, deve exibir um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoAnoInvalidoMaiorBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2017");
    }

    //teste para o tipo article com data válida no limite, deve exibir o ano
    @Test
    public void testeValidacaoAnoLimiteMaiorBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2016");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + " year = {2016}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste para o tipo article com data inválida menor que o limite, deve exibir um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoAnoInvalidoMenorBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "-1");
    }

    //teste para o tipo article com data válida no limite menor, deve ser exibido o ano informado
    @Test
    public void testeValidacaoAnoLimiteMenorBook() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "0");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + " year = {0}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }
}
