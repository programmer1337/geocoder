package com.dmnine.geocoder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestUtilTest {
  @Test
  void unitTest() {
    assertEquals(3, TestUtil.sum(1,2));
    assertEquals(123, TestUtil.sum(100,23));
    assertEquals(123, TestUtil.sum(113,10));
    assertEquals(123, TestUtil.sum(1,122));
  }
}
