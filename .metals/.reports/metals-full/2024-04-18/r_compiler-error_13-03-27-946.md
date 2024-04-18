file://<WORKSPACE>/seventh.worksheet.sc
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-runtime_3/2.5.2/mdoc-runtime_3-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-interfaces/2.5.2/mdoc-interfaces-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_3/0.12.0/metaconfig-pprint_3-0.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 4120
uri: file://<WORKSPACE>/seventh.worksheet.sc
text:
```scala
object worksheet{
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
          
          loop(new String(""), 0)
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
          nonZeroTerms.map{
              case (@@)p2.nonZeroTerms.contains(key) => new Polynomial()
          }
  
      def *(p2: Polynomial): Polynomial = ???
              
  }
          
  val test = Map(2 -> 2, 0 -> 4) // encodes 2*X^2 + 3
  val poly = new Polynomial(test)
  // poly.toString
  val testMap = Map(1 -> 1, 2 -> 2, 3 -> 3)
  val testMap2 = Map(4 -> 1, 5 -> 2, 6 -> 3)
  
  
  val mapSum = testMap.map { case (key, value) => value + key}.sum
  poly.degree
}
```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2607)
	scala.meta.internal.pc.SignatureHelpProvider$.isValid(SignatureHelpProvider.scala:83)
	scala.meta.internal.pc.SignatureHelpProvider$.notCurrentApply(SignatureHelpProvider.scala:92)
	scala.meta.internal.pc.SignatureHelpProvider$.$anonfun$1(SignatureHelpProvider.scala:48)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile(LinearSeq.scala:280)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile$(LinearSeq.scala:278)
	scala.collection.immutable.List.dropWhile(List.scala:79)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:48)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:414)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner