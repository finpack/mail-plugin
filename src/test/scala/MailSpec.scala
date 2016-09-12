import javax.mail.Message.RecipientType

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import mail.Mail._
import mail._
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import org.specs2.execute.Success
import org.specs2.matcher.Matchers
import play.api.libs.MimeTypes
import play.api.test.Helpers._
import play.api.test._

import scala.io.Source

class MailSpec extends TestKit(ActorSystem("users")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Mail" must {
    "send dummy email using mock" in {
      running(FakeApplication(additionalConfiguration = Map("mail.mock" -> "true"))) {
        val mailActor = MailActor.startWithMock(system)
        val attachment = Source.fromBytes("Ninja should wear black".toCharArray.map(_.toByte))
        val expected = new org.codemonkey.simplejavamail.Email()
        expected.setFromAddress("sender", "sender@example.com")
        expected.setSubject("A subject")
        expected.setReplyToAddress("ninja master", "master@ninja.com")
        expected.addRecipient("receiver", "receiver@example.com", RecipientType.TO)
        expected.setText("body")
        expected.addAttachment("ninja code", attachment.map(_.toByte).toArray, MimeTypes.forExtension("txt").get)
        Mail()
          .from("sender", "sender@example.com")
          .to("receiver", "receiver@example.com")
          .replyTo("ninja master", "master@ninja.com")
          .withSubject("A subject")
          .withText("body")
          .withAttachments(Attachment("ninja code", attachment, MimeTypes.forExtension("txt").get))
          .send()(system)

        Success
      }
    }
  }
}
