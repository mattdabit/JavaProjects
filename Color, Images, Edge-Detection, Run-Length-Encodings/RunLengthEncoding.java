/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Iterator;
import java.util.ArrayList;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
    private DListNode2 _head;
    private int _width;
    private int _height;
    private int _inf;
    private int _size;
  public DListNode2 getHead() {
	return _head;
    }
  public int getSize() {
	return _size;
  }

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
      this._width = width;
      this._height = height;
      int[] _length = new int[] {width * height, 0, 0, 0};
      _head = new DListNode2();
      DListNode2 _node = new DListNode2(_length);
      _head.next = _node;
      _head.prev = _node;
      _size++;
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
      this._height = height;
      this._width = width;
      _head = new DListNode2();
      DListNode2 _prev = _head;
      for(int i = 0; i < red.length; i++) {
        int[] arr = {runLengths[i], red[i], green[i], blue[i]};
        DListNode2 _node = new DListNode2(arr);
        _size++;
        _prev.next = _node;
        _head.prev = _node;
        _node.prev = _prev;
        _node.next = _head;
        _prev = _prev.next;
      }
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
     return this._width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    return this._height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
      RunIterator iter = new RunIterator(this);
      return iter;
    
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
      RunIterator it = this.iterator();
      PixImage pix = new PixImage(_width, _height);
      int[] rgb = it.next();
      int count = 1;
      for (int j = 0; j < _height; j++) {
          for (int i = 0; i < _width; i++) {
              pix.setPixel(i, j, (short) rgb[1], (short) rgb[2], (short) rgb[3]);
              count++;
              if (count > rgb[0] && it.hasNext()) {
                  count = 1;
                  rgb = it.next();
              }
              }
          }
          return pix;
      }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required,  but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {
    // Replace the following line with your solution.
    return "";
  }


  /**
   *  The following methods are required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {
    _width = image.getWidth(); 
    _height = image.getHeight();
    _head = new DListNode2();
    _head.next = _head;
    _head.prev = _head;
    DListNode2 tem = _head;
     if (_width > 0 && _height > 0){
	for (int j = 0; j < _height; j++){
	    for (int i = 0; i < _width; i++ ){
		if (image.getRed(i,j) == tem.item[1] && image.getGreen(i,j) == tem.item[2] && image.getBlue(i,j) == tem.item[3]) {
                    tem.item[0] += 1;
                }else{
                    DListNode2 n = new  DListNode2();
                    int[] newrgb = new int[] {1, image.getRed(i, j), image.getGreen(i, j), image.getBlue(i, j)};
                    n.item = newrgb;
                    n.prev = tem;
                    n.next = _head;
                    tem.next = n;
                    _head.prev = n;
                    tem = tem.next;
                    _size++;
                }
            }
        }
     }
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
      /**int tot = 0;
      RunIterator
      while (_head.item[0] != Integer.MAX_VALUE) {
          tot += _head.item[0];
          if (head.item[1] == head.next.item[1] && head.item[2] == head.next.item[2] && head.item[3] == head.next.item[3]) {
             System.out.println("they are the same");
          } else {
             System.out.println("not the same");
          }
          head = head.next;
          if (tot == _width * _height) {
             System.out.println("same length");
          }else{
             System.out.println("not the same length");
          }
          }*/
      }


  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
     DListNode2 cur = findD(x, y);
     DListNode2 ncur = cur.next;
     DListNode2 pcur = cur.prev;
     if (cur.item[0] == 1) {
        System.out.println("case 1");
	cur.item[1] = red;
	cur.item[2] = green;
	cur.item[3] = blue;
        if (isSame(cur, ncur) && isSame(cur, pcur)) {
                int[] rgb = {ncur.item[0] + pcur.item[0] + 1, red, green, blue};
		DListNode2 m = new DListNode2(rgb);
                m.next = ncur.next;
		m.prev = pcur.prev;
		ncur.next.prev = m;
		pcur.prev.next = m;
		_size -= 2;
        } else if(isSame(cur, ncur)) {
		ncur.item[0]++;
                pcur.next = ncur;
		ncur.prev = pcur;
		_size -= 1;
        } else if(isSame(cur, pcur)) {
		pcur.item[0]++;
		pcur.next = ncur;
		ncur.prev = pcur;
		_size -= 1;
       }
   } else if((findT(x, y) - (cur.item[0] + 1)) == (_width*y +x + 1)) {
	        System.out.println("case 2");
	cur.item[0]--;
        int[] rgb = {1, red, green, blue};
	DListNode2 tem = new DListNode2(rgb);
        if(isSame(tem, pcur)) {
		pcur.item[0]++;
        } else {
		pcur.next = tem;
		tem.next = cur;
		cur.prev = tem;
		tem.prev = pcur;
		_size++;
	}
    } else if(findT(x, y) == (_width*y +x+1)) {
	        System.out.println("case 3");

	cur.item[0]--;
        int[] rgb = {1, red, green, blue};
        	DListNode2 tem = new DListNode2(rgb);
 	if(isSame(tem, ncur)) {
		ncur.item[0]++;
	}else{
		ncur.prev = tem;
		tem.next = ncur;
		cur.next = tem;
		tem.prev = cur;
		_size++;
       	}
     }else{
	        System.out.println("case 4");

        int[] arr = {1, red, green, blue};
	int[] arr2 = {findT(x, y) - (_width*y + x + 1), cur.item[1], cur.item[2], cur.item[3]};
	int[] arr1 = {(cur.item[0] - (findT(x, y) - (_width*y + x + 1)) - 1), cur.item[1], cur.item[2], cur.item[3]};
	DListNode2 c = new DListNode2(arr);
	DListNode2 p = new DListNode2(arr1);
	DListNode2 n = new DListNode2(arr2);
	pcur.next = p;
	p.prev = pcur;
	p.next = c;
	c.prev = p;
	c.next = n;
	n.prev = c;
	n.next = ncur;
	_size += 2;
	}
	check();
    }
	
		


  public boolean isSame(DListNode2 a, DListNode2 b) {
	if (a.item[1] == b.item[1] && a.item[2] == b.item[2] && a.item[3] == b.item[3]) {
	   return true;
        }
	return false;
     }
  public int findT(int x, int y) {
	int num = _width * y + x + 1;
        int c = 0;
	DListNode2 tem = _head;
	while ( c < num) {
 		tem = tem.next;
		c += tem.item[0];
                }
	return c;
    }
  public DListNode2 findD(int x, int y) {
	int num = _width * y + x + 1;
        int c = 0;
	DListNode2 tem = _head;
	while ( c < num) {
 		tem = tem.next;
		c += tem.item[0];
                }
	return tem;
    }
		

  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
