object Matrix extends App {
  type Img = List[List[Int]]
  def show(m: Img): String = {

    val cols = m(0).size
    val flat = m.flatten
    val size = flat.size

    def loop(i: Int, acc: String): String = {
      if (i == size) acc
      else {
        if ((i + 1) % cols == 0)
          loop(i + 1, acc + flat(i).toString() + "\n")
        else {
          loop(i + 1, acc + flat(i).toString() + " ")
        }
      }
    }
    /*
      X, X, X, X, X, X\n
      X, X, X, X, X\n
     */

    loop(0, "")
  }

  def hFlip(img: Img): Img = {
    img.map(_.reverse)
  }

  def vFlip(img: Img): Img = {
    img.reverse
  }

  def rot90Right(img: Img): Img = {
    hFlip(vFlip(img))
  }

  def rot90Left(img: Img): Img = {
    vFlip(hFlip(img))
  }

  def invert(img: Img): Img = {
    val maxPixel = 255
    img.map(inner =>
      inner.map { case element: Int =>
        maxPixel - element;
      }
    )
  }

  def cropAt(img: Img, xSt: Int, ySt: Int, xEnd: Int, yEnd: Int): Img = {
    img.slice(xSt, xEnd + 1).map(line => line.slice(ySt, yEnd + 1))
  }

  def largerPos(img: Img, ind: Int): List[(Int, Int)] = {

    img.zipWithIndex.flatMap { case (row, rowIndex) =>
      row.zipWithIndex.collect {
        case (element: Int, colIndex: Int) if element > ind =>
          (rowIndex, colIndex)
      }
    }

    // img.zipWithIndex.flatMap {
    //   case (row, rowIndex: Int) =>
    //   row.zipWithIndex.map {
    //     case (element: Int, colIndex: Int) =>
    //       if (element > ind)
    //         (rowIndex, colIndex)
    //   }
    // }
  }

  def contrast(x: Int)(img: Img): Img = {
    img.map(inner =>
      inner.map { case elem: Int =>
        elem + x
      }
    )
  }

  def hglue(img1: Img, img2: Img): Img = {
    img1.concat(img2)
  }

  def vglue(img1: Img, img2: Img): Img = {
    img1.zip(img2).map((inner1) => inner1._1.concat(inner1._2))
  }

  def diag(img: Img): Img = {
    matrix.zipWithIndex.map { case (row, i) =>
      row.zipWithIndex.map { case (value, j) =>
        if (i == j) 1 else value
      }
    }
  }

  val a = List(
    List(0, 0, 1, 0, 0),
    List(0, 1, 0, 1, 0),
    List(0, 1, 1, 1, 0),
    List(1, 0, 0, 0, 1),
    List(1, 0, 0, 0, 1)
  )
  print(show(a))
  println(hFlip(a))
  println(vFlip(a))
  println(invert(a))
  println(cropAt(a, 1, 1, 2, 3))
  println(contrast(1)(a))
  println(hglue(a, a))
  println(vglue(a, a))

}

/*
      0 0 1 0 0
 *     0 1 0 1 0                                           1 0 1
 *     0 1 1 1 0     cropping from 1,1  to  2,3  yields:   1 1 1
 *     1 0 0 0 1
 *     1 0 0 0 1

 */
