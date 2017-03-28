# scalaShoppingBasket
A simple shopping basket in Scala, with an idiomatic approach...

### Pre-requisites ###

* Java JDK (Oracle or OpenJDK) 1.8.x
* Scala 2.12.x+ (compiled with 2.12.1) (http://www.scala-lang.org/) 
* sbt 0.13.x (http://www.scala-sbt.org/)

### How do I get set up? ###

* From the project root just run `$ sbt`
* '> update` or`compile` if you want to compile before running
* `> run <command or item to add> (eg.: run Apples Milk Bread)
* CTRL+D to exit the sbt


## Problem description

The goods that can be purchased, together with their normal prices are: 
 
- Soup – 65p per tin 
- Bread – 80p per loaf 
- Milk – £1.30 per bottle 
- Apples – £1.00 per bag 
 
Current special offers: 
 
- Apples have a 10% discount off their normal price this week 
- Buy 2 tins of soup and get a loaf of bread for half price 
 
The program should accept a list of items in the basket and output the subtotal, the special offer discounts 
and the final price. 
 
Input should be via the command line in the form PriceBasket item1 item2 item3 ... 
 
For example: 
 
PriceBasket Apples Milk Bread 
 
Output should be to the console, for example:  
Subtotal: £3.10  
Apples 10% off: ­10p  
Total: £3.00 
 
If no special offers are applicable the code should output:  
Subtotal: £1.30  
(No offers available)  
Total price: £1.30 

#Requires:
