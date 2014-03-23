package anagrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertThat;

public class AnagramsTest {

    /**
     * Test of {@link Anagrams#anagrams(Stream) anagrams} method.
     */
    @Test
    public void testAnagrams() {
        Stream<String> words = Arrays.asList("risen", "siren").stream();
        Collection<String> actual = Anagrams.anagrams(words).findFirst().get();
        assertThat(actual, containsInAnyOrder("risen", "siren"));
    }

    /**
     * Test of {@link Anagrams#anagramsFor(String, Stream) anagramsFor} method.
     */
    @Test
    public void testAnagramsFor() {
        Collection<String> words = Arrays.asList("catechism", "reset", "risen", "schematic",
                "siren", "steer", "terse", "trees", "unsorted");
        Stream<String> actual = Anagrams.anagramsFor("trees", words.stream());
        assertThat(actual.collect(toList()), containsInAnyOrder("reset", "steer", "terse"));
    }

    /**
     * Test of {@link Anagrams#anyAnagramFor(String, Stream) anyAnagramFor} method.
     */
    @Test
    public void testAnyAnagramFor() {
        Collection<String> words = Arrays.asList("catechism", "reset", "risen", "schematic",
                "siren", "steer", "terse", "trees", "unsorted");
        String actual = Anagrams.anyAnagramFor("trees", words.stream());
        assertThat(actual, isOneOf("reset", "steer", "terse"));
    }

    /**
     * Test of
     * {@link Anagrams#anagramWithMostLetters(Stream) anagramWithMostLetters}
     * method.
     */
    @Test
    public void testAnagramWithMostLetters() {
        Collection<String> words = Arrays.asList("catechism", "reset", "risen", "schematic",
                "siren", "steer", "terse", "trees", "unsorted");
        Collection<String> actual = Anagrams.anagramWithMostLetters(words.stream());
        assertThat(actual, containsInAnyOrder("catechism", "schematic"));
    }

    /**
     * Test of
     * {@link Anagrams#anagramWithMostWords(Stream) anagramWithMostWords}
     * method.
     */
    @Test
    public void testAnagramWithMostWords() {
        Collection<String> words = Arrays.asList("catechism", "reset", "risen", "schematic",
                "siren", "steer", "terse", "trees", "unsorted");
        Collection<String> actual = Anagrams.anagramWithMostWords(words.stream());
        assertThat(actual, containsInAnyOrder("reset", "steer", "terse", "trees"));
    }

    @SafeVarargs
    static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(final T... items) {
        return new BaseMatcher<Iterable<? extends T>>() {
            @Override
            public boolean matches(Object item) {
                Collection<T> expected = Arrays.asList(items);
                @SuppressWarnings("unchecked")
                Collection<T> actual = toCollection((Iterable<T>) item);
                return actual.size() == expected.size() && actual.containsAll(expected);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText(Arrays.asList(items) + " in any order");
            }
        };
    }

    @SafeVarargs
    static <T> Matcher<T> isOneOf(final T... items) {
        return new TypeSafeMatcher<T>() {
            @Override
            public boolean matchesSafely(T item) {
                return Arrays.asList(items).contains(item);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("one of " + Arrays.asList(items));
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <T> Collection<T> toCollection(Iterable<T> iter) {
        if (iter instanceof Collection) {
            return (Collection<T>) iter;
        }
        Collection<T> list = new ArrayList<>();
        for (T item: iter) {
            list.add(item);
        }
        return list;
    }
}
