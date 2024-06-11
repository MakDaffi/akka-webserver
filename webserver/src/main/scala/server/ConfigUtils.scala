package server

import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}

object ConfigUtils {
  private lazy val conf: Config = ConfigFactory.load("application.conf")

  def config(): Config = conf
}