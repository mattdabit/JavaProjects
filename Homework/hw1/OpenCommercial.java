/* OpenCommercial.java */
import java.util.*;
import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();
    inputLine = keyboard.readLine();
    int n = 5;
    URL url = new URL("http://www." + inputLine + ".com");
    List<String> line = new ArrayList<String>();
    InputStream i = url.openStream();
    InputStreamReader re = new InputStreamReader(i);
    BufferedReader e = new BufferedReader(re);
    for (int elem = 0; elem < 5; elem++) {
        line.add(e.readLine());
    }
    for (int elem = line.size() - 1; elem >= 0; elem--) {
        System.out.println(line.get(elem));
    }
  }
}
