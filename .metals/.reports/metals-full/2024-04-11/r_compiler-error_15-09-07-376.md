file://<WORKSPACE>/sixthLab.worksheet.sc
### java.lang.NoClassDefFoundError: sourcecode/Name

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.1
Classpath:
<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.1/scala3-library_3-3.3.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.10/scala-library-2.13.10.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-runtime_3/2.5.2/mdoc-runtime_3-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.1/scala3-library_3-3.3.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalameta/mdoc-interfaces/2.5.2/mdoc-interfaces-2.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_3/0.12.0/metaconfig-pprint_3-0.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.1/scala3-library_3-3.3.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.10/scala-library-2.13.10.jar [exists ]
Options:



action parameters:
offset: 2532
uri: file://<WORKSPACE>/sixthLab.worksheet.sc
text:
```scala
object worksheet{
  type Gradebook = List[(String,Int)] 
  val gradebook = List(("G",3), ("F", 10), ("M",6), ("P",4))
  
  
  def increment(g: Gradebook): Gradebook =
    g.map {
      case (name, grade) if grade > 5 => (name, grade + 1)
      case other => other
    }
  
  increment(gradebook)
  
  def average(g: Gradebook): Double = {
    val (sum, count) = g.foldRight((0, 0)) {
      case ((_, grade), (acc, accCount)) => (acc + grade, accCount + 1)
    }
    if (count == 0) 0 else sum.toDouble / count
  }
  
  average(gradebook)
  
  
  def percentage(g: Gradebook): (Double,Double) = 
      val passed = g.filter((name, grade) => if(grade > 5) true else false)
      val passedPercent = passed.size * 100 / g.size
      val failed = 100 - passedPercent
  
      (passedPercent, failed)
      // (pair._1, 2)
  
  percentage(gradebook)
  
  def pass(g: Gradebook): List[String] = 
      val filter = g.filter((name, grade) => if(grade > 5) true else false)
      filter.map {
          case (name, grade) => name
      }
  
  pass(gradebook)
  
  def honorsList(g: Gradebook): List[String] = 
      val filter = g.filter((name, grade) => if(grade > 5) true else false)
      filter.sortBy(-_._2).map(_._1) // - indicates descending. Because it is negativ!!
  
  honorsList(gradebook)
  
  type Name = String
  type Lecture = String
  type ExtGradebook = List[(Name,Lecture,Int)]
  val egradebook = List(("John","FP",4))
  
  
  def atLeastOneFail(g: ExtGradebook): List[Name] = 
      val failed = g.filter((name, subj, grade) => if(grade < 5) true else false)
      val names = failed.map {
          case (name, subect, grade) => name
      }
  
      names.distinct
      
  def groupBy[A,B](l: List[A])(criterion: A => B): List[(B,List[A])] = 
      l.groupBy(criterion).toList.map { case (key, values) => (key, values) }
  
  
  type Str = List[Char]
  type Email = Str
  
  def getNames (l: List[Email]): List[Email] = 
  
     l.map { email =>
  
      val ind = email.indexOf('@')
      if (ind != -1) email.take(ind)
      else email
    }
  
  def removeTLD(l: List[Email], tld: Str): List[Email] = 
      l.filter{
          email =>
          val ending = email.lastIndexOf('.')
          if(ending != -1){
              val domain = email.drop(ending)
              if(domain == tld) 
                  true
              else 
                  false
          }
          else false
      }
  
  def containsDuplicates(l: List[Email]): Boolean = 
      val disctincts = l.distinct
      if(disctincts.size <M@@)
  
}
```



#### Error stacktrace:

```
scala.meta.internal.tokenizers.XmlParser$Xml$.UnpStart(XmlParser.scala:48)
	scala.meta.internal.tokenizers.XmlParser$Xml$.Unparsed(XmlParser.scala:47)
	scala.meta.internal.tokenizers.XmlParser$Xml$.XmlContent(XmlParser.scala:43)
	scala.meta.internal.tokenizers.XmlParser.$anonfun$XmlExpr$1(XmlParser.scala:24)
	scala.meta.shaded.internal.fastparse.internal.RepImpls$.rec$4(RepImpls.scala:226)
	scala.meta.shaded.internal.fastparse.internal.RepImpls$.rep$extension(RepImpls.scala:266)
	scala.meta.shaded.internal.fastparse.package$ByNameOps$.rep$extension(package.scala:202)
	scala.meta.internal.tokenizers.XmlParser.XmlExpr(XmlParser.scala:24)
	scala.meta.internal.tokenizers.LegacyScanner.$anonfun$getXml$2(LegacyScanner.scala:932)
	scala.meta.shaded.internal.fastparse.SharedPackageDefs.parseInputRaw(SharedPackageDefs.scala:69)
	scala.meta.shaded.internal.fastparse.SharedPackageDefs.parseInputRaw$(SharedPackageDefs.scala:45)
	scala.meta.shaded.internal.fastparse.package$.parseInputRaw(package.scala:6)
	scala.meta.shaded.internal.fastparse.Parsed$Extra.trace(Parsed.scala:139)
	scala.meta.shaded.internal.fastparse.Parsed$Extra.traced(Parsed.scala:118)
	scala.meta.internal.tokenizers.LegacyScanner.getXml(LegacyScanner.scala:936)
	scala.meta.internal.tokenizers.LegacyScanner.fetchLT$1(LegacyScanner.scala:295)
	scala.meta.internal.tokenizers.LegacyScanner.fetchToken(LegacyScanner.scala:303)
	scala.meta.internal.tokenizers.LegacyScanner.nextToken(LegacyScanner.scala:211)
	scala.meta.internal.tokenizers.LegacyScanner.foreach(LegacyScanner.scala:1011)
	scala.meta.internal.tokenizers.ScalametaTokenizer.uncachedTokenize(ScalametaTokenizer.scala:24)
	scala.meta.internal.tokenizers.ScalametaTokenizer.$anonfun$tokenize$1(ScalametaTokenizer.scala:17)
	scala.collection.concurrent.TrieMap.getOrElseUpdate(TrieMap.scala:962)
	scala.meta.internal.tokenizers.ScalametaTokenizer.tokenize(ScalametaTokenizer.scala:17)
	scala.meta.internal.tokenizers.ScalametaTokenizer$$anon$2.apply(ScalametaTokenizer.scala:332)
	scala.meta.tokenizers.Api$XtensionTokenizeDialectInput.tokenize(Api.scala:25)
	scala.meta.tokenizers.Api$XtensionTokenizeInputLike.tokenize(Api.scala:14)
	scala.meta.internal.pc.ScriptFirstImportPosition$.tokenize(ScriptFirstImportPosition.scala:112)
	scala.meta.internal.pc.ScriptFirstImportPosition$.skipPrefixesOffset(ScriptFirstImportPosition.scala:62)
	scala.meta.internal.pc.ScriptFirstImportPosition$.skipUsingDirectivesOffset(ScriptFirstImportPosition.scala:59)
	scala.meta.internal.pc.AutoImports$.forScalaSource$1$$anonfun$1(AutoImports.scala:324)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.pc.AutoImports$.forScalaSource$1(AutoImports.scala:334)
	scala.meta.internal.pc.AutoImports$.autoImportPosition$$anonfun$1(AutoImports.scala:376)
	scala.Option.orElse(Option.scala:477)
	scala.meta.internal.pc.AutoImports$.autoImportPosition(AutoImports.scala:376)
	scala.meta.internal.pc.AutoImports$.generator(AutoImports.scala:98)
	scala.meta.internal.pc.completions.CompletionProvider.completions(CompletionProvider.scala:70)
	scala.meta.internal.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

java.lang.NoClassDefFoundError: sourcecode/Name