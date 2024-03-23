import scala.annotation.tailrec


// def sumInt(start: Int, stop: Int): Int =
// {
//     @tailrec
//     def loop(index: Int, acc: Int): Int = {
//         if(index > stop) acc
//         else loop(index+1, acc + index);
//     }

//     loop(start, 0)
// }

// sumInt(0, 10)


// def sumSquares(start: Int, stop: Int): Int = {
//     @tailrec
//     def loop(index: Int, acc: Int): Int = {
//         if(index > stop) acc
//         else loop(index+1, acc + index * index);
//     }

//     loop(start, 0)
// }

// sumSquares(0, 4)


// def sumWithF(f: Int => Int, start: Int, stop: Int): Int ={
//     def loop(i:Int, acc: Int): Int = {
//         if(i > stop) acc
//         else loop(i, f(i) + acc)
//     }

//     loop(start, 0)
// }


// def id(x: Int): Int = x

// def Sqare(x: Int): Int = x * x

// def sumInt(start: Int, stop: Int): Int = sumWithF(x => x, start, stop)

// def sumSquares(start: Int, stop: Int): Int = sumWithF(x => x * x, start, stop)

// sumInt(0, 3)


def alg1(x: Int): Int = x
def alg2(x: Int): Int = x * x
def alg3(x: Int): Int = x * x * x


// Curry = Higher order functions
def currySumWithF(f: Int => Int): (Int, Int) => Int = {
    def sumWithF(start: Int, stop: Int): Int = {
        @tailrec
        def loop(index: Int, acc: Int): Int ={
            if(index > stop) acc
            else loop(index + 1, f(index) + acc)
        }
    
        loop(start, 0)
    }

    sumWithF
}

//Takes as paramerter "In turn"
def cleanSumWithF(f: Int => Int)(start: Int, stop: Int): Int = {
    
    def loop(i: Int, acc: Int): Int = {
        if(i > stop) acc
        else loop(i + 1, f(i) + acc)
    }
    loop(start, 0)
}


val ammplyAl1 = currySumWithF(alg1)
val ammplyAl2 = currySumWithF(alg2)
val tryAlg = cleanSumWithF(alg1)

ammplyAl1(0, 10)
ammplyAl2(0, 10)
tryAlg(0, 10)
ammplyAl1(10, 20)

//Curry or Uncurry

def f(x: Int)(y: Int)(z:Int): Int = x + y + z

//Ultimele 2 ()() sunt inchideri functionale
f(1)(1)(1) 
// is a function

def unF(x: Int, y: Int, z: Int): Int = x + y + z

unF(1,1,1)

def compose(f: Int => Int, g:Int => Int): Int => Int = {
    x => f(g(x))
}

compose(x=>x, x=>x*x)(3)

//Suppose we use function to encode 2 dimentional linex

val l1: Int => Int = x => 2 * x + 1

l1(3)

def make2Dline(a: Int)(b: Int): Int => Int ={
    x => a * x + b
}


val l2 = make2Dline(2)(1)

def unCurry(f: Int => Int => Int): (Int, Int)=> Int = 
{
    (x, y) => f(x)(y)
}


// def unCurry(f: Int => Int => Int)(x: Int, y: Int): Int = 
// {
//     f(x)(y)
// }

// val l3 = unCurry(make2Dline)