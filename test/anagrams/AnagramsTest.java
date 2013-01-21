package anagrams;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnagramsTest {
    
    public AnagramsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of {@link Anagrams#anagrams(java.util.stream.Stream) anagrams} method.
     */
    @Test
    public void testAnagrams() {
        System.out.println("anagrams");
        Stream<String> words = Arrays.asList("risen", "siren").stream();
        Collection<String> actual = Anagrams.anagrams(words).findFirst().get();
        assertContainsInAnyOrder(Arrays.asList("risen", "siren"), actual);
    }

    /**
     * Test of
     * {@link Anagrams#anagramWithMostLetters(java.util.stream.Stream) anagramWithMostLetters}
     * method.
     */
    @Test
    public void testAnagramWithMostLetters() {
        System.out.println("anagramWithMostLetters");
        Collection<String> words = Arrays.asList("catechism", "reset", "risen", "schematic",
                "siren", "steer", "terse", "trees", "unsorted");
        Collection<String> actual = Anagrams.anagramWithMostLetters(words.stream());
        assertContainsInAnyOrder(Arrays.asList("catechism", "schematic"), actual);
    }

    /**
     * Test of
     * {@link Anagrams#anagramWithMostWords(java.util.stream.Stream) anagramWithMostWords}
     * method.
     */
    @Test
    public void testAnagramWithMostWords() {
        System.out.println("anagramWithMostWords");
        Collection<String> words = Arrays.asList("catechism", "reset", "risen", "schematic",
                "siren", "steer", "terse", "trees", "unsorted");
        Collection<String> actual = Anagrams.anagramWithMostWords(words.stream());
        assertContainsInAnyOrder(Arrays.asList("reset", "steer", "terse", "trees"), actual);
    }

    private static boolean assertContainsInAnyOrder(Collection<String> expected,
            Collection<String> actual) {
        return actual.size() == expected.size() && actual.containsAll(expected);
    }
}
