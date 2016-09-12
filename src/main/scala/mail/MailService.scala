package mail

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import org.codemonkey.simplejavamail.Mailer
import play.api.{Configuration, _}
import play.api.inject.ApplicationLifecycle

/**
  * Created by rwadowski on 8/31/16.
  */

/**
  * Implementation of mail service of mailer module
  * Recognized configuration properties (to be set in `application.conf`):
  *  - '''mail.mock''':     creates mocked mailer actor on startup and omits any other settings if set to `true`
  *  - '''smtp.host''':     hostname of smtp server to be used, defaults to `localhost`
  *  - '''smtp.port''':     port of smtp server to be used, defaults to `25`
  *  - '''smtp.username''': username to be used when accessing smtp server, defaults to empty string
  *  - '''smtp.password''': password to be used when accessing smtp server, defaults to empty string
  */

trait MailService {
}

@Singleton
class MailServiceImpl @Inject()(configuration: Configuration, lifecycle: ApplicationLifecycle)(implicit val ac: ActorSystem) extends MailService {

  private val DEFAULT_HOST = "localhost"
  private val DEFAULT_PORT = 25

  private lazy val mock = configuration.getBoolean("mail.mock") getOrElse false
  private lazy val host = configuration.getString("smtp.host") getOrElse DEFAULT_HOST
  private lazy val port = configuration.getInt("smtp.port") getOrElse DEFAULT_PORT
  private lazy val username = configuration.getString("smtp.username") getOrElse ""
  private lazy val password = configuration.getString("smtp.password") getOrElse ""

  private val mailer = new Mailer(host, port, username, password)

  Logger.debug("Mail plugin starting... ")
  if(mock) {
    MailActor.startWithMock
    Logger.info("Mail plugin successfully started using mocked mailer")
  }
  else {
    MailActor.startWith(mailer)
    Logger.info("Mail plugin successfully started with smtp server on %s:%s".format(host, port))
  }
}
