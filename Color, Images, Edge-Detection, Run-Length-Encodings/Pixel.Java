/* Pixel.java */
/** This class represents an array containing the intensity of each color.
 * @author Matthew Dabit. */
public class Pixel {
    /** the field variables.*/
    private short _red;
    private short _blue;
    private short _green;
    /** no values yet constructor.*/
    public Pixel() {
    }
    /** constructor for pixel.*/
    public Pixel(short red, short green, short blue) {
        _red = red;
        _green = green;
        _blue = blue;     
    }
    /** return red.
     * @return short return red.*/
    public short getRed() {
        return _red;
    }
    /** return green.
     *@return short return greem;.*/
    public short getGreen() {
        return _green;
    }
    /** return blue.
     *@return short return blue.*/
    public short getBlue() {
        return _blue;
    }
    /** set red.
     * @param r set _rd to the desired short.*/
    public void setRed(short r) {
        _red = r;
    }
    /** set green.
     * @param g set green to g. */
    public void setGreen(short g) {
        _green = g;
    }
    /** set blue.
     * @param b set blue to b.*/
    public void setBlue(short b) {
        _blue = b;
    }
    /** pixel to string.
     * @return String return a string.
     */
    public String toString() {
      String im = "";
      im += "{" + getRed() + " " + getGreen() + " " + getBlue() + "}";
      return im;
    }
}
