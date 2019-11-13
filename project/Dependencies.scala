import sbt._

object Dependencies {
  // Library Versions
  val catsVersion       = "2.0.0"
  val catsEffectVersion = "2.0.0"
  val doodleVersion     = "0.9.11"
  val miniTestVersion   = "2.7.0"
  val scalaCheckVersion = "1.14.0"

  // Libraries
  val catsCore     = "org.typelevel"     %% "cats-core"     % catsVersion
  val catsEffect   = "org.typelevel"     %% "cats-effect"   % catsEffectVersion
  val doodle       = "org.creativescala" %% "doodle"        % doodleVersion
  val miniTest     = "io.monix"          %% "minitest"      % miniTestVersion   % "test"
  val miniTestLaws = "io.monix"          %% "minitest-laws" % miniTestVersion   % "test"
  val scalaCheck   = "org.scalacheck"    %% "scalacheck"    % scalaCheckVersion % "test"
}
