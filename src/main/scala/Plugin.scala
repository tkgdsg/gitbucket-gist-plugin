import javax.servlet.ServletContext

import app.GistController
import plugin.PluginRegistry
import util.Version
import java.io.File
import util.Configurations._
import org.scalatra.servlet.ServletApiImplicits._

class Plugin extends plugin.Plugin {
  override val pluginId: String = "gist"
  override val pluginName: String = "Gist Plugin"
  override val description: String = "Provides Gist feature on GitBucket."
  override val versions: List[Version] = List(Version(1, 0))

  override def initialize(context: ServletContext, registry: PluginRegistry): Unit = {
    // Add Snippet link to the header
    registry.addJavaScript(".*",
      """
        |$('a.brand').after($('<span style="float: left; margin-top: 10px;">|&nbsp;&nbsp;&nbsp;&nbsp;<a href="/gist" style="color: black;">Snippet</a></span>'));
      """.stripMargin)

    val rootdir = new File(GistRepoDir)
    if(!rootdir.exists){
      rootdir.mkdirs()
    }

    // Mount controller
    context.mount(new GistController, "/*")

    println("-- Gist plug-in initialized --")
  }

  override def shutdown(context: ServletContext, registry: PluginRegistry): Unit = {
  }
}
