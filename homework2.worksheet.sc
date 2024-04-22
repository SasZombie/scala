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
  override def filter(pred: Token => Boolean): WTree = ???
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
  override def filterAux(pred: Token => Boolean, acc: WTree): WTree = ???
  override def size: Int                                            = ???
  override def contains(s: String): Boolean                         = ???

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

  override def contains(s: String): Boolean = ???

  override def size: Int = ???

  def filterAux(pred: Token => Boolean, acc: WTree): WTree = ???
}

def profileID: Int = 69696

val scalaDescription: String =
  "Scala is a strong statically typed general-purpose programming language which supports both object-oriented programming and functional programming designed to be concise many of Scala s design decisions are aimed to address criticisms of Java Scala source code can be compiled to Java bytecode and run on a Java virtual machine. Scala provides language interoperability with Java so that libraries written in either language may be referenced directly in Scala or Java code like Java, Scala is object-oriented, and uses a syntax termed curly-brace which is similar to the language C since Scala 3 there is also an option to use the off-side rule to structure blocks and its use is advised martin odersky has said that this turned out to be the most productive change introduced in Scala 3 unlike Java, Scala has many features of functional programming languages like Scheme, Standard ML, and Haskell, including currying, immutability, lazy evaluation, and pattern matching it also has an advanced type system supporting algebraic data types, covariance and contravariance, higher-order types (but not higher-rank types), and anonymous types other features of Scala not present in Java include operator overloading optional parameters named parameters and raw strings conversely a feature of Java not in Scala is checked exceptions which has proved controversial"
//MARK: implementations


def split(text: List[Char]): List[List[Char]] = {
  def aux(ind: Int, acc: List[Char]): List[List[Char]] = {
    if (ind >= text.length)
      if(text(ind - 1) == ' ')
        Nil
      else 
        acc :: Nil
    else {
      if (text(ind) == ' ')
      {
        val nextNonSpace = text.indexWhere(_ != ' ', ind + 1)
        
        if(nextNonSpace == -1)
          acc :: Nil 
        else
          acc :: aux(ind + 1, Nil)      
      }
      else
        aux(ind + 1, acc.appended(text(ind)))
    }
  }

  val l = aux(0, Nil)
  if (l == List(Nil)) Nil
  else l
}

/* compute the frequency of each chunk */
def computeTokens(words: List[String]): List[Token] = {
  /* insert a new string in a list of tokens */
  def insWord(s: String, acc: List[Token]): List[Token] =
    val matching = acc.exists(_.word == s)

    if(!matching)
      acc.appended(Token(s, 1))
    else
      acc.map{
        case Token(word, freq) => 
          if(word == s)
            Token(word, freq + 1)
          else Token(word, freq)
      }
  
  @tailrec
  def aux(rest: List[String], acc: List[Token]): List[Token] = {
    rest match
      case Nil => acc
      case head :: next =>
        aux(next, insWord(head, acc))
  }
  
  aux(words, Nil)
  // val test: List[String] = "AAA" :: "BBB" :: "AAA" :: Nil

  // insWord(test(0), insWord(test(1), insWord(test(2), Nil)))

}

def tokensToTree(tokens: List[Token]): WTree = ???

/* Using the previous function, which builds a tree from a list of tokens,
 *  write a function which takes a string,
 *  splits it into chunks, computes frequencies and constructs a tree.
 *  Use the function _.toList to construct a list of characters from a String.
 *
 *  A much cleaner implementation can be achieved by "sequencing" functions using
 *  andThen.
 * */

def makeTree(s: String): WTree = ???

/* build a tree with the words and frequencies from the text in the scalaDescription text */
def wordSet: WTree = ???

/* find the number of occurrences of the keyword "Scala" in the scalaDescription text */
def scalaFreq: Int = ???

/* find how many programming languages are referenced in the text.
     A PL is a keyword which starts with an uppercase
     You can reference a character from a string using (0) and you can
     also use the function isUpper

 */
def progLang: Int = ???



/* find how many words which are not prepositions or conjunctions appear in the text (any word whose size is larger than 3). */

def wordCount: Int = ???

val s = 'T' :: 'h' :: 'i' :: 's' :: ' ' :: 'i' :: 's' :: ' ' :: 'a' :: ' ' :: 't' :: 'e' :: 's' :: 't' :: Nil
split(s)
assert(split(s) == List(List('T', 'h', 'i', 's'), List('i', 's'), List('a'), List('t', 'e', 's', 't')))

val s1 = List('T','e','s','t',' ')
split(s1)
assert(split(s1) == List(List('T','e','s','t')))

val s2 = List(' ', ' ', ' ')
assert(split(s2) == Nil)

val l = List("a","b","c")
val r = Set(new Token("a",1), new Token("b",1), new Token("c",1))
computeTokens(l)
assert(computeTokens(l).toSet == r)

val l3 = List("ba", "ma", "ta", "ma", "ba", "ma")
val r3 = Set(new Token("ma",3), new Token("ta",1), new Token("ba",2))
assert(computeTokens(l3).toSet == r3)

// val l2= List("ma", "ba", "ta", "ma", "ba", "ma")
// val t2 = Node(Token("ba",2), Node(Token("ta",1), Empty, Empty), Node(Token("ma",3),Empty,Empty))
// assert(makeTree("ba ma ta ma ba ma") == t2)
// assert(makeTree("  ") == Empty)

// assert(makeTree(" ").size == 0)

// val l4 = List("ma", "ba", "ta", "ma", "ba", "ma")
// assert(makeTree("ba ma ta ma ba ma").size == 3)

// assert(!makeTree(" ").contains("text"))

// assert(makeTree("ba ma ta ma ba ma").contains("ta"))
// assert(makeTree("ba ma ta ma ba ma").contains("ma"))
// assert(makeTree("ba ma ta ma ba ma").contains("ba"))

// assert(makeTree("ba ma ta ma ba ma").filter(_.word == "ma").contains("ma"))

// assert(!makeTree("ba ma ta ma ba ma").filter(_.word != "ma").contains("ma"))

// assert(!makeTree("ba ma ta ma ba ma").filter(_ => false).contains("ma"))
// assert(scalaFreq == 11)
// assert(progLang == 8)
// assert(wordCount == 139)
