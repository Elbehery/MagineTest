import com.sun.media.sound.InvalidDataException
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.parsing.json.JSON
import net.liftweb.json._
import net.liftweb.json.Serialization.write


object ChanCOPter {

  case class Product(id: String, name: String, price: Double, channelId: List[Long])

  case class Summary(totalChannels: Int, totalUniqueChannels: Int, totalPrice: Double, uniqueChannelIds: Set[Long])

  case class ProductToBuy(id: String, name: String, price: Double)

  case class ChanCOPterOutput(summary: Summary, productToBuy: Set[ProductToBuy])


  def parseProductsJsonIntoListOfProducts(productJson: Any): List[Product] = {

    require(productJson != null, throw new NullPointerException("Input Json is Null"))
    require(productJson != "", throw new IllegalArgumentException("Input Json is Empty"))

    val products = ListBuffer[Product]()

    productJson match {
      case Some(e: Map[String, Any]) => {
        val productsJson = e("products").asInstanceOf[List[Map[String, Any]]]

        productsJson.map(x =>
          products += new Product(x("id").asInstanceOf[String], x("name").asInstanceOf[String],
            x("price").asInstanceOf[Double], x("channelIds").asInstanceOf[List[Long]])
        )
      }
      case None => throw new Error("Parsing Failed : Malformed Input Json")
    }

    products.toList
  }

  def channelPerCurrencyOptimizer(products: List[Product], budget: Double): List[Product] = {

    require(products != null, throw new NullPointerException("Input List[Product] is Null"))
    require(!products.isEmpty, throw new IllegalArgumentException("Input List[Product] is Empty"))

    // step 1: sort the products first by Price ASC, then by Channels Size DESC.
    val sortedProducts = products.sortBy(x => x.price).sortWith(_.channelId.length > _.channelId.length)

    // step 2 : iterate over the sortedProducts, and check if the price is within the budget to buy, or to skip and check the next product.
    // Stopping criteria : even the budget is zero, or the products are all traversed.
    //
    // this function utilizes the tail recursion to re-use the same function stack, to avoid possible stackOverFlow for large inputs.
    def calculateIdealPackages(budget: Double, sortedProducts: List[Product], acc: ListBuffer[Product]): List[Product] = {

      if (budget <= 0 || sortedProducts.isEmpty)
        return acc.toList
      else if (budget >= sortedProducts.head.price) {
        val product = sortedProducts.head
        acc += product
        calculateIdealPackages(budget - product.price, sortedProducts.tail, acc);
      } else {
        calculateIdealPackages(budget, sortedProducts.tail, acc);
      }
    }

    calculateIdealPackages(budget, sortedProducts, new ListBuffer[Product])

  }

  def computeChanCOPterOutput(products: List[Product]): ChanCOPterOutput = {

    require(products != null, throw new NullPointerException("Input List[Product] is Null"))
    require(!products.isEmpty, throw new IllegalArgumentException("Input List[Product] is Empty"))

    // accumulators to products values
    var totalChannels = 0;
    var totalPrice = 0.0;
    val uniqueChannelIds = scala.collection.mutable.Set[Long]()
    val productsToBuy = scala.collection.mutable.Set[ProductToBuy]()

    // map each product into an object[ProductToBuy]
    def extractProductsToBuyFromProducts(product: Product): Unit = {
      productsToBuy += new ProductToBuy(product.id, product.name, product.price)
    }

    // recurse over the products, update the accumulators with the summary.
    def extractSummaryFromProducts(products: List[Product]) {

      if (products.isEmpty)
        return

      val currentProduct = products.head
      totalChannels = totalChannels + currentProduct.channelId.size
      totalPrice = totalPrice + currentProduct.price
      uniqueChannelIds ++= currentProduct.channelId

      extractSummaryFromProducts(products.tail)
    }

    // invoke the extractors methods.. !! I could use a map function for the "Summary Extractors" as well.
    // I am just trying to use different functional schemes for the sake of the test
    products.map(x => extractProductsToBuyFromProducts(x))
    extractSummaryFromProducts(products)

    val summary = new Summary(totalChannels, uniqueChannelIds.size, totalPrice, uniqueChannelIds.toSet)

    return new ChanCOPterOutput(summary, productsToBuy.toSet)

  }


  def main(args: Array[String]): Unit = {

    if (args.length < 1)
      throw new IllegalArgumentException("Invalid Input: Please enter your Budget as a command line argument.")

    // check if the input is consist of at least "4" Digits, and only Numeric Digits. Otherwise, Exception thrown.
    require(args(0).length >= 4 && args(0).matches("\\d+"), throw new InvalidDataException("Wrong format for Input : the input format should be “öre” format, e.g. 99kr = 9900)"))
    val budget = args(0).toDouble

    // Read the Marker API and parse it.
    val fileContents = Source.fromURL(getClass.getResource("/sample.json")).getLines.mkString
    val parsedJson = JSON.parseFull(fileContents)

    // Parse each product from Json into a product object, and add it to the immutable list of products.
    val products = parseProductsJsonIntoListOfProducts(parsedJson)

    // compute the ideal TV packs according to the budget, and convert the output into the required JSON format
    val idealPackages = channelPerCurrencyOptimizer(products, budget)
    val output = computeChanCOPterOutput(idealPackages)
    implicit val formats = DefaultFormats
    val outputJson = write(output)

    println(outputJson)

  }
}
