package adthena.shopping.domain

/**
  * Created by palutz on 26/03/17.
  */


sealed trait Item {
  val name: String
}
case class Soup(name: String = "Soup") extends Item
case class Bread(name: String = "Bread") extends Item
case class Milk(name: String = "Milk") extends Item
case class Apples(name: String = "Apples") extends Item


sealed trait Condition {
  val item: Item
}
case class ItemBought(item: Item) extends Condition
case class ItemNumBought(item: Item, quantity: Int) extends Condition


sealed trait Promotion {
  val condition: Condition
  val applyTo: Item
}
case class Discount(condition: Condition, applyTo: Item, percentage : Int, descr: String) extends Promotion


case class ItemCost(item: Item, penceForUnit: Int)


case class ResultSummary(subTotal: Option[Int], offers: Either[String, List[(String, Int)]], total: Option[Int])

// Convert the result to a non negative Option (Some(_) if > 0 or None)
trait NotNegativeResult[A] { def maybeResult(i : A): Option[Int] }