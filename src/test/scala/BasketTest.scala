package  test.adthena

import adthena.shopping.basket._
import adthena.shopping.pricelist._
import adthena.shopping.basketImplicit.BasketImplicit._
import adthena.shopping.domain._

class BasketTest extends UnitTest {
  val priceList: PriceList = PriceList()
  val applePromo = Discount(ItemBought(Apples()), Apples(), 10, "Apples 10% off")
  val tinSoup = Discount(ItemNumBought(Soup(), 2), Bread(), 50, "2 tins of Soup get a Bread for 50%")
  val weekPromo = List(applePromo, tinSoup)
  val emptyPromo = List()

  describe("Creating Basket") {
    describe("with empty item list") {
      val il = Option(List())
      it("should be size 0") {
        val b = Basket(il, priceList)
        b.items.size should equal(0)
      }
      it("with Empty promo should have results 0") {
        val b = Basket(il, priceList)
        val c = b.CalculateResults(emptyPromo)
        c.subTotal.get should equal(0)
        c.total.get should equal(0)
      }
      it("with promo set up should have results 0") {
        val b = Basket(il, priceList)
        val c = b.CalculateResults(weekPromo)
        c.subTotal.get should equal(0)
        c.total.get should equal(0)
      }
    }

    describe("with mixed list") {
      val il = Option(List("Apples", "Milk", "Bread"))
      val il2 =Option(List("Apples", "Milk", "Bread", "Soup", "Soup"))
      it("should be size 3") {
        val b = Basket(il, priceList)
        b.items.size should equal(3)
      }
      it("with empty promo subTotal = total") {
        val b = Basket(il, priceList)
        val c = b.CalculateResults(emptyPromo)
        c.subTotal.get should equal(c.total.get)
      }
      it("Apples, Milk, Bread and empty promo total should be 310") {
        val b = Basket(il, priceList)
        val c = b.CalculateResults(emptyPromo)
        c.total.get should equal(310)
      }
      it("Apples, Milk, Bread with promo total should be 300") {
        val b = Basket(il, priceList)
        val c = b.CalculateResults(weekPromo)
        c.total.get should equal(300)
      }

      it("Apples, Milk, Bread, Soup, Soup and empty promo total should be 440") {
        val b = Basket(il2, priceList)
        val c = b.CalculateResults(emptyPromo)
        c.total.get should equal(440)
      }
      it("Apples, Milk, Bread, Soup, Soup with promo total should be 390") {
        val b = Basket(il2, priceList)
        val c = b.CalculateResults(weekPromo)
        c.total.get should equal(390)
      }
      it("Apples, Milk, Bread, Soup, Soup with promo should show 2 promotions applied") {
        val b = Basket(il2, priceList)
        val c = b.CalculateResults(weekPromo)
        c.offers.right.get.size should equal(2)
      }
      it("Apples, Milk, Bread, Soup, Soup with no promo should show 0 promotions applied") {
        val b = Basket(il2, priceList)
        val c = b.CalculateResults(emptyPromo)
        c.offers.isLeft should equal(true)
      }
      it("Milk, Bread with promo should show 0 promotions applied") {
        val ill =Option(List("Milk", "Bread"))
        val b = Basket(ill, priceList)
        val c = b.CalculateResults(weekPromo)
        c.offers.isLeft should equal(true)
      }
    }
  }
}
