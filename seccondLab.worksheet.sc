import scala.annotation.tailrec
import scala.math.abs


def foldWith (b: Int)(op: (Int,Int) => Int)(start: Int, stop: Int): Int = {
    def tail_fold(crt: Int, acc: Int): Int  = {

        if(crt > stop) acc
        else tail_fold(crt+1, op(acc, crt))
    }
    tail_fold(start, b)
}


val t = foldWith(6)((x, y) => (x+y))

t(3, 6)


def foldConditional(b: Int)(op: (Int,Int) => Int, p: Int => Boolean)(start: Int, stop: Int): Int = {
        def tail_fold(crt: Int, acc: Int): Int  = 
        {
                if(crt > stop || !p(crt)) acc
                else tail_fold(crt+1, op(acc, crt))
        }
        tail_fold(start, b)
    }


def foldRight (b: Int)(op: (Int,Int) => Int)(start: Int, stop: Int): Int = {
    
    def tail_fold(crt: Int): Int  = 
    {

        if(crt > stop) crt
        else op(crt, tail_fold(crt+1))
    }
    b + tail_fold(start)
}

def foldMap(op: (Int,Int) => Int, f: Int => Int)(start: Int, stop: Int): Int = {
    def tail_fold(crt: Int, acc: Int): Int  = 
    {
        if(crt > stop) acc
        else tail_fold(crt+1, op(acc, f(crt)))
    }
    tail_fold(start, 0)
}

def sumSquares(n: Int): Int = {
    foldMap((x, y) => (x+y), (x) => (x * x))(1, n)
}


sumSquares(3)


def hasDivisor(k: Int, start: Int, stop: Int): Boolean = {
        def loop: Int => Int = ind => {
        if (ind > k / 2) 0
        else if (k % ind == 0) ind
        else loop(ind + 1)
    }

    if(foldMap((x, y) => (x+y), (x) => (loop(x)))(start, stop) > 0) true
    else false


}

val smallRange = 0.005 // I dont really know what a small area is
def integrate(f: Double => Double)(start: Double, stop: Double): Double ={

    def loop(a: Double, b: Double): Double ={
        if(b - a < smallRange) (f(a) + f(b)) * f(a-b)/2
        else loop(a, (b-a)/2) + loop((b-a)/2, b)
    }

    loop(start, stop)
}

type Line2D = Int => Int
def translateOx(offset: Int)(l: Line2D): Line2D = {
    (y: Int) => y + offset
}

val line: Line2D = (x: Int) => x + 1 

translateOx(2)(line)(1)

def translateOy(offset: Int)(l: Line2D): Line2D = {

    (y: Int) => l(y) + offset
}

translateOy(2)(line)(1)

def intersect(l1: Line2D, l2: Line2D)(start: Int, stop: Int): Boolean = {

    @tailrec
    def loop(ind: Int, nLine: Line2D, nLine2: Line2D): Boolean = {
        if(l1(ind) == l2(ind)) true
        else if(ind > stop) false
        else loop(ind + 1, translateOx(ind)(nLine), translateOx(ind)(nLine2))
    }

    loop(start, l1, l2)
}

def larger(l1: Line2D, l2: Line2D)(start: Int, stop: Int): Boolean = {

    @tailrec
    def loop(ind: Int, nLine: Line2D, nLine2: Line2D): Boolean = {
        if(l1(ind) < l2(ind)) false
        else if(ind > stop) true
        else loop(ind + 1, translateOy(ind)(nLine), translateOy(ind)(nLine2))
    }

    loop(start, l1, l2)
}