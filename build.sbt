name := "fileclerk"
version := "0.0.1-SNAPSHOT"
organization := "dev.mzarnowski.sbt"
organizationHomepage := Some(url("https://github.com/mzarnowski"))

scalaVersion := "2.12.12"

homepage := Some(url("https://github.com/mzarnowski/file-clerk-sbt"))
scmInfo := Some(
  ScmInfo(url("https://github.com/mzarnowski/file-clerk-sbt"), "git@github.com:mzarnowski/file-clerk-sbt.git")
)
developers := List(Developer("mzarnowski", "Marek Żarnowski", "", url("https://github.com/mzarnowski")))
licenses := Seq("MIT license" -> url("https://opensource.org/licenses/MIT"))
publishMavenStyle := true

publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)
credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", "", "") // FIXME don't commit credentials
credentials += Credentials("GnuPG Key ID", "gpg", "", "") // FIXME don't commit credentials

enablePlugins(SbtPlugin)
