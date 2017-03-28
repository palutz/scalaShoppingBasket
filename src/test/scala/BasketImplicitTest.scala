package  test.adthena

import adthena.shopping.basket._
import adthena.shopping.pricelist._
import adthena.shopping.basketImplicit.BasketImplicit._
import adthena.shopping.domain._

class BasketImplicitTest extends UnitTest {

  describe("NonNegativeOption ") {
    it("with positive numbers should return Some(_)") {
      NotNegativeOption.maybeResult(30) should be(Some(30))
    }
    it("with 0 should return Some(_)") {
      NotNegativeOption.maybeResult(0) should be(Some(0))
    }
    it("with negative number should return None") {
      NotNegativeOption.maybeResult(-10) should be(None)
    }
  }

  describe("basketPrinter ") {
    describe("if empty results ") {
      it("should always return a List of 3 elements") {
        val l = basketPrinter(ResultSummary(None, Left(""), None))
        l.length should be(3)
      }
      it("should say (No offers available)") {
        val l = basketPrinter(ResultSummary(None, Left(""), None))
        l.contains("(No offers available)") should be(true)
      }
    }
  }

  describe("basketParser ") {
    it("with Wrong, Wrong2, Another should return an empty Item Map") {
      val il = Option(List("Wrong", "Wrong2", "Another"))
      basketParser(il).size should be(0)
    }
    it("with Wrong, Apples, Wrong2, Bread, Another should return an empty Map with 2 elem") {
      val il = Option(List("Wrong", "Apples", "Wrong2", "Bread", "Another"))
      basketParser(il).size should be(2)
    }
  }

  describe("itemFactory") {
    it("with randm string retun None") {
      itemFactory("random") should be(None)
    }
    it("with SOUP string retun Some(Soup())") {
      itemFactory("SOUP") should be(Some(new Soup()))
    }
    it("with SoUp string retun Some(Soup())") {
      itemFactory("SoUp") should be(Some(new Soup()))
    }
    it("with apples string retun Some(Apples())") {
      itemFactory("apples") should be(Some(new Apples()))
    }
    it("with apple (!!!) string retun None") {
      itemFactory("apple") should be(None)
    }
  }
}
