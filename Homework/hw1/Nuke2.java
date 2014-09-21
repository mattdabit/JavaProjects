/** Nuke2 gets rid of the second letter.
 * @author Matthew.*/

import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Nuke2 {
    public static void main(String[] bleh) throws Exception {
        BufferedReader e;
        String s;
        e =  new BufferedReader(new InputStreamReader(System.in));
        s = e.readLine();
        s = s.charAt(0) + s.substring(2);
        System.out.println(s);
    }
}
