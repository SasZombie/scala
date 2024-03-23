import scala.annotation.tailrec
println("Hello")
val n = 10
var i = 1
var factorial = 1

while (i<n){
    factorial = factorial * i
    i += 1
}

factorial



def fact(n: Int): Int = {
    if (n==0) 1
    else n * fact(n-1)
}

println(fact(5))

val a =10
val b= 20
var found: Boolean = false

///////////////////////////////////////////////////////////////////
def isPrime(n: Int, i: Int): Boolean = {
    
    if(n == 0 || n == 1) return false
    if(i == 1) return true;

    if(n%i == 0)
        return false;
    return isPrime(n, i-1);
}
println(isPrime(7, 7/2));

def primeNumberInterval(a: Int, b: Int): Boolean = 
{
    if(a > b)
        return false;
    if(isPrime(a, a/2))
        return true;
    
    return primeNumberInterval(a+1, b)  
}

println(primeNumberInterval(0, 20))

/////////////////////////////////////////////////////////////////////

def fib(n: Int): Int = {
    @tailrec
    def loop(i: Int, fx: Int, fy: Int): Int = {
        if(i == n)
            return fx;       
        loop(i+1, fx + fy, fx)
    }

    loop(0, fx = 0, fy = 1);

}


print(fib(4));