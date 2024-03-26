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
    if(n < 0) Void
    else l match
        case Cons(_, xs) => drop(n-1)(xs) 


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

def merge(l1: IList, l2: IList): IList = 
    (l1, l2) match 
        case (Void, _) => l2 
        case (_, Void) => l1 
        case (Cons(x, xs), Cons(y, ys)) =>
            if (x <= y) Cons(x, merge(xs, l2)) 
            else Cons(y, merge(l1, ys)) 


def mergeSort(l: IList): IList = {
  def split(lst: IList): (IList, IList) = 
    def splitHelper(lst: IList, acc: IList, count: Int): (IList, IList) = lst match {
      case Void => (acc, Void)
      case Cons(x, xs) if count == 0 => (acc, lst)
      case Cons(x, xs) => splitHelper(xs, Cons(x, acc), count - 1)
    }
    splitHelper(lst, Void, length(lst) / 2)
  

  def length(lst: IList): Int = lst match {
    case Void => 0
    case Cons(_, xs) => 1 + length(xs)
  }

  l match {
    case Void | Cons(_, Void) => l 
    case _ =>
      val (left, right) = split(l)
      merge(mergeSort(left), mergeSort(right)) 
  }
}