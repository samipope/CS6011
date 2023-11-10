package assign01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayscaleImageTest {

    private GrayscaleImage smallSquare;
    private GrayscaleImage smallWide;
    private GrayscaleImage largeWide;
    private GrayscaleImage largeWidePlusOne;
    private GrayscaleImage emptySquare;
    private GrayscaleImage skinnyTest;


    @BeforeEach
    void setUp() {
        smallSquare = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        smallWide = new GrayscaleImage(new double[][]{{1,2,3},{4,5,6}});
        largeWide = new GrayscaleImage(new double[][]{{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}});
        largeWidePlusOne = new GrayscaleImage(new double[][]{{0,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}});
        skinnyTest = new GrayscaleImage(new double[][]{{1,2},{3,4},{5,6}});
    }


    @Test
    void testGetPixel(){
        assertEquals(smallSquare.getPixel(0,0), 1);
        assertEquals(smallSquare.getPixel(1,0), 2);
        assertEquals(smallSquare.getPixel(0,1), 3);
        assertEquals(smallSquare.getPixel(1,1), 4);

        assertEquals(largeWide.getPixel(2,0),3);
        //testing the first coordinate
        assertEquals(largeWide.getPixel(0,0),1);
        assertEquals(largeWide.getPixel(4,0),5);
        //testing the last coordinate
        assertEquals(largeWide.getPixel(8,1),9);
    }

    @Test
    void testEquals() {
        assertEquals(smallSquare, smallSquare);
        var equivalent = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        assertEquals(smallSquare, equivalent);
        assertEquals(largeWide,largeWide);
        var skinnyTestEquiv = new GrayscaleImage(new double[][]{{1,2},{3,4},{5,6}});
        assertEquals(skinnyTest, skinnyTestEquiv);


    }

    @Test
    void testNotEquals(){
        //checking that the boolean returns false when we give it two images that do not match
        assertNotEquals(smallSquare, smallWide);
        assertNotEquals(smallSquare,largeWide);
        //I changed only one pixel in largeWidePlusOne so I knew that the comparing each pixel part of the function was working
        assertNotEquals(largeWide,largeWidePlusOne);
    }

    @Test
    void averageBrightness() {
        assertEquals(smallSquare.averageBrightness(), 2.5, 2.5*.001);
        var bigZero = new GrayscaleImage(new double[1000][1000]);
        assertEquals(bigZero.averageBrightness(), 0);
    }

    @Test
    void normalized() {
        var smallNorm = smallSquare.normalized();
        assertEquals(smallNorm.averageBrightness(), 127, 127*.001);
        var scale = 127/2.5;
        var expectedNorm = new GrayscaleImage(new double[][]{{scale, 2*scale},{3*scale, 4*scale}});
        for(var row = 0; row < 2; row++){
            for(var col = 0; col < 2; col++){
                assertEquals(smallNorm.getPixel(col, row), expectedNorm.getPixel(col, row),
                        expectedNorm.getPixel(col, row)*.001,
                        "pixel at row: " + row + " col: " + col + " incorrect");
            }
        }

    }

    @Test
    void mirrored() {
        var expected = new GrayscaleImage(new double[][]{{2,1},{4,3}});
        assertEquals(smallSquare.mirrored(), expected);

    }

    @Test
    void mirroredOnSkinnyImage(){
        var skinnyMirrored = new GrayscaleImage(new double[][]{{2,1},{4,3},{6,5}});
        assertEquals(skinnyTest.mirrored(),skinnyMirrored);
    }

//    @Test
//    void mirroredOnFatImage(){
//        var  smallWideMirrored = new GrayscaleImage(new double[][]{{3,2,1},{6,5,4}});
//        System.out.println(smallWide.mirrored());
//        System.out.println(smallWideMirrored.equals(smallWide.mirrored()));
//      //  assertEquals(smallWide.mirrored(),smallWideMirrored);
//    }

    @Test
    void cropped() {
        var cropped = smallSquare.cropped(1,1,1,1);
        assertEquals(cropped, new GrayscaleImage(new double[][]{{4}}));
        var croppedWide = smallWide.cropped(1,1,1,1);
        assertEquals(croppedWide, new GrayscaleImage(new double[][]{{5}}));

    }

    @Test
    void squarified() {
        var squared = smallWide.squarified();
        var expected = new GrayscaleImage(new double[][]{{1,2},{4,5}});
        assertEquals(squared, expected);
    }

    @Test
    void squarifiedOnASquare(){
        //checking that if the function is already a square, it will not change it
        var squared = smallSquare.squarified();
        assertEquals(smallSquare,squared);
    }

    @Test
    void testGetPixelThrowsOnNegativeXandY(){
        //testing that it throws on negative number
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(-1,0);});
        assertThrows(IllegalArgumentException.class, () -> { largeWide.getPixel(-1,0);});
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(1,-2);});
        assertThrows(IllegalArgumentException.class, () -> { smallWide.getPixel(1,-1);});
    }

    @Test
    void testGetPixelThrowsOnOutofRange(){
        //these pixels could exist, but are out of range of the given grayscale images
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(3,0);});
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(0,3);});
        assertThrows(IllegalArgumentException.class, () -> { largeWide.getPixel(10,0);});
        assertThrows(IllegalArgumentException.class, () -> { largeWide.getPixel(0,4);});


    }

    @Test
    void testEmptyContructorThrows(){
        //checking that it throws the excpetion when the image is empty
        assertThrows(IllegalArgumentException.class, () -> { emptySquare = new GrayscaleImage(new double[][]{{},{}});});
    }

}