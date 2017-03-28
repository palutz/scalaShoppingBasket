package adthena.shopping.pricelist
/**
  * Created by palutz on 26/03/17.
  */
import adthena.shopping.domain._

class PriceList(val list: Map[Item, Int]) {
  def priceForItem(item: Item): Option[Int] = list get item
}

object PriceList{
  def apply(): PriceList = new PriceList(
    Map(
      Soup() -> 65,
      Bread() -> 80,
      Milk() -> 130,
      Apples() -> 100
    )
  )
}