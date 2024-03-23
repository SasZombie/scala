import scala.annotation.tailrec


def fact (n: Int): Int = {

   @tailrec
   def aux_fact(i: Int, acc: Int): Int = 
       if (i == n) acc
       else aux_fact(i+1, acc * i)
    
    aux_fact(1, n);
}

fact(4)

@tailrec
final def gcd(n: Int, m: Int): Int = 
{
    if(m == 0) n
    
    else gcd(m, n%m)

}


gcd(4, 6)

// 1 , 2 * 2, 3 8 3

def sumSquares(n: Int): Int =
{
    @tailrec
    def aux_sum(i : Int, acc: Int): Int = {
        if(i == n)  acc +  i*i
        else aux_sum(i + 1, acc + i * i)

    }

    aux_sum(0, 0)
}

sumSquares(3)


def sumNats(start: Int, stop: Int): Int = 
{
    def noTail(i : Int): Int =
        if(i == stop) i
        else i + noTail(i+1)

    noTail(start)
}

sumNats(3, 6)


def sumNatsTail(start: Int, stop: Int): Int = 
{
    @tailrec
    def yesTail(i : Int, acc: Int): Int =
        if(i == stop) acc + i
        else yesTail(i+1, acc + i)

    yesTail(start, 0)
}

sumNatsTail(3, 6)

def subtractRange(x: Int, start: Int, stop: Int): Int = 
{
    @tailrec
    def tailRec(i : Int, diff : Int): Int =
    {
        if(i > stop) diff
        else tailRec(i+1, diff - i)
    }


    tailRec(start, x)
}

subtractRange(100, 3, 6)


def substractRange2(x: Int, start: Int, stop: Int): Int = {

    def noTail(i : Int): Int ={
        if(i == stop) i
        else x - noTail(i + 1)
    }

    noTail(start)
}

substractRange2(100, 3, 6)

def improve(xn: Double, a: Double): Double = {
    1/2 * (xn + a/xn)
}

def nth_guess(n: Int, a: Double): Double = {
    
    def aux(i :Int, newImpr: Double): Double = {
        if(i == n) newImpr
        else improve(newImpr, a)
    }

    aux(1, improve(1, a))
}


def acceptable(xn: Double, a: Double): Boolean = {
    if(math.abs(xn * xn - a) < 0.001) true
    else false
}

