package anagrams;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.List;
import static java.util.stream.Collectors.groupingByConcurrent;
import java.util.stream.Stream;

public class Anagrams {

  public static void main(String[] args) throws IOException {
    assert args.length == 1; // path-to-words
    Path path = FileSystems.getDefault().getPath(args[0]);
    anagrams(Files.lines(path)).forEach(System.out::println);
  }

  public static Stream<List<String>> anagrams(Stream<String> words) {
    return words.parallel().collect(groupingByConcurrent(Anagrams::key))
        .values().parallelStream().filter(v -> v.size() > 1);
  }

  public static Stream<String> anagramsFor(String word, Stream<String> words) {
    String keyWord = key(word);
    return words.parallel().filter(s -> key(s).equals(keyWord) && !s.equals(word));
  }

  public static String anyAnagramFor(String word, Stream<String> words) {
    return anagramsFor(word, words).findAny().orElse(null);
  }

  private static String key(String s) {
    int[] codePoints = s.codePoints().sorted().toArray();
    return new String(codePoints, 0, codePoints.length);
  }

  public static Collection<String> anagramWithMostLetters(Stream<String> words) {
    return anagrams(words).max(mostLetters).orElse(null);
  }

  public static Collection<String> anagramWithMostWords(Stream<String> words) {
    return anagrams(words).max(mostWords).orElse(null);
  }

  private static final Comparator<Collection<String>> mostLetters
      = comparingInt(cs -> cs.iterator().next().length());

  private static final Comparator<Collection<String>> mostWords
      = comparingInt(Collection::size);
}
