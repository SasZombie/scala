import scala.annotation.tailrec
import javax.swing.filechooser.FileNameExtensionFilter
trait Nat {
    def isZero: Boolean
    def add(other: Nat): Nat
    def subtract(other: Nat): Nat
    def greater(other: Nat): Boolean
    def toInt: Int
}


case object Zero extends Nat {
  override def isZero: Boolean = true

  override def add(other: Nat): Nat = other
  override def subtract(other: Nat): Nat = Zero // I assume we have this - other, not clear if we are other - this 
  override def greater(other: Nat): Boolean = if(!other.isZero) true else false
  override def toInt: Int = 0
}

case class Succ(n: Nat) extends Nat {
    override def isZero: Boolean = false

    override def add(other: Nat): Nat = Succ(n.add(other))
    override def subtract(other: Nat): Nat = if(other.isZero) this else n.subtract(other.subtract(Zero))
    override def greater(other: Nat): Boolean = !other.isZero && n.greater(other.subtract(Zero))
    override def toInt: Int = 1 + n.toInt
}

trait OList{
  def head: Int = 0
  def tail: OList = Empty
  def foldRight[B](acc: B)(op: (Int,B) => B): B = acc
  def foldLeft[B](acc: B)(op: (B,Int) => B): B = acc
  def indexOf(i: Int): Int = -1
  def filter(p: Int => Boolean): OList = Empty
  def map(f: Int => Int): OList = Empty
  def partition(p: Int => Boolean): (OList, OList) = (Empty, Empty)
  def slice(start: Int, stop: Int): OList = Empty
  def forall(p: Int => Boolean): Boolean = true
}

case object Empty extends OList

case class Next(number: Int, xs: OList) extends OList{

    // @tailrec
    final override def foldLeft[B](acc: B)(op: (B, Int) => B): B = xs.foldLeft(op(acc, number))(op) //Weird Erorr

    override def filter(p: Int => Boolean): OList = if(p(number)) foldRight(xs)((numar, lista) => Next(numar, xs.filter(p))) 
                                                  else xs.filter(p)

    override def tail: OList = if(xs != Empty) xs else xs.tail 

    override def forall(p: Int => Boolean): Boolean = if(p(number)) xs.forall(p) else false

    override def slice(start: Int, stop: Int): OList = { //Super hard :c

      if (start >= stop || start < 0) Empty 
      else if (start == 0) Next(number, xs.slice(0, stop - 1))
      else xs.slice(start - 1, stop - 1) 
    }

    override def head: Int = if(xs != Empty) number else xs.head   // Wtich one is the head? 

    override def indexOf(i: Int): Int = if(number != i) xs.indexOf(i) else foldRight(0)((acc, ind) => acc + 1) - 1

    override def foldRight[B](acc: B)(op: (Int, B) => B): B = op(number, xs.foldRight(acc)(op))

    override def partition(p: Int => Boolean): (OList, OList) = if(p(number)) (foldRight(xs)((numar, lista) => Next(numar, xs.partition(p)._1 )), xs.partition(p)._2)
                                                  else (xs.partition(p)._1, foldRight(xs)((numar, lista) => Next(numar, xs.partition(p)._2))) 

    override def map(f: Int => Int): OList = foldLeft(xs)((lis, num) => Next(f(number), xs.map(f)))
    
}
val l = Next(1, Next(2, Next(3, Empty)))

l.head


val list = Next(1, Next(2, Next(3, Next(4, Next(5, Next(6, Next(7, Next(8, Next(9, Next(10, Empty))))))))))

val result = list.foldRight(0)((element, accum) => element + accum)
val test = list.filter((x)=> if(x%2 == 0) true else false)
val ind = list.indexOf(2)
val map = list.map(_*2)
val partition = list.partition(_%2 == 0)
partition._1
partition._2
val slice = list.slice(2, 4)
val all = list.forall(_%2 == 0)
val all2 = list.forall(_+1 > 0)