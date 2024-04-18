import scala.annotation.tailrec
import scala.annotation.meta.setter
trait Nat {
   def +(other: Nat): Nat
}
case object Zero extends Nat{
   override def +(other: Nat): Nat = other
}
case class Succ(n: Nat) extends Nat{
   override def +(other: Nat): Nat = Succ(n + other)
}


object Nat { 
    def apply(i: Int): Nat = {
        if (i <= 0) Zero
        else Succ(apply(i - 1))
    }

    def apply(str: String): Nat = {
        if(str.toIntOption != None)
            apply(str.toInt)
        else
            Zero
    }
}

Nat(2) + Zero + Nat("2")


def fromList(l: List[Integer]): List[Option[Nat]] = 
    l.map { i =>
    if (i.intValue() >= 0) Some(Nat(i.intValue()))
    else None
  }

def fromOptions(l: List[Option[Nat]]): Option[List[Nat]] = 
    if(l.contains(None)) 
        None
    else
        Some(l.flatten)

class Dictionary[K,V](inner: List[(K,V)]) {
    def +(pair: (K, V)): Dictionary[K,V] = 
        new Dictionary(pair :: inner)

    def contains(value: V): Boolean = {
        inner.exists(_._2 == value)
    }

    def containsKey(key: K): Boolean = {
        inner.exists(_._1 == key)
    }

    def get(key: K): Option[V] = 
        inner.find(_._1 == key) match
            case Some(value) => Some(value._2)        
            case None => None

    def getOrElse(default: V)(key: K): V = {
        val temp = this.get(key)

        if(temp == None)
            default
        else 
            temp.get
    }

    def map(f: ((K, V)) => (K, V)): Dictionary[K, V] = {
        new Dictionary(inner.map(f))
    }

    //Que? Que? Que? Nu am idee ce vrea sa incemne asta :c
   

}

object Dictionary {
  def apply[K,V](inner: List[(K,V)]): Dictionary[K,V] = {
    new Dictionary(inner)
  }
}

class Dictionary2[K,V](inner: List[(K,V)], default: Option[V]) {

    def get(key: K): Option[V] = 
        inner.find(_._1 == key) match
            case Some(value) => Some(value._2)        
            case None =>
                if(default == None) None
                else default        

    def withDefaultValue(default: V): Dictionary2[K,V] = {
        val newInner = inner
        new Dictionary2(newInner, None)
    }
}
object Dictionary2 {
  def apply[K,V](inner: List[(K,V)]): Dictionary2[K,V] = {
    new Dictionary2(inner, None)
  }
  def apply[K,V](inner: List[(K,V)], default: Option[V]): Dictionary2[K,V] = {
    new Dictionary2(inner, default)
  }
}

val list = List(("key1", "value1"), ("key2", "value2"), ("key3", "value3"))

val dict = Dictionary(list)

val newDict = dict + ("key4", "value4")

val valuer = dict.get("key2")

val dict2 = Dictionary2(list)


case class Polynomial (nonZeroTerms: Map[Int,Int]) 
{
    def *(n: Int): Polynomial = 
        new Polynomial(nonZeroTerms.view.mapValues(_ * n).toMap)
        
    override def toString: String = {
        val orderedKeys = nonZeroTerms.keys.toList.sortBy(-_)
        val stop = orderedKeys.size

        @tailrec
        def loop(acc: String, ind: Int): String = 
        {
            if(ind == stop) acc
            else{
                val power = orderedKeys(ind) //Key
                if(power != 0)
                    loop(acc.concat(nonZeroTerms.get(power).get.toString()).concat("*X^").concat(power.toString()).concat("+"), ind + 1)
                else
                    loop(acc.concat(nonZeroTerms.get(power).get.toString()), ind + 1)

            } 
        }
        
        var str = loop(new String(""), 0)
        if(str.last == '+')
            str = str.init
        str
    }   
    def hasRoot(r: Int): Boolean = {
        val sum = nonZeroTerms.map{
            case (key, value) => value * r ^ key
        }.sum

        (sum == 0)
    }

    def degree: Int = 
        val orderedKeys = nonZeroTerms.keys.toList.sortBy(-_)
        orderedKeys(0)

    def +(p2: Polynomial): Polynomial = 
        new Polynomial(
            nonZeroTerms.map{
            case (key, value) => 
                if(p2.nonZeroTerms.contains(key)) 
                    (key, value + p2.nonZeroTerms.get(key).get)
                else 
                    (key, value)
        }.concat(
            p2.nonZeroTerms.map{
            case (key, value) => 
                if(nonZeroTerms.contains(key)) 
                    (key, value + nonZeroTerms.get(key).get)
                else 
                    (key, value)
            }
        )
        )


    
    
    //Not sure this works... I dont know how to muliply polinomials :)
    def *(p2: Polynomial): Polynomial = 
    {
        val degree = this.degree + p2.degree
        new Polynomial(
            nonZeroTerms.map{
            case (key, value) => 
                if(p2.nonZeroTerms.contains(key)) 
                    (key, value * p2.nonZeroTerms.get(key).get)
                else 
                    (key, value)
        }.concat(
            p2.nonZeroTerms.map{
            case (key, value) => 
                if(nonZeroTerms.contains(key)) 
                    (key, value * nonZeroTerms.get(key).get)
                else 
                    (key, value)
            }
        )
        )
    }   
        

            
}
        
val test = Map(2 -> 2, 1 -> 4)
val test2 = Map(2->2, 0->4)
val poly = new Polynomial(test)
val poly2 = new Polynomial(test2)

poly + poly2
// poly.toString
val testMap = Map(1 -> 1, 2 -> 2, 3 -> 3)
val testMap2 = Map(4 -> 1, 5 -> 2, 6 -> 3)


val mapSum = testMap.map { case (key, value) => value + key}.sum
poly.degree

