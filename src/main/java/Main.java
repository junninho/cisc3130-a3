// import necessary java libraries
import java.io.*;
import java.util.*;

//import CSVReader and dependencies
import com.opencsv.CSVReader;
import java.nio.charset.StandardCharsets;

class Movie {
  String title;

  Movie left, right;

  public Movie(String mov) {
    title = mov;

    left = right = null;
  }
}

class MovieBST {
  Movie root;

  public void subSet(Movie node, String start, String end) {
    if (node == null) {
      return;
    }

    if (start.compareTo(node.title) < 0) {
      subSet(node.left, start, end);
    }

    if (start.compareTo(node.title) <= 0 && end.compareTo(node.title) >= 0) {
      System.out.println(node.title);
    }

    if (end.compareTo(root.title) > 0) {
      subSet(node.right, start, end);
    }
  }

  public Movie addMovie(Movie current, String title) {
    if (current == null) {
      return new Movie(title);
    }

    if (title.compareTo(current.title) < 0) {
      current.left = addMovie(current.left, title);
    } else if (title.compareTo(current.title) > 0) {
      current.right = addMovie(current.right, title);
    } else {
      return current;
    }

    return current;

  }

  public void add(String movie) {
    root = addMovie(root, movie);
  }

}
//----------------------------------------------------------------------



// main class
public class Main {
  public static void main(String[] args) throws Exception {
    String file = "data/ml-latest-small/movies.csv";
    MovieBST movies = new MovieBST();

    try (FileInputStream fis = new FileInputStream(file);
    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(isr)) {
      String title;
      int year;
      String[] genres;
      int movieId;
      String[] nextLine; // store line for processing

      reader.readNext();

      while ((nextLine = reader.readNext()) != null) {
        if (nextLine[1].endsWith(")")) {
          title = nextLine[1].substring(0, nextLine[1].length() - 6);
          year = Integer.parseInt(nextLine[1].substring(nextLine[1].length() - 5, nextLine[1].length() - 1));
        } else if (nextLine[1].endsWith(") ")) {
          title = nextLine[1].substring(0, nextLine[1].length() - 7);
          year = Integer.parseInt(nextLine[1].substring(nextLine[1].length() - 6, nextLine[1].length() - 2));
        } else {
          title = nextLine[1];
          year = 0;
        }

        genres = nextLine[2].split("\\|");
        movieId = Integer.parseInt(nextLine[0]);
    
        movies.add(title);
      }
    }


    movies.subSet(movies.root, "Toy Story", "Wreck-It Ralph");
  }
}