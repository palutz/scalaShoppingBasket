package adthena.shopping

import scala.util.Try
import adthena.shopping.basket._
import adthena.shopping.domain._
import adthena.shopping.pricelist._


object Main {
  import adthena.shopping.basketImplicit.BasketImplicit._

  def main(args: Array[String]): Unit = {
    val b = Basket(Try(args.toList).toOption, PriceList())
    val applePromo = Discount(ItemBought(Apples()), Apples(), 10, "Apples 10% off")
    val tinSoup = Discount(ItemNumBought(Soup(), 2), Bread(), 50, "2 tins of Soup get a Bread for 50%")
    val weekPromo = List(applePromo, tinSoup)
    val tot = b.printResult(b.CalculateResults(weekPromo))
    tot.foreach(println(_))
  }
}
