//import scalaz._
//import Scalaz._
//import std.option._
//import syntax.apply._
//val x = 1.some
//val y = 2.some
//val r = x.flatMap(a => y.map(a + _))
//
//val rf = for {
//  a <- x
//  b <- y
//} yield a + b
//val xy = (x |@| y)(_ + _)

// this is just a map in this case, between the proper command and the action
// or I can actually fake the use of proper command and use a list like this)
//
//val availableItems = List("Soup", "Bread", "Milk", "Apples")
//val priceList = Map(
//  "Soup" -> 650,
//  "Bread" -> 800,
//  "Milk" -> 1300,
//  "Apples" -> 1000)
//
//val parsed = for {
//  vc <- validCmd
//  c <- cmd
//  if(c == vc._1)
//} yield vc._2
//
//validCmd.flatMap(vc =>
//  cmd.withFilter(c => c == vc._1).map(_ => vc._2)
//)
//
//
//parsed.map {
//  x => x match {
//    case ("add", x) => x
//    case _ => "none"
//  }
//}
val a : Option[Int] = None // Some(1)
a.map(x => x * 20)
a.map(b => ("cippa", b))

val ll : List[(Option[Int], Int)] = List((Some(20), 0), (None, 10))
ll.collect{case (Some(x),y) => (x, y)}


val et :  Either[String, List[(String, Int)]] = Right(List(("cippa", 10), ("lippa", 1)))

et match {
  case Right(x) => x.map(ll => s"${ll._1}: -${ll._2 / 100}p").mkString(", ")
  case Left(_) => 0
}

val x: Option[Int] = None
x.fold("0")(xx => xx.toString)

val q ="11"
val w = q.takeRight(2)
val y ="1".take("1".length -2)
y.length

def intToDoubleStr(i : Int) : String = {
  val istr = s"$i"
  val dec = istr.takeRight(2)
  val l = istr.take(istr.length - 2)
  if(l.length > 0)
    s"$l.$dec"
  else
    s"0.$dec"
}
val res : Option[Int] = None
res.fold("0")(v => intToDoubleStr(v))

val p: List[Option[String]] = List(Some("a"), Some("a"), None, Some("b"))
val k = p.flatMap(x => x.toList).groupBy(x => x)
k.toList.map(mx => (mx._1, mx._2.length))

val a1 = Some(1)
val aa = None

a1.isDefined
aa.isDefined


