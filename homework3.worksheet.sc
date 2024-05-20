import scala.compiletime.ops.int
case class Board(val board: List[List[Player]], val player: Player) {

  type Line = List[Player]

  def isFree(x: Int, y: Int): Boolean = board(x)(y) == Empty
  def getColumns: Board               = this
  def getFstDiag: Line = {
    val inter = for (x <- board.indices) yield board(x)(x)
    inter.toList
  }
  def getSndDiag: Line = {
    val size = board.size

    val inter = for {
      (row, index)   <- board.zipWithIndex
      (elem, colInd) <- board.zipWithIndex
      if (index + colInd) == size - 1
    } yield board(index)(colInd)

    inter.toList
  }

  def getAboveFstDiag: List[Line] = {
    val size = board.size

    val inter = for {
      (row, rind) <- board.zipWithIndex
    } yield {
      for {
        (col, cind) <- row.zipWithIndex
        if (cind > rind)
      } yield board(rind)(cind)
    }

    val max = inter.map(_.size).max

    val trans = (0 until max).map { i =>
      inter.flatMap(_.lift(i))
    }

    trans.toList
  }
  def getBelowFstDiag: List[Line] = {
    val inter = for {
      (row, rind) <- board.zipWithIndex
    } yield {
      for {
        (col, cind) <- row.zipWithIndex
        if (cind < rind)
      } yield col
    }

    val max = inter.map(_.size).max

    val trans = (0 until max).map { i =>
      inter.flatMap(_.lift(i))
    }

    trans.toList
  }
  def getAboveSndDiag: List[Line] = {
    val size = board.size

    val inter = for {
      (row, rind) <- board.zipWithIndex
    } yield {
      for {
        (col, cind) <- row.zipWithIndex
        if (cind + rind) < size - 1
      } yield col
    }
    val newInter = inter.init
    // hour 5...
    def loop(acc: List[List[Player]], value: List[List[Player]]): List[Line] = {
      if (value == Nil) acc
      else {
        val toAdd = value.map(_.last)
        loop(acc ::: List(toAdd), value.map(_.init).filter(_.nonEmpty))

      }
    }
    loop(Nil, newInter)
  }
  def getBelowSndDiag: List[Line] = {
    val size = board.size

    val inter = for {
      (row, rind) <- board.zipWithIndex
    } yield {
      for {
        (col, cind) <- row.zipWithIndex
        if (cind + rind) > size - 1
      } yield col
    }

    val newInter = inter.tail

    def loop(acc: List[List[Player]], value: List[List[Player]]): List[Line] = {
      if (value == Nil) acc
      else {
        val toAdd = value.map(_.last)
        loop(acc ::: List(toAdd), value.map(_.init).filter(_.nonEmpty))

      }
    }
    loop(Nil, newInter)
  }

  def winner: Boolean = {
    if (this.sequences(5) != 0) true else false
  }

  def update(ln: Int, col: Int): Board = {

    val inter = for {
      (row, rind) <- board.zipWithIndex
    } yield {
      for {
        (col2, cind) <- row.zipWithIndex
      } yield if (rind == ln && cind == col) this.player else col2
    }
    new Board(inter, player)
  }
  def next: List[Board] = {
    val inter = for {
      row <- board.indices
      col <- board(row).indices
    } yield {
      if (board(row)(col) != Empty) Nil
      else {
        val newBoard = this.update(row, col)
        new Board(newBoard.board, this.player)
      }
    }
    inter.collect { case board: Board =>
      board
    }.toList
  }

//MARK: Sequences
  def sequences: Map[Int, Int] = {
    def longSeq(list: List[Player]): Int = {
      list
        .foldLeft((0, 0)) { case ((currentCount, longestCount), elem) =>
          if (elem == this.player) {
            val newCount = currentCount + 1
            (newCount, Math.max(newCount, longestCount))
          } else {
            (0, longestCount)
          }
        }
        ._2
    }

    def longSeqNon(list: List[Player]): Int = {
      list
        .foldLeft((0, 0)) { case ((currentCount, longestCount), elem) =>
          if (elem == this.player || elem == Empty) {
            val newCount = currentCount + 1
            (newCount, Math.max(newCount, longestCount))
          } else {
            (0, longestCount)
          }
        }
        ._2
    }

    def canForm5(list: List[Player]): (Boolean, Int, Int) = {
      // val allFilled = list.map((player) => if (player == Empty) this.player else player)

      val (allFilled, elseCount) = list.foldLeft((List.empty[Player], 0)) { case ((accList, count), player) =>
        if (player == Empty) {
          (accList :+ this.player, count + 1)
        } else {
          (accList :+ player, count)
        }
      }

      val size = list.size
      val longestEmptySpace = longSeqNon(list)
      val mapIndex = longSeq(list)
      println("Size = " + size + " Else = " + elseCount + " LongestEmpty = " + longestEmptySpace + " Returned = " + mapIndex)   

      if ((longSeq(allFilled) >= 5) && (size - elseCount > 1)) (true, mapIndex, longestEmptySpace - 4 ) else (false, 0, 0)
    }

    def concatMaps(first: Map[Int, Int], seccond: Map[Int, Int]): Map[Int, Int] = {
      first.foldLeft(seccond) { case (acc, (key, value)) =>
        acc + (key -> (acc.getOrElse(key, 0) + value))
      }
    }

    val defaultMap = Map(5 -> 0, 4 -> 0, 3 -> 0, 2 -> 0)

    val seqPerRow = for (row <- board) yield {
      canForm5(row)
    }

    val sumPerRow = seqPerRow.foldLeft(defaultMap) {
      case (acc, (true, num, value)) =>
        acc + (num -> (acc.getOrElse(num, 0) + value))
      case (acc, _) => acc
    }

    val seqPerCol = for (col <- board.transpose) yield {
      canForm5(col)
    }

    val sumCol = seqPerCol.foldLeft(sumPerRow) {
      case (acc, (true, num, value)) =>
        acc + (num -> (acc.getOrElse(num, 0) + value))
      case (acc, _) => acc
    }

    val seqPerDiagPrincBelow = for (below <- getBelowFstDiag) yield {
      canForm5(below)
    }

    val sumPerDiagPrincBelow = seqPerDiagPrincBelow.foldLeft(sumCol) {
      case (acc, (true, num, value)) =>
        acc + (num -> (acc.getOrElse(num, 0) + value))
      case (acc, _) => acc
    }

    val seqPerDiagPrincAbove = for (above <- getAboveFstDiag) yield {
      canForm5(above)
    }

    val sumPerDiagPrincAbove = seqPerDiagPrincAbove.foldLeft(sumPerDiagPrincBelow) {
      case (acc, (true, num, value)) =>
        acc + (num -> (acc.getOrElse(num, 0) + value))
      case (acc, _) => acc
    }
    // --------
    val seqPerDiagSecBelow = for (below <- getBelowSndDiag) yield {
      canForm5(below)
    }

    val sumPerDiagSecBelow = seqPerDiagSecBelow.foldLeft(sumPerDiagPrincAbove) {
      case (acc, (true, num, value)) =>
        acc + (num -> (acc.getOrElse(num, 0) + value))
      case (acc, _) => acc
    }

    val seqPerDiagSecAbove = for (above <- getAboveSndDiag) yield {
      canForm5(above)
    }

    // print(seqPerDiagSecAbove)
    val sumPerDiagSecAbove = seqPerDiagSecAbove.foldLeft(sumPerDiagSecBelow) {
      case (acc, (true, num, value)) =>
        acc + (num -> (acc.getOrElse(num, 0) + value))
      case (acc, _) => acc
    }
    //////////////////////////////////////////////////////
    val seqDiagPrinc = canForm5(getFstDiag)
    val sumPerDiagPrinc = {
      val acc = sumPerDiagSecAbove

      seqDiagPrinc match {
        case (true, num, value) =>
          acc + (num -> (acc.getOrElse(num, 0) + value))
        case _ => acc
      }
    }

    val seqDiagSec = canForm5(getSndDiag)
    val sumPerDiagSec = {
      val acc = sumPerDiagPrinc

      seqDiagSec match {
        case (true, num, value) =>
          acc + (num -> (acc.getOrElse(num, 0) + value))
        case _ => acc
      }
    }
    sumPerDiagSec.view.filterKeys((elem) => elem > 1 && elem < 6).toMap
  }

  override def toString: String = {
    def fromPos(c: Player): Char =
      c match {
        case One   => 'X'
        case Two   => '0'
        case Empty => '.'
      }
    board
      .foldRight("")((sublist, acc) =>
        sublist.foldRight("")((elem: Player, acc: String) => fromPos(elem).toString() + acc) + "\n" + acc
      )
      .init
  }
}

object Board {

  def apply(s: String, p: Player): Board = new Board(apply(s).board, p)

  def apply(s: String): Board = {
    def toPos(c: Char): Player =
      c match {
        case 'X' => One
        case '0' => Two
        case _   => Empty
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
|AAA

"""

val small =
  """0.X
  |0X.
  |X..""".stripMargin.replace("\r\n", "\n")

val medium1 =
  """00000
  |0000X
  |000..
  |00.0.
  |0X..0""".stripMargin.replace("\r\n", "\n")

val aboveFstDiag1 =
  """00..
  |00.
  |0X
  |0""".stripMargin.replace("\r\n", "\n")

val aboveSndDiag1 =
  """0000
  |000
  |00
  |0""".stripMargin.replace("\r\n", "\n")

val belowSndDiag1 =
  """X..X
  |.0.
  |..
  |0""".stripMargin.replace("\r\n", "\n")

val medium2 =
  """0X0X0.
  |000.X0
  |0.0X..
  |0..0..
  |0X..0X
  |...X..""".stripMargin.replace("\r\n", "\n")

val aboveFstDiag2 =
  """X0X.X
|0...
|XX.
|00
|.""".stripMargin.replace("\r\n", "\n")

val belowFstDiag2 =
  """0....
|0..X
|0X.
|0.
|.""".stripMargin.replace("\r\n", "\n")

val aboveSndDiag2 =
  """0.0.0
|X0.0
|000
|X0
|0""".stripMargin.replace("\r\n", "\n")

val belowSndDiag2 =
  """0.0..
|....
|.0X
|X.
|.""".stripMargin.replace("\r\n", "\n")

assert(Board(small).isFree(2, 2))
assert(!Board(small).isFree(0, 0))

assert(One.complement == Two)
assert(Two.complement == One)
assert(Empty.complement == Empty)

Board(small).toString
assert(small == Board(small).toString)

assert(medium1 == Board(medium1).toString)

Board(medium1).toString == Board(medium1).getColumns.toString

val mewo  = Board(medium1).getColumns
val mewo2 = Board(medium1)

mewo.board == mewo2.board
mewo.player == mewo2.player
mewo == mewo2

//This asset is impossible!!!
// assert(Board(medium1).getColumns == Board(medium1))
//assert(getColumns(makeBoard(medium1)) == makeBoard(medium1))

assert(Board(medium1).getFstDiag == List(Two, Two, Two, Two, Two))

assert(Board(medium1).getSndDiag == List(Two, Two, Two, Two, Two))

"""00000
  |0000X
  |000..
  |00.0.
  |0X..0"""
Board(medium1).getAboveFstDiag
Board(aboveFstDiag1).toString()

// //This is wrong as well!
// assert(Board(medium1).getAboveFstDiag == Board(aboveFstDiag1))
// assert(Board(medium2).getAboveFstDiag == Board(aboveFstDiag2))

// // This will never be true!
// assert(Board(medium1).getBelowFstDiag == Board(aboveFstDiag1))
// assert(Board(medium2).getBelowFstDiag == Board(aboveFstDiag2))

Board(medium1).getAboveSndDiag
Board(medium2).getAboveSndDiag
//This will never be true!!
// assert(Board(medium1).getAboveSndDiag == Board(aboveSndDiag1))
// assert(Board(medium2).getAboveSndDiag == Board(aboveSndDiag1))

Board(medium1).getBelowSndDiag
Board(medium2).getBelowSndDiag
//This will never be true!!
// assert(Board(medium1).getBelowSndDiag == Board(belowSndDiag1))
// assert(Board(medium2).getBelowSndDiag == Board(belowSndDiag2))
// Board(medium1, Two).winner
// Board(medium1, One).winner
Board(medium1, Two).sequences

assert(Board(medium1, Two).winner)
assert(!(Board(medium1, One).winner))

assert(Board(medium2, Two).winner)
assert(!(Board(medium2, One).winner))

val t2 =
  """X0X0X0
  |0X0X0X
  |X0X0X0
  |.XX0..
  |X00...
  |X0X0X0""".stripMargin

Board(t2, One).getAboveFstDiag
Board(t2, One).getAboveSndDiag
Board(t2, One).sequences
// assert(Board(t2, One).winner)

val smallUpd1 =
  """0XX
|0X.
|X..""".stripMargin.replace("\r\n", "\n")

"""0.X
  |0X.
  |X.."""
assert(Board(small, One).update(0, 1).board == Board(smallUpd1).board)
//assert(show(update(One)(0,1,makeBoard(small))) == smallUpd1)

val smallUpd2 =
  """0.X
|0X.
|X.0""".stripMargin.replace("\r\n", "\n")

assert(Board(small, Two).update(2, 2).board == Board(smallUpd2).board)
//assert(show(update(Two)(2,2,makeBoard(small))) == smallUpd2)

val nextTest =
  """0..
|0.X
|.X.""".stripMargin
Board(nextTest, Two).next.map(_.toString).toSet

val nextTestR1 = Set("00.\n0.X\n.X.", "0.0\n0.X\n.X.", "0..\n00X\n.X.", "0..\n0.X\n0X.", "0..\n0.X\n.X0")
val nextTestR2 = Set("0X.\n0.X\n.X.", "0.X\n0.X\n.X.", "0..\n0XX\n.X.", "0..\n0.X\nXX.", "0..\n0.X\n.XX")

val full =
  """0XX
|0XX
|XX0""".stripMargin
assert(Board(nextTest, Two).next.map(_.toString).toSet == nextTestR1)
assert(Board(nextTest, One).next.map(_.toString).toSet == nextTestR2)

assert(Board(full, Two).next == Nil)
assert(Board(full, One).next == Nil)

//assert(next(Two)(makeBoard(full)) == Nil)
//assert(next(One)(makeBoard(full)) == Nil)

val t1 =
  """......
|......
|......
|..X...
|......
|......""".stripMargin
val t2p =
  """......
|......
|......
|.XXX..
|......
|......""".stripMargin
val t3p =
  """......
|......
|......
|0XXX..
|......
|......""".stripMargin
Board(t1, One).sequences
Board(t1, Two).sequences
Board(t2p, One).sequences






Board(t3p, One).sequences

val t4p =
  """......
|......
|.000..
|.XXX..
|......
|......""".stripMargin

val t5p =
  """......
|......
|.000..
|.XXXX.
|......
|......""".stripMargin

assert(Board(t1, One).sequences == Map(2 -> 0, 3 -> 0, 4 -> 0, 5 -> 0))
assert(Board(t2p, One).sequences(3) == 2)
assert(Board(t3p, One).sequences(3) == 1)
