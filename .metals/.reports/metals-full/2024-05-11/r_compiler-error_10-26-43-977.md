file://<WORKSPACE>/homework3.worksheet.sc
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-runtime_3/2.5.2/mdoc-runtime_3-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-interfaces/2.5.2/mdoc-interfaces-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_3/0.12.0/metaconfig-pprint_3-0.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 4667
uri: file://<WORKSPACE>/homework3.worksheet.sc
text:
```scala
object worksheet{
  class Board(val board: List[List[Player]], val player: Player) {
  
    type Line = List[Player]
  
    def isFree(x: Int, y: Int): Boolean = ???
    def getColumns: Board = ???
    def getFstDiag: Line = ???
    def getSndDiag: Line = ???
  
    def getAboveFstDiag: List[Line] = ???
    def getBelowFstDiag: List[Line] = ???
    def getAboveSndDiag: List[Line] = ???
    def getBelowSndDiag: List[Line] = ???
  
    def winner: Boolean = ???
    def update(ln: Int, col: Int): Board = ???
    def next: List[Board] = ???
  
    def sequences: Map[Int,Int] = ???
  
    override def toString: String = ???
  }
  
  object Board {
  
    def apply(s: String, p: Player): Board = ???
  
    def apply(s: String): Board = {
      def toPos(c: Char): Player =
        c match {
          case 'X' => One
          case '0' => Two
          case _ => Empty
        }
      ???
    }
  }
   
  trait Player {
    def complement: Player
  }
  
  case object One extends Player {
    override def complement: Player = ???
  }
  case object Two extends Player {
    override def complement: Player = ???
  }
  case object Empty extends Player {
    override def complement: Player = ???
  }
  
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
  
  
  assert(Board(small).isFree(2,2))
  assert(!Board(small).isFree(0,0))
  
  
  assert(One.complement == Two)
  assert(Two.complement == One)
  assert(Empty.complement == Empty)
  
  
  
  assert(small == Board(small).toString)
  
  assert(medium1 == Board(medium1).toString)
  
  assert(Board(medium1).getColumns == Board(medium1))
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
  val t3[@@] =
  """......
......
......
0XXX..
......
......""".stripMargin
  
  val t4 =
  """......
......
.000..
.XXX..
......
......""".stripMargin
  
  val t5 =
  """......
......
.000..
.XXXX.
......
......""".stripMargin
  
  assert(Board(t1, One).sequences == Map(2 -> 0, 3 -> 0, 4 -> 0, 5 -> 0))
  assert(Board(t2, One).sequences(3) == 2)
  assert(Board(t3, One).sequences(3) == 1)
}
```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2607)
	scala.meta.internal.pc.SignatureHelpProvider$.isValid(SignatureHelpProvider.scala:83)
	scala.meta.internal.pc.SignatureHelpProvider$.notCurrentApply(SignatureHelpProvider.scala:96)
	scala.meta.internal.pc.SignatureHelpProvider$.$anonfun$1(SignatureHelpProvider.scala:48)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile(LinearSeq.scala:280)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile$(LinearSeq.scala:278)
	scala.collection.immutable.List.dropWhile(List.scala:79)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:48)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:414)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner