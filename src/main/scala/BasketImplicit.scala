package adthena.shopping.basketImplicit

import adthena.shopping.domain._

/**
  * Created by palutz on 26/03/17.
  */

// Defining all the implicits used inside the Basket
object BasketImplicit {
  // Convert between string and the right representation of Item in our domain
  implicit def itemFactory(item: String): Option[Item] = {
    item match {
      case x if x.equalsIgnoreCase("soup") => Some(Soup())
      case x if x.equalsIgnoreCase("bread") => Some(Bread())
      case x if x.equalsIgnoreCase("milk") => Some(Milk())
      case x if x.equalsIgnoreCase("apples") => Some(Apples())
      case _ => None
    }
  }

  // Convert a list of string to a, eventually, list of Item
  implicit def basketParser(items: Option[List[String]])(implicit f: (String) => Option[Item]) : Map[Item, Int] = {
    val strings = items.toList.flatten.map(_.toLowerCase)   // flattening the Option
    strings.groupBy(x => x).   // grouping all the same string in a Map[String, List[String]]
          toList.map(x => (f(x._1), x._2.length)).   // List[(Option[Item], Int)]
          collect{case (Some(x),y) => (x, y)}.     // rmeoved empty (None) values and got a List[(Item, Int)]
          toMap
  }

    // Convert a result to an Option, returning None if the result is negative
  implicit def NotNegativeOption = new NotNegativeResult[Int] {
    def maybeResult(i : Int) = if(i >= 0) Some(i) else None
  }

  private def totalPrinter(tPr : String, v: String): String = s"$tPr: £$v"

  private def centsIntToDoubleStr(i : Int) : String = {
    val istr = s"$i"
    val dec = istr.takeRight(2)
    val l = istr.take(istr.length - 2)
    if(l.length > 0)
      s"$l.$dec"
    else
      s"0.$dec"
  }

  implicit def basketPrinter(res: ResultSummary): List[String] = {
    val subT = totalPrinter("Subtotal", res.subTotal.fold("0")(v => centsIntToDoubleStr(v)))
    val promo = res.offers match {
      case Left(_) => "(No offers available)"
      case Right(l) => l.map(ll => s"${ll._1}: -${ll._2}p").mkString(", ")
    }
    val tot = totalPrinter("Total price", res.total.fold("0")(v => centsIntToDoubleStr(v)))

    List(subT, promo, tot)
  }
}
