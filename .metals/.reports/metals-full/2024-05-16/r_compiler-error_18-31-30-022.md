file://<WORKSPACE>/homework4.worksheet.sc
### java.lang.NoClassDefFoundError: sourcecode/Name

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-runtime_3/2.5.2/mdoc-runtime_3-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-interfaces/2.5.2/mdoc-interfaces-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_3/0.12.0/metaconfig-pprint_3-0.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 5432
uri: file://<WORKSPACE>/homework4.worksheet.sc
text:
```scala
object worksheet{
  import javax.xml.crypto.Data
  import scala.io.Source
  import scala.math.sqrt
  
  def profileID = 2
  
  def dataset = Dataset("datasets/houseds.csv")
  def steps   = 10000 // the number of steps for gradient descent
  def alpha   = 0.000000000001
  
  case class Matrix(m: Option[List[List[Double]]]) {
  
    type Tabular = List[List[Double]]
  
    def transpose: Matrix = {
      assert(m != None)
  
      Matrix(this.m.get.transpose)
    }
  
    def *(other: Matrix): Matrix = {
      val row1 = m.get.length
      val row2 = other.m.get.length
      val col1 = m.get(0).length
      val col2 = other.m.get(0).length
  
      // val result = List.ofDim[Double](row1, col2)
  
      if (col1 != row2)
        Matrix()
      else {
        val result = for (i <- 0 until row1) yield {
          for (j <- 0 until col2) yield {
            val sum = (for (k <- 0 until col1) yield this.m.get(i)(k) * other.m.get(k)(j)).sum
            sum
          }
        }.toList
  
        Matrix(result.toList)
      }
  
    }
  
    def -(other: Matrix): Matrix = {
  
      if (this.m.get.size != other.m.get.size || this.m.get(0).size != other.m.get(0).size)
        Matrix()
      else
        Matrix(this.m.get.lazyZip(other.m.get).map { (inner1, inner2) =>
          (inner1.lazyZip(inner2)).map(_ - _)
        })
    }
  
    def normalize: Matrix = {
      val size = this.m.size * this.m.get(0).size
      Matrix()
      // Matrix(this.m.get.map{
      //     (inner) => inner.map{
      //         case elem: Double => 1/size
      //     }.toList
      // }.toList)
    }
  
    def map(f: Double => Double): Matrix = {
      Matrix(this.m.get.map { (inner) =>
        inner.map { case elem: Double =>
          f(elem)
        }.toList
      }.toList)
    }
  
    def ++(x: Double): Matrix = {
      Matrix(this.m.get.map { (innerList) =>
        innerList ++ List(x)
      })
    }
  
    def dimensions: String = ???
  
    // computes the mean squared error, only if this matrix is of dimension n x 1:
    def meanSquaredError: Option[Double] = {
      if (this.m.get(0).size > 1)
        None
      else {
        //Lets pretend this is correct
  
         val inter = this.m.get.flatten
         val sum = inter.sum
         Some((sum * sum)/inter.size)
      }
    }
  
    override def toString: String = m.toString
  }
  
  object Matrix {
  
    def apply(): Matrix                        = new Matrix(None)
    def apply(raw: List[List[Double]]): Matrix = new Matrix(Option(raw))
  
    def apply(dataset: Dataset): Matrix = new Matrix(
      Option(
        dataset.data.map { (inner) =>
          inner.map { case x =>
            x.toDouble
          }.toList
        }.toList
      )
    )
    def apply(s: String): Matrix = {
      val filtered = s.filter(c => c.isDigit || c == '.' || c == ' ' || c == '\n')
      val splited  = filtered.split('\n')
      val matrix = splited.map { subsr =>
        val tokens = subsr.trim.split(' ')
        tokens.map { token => token.toDouble }.toList
      }.toList
  
      Matrix(matrix)
    }
  }
  
  // new Matrix(Option(filtered.map(subsr => subsr.split(' ').map(char => char.toDouble).toList).toList))
  
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
      // 4 * 0.5 = 2
      //
      val toDrop           = 1 - percentage
      val (header, unrows) = data.splitAt(1)
      val rows             = util.Random.shuffle(unrows)
      (
        Dataset(header ++ rows.take((rows.length * toDrop).round.toInt)),
        Dataset(header ++ rows.drop((rows.length * toDrop).round.toInt))
      )
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
  
  def rootMeanSquareError(X: Matrix, Y: Matrix, parameters: Matrix): Option[Double] = 
  {
    val predictied = X * parameters;
  
    val squaredError = (predictied - Y).map(x => x * x)
  
    val meanSquare = squaredError.m.get.flatten.sum / (X.m.get(0).size * X.m.get.size)
  
    val root = math.sqrt(meanSquare)
  
    Some(root)
  }
  
  def gradientDescentStep(X: Matrix, Y: Matrix, parameters: Matrix): Matrix = 
  { 
    val pred = X * parameters;
  
    val loss = rootMeanSquareError(X, Y, parameters)
    var noOption = 2.3
    if(loss == None)
      Matrix()
    else
      noOption = loss.get
  
    val grad = for(elem <_@@)
  
  }
  
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
    Matrix(List(List(1.0, 2.0, 3.0), List(4.0, 5.0, 6.0), List(7.0, 8.0, 9.0))).transpose == Matrix(
      List(List(1.0, 4.0, 7.0), List(2.0, 5.0, 8.0), List(3.0, 6.0, 9.0))
    )
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
  
  Matrix(ds1)
  Matrix(ds2)
  Matrix(ds1) * Matrix(ds2)
  
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
  Matrix(ds1b)
  Matrix(ds2b)
  assert(Matrix(ds1b) - Matrix(ds2b) == Matrix())
  
  val matrix       = Matrix(List(List(1.0, 2.0, 3.0), List(4.0, 5.0, 6.0)))
  val mappedMatrix = matrix.map(_ * 2)
  assert(mappedMatrix == Matrix(List(List(2.0, 4.0, 6.0), List(8.0, 10.0, 12.0))))
  
  val matrix1    = Matrix(List(List(1.0, 2.0), List(3.0, 4.0)))
  val newMatrix1 = matrix1.++(0.0)
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
  
}
```



#### Error stacktrace:

```
scala.meta.internal.tokenizers.XmlParser$Xml$.UnpStart(XmlParser.scala:48)
	scala.meta.internal.tokenizers.XmlParser$Xml$.Unparsed(XmlParser.scala:47)
	scala.meta.internal.tokenizers.XmlParser$Xml$.XmlContent(XmlParser.scala:43)
	scala.meta.internal.tokenizers.XmlParser.$anonfun$XmlExpr$1(XmlParser.scala:24)
	scala.meta.shaded.internal.fastparse.internal.RepImpls$.rec$4(RepImpls.scala:226)
	scala.meta.shaded.internal.fastparse.internal.RepImpls$.rep$extension(RepImpls.scala:266)
	scala.meta.shaded.internal.fastparse.package$ByNameOps$.rep$extension(package.scala:202)
	scala.meta.internal.tokenizers.XmlParser.XmlExpr(XmlParser.scala:24)
	scala.meta.internal.tokenizers.LegacyScanner.$anonfun$getXml$2(LegacyScanner.scala:903)
	scala.meta.shaded.internal.fastparse.SharedPackageDefs.parseInputRaw(SharedPackageDefs.scala:69)
	scala.meta.shaded.internal.fastparse.SharedPackageDefs.parseInputRaw$(SharedPackageDefs.scala:45)
	scala.meta.shaded.internal.fastparse.package$.parseInputRaw(package.scala:6)
	scala.meta.shaded.internal.fastparse.Parsed$Extra.trace(Parsed.scala:139)
	scala.meta.internal.tokenizers.LegacyScanner.getXml(LegacyScanner.scala:907)
	scala.meta.internal.tokenizers.LegacyScanner.fetchLT$1(LegacyScanner.scala:298)
	scala.meta.internal.tokenizers.LegacyScanner.fetchToken(LegacyScanner.scala:306)
	scala.meta.internal.tokenizers.LegacyScanner.nextToken(LegacyScanner.scala:214)
	scala.meta.internal.tokenizers.LegacyScanner.foreach(LegacyScanner.scala:982)
	scala.meta.internal.tokenizers.ScalametaTokenizer.uncachedTokenize(ScalametaTokenizer.scala:23)
	scala.meta.internal.tokenizers.ScalametaTokenizer.$anonfun$tokenize$1(ScalametaTokenizer.scala:16)
	scala.collection.concurrent.TrieMap.getOrElseUpdate(TrieMap.scala:962)
	scala.meta.internal.tokenizers.ScalametaTokenizer.tokenize(ScalametaTokenizer.scala:16)
	scala.meta.internal.tokenizers.ScalametaTokenizer$$anon$2.apply(ScalametaTokenizer.scala:331)
	scala.meta.tokenizers.Api$XtensionTokenizeDialectInput.tokenize(Api.scala:25)
	scala.meta.tokenizers.Api$XtensionTokenizeInputLike.tokenize(Api.scala:14)
	scala.meta.internal.pc.ScriptFirstImportPosition$.tokenize(ScriptFirstImportPosition.scala:70)
	scala.meta.internal.pc.ScriptFirstImportPosition$.infer(ScriptFirstImportPosition.scala:48)
	scala.meta.internal.pc.AutoImports$.forScalaSource$1$$anonfun$1(AutoImports.scala:327)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.pc.AutoImports$.forScalaSource$1(AutoImports.scala:338)
	scala.meta.internal.pc.AutoImports$.autoImportPosition$$anonfun$1(AutoImports.scala:381)
	scala.Option.orElse(Option.scala:477)
	scala.meta.internal.pc.AutoImports$.autoImportPosition(AutoImports.scala:381)
	scala.meta.internal.pc.AutoImports$.generator(AutoImports.scala:98)
	scala.meta.internal.pc.completions.CompletionProvider.completions(CompletionProvider.scala:70)
	scala.meta.internal.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:147)
```
#### Short summary: 

java.lang.NoClassDefFoundError: sourcecode/Name