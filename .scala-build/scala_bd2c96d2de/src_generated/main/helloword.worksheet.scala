

final class helloword$u002Eworksheet$_ {
def args = helloword$u002Eworksheet_sc.args$
def scriptPath = """helloword.worksheet.sc"""
/*<script>*/
println("Hello, World!")
println("Meow")
val x = 0
/*</script>*/ /*<generated>*//*</generated>*/
}

object helloword$u002Eworksheet_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new helloword$u002Eworksheet$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export helloword$u002Eworksheet_sc.script as `helloword.worksheet`

