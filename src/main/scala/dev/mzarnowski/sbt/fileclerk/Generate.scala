package dev.mzarnowski.sbt.fileclerk

import java.io.File
import java.nio.file.Files
import sbt.Compile
import sbt.Def
import sbt.Fork
import sbt.Keys.compile
import sbt.Keys.compileIncremental
import sbt.Keys.fullClasspath
import sbt.Task

object Generate {
  def apply(): Def.Initialize[Task[Unit]] =
    Def
      .taskDyn(Def.sequential(Keys.fileGenerators.value.values.map(Generate(_)).toList))
      .triggeredBy(compile in Compile)

  def apply(generator: Generator): Def.Initialize[Task[Unit]] = Def.taskDyn {
    val sourcesNotChanged = !(compileIncremental in Compile).value.hasModified
    val output = Generator.outputOf(generator).value
    val skip = sourcesNotChanged && Files.exists(output)

    if (skip) Def.task(())
    else {
      val arguments = {
        val mainClass = generator.mainClass
        val outputDir = output.toAbsolutePath.toString
        List(mainClass, outputDir, "--") ++ generator.arguments
      }

      Def.task {
        val classpath = (fullClasspath in Compile).value.map(_.data).mkString(File.pathSeparator)
        val forkOpts = sbt.ForkOptions().withRunJVMOptions(Vector("-cp", classpath))
        val fork = new Fork("java", Some(Keys.runnerClass.value))
        fork(forkOpts, arguments)
        ()
      }
    }
  }
}
