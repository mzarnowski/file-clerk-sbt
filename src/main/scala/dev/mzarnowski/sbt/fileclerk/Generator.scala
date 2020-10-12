package dev.mzarnowski.sbt.fileclerk

import java.nio.file.Path
import java.security.MessageDigest
import sbt.Def
import sbt.Task

abstract class Generator(val mainClass: String) {
  def arguments: Array[String] = Array.empty
}

object Generator {
  trait Key

  def outputOf(generator: Generator): Def.Initialize[Task[Path]] = Def.task {
    val input = {
      val mainClass = generator.mainClass
      val arguments = generator.arguments
      s"$mainClass ${arguments.mkString(" ")}"
    }
    val bytes = MessageDigest.getInstance("MD5").digest(input.getBytes)
    val hash: String = bytes.map { "%02x".format(_) }.mkString
    Keys.outputRoot.value.resolve(hash)
  }

  def outputs(): Def.Initialize[Task[Map[Key, Path]]] = Def.taskDyn {
    Keys.fileGenerators.value
      .map { case (k, g) => (k, outputOf(g)) }
      .foldLeft(Def.task(Map.empty[Key, Path])) {
        case (acc, (key, output)) => Def.taskDyn { acc.map(_ + (key -> output.value)) }
      }
  }
}
