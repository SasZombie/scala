file://<WORKSPACE>/homework4.worksheet.sc
### java.lang.AssertionError: assertion failed: position error, parent span does not contain child span
parent      = new Matrix(null: <notype>) # -1,
parent span = <1495..1562>,
child       = null # -1,
child span  = [1506..1564..1564]

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-runtime_3/2.5.2/mdoc-runtime_3-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-interfaces/2.5.2/mdoc-interfaces-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_3/0.12.0/metaconfig-pprint_3-0.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:



action parameters:
uri: file://<WORKSPACE>/homework4.worksheet.sc
text:
```scala
import javax.xml.crypto.Data
import scala.io.Source
import scala.math.sqrt

def profileID = 2

def dataset = Dataset("datasets/houseds.csv")
def steps   = 10000 // the number of steps for gradient descent
def alpha   = 0.000000000001

def rootMeanSquareError(X: Matrix, Y: Matrix, parameters: Matrix): Option[Double] = ???

def gradientDescentStep(X: Matrix, Y: Matrix, parameters: Matrix): Matrix = ???

def linearRegression(steps: Int, parameters: Matrix, features: List[String]): (Matrix, Option[Double]) = ???

object Helpers {
  def zipWith[A, B, C](op: (A, B) => C)(l1: List[A], l2: List[B]): List[C] = {
    assert(l1.size == l2.size)

    l1.zip(l2).map { case (x, y) =>
      op(x, y)
    }
  }
}

Helpers.zipWith((a: Int, b: Int) => a + b)(List(1, 2, 3), List(2, 3, 4))

case class Matrix(m: Option[List[List[Double]]]) {

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

  def apply(): Matrix                        = new Matrix(None)
  def apply(raw: List[List[Double]]): Matrix = new Matrix(Option(raw))
  def apply(dataset: Dataset): Matrix        = new Matrix(
    
  def apply(s: String): Matrix               = ???

}

case class Dataset(data: List[List[String]]) {

  override def toString: String = data.toString()

  // shorthand function for selecting a single column
  def selectColumn(col: String): Dataset = {
    selectColumns(List(col))
  }
  /*  Selects columns for linear regression (most likely two) */
  // Very confusing
  def selectColumns(cols: List[String]): Dataset = {

    val colIndices = cols.map(col => data.head.indexOf(col))
    val header     = data.map(row => colIndices.map(idx => row(idx)))
    Dataset(header)

  }

  /* splits a DS into training and evaluation. Percentage is a value between 0 and 1
   * the first value of the pair is for training, the second is for evaluation */
  def split(percentage: Double): (Dataset, Dataset) = {
    //4 * 0.5 = 2
    //
    val toDrop = 1 - percentage
    val (header, unrows) = data.splitAt(1)
    val rows = util.Random.shuffle(unrows)
    (Dataset(header ++ rows.take((rows.length * toDrop).round.toInt)), Dataset(header ++ rows.drop((rows.length * toDrop).round.toInt)))
  }

  // drop the header column
  def getRows: List[List[String]] = data.tail
  def getColumns: List[String]    = data.head
}

object Dataset {
  def apply(csv_filename: String): Dataset = {
    var data: List[List[String]] = Nil
    for (line <- Source.fromFile(csv_filename).getLines())
      data = data ++ List(line.split(",").toList)
    new Dataset(data)
  }

  def apply(ds: List[List[String]]): Dataset = new Dataset(ds)
}

val test_ds = Dataset("datasets/tinyds.csv")
// Tolerance for result and sum of errors
val tolerance: Double = 0.05

//List(List(Name), List(firstElem), List(seccondElem), List(thirdElem)) of the columns
//Dataset Tests
val selcol = test_ds.selectColumns(List("GrLivArea"))
assert(selcol.getColumns == List("GrLivArea"))
assert(selcol.getRows == List(List("100"), List("200"), List("210"), List("300")))
val selcols = test_ds.selectColumns(List("GrLivArea", "GarageArea"))
assert(
  selcols.getRows == List(List("100", "548"), List("200", "460"), List("210", "608"), List("300", "642"))
)
assert(selcols.getColumns == List("GrLivArea", "GarageArea"))

println(test_ds.split(0.0))

test_ds.split(0.0)._1 
//Imposible assert! This is, missing 1 argument
// assert(
//   test_ds.split(0.0)._1 == Dataset(
//     List(
//       List("GrLivArea", "GarageArea", "SalePrice"),
//       List("100", "548", "50000"),
//       List("210", "608", "100500"),
//       List("300", "642", "153000")
//     )
//   )
// )
test_ds.split(0.0)._2 
//Somehow this works
assert(test_ds.split(0.0)._2 == Dataset(List(List("GrLivArea", "GarageArea", "SalePrice"))))
test_ds.split(0.5)._1

// assert(
//   test_ds.split(0.5)._1 == Dataset(
//     List(
//       List("GrLivArea", "GarageArea", "SalePrice"),
//       List("100", "548", "50000"),
//       List("300", "642", "153000")
//     )
//   )
// )
// assert(
//   test_ds.split(0.5)._2 == Dataset(
//     List(
//       List("GrLivArea", "GarageArea", "SalePrice"),
//       List("200", "460", "100000"),
//       List("210", "608", "100500")
//     )
//   )
// )

assert(test_ds.split(1.0)._1 == Dataset(List(List("GrLivArea", "GarageArea", "SalePrice"))))
test_ds.split(1.0)._2
//Impossible assert, the arguments are miss-matched
// assert(
//   test_ds.split(1.0)._2 == Dataset(
//     List(
//       List("GrLivArea", "GarageArea", "SalePrice"),
//       List("200", "460", "100000"),
//       List("100", "548", "50000"),
//       List("210", "608", "100500"),
//       List("300", "642", "153000")
//     )
//   )
// )

//Matrix Tests
// Square matrix
assert(
  Matrix(List(List(1.0, 2.0, 3.0), List(4.0, 5.0, 6.0), List(7.0, 8.0, 9.0))).transpose ==
    Matrix(List(List(1.0, 4.0, 7.0), List(2.0, 5.0, 8.0), List(3.0, 6.0, 9.0)))
)
// Rectangular matrix
assert(
  Matrix(List(List(1.0, 2.0, 3.0), List(4.0, 5.0, 6.0))).transpose ==
    Matrix(List(List(1.0, 4.0), List(2.0, 5.0), List(3.0, 6.0)))
)
// Empty
assert(Matrix(List()).transpose == Matrix(List()))
// Single row
assert(Matrix(List(List(1.0, 2.0, 3.0))).transpose == Matrix(List(List(1.0), List(2.0), List(3.0))))
// Single Column
assert(Matrix(List(List(1.0), List(2.0), List(3.0))).transpose == Matrix(List(List(1.0, 2.0, 3.0))))

val ds1 =
  """1.0 2.0
3.0 4.0""".stripMargin
val ds2 =
  """2.0 0.0
1.0 2.0""".stripMargin

val ds3 =
  """4.0 4.0
10.0 8.0""".stripMargin

assert(Matrix(ds1) * Matrix(ds2) == Matrix(ds3))

val ds4 =
  """1.0 2.0 3.0
4.0 5.0 6.0
7.0 8.0 9.0""".stripMargin

val ds5 =
  """7.0 8.0
9.0 10.0
11.0 12.0""".stripMargin

val ds6 =
  """58.0 64.0
139.0 154.0
220.0 244.0""".stripMargin

assert(Matrix(ds4) * Matrix(ds5) == Matrix(ds6))

val ds7 =
  """1.0 2.0 3.0""".stripMargin

val ds8 =
  """4.0
5.0
6.0""".stripMargin

assert(Matrix(ds7) * Matrix(ds8) == Matrix(List(List(32.0))))

val ds9 =
  """4.0
5.0
6.0""".stripMargin

val ds10 =
  """1.0 2.0 3.0""".stripMargin

val ds11 =
  """4.0 8.0 12.0
5.0 10.0 15.0
6.0 12.0 18.0""".stripMargin

// Single column * Single row ?
assert(Matrix(ds9) * Matrix(ds10) == Matrix(ds11))

val ds12 =
  """1.0 2.0 3.0
4.0 5.0 6.0""".stripMargin

val ds13 =
  """7.0 8.0
9.0 10.0""".stripMargin
// None
assert(Matrix(ds12) * Matrix(ds13) == Matrix())

val ds113 =
  """4.0 5.0
6.0 7.0""".stripMargin

val ds22 =
  """1.0 2.0
3.0 4.0""".stripMargin

val ds33 =
  """3.0 3.0
3.0 3.0""".stripMargin

assert(Matrix(ds113) - Matrix(ds22) == Matrix(ds33))

val ds111 =
  """4.0 5.0 6.0
7.0 8.0 9.0""".stripMargin

val ds222 =
  """1.0 2.0 3.0
4.0 5.0 6.0""".stripMargin

val ds333 =
  """3.0 3.0 3.0
3.0 3.0 3.0""".stripMargin
assert(Matrix(ds111) - Matrix(ds222) == Matrix(ds333))

val ds1a =
  """4.0 5.0 6.0""".stripMargin

val ds2a =
  """1.0 2.0 3.0""".stripMargin

val ds3a =
  """3.0 3.0 3.0""".stripMargin

assert(Matrix(ds1a) - Matrix(ds2a) == Matrix(ds3a))

val ds1b =
  """4.0 5.0 6.0
7.0 8.0 9.0""".stripMargin

val ds2b =
  """1.0 2.0 3.0""".stripMargin
// None
assert(Matrix(ds1b) - Matrix(ds2b) == Matrix())

val matrix       = Matrix(List(List(1.0, 2.0, 3.0), List(4.0, 5.0, 6.0)))
val mappedMatrix = matrix.map(_ * 2)
assert(mappedMatrix == Matrix(List(List(2.0, 4.0, 6.0), List(8.0, 10.0, 12.0))))

val matrix1    = Matrix(List(List(1.0, 2.0), List(3.0, 4.0)))
val newMatrix1 = matrix.++(0.0)
assert(newMatrix1 == Matrix(List(List(1.0, 2.0, 0.0), List(3.0, 4.0, 0.0))))

// Expected values
val expectedResults: List[(Matrix, Option[Double])] = List(
  (Matrix(List(List(0.0), List(0.0))), Some(60000.0)),
  (Matrix(List(List(0.0), List(0.0))), Some(80000.0)),
  (Matrix(List(List(0.0), List(0.0), List(0.0))), Some(50000.0))
)

// Compute results using linear regression function
val computedResults: List[(Matrix, Option[Double])] = List(
  linearRegression(steps, Matrix(List(List(0.0, 0.0)).transpose), List("GrLivArea", "SalePrice")),
  linearRegression(steps, Matrix(List(List(0.0, 0.0)).transpose), List("GarageArea", "SalePrice")),
  linearRegression(
    steps,
    Matrix(List(List(0.0, 0.0, 0.0)).transpose),
    List("GrLivArea", "GarageArea", "SalePrice")
  )
)

// we compare the expected vs achieved square mean error only in tests. We do not test parameters in any way;
val trueValues: List[Boolean] = {
  for (((_, cerr), (_, eerr)) <- computedResults.zip(expectedResults))
    yield {
      (cerr, eerr) match {
        case (Some(x), Some(y)) => math.abs(x - y) < 10000
        case _                  => false
      }
    }
}
// all comparisons much succeed
assert(trueValues.foldRight(true)(_ && _))

```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:175)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:333)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:333)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:200)
	dotty.tools.dotc.ast.Positioned.check$1$$anonfun$3(Positioned.scala:205)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:333)
	dotty.tools.dotc.ast.Positioned.check$1(Positioned.scala:205)
	dotty.tools.dotc.ast.Positioned.checkPos(Positioned.scala:226)
	dotty.tools.dotc.parsing.Parser.parse$$anonfun$1(ParserPhase.scala:39)
	dotty.tools.dotc.parsing.Parser.parse$$anonfun$adapted$1(ParserPhase.scala:40)
	scala.Function0.apply$mcV$sp(Function0.scala:42)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:440)
	dotty.tools.dotc.parsing.Parser.parse(ParserPhase.scala:40)
	dotty.tools.dotc.parsing.Parser.runOn$$anonfun$1(ParserPhase.scala:49)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:333)
	dotty.tools.dotc.parsing.Parser.runOn(ParserPhase.scala:49)
	dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:246)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1323)
	dotty.tools.dotc.Run.runPhases$1(Run.scala:262)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:270)
	dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:279)
	dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:71)
	dotty.tools.dotc.Run.compileUnits(Run.scala:279)
	dotty.tools.dotc.Run.compileSources(Run.scala:194)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:165)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:34)
	scala.meta.internal.pc.ScalaPresentationCompiler.semanticdbTextDocument$$anonfun$1(ScalaPresentationCompiler.scala:217)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: position error, parent span does not contain child span
parent      = new Matrix(null: <notype>) # -1,
parent span = <1495..1562>,
child       = null # -1,
child span  = [1506..1564..1564]