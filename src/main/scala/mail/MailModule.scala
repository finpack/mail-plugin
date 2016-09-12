package mail

import play.api._
import play.api.inject.{Binding, Module}

/** Play module implementation for mailer
  */
class MailModule(app: Application) extends Module {

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(bind[MailService].to[MailServiceImpl])
  }
}
