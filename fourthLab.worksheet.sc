trait IList 
case object Void extends IList
case class Cons(x: Int, xs: IList) extends IList

def isEmpty(l: IList): Boolean = 
    l match
        case Void => true
        case _ => false


def size(l: IList): Int = 
    l match
        case Void => 0
        case Cons(_, xs) => 1 + size(xs)

def contains(e: Int, l: IList): Boolean = 
    l match
        case Void => false
        case Cons(x, xs) => 
            if(x == e) true
            else contains(e, xs)

def max(l: IList): Int = 
    l match
        case Void => 0
        case Cons(x, Void) => x
        case Cons(x, xs) => 
            val maximum = max(xs)
            if(x > maximum) x
            else maximum


def take(n: Int)(l: IList): IList = 
    if(n < 0) Void
    else l match                 
        case Cons(x, xs) => Cons(x, take(n - 1)(xs)) 
  
def drop(n: Int)(l: IList): IList = 
    if(n < 1) l
    else l match
        case Cons(_, xs) => drop(n-1)(xs) 

val l = Cons(1, Cons(2, Cons(3, Void)))
drop(0)(l)
drop(1)(l)
drop(2)(l)
drop(3)(l)


def append(l1: IList, l2: IList): IList = 
    l1 match
        case Void => l2
        case Cons(x, xs) => Cons(x, append(l1, l2))
    

def last(l: IList): Int =
    l match
        case Void => 0
        case Cons(x, Void) => x
        case Cons(_, xs) => last(xs)  


def reverse(l: IList): IList = 
    l match
        case Void => Void
        case Cons(x, xs) => append(reverse(xs), Cons(x, Void))


 def reverseTail(l: IList): IList = {
  def reverseTailer(l: IList, acc: IList): IList = l match {
    case Void => acc
    case Cons(x, xs) => reverseTailer(xs, Cons(x, acc)) 
  }

  reverseTailer(l, Void)
}   


def isSorted(l: IList): Boolean = {
    l match 
        case Void => true  
        case Cons(_, Void) => true 
        case Cons(x, Cons(y, _)) if x > y => false 
        case Cons(_, xs) => isSorted(xs) 
}

def isSortedGen(op: (Int, Int) => Boolean)(l: IList): Boolean = {
    l match 
        case Void => true  
        case Cons(_, Void) => true 
        case Cons(x, Cons(y, _)) if op(x, y) == false => false 
        case Cons(_, xs) => isSorted(xs) 
}

def merge(l1: IList, l2: IList): IList = 
    (l1, l2) match 
        case (Void, _) => l2 
        case (_, Void) => l1 
        case (Cons(x, xs), Cons(y, ys)) =>
            if (x <= y) Cons(x, merge(xs, l2)) 
            else Cons(y, merge(l1, ys)) 



def mergeSort(l: IList): IList = {
  val len = size(l) / 2
  if (len == 0) l
  else {
    val left = take(len)(l)
    val right = drop(len)(l)
    merge(mergeSort(left), mergeSort(right))
  }
}
