package data_structures.tries.__tests__;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import data_structures.tries.Trie;
import data_structures.tries.TrieNode;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Trie_Test {
  Trie<Integer> trie;
  Trie<String> trie2;
  TrieNode<Integer> node;
  TrieNode<String> node2;

  @Nested
  class When_New {

    @BeforeEach
    void create_trie() {
      trie = new Trie<>();
    }

    @Test
    void is_empty() {
      assertEquals(0, trie.size());
    }

    @Test
    void insertion() {
      trie.insert("one", 1);

      assertEquals(1, trie.get("one"));
    }

    @Test
    void null_on_nonexistent_word() {
      assertNull(trie.get("test"));
    }

    @Test
    void empty_trie_string() {
      assertEquals("{}", trie.toString());
    }

    @Test
    void getPrefix_throws_on_null() {
      assertThrows(NullPointerException.class, () -> trie.getPrefix(null));
    }

    @Test
    void getPrefix() {
      trie.insert("one", 1);
      assertEquals("one", trie.getPrefix(trie.search("one")));
    }

    @Test
    void findWords_is_null() {
      assertNull(trie.findWords());
      assertNull(trie.findWords(""));
      assertNull(trie.findWords("a"));
    }

    @Test
    void delete_throws() {
      assertThrows(IllegalArgumentException.class, () -> trie.delete(null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "  ", "\t", "\n" })
    void insert_throws_on_bad_words(String word) {
      trie2 = new Trie<>();

      assertThrows(IllegalArgumentException.class, () -> trie.insert(word, 1));
    }

  }

  @Nested
  class Multiple_Insertions {

    @BeforeEach
    void create_and_insert() {
      trie = new Trie<>();

      trie.insert("one", 1);
      trie.insert("two", 2);
      trie.insert("three", 3);
      trie.insert("four", 4);
      trie.insert("five", 5);
      trie.insert("boats", 6);
      trie.insert("boars", 7);
      trie.insert("boar", 8);
    }

    @Test
    void all_inserts_succeed() {
      assertAll(
        () -> assertEquals(1, trie.get("one")),
        () -> assertEquals(2, trie.get("two")),
        () -> assertEquals(3, trie.get("three")),
        () -> assertEquals(4, trie.get("four")),
        () -> assertEquals(5, trie.get("five")),
        () -> assertEquals(6, trie.get("boats")),
        () -> assertEquals(7, trie.get("boars")),
        () -> assertEquals(8, trie.get("boar"))
      );
    }

    @Test
    void search() {
      node = trie.search("four");

      assertEquals(4, node.getValue());
    }

    @Test
    void delete() {
      trie.delete("one");

      assertEquals(7, trie.size());
      assertNull(trie.get("one"));
    }

    @Test
    void to_string() {
      assertEquals("{\n"
          + "\s\s\"boar -> 8\",\n"
          + "\s\s\"boars -> 7\",\n"
          + "\s\s\"boats -> 6\",\n"
          + "\s\s\"five -> 5\",\n"
          + "\s\s\"four -> 4\",\n"
          + "\s\s\"one -> 1\",\n"
          + "\s\s\"three -> 3\",\n"
          + "\s\s\"two -> 2\",\n"
          + "}",
        trie.toString());
    }
  }

  @Nested
  class Close_Words {

    @BeforeEach
    void prep() {
      trie = new Trie<>();

      trie.insert("boa", 1);
      trie.insert("boat", 2);
      trie.insert("boar", 3);
      trie.insert("boats", 4);
    }

    @Test
    void delete_parent() {
      trie.delete("boat");
      assertEquals(4, trie.get("boats"));
    }

    @Test
    void delete_leaf() {
      trie.delete("boats");
      assertEquals(2, trie.get("boat"));
      assertNull(trie.get("boats"));
    }

    @Test
    void findWords() {
      String[] words = { "boa", "boar", "boat", "boats" };
      assertArrayEquals(words, trie.findWords("boa"));
      assertArrayEquals(words, trie.findWords(""));
      assertArrayEquals(words, trie.findWords());
    }
  }

  @Nested
  class Many_Insertions {

    @Test
    void insertions() {
      trie = new Trie<>();

      trie.insert("one", 1);
      trie.insert("two", 2);
      trie.insert("three", 3);
      trie.insert("four", 4);
      trie.insert("five", 5);
      trie.insert("six", 6);
      trie.insert("seven", 7);
      trie.insert("eight", 8);
      trie.insert("nine", 9);
      trie.insert("ten", 10);
      trie.insert("one-hundred-ten", 110);
      trie.insert("one-hundred-twenty", 120);
      trie.insert("thirteen", 13);
      trie.insert("fourteen", 14);
      trie.insert("fifteen", 15);
      trie.insert("sixteen", 16);
      trie.insert("seventeen", 17);
      trie.insert("eighteen", 18);
      trie.insert("nineteen", 19);
      trie.insert("twenty", 20);

      assertNotNull(trie.get("one"));
      assertNotNull(trie.get("two"));
      assertNotNull(trie.get("three"));
      assertNotNull(trie.get("four"));
      assertNotNull(trie.get("five"));
      assertNotNull(trie.get("six"));
      assertNotNull(trie.get("seven"));

      trie.delete("three");
      trie.delete("four");

      assertNotNull(trie.get("eight"));
      assertNotNull(trie.get("nine"));
      assertNotNull(trie.get("ten"));
      assertEquals(110, trie.get("onehundredten"));
      assertNotNull(trie.get("onehundredtwenty"));
      assertNotNull(trie.get("thirteen"));
      assertNotNull(trie.get("fourteen"));
      assertNotNull(trie.get("fifteen"));
      assertNotNull(trie.get("sixteen"));

      trie.delete("onehundredten");
      trie.delete("fourteen");

      assertNotNull(trie.get("seventeen"));
      assertNotNull(trie.get("eighteen"));
      assertNotNull(trie.get("nineteen"));
      assertNotNull(trie.get("twenty"));

      assertNotNull(trie.get("one"));
      assertNotNull(trie.get("two"));
      assertNotNull(trie.get("five"));
      assertNotNull(trie.get("six"));
      assertNotNull(trie.get("seven"));
      assertNotNull(trie.get("eight"));
      assertNotNull(trie.get("nine"));
      assertNotNull(trie.get("ten"));
      assertNotNull(trie.get("onehundredtwenty"));
      assertNotNull(trie.get("thirteen"));
      assertNotNull(trie.get("fifteen"));
      assertNotNull(trie.get("sixteen"));
      assertNotNull(trie.get("eighteen"));
      assertNotNull(trie.get("nineteen"));
      assertNotNull(trie.get("twenty"));
    }
  }
}
