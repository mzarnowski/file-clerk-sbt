package dev.mzarnowski.sbt.fileclerk

import java.nio.file.Path
import sbt.SettingKey
import sbt.TaskKey

object Keys {
  val version: SettingKey[String] = SettingKey("fileClerkVersion")
  val runnerClass: SettingKey[String] = SettingKey("fileClerkRunner")
  val outputRoot: SettingKey[Path] = SettingKey("fileClerkOutputRoot")

  val fileGenerators: SettingKey[Map[Generator.Key, Generator]] = SettingKey("fileGenerators")
  val generateFiles: TaskKey[Unit] = TaskKey("generateFiles")

  val output: TaskKey[Map[Generator.Key, Path]] = TaskKey("fileClerkOutputs")
}
