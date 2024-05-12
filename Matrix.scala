import Helpers.zipWith

import scala.math.sqrt
case class Matrix(m: Option[List[List[Double]]]){

  type Tabular = List[List[Double]]

  def transpose: Matrix = ???

  def *(other: Matrix): Matrix = ???

  def -(other: Matrix): Matrix = ???

  def normalize: Matrix = ???

  def map(f: Double => Double): Matrix = ???

  def ++(x: Double): Matrix = ???

  def dimensions: String = ???

  // computes the mean squared error, only if this matrix is of dimension n x 1:
  def meanSquaredError: Option[Double] = ???

  override def toString: String = m.toString
}

object Matrix {

  def apply(): Matrix = ???
  def apply(raw: List[List[Double]]): Matrix = ???
  def apply(dataset: Dataset): Matrix = ???
  def apply(s: String): Matrix = ???

}
