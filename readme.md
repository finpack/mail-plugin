A Play 2.0 plugin providing a scala wrapper to simple-java-mail.

h1. Installation

h2. As a binary
Checkout the project, build it with "sbt package" command.
Put the jar available in target/scala-2.9.1 to the lib folder of your play app.

h2. As a Git submodule
You can add it as a submodule of your play project.
Checkout the project in modules/mail-plugin.
In your project Build.scala add the dependency to the plugin :

val mailPlugin = Project("mailPlugin", file("modules/mail-plugin"))
val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA)
                .dependsOn(mailPlugin)


h1. Usage

Add a play.plugins file in your conf directory with :
400:play.modules.mail.MailPlugin

Then in your controller, you can do :

import play.modules.mail._
import play.modules.mail.MailBuilder._

def sendMail = Action { request =>
    val m = Mail()
                .from("Bibi","no-reply@bibi.com")
                .to(List(("Toto","toto@bibi.com",To())))
                .subject("A subject")
                .html(views.html.mail())
    MailPlugin.send(m)
    Ok("It works")
}
