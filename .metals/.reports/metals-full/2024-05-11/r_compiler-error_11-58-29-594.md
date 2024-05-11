file://<WORKSPACE>/homework3.worksheet.sc
### java.lang.NoClassDefFoundError: sourcecode/Name

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-runtime_3/2.5.2/mdoc-runtime_3-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-interfaces/2.5.2/mdoc-interfaces-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_3/0.12.0/metaconfig-pprint_3-0.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 479
uri: file://<WORKSPACE>/homework3.worksheet.sc
text:
```scala
object worksheet{
  class Board(val board: List[List[Player]], val player: Player) {
  
    type Line = List[Player]
  
    def isFree(x: Int, y: Int): Boolean = board(x)(y) == Empty
    def getColumns: Board = this
    def getFstDiag: Line = 
    {
      val inter = for(x <- board.indices) yield board(x)(x)
      inter.toList
    }
    def getSndDiag: Line = 
    {
      val size = board.size
  
      for{
        (row, index) <- board.zipWithIndex
        (elem, colInd) <_@@
      }
    }
  
    def getAboveFstDiag: List[Line] = ???
    def getBelowFstDiag: List[Line] = ???
    def getAboveSndDiag: List[Line] = ???
    def getBelowSndDiag: List[Line] = ???
  
    def winner: Boolean = ???
    def update(ln: Int, col: Int): Board = ???
    def next: List[Board] = ???
  
    def sequences: Map[Int,Int] = ???
  
    override def toString: String = 
    {
      def fromPos(c: Player): Char =
        c match {
          case One => 'X'
          case Two => '0'
          case Empty => '.'
        }
      board.foldRight("")((sublist, acc) => sublist.foldRight("")((elem: Player, acc: String) => fromPos(elem).toString() + acc) + "\n" + acc).init
    }
  }
  
  object Board {
  
    def apply(s: String, p: Player): Board = new Board(apply(s).board, p)
  
    def apply(s: String): Board = {
      def toPos(c: Char): Player =
        c match {
          case 'X' => One
          case '0' => Two
          case _ => Empty
        }
      
      val splited = s.split('\n');
      new Board(splited.map(subsr => subsr.map(char => toPos(char)).toList).toList, Empty)
    } 
  }
   
  trait Player {
    def complement: Player
  }
  
  case object One extends Player {
    override def complement: Player = Two
  }
  case object Two extends Player {
    override def complement: Player = One
  }
  case object Empty extends Player {
    override def complement: Player = Empty
  }
  
  val test = """
  AAA 
AAA
  
  """
  
  val small =
  """0.X
0X.
X..""".stripMargin.replace("\r\n","\n")
  
  val medium1 =
  """00000
0000X
000..
00.0.
0X..0""".stripMargin.replace("\r\n","\n")
  
  val aboveFstDiag1 =
  """00..
00.
0X
0""".stripMargin.replace("\r\n","\n")
  
  val aboveSndDiag1 =
  """0000
000
00
0""".stripMargin.replace("\r\n","\n")
  
  val belowSndDiag1 =
  """X..X
.0.
..
0""".stripMargin.replace("\r\n","\n")
  
  val medium2 =
  """0X0X0.
000.X0
0.0X..
0..0..
0X..0X
...X..""".stripMargin.replace("\r\n","\n")
  
  val aboveFstDiag2 =
  """X0X.X
0...
XX.
00
.""".stripMargin.replace("\r\n","\n")
  
  val belowFstDiag2 =
  """0....
0..X
0X.
0.
.""".stripMargin.replace("\r\n","\n")
  
  val aboveSndDiag2 =
  """0.0.0
X0.0
000
X0
0""".stripMargin.replace("\r\n","\n")
  
  val belowSndDiag2 =
  """0.0..
....
.0X
X.
.""".stripMargin.replace("\r\n","\n")
  az
  assert(Board(small).isFree(2,2))
  assert(!Board(small).isFree(0,0))
  
  
  assert(One.complement == Two)
  assert(Two.complement == One)
  assert(Empty.complement == Empty)
  
  
  Board(small).toString
  assert(small == Board(small).toString)
  
  assert(medium1 == Board(medium1).toString)
  
  Board(medium1).toString == Board(medium1).getColumns.toString
  
  val mewo = Board(medium1).getColumns
  val mewo2 = Board(medium1)
  
  mewo.board == mewo2.board
  mewo.player == mewo2.player
  mewo == mewo2 
  
  
  // assert(Board(medium1).getColumns == Board(medium1))
  //assert(getColumns(makeBoard(medium1)) == makeBoard(medium1))
  
  assert(Board(medium1).getFstDiag == List(Two,Two,Two,Two,Two))
  
  assert(Board(medium1).getSndDiag == List(Two,Two,Two,Two,Two))
  
  assert(Board(medium1).getAboveFstDiag == Board(aboveFstDiag1))
  
  
  assert(Board(medium2).getAboveFstDiag == Board(aboveFstDiag2))
  
  
  assert(Board(medium1).getBelowFstDiag == Board(aboveFstDiag1))
  
  assert(Board(medium2).getBelowFstDiag == Board(aboveFstDiag2))
  
  
  assert(Board(medium1).getAboveSndDiag == Board(aboveSndDiag1))
  
  
  assert(Board(medium2).getAboveSndDiag == Board(aboveSndDiag1))
  
  
  assert(Board(medium1).getBelowSndDiag == Board(belowSndDiag1))
  
  
  
  assert(Board(medium2).getBelowSndDiag == Board(belowSndDiag2))
  
  
  assert(Board(medium1,Two).winner )
  assert(!(Board(medium1, One).winner))
  
  
  assert(Board(medium2, Two).winner)
  assert(!(Board(medium2, One).winner))
  
  
  val t2 =
  """X0X0X0
0X0X0X
X0X0X0
.XX0..
X00...
X0X0X0""".stripMargin
    assert(Board(t2,One).winner)
  
  val smallUpd1 =
  """0XX
0X.
X..""".stripMargin.replace("\r\n","\n")
  
  assert(Board(small,One).update(0,1).board == Board(smallUpd1).board)
  //assert(show(update(One)(0,1,makeBoard(small))) == smallUpd1)
  
  val smallUpd2 =
  """0.X
0X.
X.0""".stripMargin.replace("\r\n","\n")
  
  assert(Board(small,Two).update(2,2).board == Board(smallUpd2).board)
  //assert(show(update(Two)(2,2,makeBoard(small))) == smallUpd2)
  
  
  
  
  val full =
  """0XX
0XX
XX0""".stripMargin
  
  assert(Board(full,Two).next == Nil)
  assert(Board(full,One).next == Nil)
  
  //assert(next(Two)(makeBoard(full)) == Nil)
  //assert(next(One)(makeBoard(full)) == Nil)
  
  val nextTest =
  """0..
0.X
.X.""".stripMargin
  
  val nextTestR1 = Set("00.\n0.X\n.X.","0.0\n0.X\n.X.", "0..\n00X\n.X.", "0..\n0.X\n0X.", "0..\n0.X\n.X0")
  val nextTestR2 = Set("0X.\n0.X\n.X.","0.X\n0.X\n.X.", "0..\n0XX\n.X.", "0..\n0.X\nXX.", "0..\n0.X\n.XX")
  
  
  
  assert(Board(nextTest,Two).next.map(_.toString).toSet == nextTestR1)
  assert(Board(nextTest,One).next.map(_.toString).toSet == nextTestR2)
  
  val t1 =
  """......
......
......
..X...
......
......""".stripMargin
  val t2p =
  """......
......
......
.XXX..
......
......""".stripMargin
  val t3p =
  """......
......
......
0XXX..
......
......""".stripMargin
  
  val t4p =
  """......
......
.000..
.XXX..
......
......""".stripMargin
  
  val t5p =
  """......
......
.000..
.XXXX.
......
......""".stripMargin
  
  assert(Board(t1, One).sequences == Map(2 -> 0, 3 -> 0, 4 -> 0, 5 -> 0))
  assert(Board(t2p, One).sequences(3) == 2)
  assert(Board(t3p, One).sequences(3) == 1)
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