val listOfLists: List[List[Char]] = List(
  List('a', 'p', 'p', 'l', 'e'),
  List('b', 'a', 'n', 'a', 'n', 'a'),
  List('o', 'r', 'a', 'n', 'g', 'e')
)

val listOfStrings: List[String] = listOfLists.map((chars: List[Char]) => chars.mkString)

println(listOfStrings)
