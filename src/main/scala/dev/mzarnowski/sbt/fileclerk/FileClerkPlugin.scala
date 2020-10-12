package dev.mzarnowski.sbt.fileclerk

import sbt.AutoPlugin
import sbt.Def
import sbt._

object FileClerkPlugin extends AutoPlugin {

  private val pluginSettings = Seq(
    Keys.version := "0.0.4",
    Keys.runnerClass := "dev.mzarnowski.infra.fs.FileClerkCLI",
    Keys.outputRoot := sbt.Keys.crossTarget.value.toPath.resolve("file-clerk"),
    Keys.fileGenerators := Map.empty,
    Keys.output := Generator.outputs().value,
    Keys.generateFiles := Generate.apply().value,
    sbt.Keys.libraryDependencies += "dev.mzarnowski.infra.fs" % "fileclerk" % Keys.version.value
  )

  override def projectSettings: Seq[Def.Setting[_]] = pluginSettings
}
