// import necessary java libraries
import java.io.*;
import java.util.*;

//import CSVReader and dependencies
import com.opencsv.CSVReader;
import java.nio.charset.StandardCharsets;


// Movie node
class Movie {
  String title;

  Movie left, right;

  public Movie(String mov) {
    title = mov;

    left = right = null;
  }
}

// Binary search tree class
class MovieBST {
  Movie root;

  // recursive subset method
  public void subSet(Movie node, String start, String end) {
    // base case
    if (node == null) {
      return;
    }

    // case comparisons and output
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

  // recursive method to add movie to tree
  public Movie addMovie(Movie current, String title) {
    
    //base case
    if (current == null) {
      return new Movie(title);
    }

    // comparisons to choose where to add movie node
    if (title.compareTo(current.title) < 0) {
      current.left = addMovie(current.left, title);
    } else if (title.compareTo(current.title) > 0) {
      current.right = addMovie(current.right, title);
    } else {
      return current;
    }

    return current;

  }

  // add movie
  public void add(String movie) {
    root = addMovie(root, movie);
  }

}


// main class
public class Main {
  public static void main(String[] args) throws Exception {
    String file = "data/ml-latest-small/movies.csv"; // set file name
    MovieBST movies = new MovieBST(); // create Binary Seart Tree object

    try (FileInputStream fis = new FileInputStream(file);
    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(isr)) {
      // initialize variables to store data from file
      String title;
      int year;
      String[] genres;
      int movieId;
      String[] nextLine; // store line for processing

      reader.readNext(); // skip first line

      // read in line from file
      while ((nextLine = reader.readNext()) != null) {

        // conditional to account for inconsistencies in data
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

        genres = nextLine[2].split("\\|"); // remove | from genres and store in array
        movieId = Integer.parseInt(nextLine[0]); // convert string to integer
        
        // add title to binary tree
        movies.add(title);
      }
    }

    // subset tests
    System.out.println("\nTest 1");
    movies.subSet(movies.root, "Toy Story", "Wreck-It Ralph");
  }
}