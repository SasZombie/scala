import scala.io.Source
import Helpers.zipWith

case class Dataset (data: List[List[String]]) {

  override def toString: String = data.toString()

  // shorthand function for selecting a single column
  def selectColumn(col: String): Dataset =  ???
  /*  Selects columns for linear regression (most likely two) */
  def selectColumns(cols: List[String]): Dataset =  ???

  /* splits a DS into training and evaluation. Percentage is a value between 0 and 1
  * the first value of the pair is for training, the second is for evaluation */
  def split(percentage: Double): (Dataset, Dataset) = ???

  // drop the header column
    def getRows: List[List[String]] = data.tail
    def getColumns: List[String] = data.head
}

object Dataset{
  def apply(csv_filename: String): Dataset = {
    var data: List[List[String]] = Nil
    for (line <- Source.fromFile(csv_filename).getLines())
      data = data ++ List(line.split(",").toList)
    new Dataset(data)
  }

  def apply(ds: List[List[String]]): Dataset = new Dataset(ds)
}



