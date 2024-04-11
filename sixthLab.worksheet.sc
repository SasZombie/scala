import java.util.HashSet
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
    if(disctincts.size != l.size) true
    else false

def countDuplicates(l: List[Email]): Int = 
    val groupedByEmail = l.groupBy { email =>
    val atIndex = email.indexOf('@')
    if (atIndex != -1) email.take(atIndex).mkString 
    else email.mkString // Daca @ nu e prezent
    }
  
    groupedByEmail.values.count(_.size > 1)

def extractDuplicates(l: List[Email]): List[List[Email]] = 
    val groupedByEmail = l.groupBy { email =>
    val atIndex = email.indexOf('@')
    if (atIndex != -1) email.take(atIndex).mkString 
    else email.mkString // Daca @ nu e prezent
    }
  
    groupedByEmail.filter(_._2.size > 1).values.toList


//Why would We do this???
def deleteDuplicates(l: List[Email]): List[Email] = 
    val toDelete = extractDuplicates(l)
    l.filterNot(toDelete.toList.contains)
