

final class seccondLab$u002Eworksheet$_ {
def args = seccondLab$u002Eworksheet_sc.args$
def scriptPath = """seccondLab.worksheet.sc"""
/*<script>*/
import scala.math.abs


def foldWith (b: Int)(op: (Int,Int) => Int)(start: Int, stop: Int): Int = {
    def tail_fold(crt: Int, acc: Int): Int  = 
    {

        if(crt > stop) acc
        else tail_fold(crt+1, op(acc, crt))
    }
    tail_fold(start, b)
}


val t = foldWith(6)((x, y) => (x+y))

t(3, 6)


def foldConditional(b: Int)(op: (Int,Int) => Int, p: Int => Boolean)(start: Int, stop: Int): Int =
    {
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

def foldMap(op: (Int,Int) => Int, f: Int => Int)(start: Int, stop: Int): Int =
{
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


def hasDivisor(k: Int, start: Int, stop: Int): Boolean = 
    {
         def loop: Int => Int = ind => {
            if (ind > k / 2) 0
            else if (k % ind == 0) ind
            else loop(ind + 1)
        }

        if(foldMap((x, y) => (x+y), (x) => (loop(x)))(start, stop) > 0) true
        else false


    }


//     We can compute the sum of an area defined by a function within a range a,b (the integral of that function given the range), using the following recursive scheme:

//     if the range is small enough, we treat f as a line (and the area as a trapeze). It's area is (f(a)+f(b))(b−a)/2

//     .
//     otherwise, we compute the mid of the range, we recursively compute the integral from a to mid and from mid to b, and add-up the result.

// Implement the function integrate which computes the integral of a function f given a range: 

val smallRange = 0.5 // I dont really know what a small area is
def integrate(f: Double => Double)(start: Double, stop: Double): Double =
{

}
/*</script>*/ /*<generated>*//*</generated>*/
}

object seccondLab$u002Eworksheet_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new seccondLab$u002Eworksheet$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export seccondLab$u002Eworksheet_sc.script as `seccondLab.worksheet`

