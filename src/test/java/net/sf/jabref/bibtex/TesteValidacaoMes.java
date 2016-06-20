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

public class TesteValidacaoMes {

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

    //testa o campo mes para um valor maior que o esperado, deve retornar um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoMesInvalidoMaior() throws IOException {

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("month", "13");
    }

    //testa o campo mes para um valor menor que o esperado, deve retornar um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoMesInvalidoMenor() throws IOException {

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("month", "0");
    }

    //testa o campo mes em um formato indesejado que o esperado, deve retornar um estado de exceção
    @Test(expected = IllegalArgumentException.class)
    public void testeValidacaoMesInvalidoInadequado() throws IOException {

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("month", "abril");
    }

    //testa o campo mes para um valor limitante inferior, a saída deve mostrar o mês corretamente
    @Test
    public void testeValidacaoMesValidoLimiteInferior() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("month", "1");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " month = {Janeiro}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //testa o campo mes para um valor limitante superior, a saída deve mostrar o mês corretamente
    @Test
    public void testeValidacaoMesValidoLimiteSuperior() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("month", "12");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + " month = {Dezembro}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

}
