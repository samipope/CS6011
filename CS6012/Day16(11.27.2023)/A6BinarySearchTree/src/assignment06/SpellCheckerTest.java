package assignment06;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SpellCheckerTest {
    @Test
    public void testAddAndRemoveFromDictionary() {
        SpellChecker spellChecker = new SpellChecker();
        spellChecker.addToDictionary("example");
        assertTrue(spellChecker.dictionary.contains("example"));

        spellChecker.removeFromDictionary("example");
        assertFalse(spellChecker.dictionary.contains("example"));
    }

    @Test
    public void testSpellCheck() throws IOException {
        // Create a temporary file with some sample text
        File tempFile = File.createTempFile("test", ".txt");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("This is a tst document with some erors.");
        writer.close();

        // Add correct words to the dictionary
        SpellChecker spellChecker = new SpellChecker(Arrays.asList("this", "is", "a", "document", "with", "some"));

        List<String> misspelledWords = spellChecker.spellCheck(tempFile);
        assertEquals(Arrays.asList("tst", "erors"), misspelledWords);
    }

    @Test
    public void testConstructorWithList() {
        List<String> words = Arrays.asList("hello", "world");
        SpellChecker spellChecker = new SpellChecker(words);
        assertTrue(spellChecker.dictionary.contains("hello"));
        assertTrue(spellChecker.dictionary.contains("world"));
    }

    @Test
    public void testConstructorWithFile() throws IOException {
        // Create a temporary file with some sample words
        File tempFile = File.createTempFile("dictionary", ".txt");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("alpha\nbeta\ngamma");
        writer.close();

        SpellChecker spellChecker = new SpellChecker(tempFile);
        assertTrue(spellChecker.dictionary.contains("alpha"));
        assertTrue(spellChecker.dictionary.contains("beta"));
        assertTrue(spellChecker.dictionary.contains("gamma"));
    }


}
