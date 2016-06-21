ckage net.sf.jabref.bibtex;

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

public class ValidacaoBibtexKeyTeste {

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

    // teste de uma bibtexkey invalida somente com números, a saida mostrada não insere a key
    @Test
    public void testeBibtexkeyInvalidaNumerica() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("123567");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste de bibtexkey inválida com só 1 caracter, a saída não deve mostrar a key
    @Test
    public void testeBibtexkeyInvalidaTamanho() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("a");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    //teste de bibtexkey invalida com numeral no início, a saída não deve mostrar a key
    @Test
    public void testeBibtexkeyInvalidaNumeralInicio() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("1a");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    // teste de uma bibtexkey válida
    @Test
    public void testeBibtexkeyValida() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setCiteKey("a2E1");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{" + entry.getCiteKey() + ',' + Globals.NEWLINE + "}"
                + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

}
