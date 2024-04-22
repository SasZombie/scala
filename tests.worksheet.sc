case class Token(word: String, num: Int)

val tokens: List[Token] = List(Token("apple", 1), Token("banana", 2), Token("apple", 3))

val searchString = "apple"
val stringMatches = tokens.exists(_.word == searchString)

if (stringMatches) {
  println(s"A token with the string '$searchString' was found.")
} else {
  println(s"No token with the string '$searchString' was found.")
}