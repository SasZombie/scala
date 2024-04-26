type Matrix = List[List[Int]]

def sum(m : Matrix): Int ={    
    m.flatten.sum
}

def scalarMult(number: Int, m :Matrix): Matrix={
    m.map(inner => inner.map{
        case element: Int => element * number;
    })
}

def add(m1: Matrix, m2: Matrix): Matrix = {
    m1.lazyZip(m2).map{
        (inner1, inner2) => (inner1.lazyZip(inner2)).map(_ + _)
    }
}

//Should be called single Colom tbh :)
def singleLine(m: Matrix): List[Int] = {
    m.map(_.head)
}
def remCol(m: Matrix): Matrix = {
    m.map(_.tail)
}

//m.transpose :3
def transpose(m: Matrix): Matrix = {
   
    def loop(currMat: Matrix, acc: List[List[Int]]): Matrix = 
    {
        if(currMat(0).isEmpty) acc
        else{
            val line = singleLine(currMat)
            loop(remCol(currMat), acc :+ line)
        }
    }
    loop(m, Nil)
}

def mult(m1: Matrix, m2: Matrix): Matrix = {
    val trans = transpose(m2)
    val cols = m1(0).size
    val rows = m1.size
    
    m1.map{
        firstLine => trans.map{
            seccondLine => 
                firstLine.zip(seccondLine).map{
                    case (x, y) => x * y
                }.sum
        }
    }

    // m1.lazyZip(trans).map{
    //     (inner1, inner2) => (inner1.lazyZip(inner2)).map(_ * _)
    // }

    // flats.lazyZip(flats).map{
    //     case (a, b) => a * b
    // }.grouped(trans.head.size).toList
    // def loop(i:Int, acc: List[List[Int]]): Matrix = 
    // {
    //     if(i >= rows) acc
    //     else{
    //         loop(i + cols, )
    //     }
    // }
}   


val mat = List(List(1, 2), List(3, 4), List(5, 6), List(7, 8))
val mat2 = List(List(1, 2), List(3, 4))
singleLine(mat2)

sum(mat)

scalarMult(2, mat);
add(mat, mat)

singleLine(mat)
remCol(mat)

transpose(mat)
transpose(mat2)

mult(mat2, mat2)
