package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  // TODO: add more unit tests of your own
  @Test
  public void testAdditionZeros() {
    MathBot mazza = new MathBot();
    double output = mazza.add(0, 0);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void testSubtractionZeros() {
    MathBot mazza = new MathBot();
    double output = mazza.subtract(0, 0);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void addNothing() {
    MathBot mazza = new MathBot();
    double output = mazza.add(8, 0);
    assertEquals(8, output, 0.01);
  }

  @Test
  public void subtractNothing() {
    MathBot mazza = new MathBot();
    double output = mazza.subtract(8, 0);
    assertEquals(8, output, 0.01);
  }
  @Test
  public void nothingAdd() {
    MathBot mazza = new MathBot();
    double output = mazza.add(0, 8);
    assertEquals(8, output, 0.01);
  }

  @Test
  public void nothingSubtract() {
    MathBot mazza = new MathBot();
    double output = mazza.subtract(0, 8);
    assertEquals(-8, output, 0.01);
  }

  @Test
  public void addNegative() {
    MathBot mazza = new MathBot();
    double output = mazza.add(18, -17);
    assertEquals(1, output, 0.01);
  }

  @Test
  public void addNegativeToZero() {
    MathBot mazza = new MathBot();
    double output = mazza.add(18, -18);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void negativeAdd() {
    MathBot mazza = new MathBot();
    double output = mazza.add(-17, 18);
    assertEquals(1, output, 0.01);
  }

  @Test
  public void negativeAddToZero() {
    MathBot mazza = new MathBot();
    double output = mazza.add(-18, 18);
    assertEquals(0, output, 0.01);
  }

  @Test
  public void negativeNegativeAdd() {
    MathBot mazza = new MathBot();
    double output = mazza.add(-18, -18);
    assertEquals(-36, output, 0.01);
  }

  @Test
  public void subtractNegative() {
    MathBot mazza = new MathBot();
    double output = mazza.subtract(10, -5);
    assertEquals(15, output, 0.01);
  }

  @Test
  public void makeNegative() {
    MathBot mazza = new MathBot();
    double output = mazza.subtract(5, 10);
    assertEquals(-5, output, 0.01);
  }

  @Test
  public void multipleNegativeSigns() {
    MathBot mazza = new MathBot();
    double output = mazza.subtract(-(-5), (-10));
    assertEquals(15, output, 0.01);
  }









}
