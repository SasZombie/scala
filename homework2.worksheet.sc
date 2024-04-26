import scala.annotation.tailrec
case class Token(word: String, freq: Int) {
  override def toString: String = s"($word,$freq)"
}

trait WTreeInterface {

  def height: Int
  def balance: Int
  def rotateLeft: WTree
  def rotateRight: WTree
  def rotateRightLeft: WTree
  def rotateLeftRight: WTree
  def rebalance: WTree

  def isEmpty: Boolean
  def filter(pred: Token => Boolean): WTree
  def ins(w: Token): WTree
  def contains(s: String): Boolean
  def size: Int
}

abstract class WTree extends WTreeInterface {
  override def filter(pred: Token => Boolean): WTree = {
    filterAux(pred, Empty)
  }
  def filterAux(pred: Token => Boolean, acc: WTree): WTree

}

case object Empty extends WTree {
  override def balance: Int           = 0
  override def height: Int            = 0
  override def rotateLeft: WTree      = this
  override def rotateRight: WTree     = this
  override def rotateRightLeft: WTree = this
  override def rotateLeftRight: WTree = this
  override def rebalance: WTree       = this

  override def isEmpty                                              = true
  override def ins(w: Token): WTree                                 = Node(w, Empty, Empty)
  override def filterAux(pred: Token => Boolean, acc: WTree): WTree = acc
  override def size: Int                                            = 0
  override def contains(s: String): Boolean                         = false

}

case class Node(word: Token, left: WTree, right: WTree) extends WTree {

  override def balance: Int = right.height - left.height
  override def height: Int  = 1 + (left.height max right.height)
  override def rotateLeft: WTree =
    right match {
      // the tree is unbalanced, hence the right sub-tree is nonempty
      case Node(w, l, r) => Node(w, Node(word, left, l), r)
    }
  override def rotateRight: WTree =
    left match {
      case Node(w, l, r) => Node(w, l, Node(word, r, right))
    }
  override def rotateRightLeft: WTree =
    Node(word, left, right.rotateRight).rotateLeft
  override def rotateLeftRight: WTree =
    Node(word, left.rotateLeft, right).rotateRight
  override def rebalance: WTree = {
    if (balance < -1 && left.balance == -1) this.rotateRight
    else if (balance > 1 && right.balance == 1) this.rotateLeft
    else if (balance < -1 && left.balance == 1) this.rotateLeftRight
    else if (balance > 1 && right.balance == -1) this.rotateRightLeft
    else this
  }

  override def isEmpty = false

  override def ins(w: Token): WTree =
    if (w.freq > word.freq) Node(word, left, right.ins(w))
    else Node(word, left.ins(w), right)

  override def contains(s: String): Boolean =
    if (word.word == s) true else (left.contains(s) || right.contains(s))

  override def size: Int = 1 + this.left.size + this.right.size

  private def merge(left: WTree, right: WTree): WTree = left match {
    case Empty         => right
    case Node(w, l, r) => Node(w, merge(l, right), r)
  }
  def filterAux(pred: Token => Boolean, acc: WTree): WTree = {
    // val leftFiltered = left.filterAux(pred, acc)
    // val rightFiltered = right.filterAux(pred, acc)
    // if(pred(word)) {
    //   acc.ins(word)
    // }
    // else{
    //   leftFiltered.filterAux(pred, rightFiltered.filterAux(pred, acc))
    // }

    // Need explanetion why we dont do this... Way better and has a good size

    val leftFiltered  = left.filterAux(pred, acc)
    val rightFiltered = right.filterAux(pred, acc)
    if (pred(word)) Node(word, leftFiltered, rightFiltered)
    else { // Super Hard
      leftFiltered match {
        case Empty                   => rightFiltered
        case Node(word, left, right) => Node(word, merge(left, rightFiltered), right)
      }
    }
  }
}

def profileID: Int = 69696

val scalaDescription: String =
  "Scala is a strong statically typed general-purpose programming language which supports both object-oriented programming and functional programming designed to be concise many of Scala s design decisions are aimed to address criticisms of Java Scala source code can be compiled to Java bytecode and run on a Java virtual machine. Scala provides language interoperability with Java so that libraries written in either language may be referenced directly in Scala or Java code like Java, Scala is object-oriented, and uses a syntax termed curly-brace which is similar to the language C since Scala 3 there is also an option to use the off-side rule to structure blocks and its use is advised martin odersky has said that this turned out to be the most productive change introduced in Scala 3 unlike Java, Scala has many features of functional programming languages like Scheme, Standard ML, and Haskell, including currying, immutability, lazy evaluation, and pattern matching it also has an advanced type system supporting algebraic data types, covariance and contravariance, higher-order types (but not higher-rank types), and anonymous types other features of Scala not present in Java include operator overloading optional parameters named parameters and raw strings conversely a feature of Java not in Scala is checked exceptions which has proved controversial"
//MARK: implementations

def split(text: List[Char]): List[List[Char]] = {
  def aux(ind: Int, acc: List[Char]): List[List[Char]] = {
    if (ind >= text.length)
      if (text(ind - 1) == ' ')
        Nil
      else
        acc :: Nil
    else {
      if (text(ind) == ' ') {
        val nextNonSpace = text.indexWhere(_ != ' ', ind + 1)

        if (nextNonSpace == -1)
          acc :: Nil
        else
          acc :: aux(ind + 1, Nil)
      } else
        aux(ind + 1, acc.appended(text(ind)))
    }
  }

  val l = aux(0, Nil)
  if (l == List(Nil)) Nil
  else l
}

def computeTokens(words: List[String]): List[Token] = {
  def insWord(s: String, acc: List[Token]): List[Token] = {
    val matching = acc.exists(_.word == s)

    if (!matching)
      acc.appended(Token(s, 1))
    else
      acc.map { case Token(word, freq) =>
        if (word == s)
          Token(word, freq + 1)
        else Token(word, freq)
      }
  }
  @tailrec
  def aux(rest: List[String], acc: List[Token]): List[Token] = {
    rest match {
      case Nil => acc
      case head :: next =>
        aux(next, insWord(head, acc))
    }
  }

  aux(words, Nil)
}

def tokensToTree(tokens: List[Token]): WTree = {
  tokens.foldLeft(Empty: WTree)((t, n) => t.ins(n))
}

def makeTree(s: String): WTree = {
  val splited           = split(s.toList)
  val toComputeTokens   = splited.map((chars: List[Char]) => chars.mkString)
  val finishedComputing = computeTokens(toComputeTokens)
  tokensToTree(finishedComputing)
}

def wordSet: WTree =
  makeTree(scalaDescription)

def scalaFreq: Int = {
  val tree = makeTree(scalaDescription)
  val only = tree.filter((token) => token.word == "Scala")

  only match { // How is this possible?
    case Node(token, left, right) => token.freq
    case Empty                    => 0
  }
}
//MARK: Seccond Part

def progLang: Int = {
  val tree = makeTree(scalaDescription)
  val only = tree.filter((token) => token.word.charAt(0).isUpper)
  only.size
}

def wordCount: Int = {
  val words         = split(scalaDescription.toList)
  val filteredWords = words.filter(_.length > 3)
  filteredWords.size
}

val s = 'T' :: 'h' :: 'i' :: 's' :: ' ' :: 'i' :: 's' :: ' ' :: 'a' :: ' ' :: 't' :: 'e' :: 's' :: 't' :: Nil
split(s)
assert(split(s) == List(List('T', 'h', 'i', 's'), List('i', 's'), List('a'), List('t', 'e', 's', 't')))

val s1 = List('T', 'e', 's', 't', ' ')
split(s1)
assert(split(s1) == List(List('T', 'e', 's', 't')))

val s2 = List(' ', ' ', ' ')
assert(split(s2) == Nil)

val l = List("a", "b", "c")
val r = Set(new Token("a", 1), new Token("b", 1), new Token("c", 1))
computeTokens(l)
assert(computeTokens(l).toSet == r)

val l3 = List("ba", "ma", "ta", "ma", "ba", "ma")
val r3 = Set(new Token("ma", 3), new Token("ta", 1), new Token("ba", 2))
assert(computeTokens(l3).toSet == r3)

val l2 = List("ma", "ba", "ta", "ma", "ba", "ma")
val t2 = Node(Token("ba", 2), Node(Token("ta", 1), Empty, Empty), Node(Token("ma", 3), Empty, Empty))

makeTree("ba ma ta ma ba ma")
assert(makeTree("ba ma ta ma ba ma") == t2)
assert(makeTree("  ") == Empty)
assert(makeTree(" ").size == 0)

val l4 = List("ma", "ba", "ta", "ma", "ba", "ma")
assert(makeTree("ba ma ta ma ba ma").size == 3)

assert(!makeTree(" ").contains("text"))

assert(makeTree("ba ma ta ma ba ma").contains("ta"))
assert(makeTree("ba ma ta ma ba ma").contains("ma"))
assert(makeTree("ba ma ta ma ba ma").contains("ba"))

assert(makeTree("ba ma ta ma ba ma").filter(_.word == "ma").contains("ma"))

assert(!makeTree("ba ma ta ma ba ma").filter(_.word != "ma").contains("ma"))

assert(!makeTree("ba ma ta ma ba ma").filter(_ => false).contains("ma"))

scalaFreq
assert(scalaFreq == 11)
progLang
assert(progLang == 8)
wordCount
assert(wordCount == 139)
