package adthena.shopping.basket
/**
  * Created by palutz on 26/03/17.
  */
import adthena.shopping.basketImplicit.BasketImplicit._
import adthena.shopping.domain._
import adthena.shopping.pricelist._

class Basket (val items: Map[Item, Int], priceList: PriceList) {

  def CalculateSubTotal() : Int = {
    items.map(x => priceList.priceForItem(x._1).getOrElse(0) * x._2).sum  // map all the prices to the items
              // .collect{case Some(x) => x}.sum  // partial function to collect only the valid value and then sum them up
  }

  private def amountCalc(item: Item, percen: Int, occur: Int) : Option[Int] =
    priceList.priceForItem(item).map(x => (x * percen / 100) * occur)

  private def calculatePromotions(p: Promotion): Option[(String, Int)] = {
//    val matches = items.find(i => i == p.condition.item) // select all the items that match the offer
//    val applyTo = items.find(i => i == p.applyTo) // select all the items ordered that could be affected by the offer
    val matches = items getOrElse (p.condition.item, 0)
    val applyN = items getOrElse (p.applyTo, 0)

    if (matches > 0 && applyN > 0) {
      p match {
        case d : Discount => {
          d.condition match {
            case c : ItemBought => amountCalc(d.applyTo, d.percentage, matches).map(r => (d.descr, r)) // apply the discount to all items that matches
            case c : ItemNumBought if c.quantity <= matches =>
              amountCalc(d.applyTo, d.percentage, matches / c.quantity).map(r => (d.descr, r))  // apply to all grouped (size of items required) that match
            case _ => None
          }
        }
        case _ => None
      }
    } else None
  }

  def applyPromotions(prom: List[Promotion]): Either[String, List[(String, Int)]] = {
    def applyProm(toApply: List[Promotion]): List[(String, Int)] = {
      def inner(ps: List[Promotion], rs: List[Option[(String, Int)]]): List[(String, Int)] = {
        ps match {
          case x :: xs => inner(xs, calculatePromotions(x) :: rs)
          case _ => rs.flatten
        }
      }
      inner(toApply, List())
    }

    val ps = for {
      p <- prom
      i <- items
      if p.condition.item == i._1
    } yield p

    ps match {
      case x :: xs => Right(applyProm(ps))
      case _ => Left("(No offers available)")
    }
  }


  // Calculate all the basket's results and return in a proper structure
  def CalculateResults(proms: List[Promotion])(implicit notNeg: NotNegativeResult[Int]): ResultSummary = {
    val subTot = notNeg.maybeResult(CalculateSubTotal()) // get the subTotal and check if is not negative
    val promotions = applyPromotions(proms) // list of all available promotions
    val endTot = promotions match {  // calculate the Total price
      case Right(p) => subTot.flatMap(st => notNeg.maybeResult(st - p.map(_._2).sum))
      case Left(_) => subTot  // no changes.. no promotions applied
    }
    ResultSummary(subTot, promotions, endTot)
  }

  def printResult(res: ResultSummary)(implicit outputFormatter: ResultSummary => List[String]): List[String] =
    outputFormatter(res)
}


object Basket {
  def apply(items: Option[List[String]], priceList: PriceList)(implicit itemParser: Option[List[String]] => Map[Item, Int]) = {
    new Basket(itemParser(items), priceList)
  }
}