import scala.compiletime.ops.boolean
import scala.annotation.tailrec
val empty: Int => Boolean = x => false

type Set = Int => Boolean

def profileID: Int = 6969

def member(e: Int)(s: Set): Boolean =
{
    s(e) // Goodness Gracious
}

def singleton(x: Int): Set = 
{
    (y: Int) => y == x
}

def ins(x: Int)(s: Set): Set = 
{
    (y: Int) => y == x || s(y)
}

def fromBounds(start: Int, stop: Int): Set = 
{
    def loop(ind: Int): Set =
    {
        if(ind > stop) singleton(start)
        else ins(ind)(loop(ind + 1))

    }

    loop(start)
}

def union(set1: Set, set2: Set): Set =
{
    (y: Int) => member(y)(set1) || member(y)(set2)
}

def complement(s1: Set): Set = 
{
    (y: Int) => !(s1(y))
}


def sumSet(b: Int)(start: Int, stop: Int)(s: Set): Int =
{
    def checkIfInSet(x: Int): Int = 
    {
        if(member(x)(s)) x
        else 0
    }

    @tailrec
    def auxSum(crt: Int, acc: Int): Int = 
    {
        if(crt > stop) acc
        else auxSum(crt + 1, acc + checkIfInSet(crt))
    }

    auxSum(start, b)
}

def foldLeftSet (b:Int)(op: (Int,Int) => Int)(start: Int, stop: Int)(set: Set): Int =    // the set to be folded
{
    def checkIfInSet(x: Int): Int = 
    {
        if(member(x)(set)) x
        else 0
    }

    @tailrec
    def auxSum(crt: Int, acc: Int): Int = 
    {
        if(crt > stop) acc
        else auxSum(crt + 1, op(acc, checkIfInSet(crt)))
    }

    auxSum(start, b)
}

def foldRightSet(b:Int)(op: (Int,Int) => Int)(start: Int, stop: Int)(s: Set): Int = 
{
    def checkIfInSet(x: Int): Int = 
    {
        if(member(x)(s)) x
        else 0
    }

    def auxSum(crt: Int): Int = 
    {
        if(crt > stop) b
        else op(checkIfInSet(crt), auxSum(crt + 1))
    }

    auxSum(start)
}

def filter(p: Int => Boolean)(s: Set): Set = 
{
    (y: Int) => member(y)(s) && p(y)
}

def partition(p: Int => Boolean)(s: Set): (Set,Set) = 
{
    (filter(p)(s), filter((x: Int) => !p(x))(s))
}

def forall(cond: Int => Boolean)(start: Int, stop: Int)(s: Set): Boolean = 
{
    val pair = partition(cond)(s)


    def inPair(x: Int): Int = 
    {
        if(member(x)(pair._1)) x
        else 0
    }

    @tailrec
    def loop(crt: Int, acc: Int): Int = 
    {
        if(crt > stop) acc
        else loop(crt + 1, acc + inPair(crt))
    }
    
    val x = loop(start, 0)
    val y = sumSet(0)(start, stop)(s)

    x == y
}

def exists(cond: Int => Boolean)(start: Int, stop: Int)(s: Set): Boolean = 
{
    val pair = partition(cond)(s)


    def inPair(x: Int): Int = 
    {
        if(member(x)(pair._1)) 1
        else 0
    }

    @tailrec
    def loop(crt: Int, acc: Int): Int = 
    {
        if(crt > stop) acc
        else loop(crt + 1, acc + inPair(crt))
    }
    
    val x = loop(start, 0)

    (x > 0)

}


def setOfDivByK(k: Int): Set =
{
    (x: Int) => x % k == 0
}

def moreDivs(k: Int)(start: Int, stop:Int)(s1: Set, s2: Set): Boolean = 
{
    def isDiv(x: Int): Boolean = 
    {
        if(x%k == 0) true
        else false
    }
    //We could also divide in partitions and then only check if it is inSet
    // val pat = partition(isDiv)(s1)
    // val pat2 = partition(isDiv)(s2)

    def inSet(x: Int, s: Set): Boolean = 
    {
        if(member(x)(s)) true
        else false
    }

    def inBoth(x: Int, s: Set): Int =
    {
        if(isDiv(x) && inSet(x, s)) 1
        else 0
    }

    @tailrec
    def loop(ind: Int, acc: Int): Int = 
    {
        if(ind > stop) acc
        else loop(ind + 1, acc + inBoth(ind, s1))
    }

    @tailrec
    def loop2(ind: Int, acc: Int): Int = 
    {
        if(ind > stop) acc
        else loop2(ind + 1, acc + inBoth(ind, s2))
    }

    loop(start, 0) > loop2(start, 0)    
}


member(2)(setOfDivByK(2))


forall(_ => false)(0,100)(empty)
forall(_ % 2 == 0)(0,100)(filter(_ % 2 == 0)(fromBounds(0,100)))

fromBounds(0, 4)

assert(member(1)(singleton(1)))
assert(member(1)(fromBounds(0, 4)))

assert(!member(1)(empty))
assert(!member(10)(empty))

member(1)(singleton(1))

assert(member(1)(singleton(1)))
assert(member(10)(singleton(10)))
assert(!member(1)(singleton(2)))


assert(member(1)(ins(1)(singleton(0))))
assert(member(0)(ins(1)(singleton(0))))
assert(!member(2)(ins(1)(singleton(0))))

assert(member(1)(union(singleton(1),singleton(2))))
assert(member(2)(union(singleton(1),singleton(2))))
assert(!member(0)(union(singleton(1),singleton(2))))

assert(!member(1)(complement(singleton(1))))
assert(member(0)(complement(singleton(1))))


assert(sumSet(0)(1,1)(singleton(1)) == 1)
assert(sumSet(99)(1,1)(singleton(1)) == 100)
assert(sumSet(99)(1,1)(singleton(0)) == 99)
assert(sumSet(99)(2,1)(singleton(2)) == 99)
assert(sumSet(0)(1,3)(union(singleton(1),union(singleton(2),singleton(3)))) == 6)

assert(foldLeftSet(0)(_ + _)(1,1)(singleton(1)) == 1)
assert(foldLeftSet(99)(_ + _)(1,1)(singleton(1)) == 100)
assert(foldLeftSet(99)(_ + _)(1,1)(singleton(0)) == 99)
assert(foldLeftSet(99)(_ + _)(2,1)(singleton(2)) == 99)
assert(foldLeftSet(0)(_ + _)(1,3)(union(singleton(1),union(singleton(2),singleton(3)))) == 6)
assert(foldLeftSet(100)(_ - _)(1,3)(union(singleton(1),union(singleton(2),singleton(3)))) == 94)


assert(foldRightSet(0)(_ + _)(1,1)(singleton(1)) == 1)
assert(foldRightSet(99)(_ + _)(1,1)(singleton(1)) == 100)
assert(foldRightSet(99)(_ + _)(1,1)(singleton(0)) == 99)
assert(foldRightSet(99)(_ + _)(2,1)(singleton(2)) == 99)
assert(foldRightSet(0)(_ + _)(1,3)(union(singleton(1),union(singleton(2),singleton(3)))) == 6)
assert(foldRightSet(0)(_ - _)(1,3)(union(singleton(1),union(singleton(2),singleton(3)))) == 2)

assert(!member(1)(filter(_ %2 == 0)(union(singleton(1),union(singleton(2),singleton(3))))))
assert(member(2)(filter(_ %2 == 0)(union(singleton(1),union(singleton(2),singleton(3))))))
assert(!member(3)(filter(_ %2 == 0)(union(singleton(1),union(singleton(2),singleton(3))))))

val pair = partition(_%2 == 0)(union(singleton(1),union(singleton(2),singleton(3))))
assert(!member(1)(pair._1))
assert(member(2)(pair._1))
assert(!member(3)(pair._1))
assert(member(1)(pair._2))
assert(!member(2)(pair._2))
assert(member(3)(pair._2))

assert(forall(_ => false)(0,100)(empty))
assert(forall(_ % 2 == 0)(0,100)(filter(_ % 2 == 0)(fromBounds(0,100))))
assert(!forall(_ % 2 == 0)(0,100)(union(singleton(1),filter(_ % 2 == 0)(fromBounds(0,100)))))

assert(!exists(_ => true)(0,100)(empty))
assert(!exists(_ % 2 == 0)(0,100)(filter(_ % 2 == 1)(fromBounds(0,100))))
assert(exists(_ % 2 == 0)(0,100)(union(singleton(1),filter(_ % 2 == 0)(fromBounds(0,100)))))

assert(member(2)(setOfDivByK(2)))
assert(member(22)(setOfDivByK(11)))
assert(member(49)(setOfDivByK(7)))
assert(!member(2)(setOfDivByK(7)))

assert(moreDivs(2)(0,100)(union(singleton(2),singleton(4)), singleton(2)))
assert(moreDivs(2)(0,100)(setOfDivByK(2),setOfDivByK(4)))
assert(!moreDivs(7)(0,100)(setOfDivByK(49),setOfDivByK(7)))