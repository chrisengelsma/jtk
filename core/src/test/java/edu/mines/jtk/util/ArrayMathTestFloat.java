package edu.mines.jtk.util;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static edu.mines.jtk.util.ArrayMath.*;
import static edu.mines.jtk.util.ArrayMath.transpose;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ArrayMathTestFloat extends ArrayMathTest {

  @BeforeMethod
  public void setUp() {
    n3 = 8; n2 = 6; n1 = 4;
    a1 = rampfloat(0,1,n1);
    a2 = rampfloat(0,1,10,n1,n2);
    a3 = rampfloat(0,1,10,100,n1,n2,n3);
    b1 = zerofloat(n1);
    b2 = zerofloat(n1,n2);
    b3 = zerofloat(n1,n2,n3);
  }

  @Test
  public void testEqualityComparators() {
    zero(a3);
    assertTrue(equal(a3,b3));

    fill(1,a3);
    assertFalse(equal(a3,b3));

    assertTrue(equal(1.0f,a3,b3));
    assertFalse(equal(0.99f,a3,b3));
  }

  @Test
  public void testRampFloats() {
    assertArraySize(a1,n1);
    assertArraySize(a2,n1,n2);
    assertArraySize(a3,n1,n2,n3);

    for (int i1=0; i1<n1; ++i1) {
      float expected = (float)i1;
      assertEquals(expected,a3[0][0][i1]);
      assertEquals(expected,a2[0][i1]);
      assertEquals(expected,a1[i1]);
    }
  }

  @Test
  public void testFillFloats() {
    float val = 1.0f;

    assertEqual(zerofloat(n1,n2,n3),fillfloat(0.0f,n1,n2,n3));

    a1 = fillfloat(val,n1);
    a2 = fillfloat(val,n1,n2);
    a3 = fillfloat(val,n1,n2,n3);

    assertArraySize(a1,n1);
    assertArraySize(a2,n1,n2);
    assertArraySize(a3,n1,n2,n3);

    assertOnlyContains(val,a3);
    assertOnlyContains(val,a2);
    assertOnlyContains(val,a1);
  }

  @Test
  public void testZero() {
    zero(a1);
    zero(a2);
    zero(a3);

    assertOnlyContains(0,a1);
    assertOnlyContains(0,a2);
    assertOnlyContains(0,a3);
  }

  @Test
  public void testRand() {
    // Rand
    rand(a1); b1 = copy(a1);
    rand(a2); b2 = copy(a2);
    rand(a3); b3 = copy(a3);

    assertTrue(equal(a1,b1));
    assertTrue(equal(a2,b2));
    assertTrue(equal(a3,b3));

    b1 = randfloat(n1);
    b2 = randfloat(n1,n2);
    b3 = randfloat(n1,n2,n3);

    assertFalse(equal(a1,b1));
    assertFalse(equal(a2,b2));
    assertFalse(equal(a3,b3));
  }

  @Test
  public void testCopySimple() {
    b1 = copy(a1);
    b2 = copy(a2);
    b3 = copy(a3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

    copy(a1,b1);
    copy(a2,b2);
    copy(a3,b3);
    assertEqual(b1,a1);
    assertEqual(b2,a2);
    assertEqual(b3,a3);

    b1 = copy(n1 - 1,a1);
    b2 = copy(n1 - 1,n2 - 1,a2);
    b3 = copy(n1 - 1,n2 - 1,n3 - 1,a3);
    assertEqual(b1,rampfloat(0,1,n1 - 1));
    assertEqual(b2,rampfloat(0,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampfloat(0,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    copy(n1 - 1,a1,b1);
    copy(n1 - 1,n2 - 1,a2,b2);
    copy(n1 - 1,n2 - 1,n3 - 1,a3,b3);
    assertEqual(b1,rampfloat(0,1,n1 - 1));
    assertEqual(b2,rampfloat(0,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampfloat(0,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    b1 = copy(n1 - 1,1,a1);
    b2 = copy(n1 - 2,n2 - 1,2,1,a2);
    b3 = copy(n1 - 3,n2 - 2,n3 - 1,3,2,1,a3);
    assertEqual(b1,rampfloat(1,1,n1 - 1));
    assertEqual(b2,rampfloat(12,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampfloat(123,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    copy(n1 - 1,1,a1,0,b1);
    copy(n1 - 2,n2 - 1,2,1,a2,0,0,b2);
    copy(n1 - 3,n2 - 2,n3 - 1,3,2,1,a3,0,0,0,b3);
    assertEqual(b1,rampfloat(1,1,n1 - 1));
    assertEqual(b2,rampfloat(12,1,10,n1 - 1,n2 - 1));
    assertEqual(b3,rampfloat(123,1,10,100,n1 - 1,n2 - 1,n3 - 1));

    b1 = copy(n1 / 2,0,2,a1);
    b2 = copy(n1 / 2,n2 / 2,0,0,2,2,a2);
    b3 = copy(n1 / 2,n2 / 2,n3 / 2,0,0,0,2,2,2,a3);
    assertEqual(b1,rampfloat(0,2,n1 / 2));
    assertEqual(b2,rampfloat(0,2,20,n1 / 2,n2 / 2));
    assertEqual(b3,rampfloat(0,2,20,200,n1 / 2,n2 / 2,n3 / 2));

    b1 = copy(a1);
    b2 = copy(a2);
    b3 = copy(a3);
    copy(n1 - 1,1,a1,1,b1);
    copy(n1 - 2,n2 - 1,2,1,a2,2,1,b2);
    copy(n1 - 3,n2 - 2,n3 - 1,3,2,1,a3,3,2,1,b3);
    assertEqual(b1,rampfloat(0,1,n1));
    assertEqual(b2,rampfloat(0,1,10,n1,n2));
    assertEqual(b3,rampfloat(0,1,10,100,n1,n2,n3));
  }

  @Test
  public void testReverse() {
    b1 = reverse(reverse(a1));
    assertEqual(b1,a1);
  }

  @Test
  public void testReshape() {
    b2 = reshape(n1,n2,flatten(a2));
    b3 = reshape(n1,n2,n3,flatten(a3));
    assertEqual(a2,b2);
    assertEqual(a3,b3);
  }

  @Test
  public void testTranspose() {
    b2 = transpose(transpose(a2));
    assertEqual(a2,b2);
  }

  @Test
  public void testDistinct() {
    a3 = b3;
    a2 = b2;
    a1 = b1;
    assertFalse(distinct(a3,b3));
    assertFalse(distinct(a2,b2));
    assertFalse(distinct(a1,b1));

    b3 = new float[n3][n2][n1];
    b2 = new float[n2][n1];
    b1 = new float[n1];
    assertTrue(distinct(a3,b3));
    assertTrue(distinct(a2,b2));
    assertTrue(distinct(a1,b1));

    b3[1] = a3[1];
    b2[1] = a2[1];
    assertFalse(distinct(a3,b3));
    assertFalse(distinct(a2,b2));
  }

  @Test
  public void testAddition() {
    b1 = rampfloat(1,1,n1);                 assertEqual(b1,add(1,a1));
                                            assertEqual(b1,add(a1,1));
    b2 = rampfloat(1,1,10,n1,n2);           assertEqual(b2,add(1,a2));
                                            assertEqual(b2,add(a2,1));
    b3 = rampfloat(1,1,10,100,n1,n2,n3);    assertEqual(b3,add(1,a3));
                                            assertEqual(b3,add(a3,1));

    b1 = rampfloat(0,2,n1);                 assertEqual(b1,add(a1,a1));
    b2 = rampfloat(0,2,20,n1,n2);           assertEqual(b2,add(a2,a2));
    b3 = rampfloat(0,2,20,200,n1,n2,n3);    assertEqual(b3,add(a3,a3));

    add(a1,a1,a1);                          assertEquals(b1,a1);
    add(a2,a2,a2);                          assertEquals(b2,b2);
    add(a3,a3,a3);                          assertEquals(b3,a3);
  }

  @Test
  public void testSubtraction() {
    b1 = rampfloat(-1,1,n1);                assertEqual(b1,sub(a1,1));
    b2 = rampfloat(-1,1,10,n1,n2);          assertEqual(b2,sub(a2,1));
    b3 = rampfloat(-1,1,10,100,n1,n2,n3);   assertEqual(b3,sub(a3,1));

    b1 = rampfloat(1,-1,n1);                assertEqual(b1,sub(1,a1));
    b2 = rampfloat(1,-1,-10,n1,n2);         assertEqual(b2,sub(1,a2));
    b3 = rampfloat(1,-1,-10,-100,n1,n2,n3); assertEqual(b3,sub(1,a3));

    zero(b1);                               assertEqual(b1,sub(a1,a1));
    zero(b2);                               assertEqual(b2,sub(a2,a2));
    zero(b3);                               assertEqual(b3,sub(a3,a3));

    sub(a1,a1,a1);                          assertEqual(b1,a1);
    sub(a2,a2,a2);                          assertEqual(b2,a2);
    sub(a3,a3,a3);                          assertEqual(b3,a3);
  }

  @Test
  public void testMultiplication() {
    b1 = rampfloat(0,2,n1);                 assertEqual(b1,mul(2,a1));
                                            assertEqual(b1,mul(a1,2));
    b2 = rampfloat(0,2,20,n1,n2);           assertEqual(b2,mul(2,a2));
                                            assertEqual(b2,mul(a2,2));
    b3 = rampfloat(0,2,20,200,n1,n2,n3);    assertEqual(b3,mul(2,a3));
                                            assertEqual(b3,mul(a3,2));

    b1 = rampfloat(0,1,n1);                 assertEqual(b1,mul(a1,a1));
    b2 = rampfloat(0,1,100,n1,n2);          assertEqual(b2,mul(a2,a2));
    b3 = rampfloat(0,1,100,10000,n1,n2,n3); assertEqual(b3,mul(a3,a3));

    mul(a1,a1,a1);                          assertEqual(b1,a1);
    mul(a2,a2,a2);                          assertEqual(b2,a2);
    mul(a3,a3,a3);                          assertEqual(b3,a3);
  }

  @Test
  public void testLogAndExp() {
    float val = 2.0f;
    assertEqual(a3,log(exp(a3)));
  }

  @Test
  public void testSquareRoot() {
    assertAlmostEqual(a3,mul(sqrt(a3),sqrt(a3)));
  }


  @Test
  public void testPow() {
    assertAlmostEqual(a3,pow(sqrt(a3),2.0f));
  }

  @Test
  public void testIndexAndValueMaxes() {
    int[] imax = {-1,-1,-1};
    float rmax = max(a3,imax);

    assertTrue(rmax == a3[n3-1][n2-1][n1-1]);
    assertEq(n1-1,imax[0]);
    assertEq(n2-1,imax[1]);
    assertEq(n3-1,imax[2]);
  }

  @Test
  public void testIndexAndValueMins() {
    int[] imin = {-1,-1,-1};
    float rmin = min(a3,imin);

    assertTrue(rmin==a3[0][0][0]);
    assertEq(0,imin[0]);
    assertEq(0,imin[1]);
    assertEq(0,imin[2]);
  }

  @Test
  public void testRegularity() {
    assertTrue(isRegular(a2));
    assertTrue(isRegular(a3));

    a2[0] = new float[3];
    a3[0] = a2;

    assertFalse(isRegular(a2));
    assertFalse(isRegular(a3));
  }

  @Test
  public void testIncreasingDecreasingMonotonic() {
    assertTrue(isIncreasing(a1));
    assertFalse(isDecreasing(a1));
    assertTrue(isMonotonic(a1));

    a1 = reverse(a1);

    assertFalse(isIncreasing(a1));
    assertTrue(isDecreasing(a1));
    assertTrue(isMonotonic(a1));

    zero(a1);

    assertFalse(isIncreasing(a1));
    assertFalse(isDecreasing(a1));
    assertFalse(isMonotonic(a1));
  }

  protected static void assertOnlyContains(float val,float[][][] a) {
    for (int i3=0; i3<a.length; ++i3)
      assertOnlyContains(val,a[i3]);
  }

  protected static void assertOnlyContains(float val,float[][] a) {
    for (int i2=0; i2<a.length; ++i2)
      assertOnlyContains(val,a[i2]);
  }

  protected static void assertOnlyContains(float val,float[] a) {
    for (int i1=0; i1<a.length; ++i1)
      assertEquals(val,a[i1]);
  }

  protected static void assertArraySize(float[][][] a,int n1,int n2,int n3) {
    assertEquals(n3,a.length);
    assertEquals(n2,a[0].length);
    assertEquals(n1,a[0][0].length);
  }

  protected static void assertArraySize(float[][] a,int n1,int n2) {
    assertEquals(n2,a.length);
    assertEquals(n1,a[0].length);
  }

  protected static void assertArraySize(float[] a,int n) {
    assertEquals(n,a.length);
  }


  protected float[]     a1;
  protected float[][]   a2;
  protected float[][][] a3;

  protected float[]     b1;
  protected float[][]   b2;
  protected float[][][] b3;

}
