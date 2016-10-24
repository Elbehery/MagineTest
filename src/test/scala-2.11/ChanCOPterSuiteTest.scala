import net.liftweb.json.DefaultFormats
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import scala.io.Source
import scala.util.parsing.json.JSON
import net.liftweb.json.Serialization.write


class ChanCOPterSuiteTest extends FunSuite with BeforeAndAfterAll {

  private val productMagic: ChanCOPter.Product = new ChanCOPter.Product("00001SEPAC", "Magic", 9900.0, List[Double](191.0, 12397.0, 10061.0, 181.0, 10198.0, 232.0, 250.0, 154.0, 11227.0, 72.0, 176.0, 227.0, 11573.0, 52.0, 158.0, 75.0, 119.0, 123.0, 120.0, 10196.0, 178.0, 10116.0, 15199.0).asInstanceOf[List[Long]])
  private val productKids: ChanCOPter.Product = new ChanCOPter.Product("00003SEPAC", "Kids", 5900.0, List(52.0, 158.0, 75.0, 232.0, 250.0, 14380.0, 154.0, 11227.0).asInstanceOf[List[Long]])
  private val productDiscovery: ChanCOPter.Product = new ChanCOPter.Product("00004SEPAC", "Discovery", 3900.0, List(121.0, 10210.0, 122.0).asInstanceOf[List[Long]])
  private val productDisney: ChanCOPter.Product = new ChanCOPter.Product("00005SEPAC", "Disney", 3900.0, List(52.0, 158.0, 75.0).asInstanceOf[List[Long]])
  private val productNews: ChanCOPter.Product = new ChanCOPter.Product("00006SEPAC", "News", 2900.0, List(10116.0, 30.0, 181.0, 191.0).asInstanceOf[List[Long]])
  private val productBBC: ChanCOPter.Product = new ChanCOPter.Product("00024SEPAC", "BBC", 3900.0, List(12397.0, 10061.0, 191.0).asInstanceOf[List[Long]])
  private val productSportsAndAction: ChanCOPter.Product = new ChanCOPter.Product("00007SEPAC", "Sports & Action", 3900.0, List(178.0, 10135.0, 13707.0, 13702.0, 10058.0, 14153.0).asInstanceOf[List[Long]])
  private val productHorseAndCountry: ChanCOPter.Product = new ChanCOPter.Product("00013SEPAC", "Horse and Country", 3900.0, List(14055.0).asInstanceOf[List[Long]])
  private val productMotorsTV: ChanCOPter.Product = new ChanCOPter.Product("00018SEPAC", "Motors TV", 1900.0, List(10058.0).asInstanceOf[List[Long]])
  private val productDocubox: ChanCOPter.Product = new ChanCOPter.Product("00033SEPAC", "Docubox", 1900.0, List(14290.0).asInstanceOf[List[Long]])
  private val productDansk: ChanCOPter.Product = new ChanCOPter.Product("00009SEPAC", "Dansk", 2900.0, List(1.0, 2.0, 10155.0, 10154.0, 10153.0, 3.0).asInstanceOf[List[Long]])
  private val productSuomi: ChanCOPter.Product = new ChanCOPter.Product("00010SEPAC", "Suomi", 1900.0, List(10206.0, 14089.0, 14090.0).asInstanceOf[List[Long]])
  private val productNorsk: ChanCOPter.Product = new ChanCOPter.Product("00023SEPAC", "Norsk", 2900.0, List(130.0, 129.0, 173.0, 142.0, 10003.0).asInstanceOf[List[Long]])
  private val productTürkçe: ChanCOPter.Product = new ChanCOPter.Product("00026SEPAC", "Türkçe", 2900.0, List(10203.0, 10125.0, 10130.0, 10126.0, 10129.0).asInstanceOf[List[Long]])
  private val productDeutsch: ChanCOPter.Product = new ChanCOPter.Product("00025SEPAC", "Deutsch", 6900.0, List(2001.0, 2002.0, 2003.0, 2004.0, 2005.0, 2006.0, 2007.0, 2008.0, 2009.0, 2010.0, 2011.0, 2012.0, 2013.0, 2014.0, 2015.0, 2016.0, 2017.0).asInstanceOf[List[Long]])
  private val productPolski: ChanCOPter.Product = new ChanCOPter.Product("00012SEPAC", "Polski", 2900.0, List(10925.0, 10142.0, 10924.0).asInstanceOf[List[Long]])

  var products: List[ChanCOPter.Product] = List()
  var nullProducts = null
  var emptyProducts = List.empty

  val jsonProducts = Source.fromURL(getClass.getResource("/sample.json")).getLines.mkString
  var nullJson = null
  var emptyJson = ""
  val jsonOutput = "{\"summary\":{\"totalChannels\":48,\"totalUniqueChannels\":41,\"totalPrice\":22700.0,\"uniqueChannelIds\":[10198.0,10061.0,2014.0,120.0,2010.0,52.0,15199.0,2007.0,2017.0,2002.0,2011.0,2015.0,10116.0,14380.0,12397.0,2006.0,2001.0,176.0,191.0,2016.0,181.0,2005.0,123.0,10196.0,11573.0,2012.0,154.0,72.0,2013.0,250.0,158.0,2004.0,2009.0,75.0,119.0,11227.0,2003.0,2008.0,178.0,227.0,232.0]},\"productToBuy\":[{\"id\":\"00003SEPAC\",\"name\":\"Kids\",\"price\":5900.0},{\"id\":\"00025SEPAC\",\"name\":\"Deutsch\",\"price\":6900.0},{\"id\":\"00001SEPAC\",\"name\":\"Magic\",\"price\":9900.0}]}"

  override def beforeAll() {

    products = List(productMagic, productKids, productDiscovery, productDisney,
      productNews, productBBC, productSportsAndAction, productHorseAndCountry,
      productMotorsTV, productDocubox, productDansk, productSuomi, productNorsk,
      productTürkçe, productDeutsch, productPolski)

  }

  override def afterAll() {
    products = Nil
  }


  test(" test parseProductsJsonIntoListOfProducts with Null argument ") {
    intercept[NullPointerException] {
      ChanCOPter.parseProductsJsonIntoListOfProducts(nullJson)
    }
  }

  test(" test parseProductsJsonIntoListOfProducts with Empty argument ") {
    intercept[IllegalArgumentException] {
      ChanCOPter.parseProductsJsonIntoListOfProducts(emptyJson)
    }
  }

  test(" test parseProductsJsonIntoListOfProducts with Correct JSON argument ") {
    val actual = ChanCOPter.parseProductsJsonIntoListOfProducts(JSON.parseFull(jsonProducts))
    assert(actual === products, "parseProductsJsonIntoListOfProducts fails with Correct JSON ")
  }

  test(" test channelPerCurrencyOptimizer with Null List[Products] ") {
    intercept[NullPointerException] {
      ChanCOPter.channelPerCurrencyOptimizer(nullProducts, 10.0)
    }
  }

  test(" test channelPerCurrencyOptimizer with Empty List[Products] ") {
    intercept[IllegalArgumentException] {
      ChanCOPter.channelPerCurrencyOptimizer(emptyProducts, 10.0)
    }
  }

  test(" test channelPerCurrencyOptimizer with Budget 20000 ") {

    assert(ChanCOPter.channelPerCurrencyOptimizer(products, 20000.0) === List(productMagic, productDeutsch, productDansk), "channelPerCurrencyOptimizer with Budget 20000 fails")
  }

  test(" test channelPerCurrencyOptimizer with Budget 40000 ") {

    assert(ChanCOPter.channelPerCurrencyOptimizer(products, 40000.0) === List(productMagic, productDeutsch, productKids, productDansk, productSportsAndAction, productNorsk, productTürkçe, productNews), "channelPerCurrencyOptimizer with Budget 40000 fails")
  }

  test(" test channelPerCurrencyOptimizer with Budget 15000 ") {

    assert(ChanCOPter.channelPerCurrencyOptimizer(products, 15000.0) === List(productMagic, productDansk, productSuomi), "channelPerCurrencyOptimizer with Budget 15000 fails")
  }

  test(" test channelPerCurrencyOptimizer with Budget 10000 ") {

    assert(ChanCOPter.channelPerCurrencyOptimizer(products, 10000.0) === List(productMagic), "channelPerCurrencyOptimizer with Budget 10000 fails")
  }

  test(" test channelPerCurrencyOptimizer with Budget 9000 ") {

    assert(ChanCOPter.channelPerCurrencyOptimizer(products, 9000.0) === List(productDeutsch, productSuomi), "channelPerCurrencyOptimizer with Budget 9000 fails")
  }

  test(" test channelPerCurrencyOptimizer with Budget 7000 ") {

    assert(ChanCOPter.channelPerCurrencyOptimizer(products, 5000.0) === List(productDansk, productSuomi), "channelPerCurrencyOptimizer with Budget 5000 fails")
  }


  test(" test computeChanCOPterOutput with Null List[Products] ") {
    intercept[NullPointerException] {
      ChanCOPter.computeChanCOPterOutput(nullProducts)
    }
  }

  test(" test computeChanCOPterOutput with Empty List[Products] ") {
    intercept[IllegalArgumentException] {
      ChanCOPter.computeChanCOPterOutput(emptyProducts)
    }
  }

  test(" test computeChanCOPterOutput with Products of Budget 23000 ") {

    implicit val formats = DefaultFormats
    assert(write(ChanCOPter.computeChanCOPterOutput(ChanCOPter.channelPerCurrencyOptimizer(products, 23000))) === jsonOutput, "computeChanCOPterOutput with Budget 23000 fails")
  }

}
