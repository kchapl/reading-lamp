package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleTokenResponse}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import scala.concurrent.{ExecutionContext, Future}
import models.User
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AuthController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  private val jsonFactory = JacksonFactory.getDefaultInstance
  private val httpTransport = GoogleNetHttpTransport.newTrustedTransport

  private val clientId = sys.env.getOrElse("GOOGLE_CLIENT_ID", "your_google_client_id")
  private val clientSecret = sys.env.getOrElse("GOOGLE_CLIENT_SECRET", "your_google_client_secret")
  private val redirectUri = sys.env.getOrElse("GOOGLE_REDIRECT_URI", "http://localhost:9000/authCallback")

  private val flow = new GoogleAuthorizationCodeFlow.Builder(
    httpTransport,
    jsonFactory,
    clientId,
    clientSecret,
    java.util.Collections.singleton("profile")
  ).build()

  def login = Action { implicit request: Request[AnyContent] =>
    val url = flow.newAuthorizationUrl().setRedirectUri(redirectUri).build()
    Redirect(url)
  }

  def authCallback = Action.async { implicit request: Request[AnyContent] =>
    val codeOpt = request.getQueryString("code")
    codeOpt match {
      case Some(code) =>
        val tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute()
        val credential = flow.createAndStoreCredential(tokenResponse, null)
        val userInfo = getUserInfo(credential.getAccessToken)
        userInfo.map { user =>
          // Save user information to the database
          // For now, just print it to the console
          println(s"User info: $user")
          Redirect(routes.ApplicationController.index).withSession("userId" -> user.id)
        }.recover {
          case ex: Exception =>
            Redirect(routes.ApplicationController.index).flashing("error" -> "Authentication failed")
        }
      case None =>
        Future.successful(Redirect(routes.ApplicationController.index).flashing("error" -> "Authentication failed"))
    }
  }

  def logout = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.ApplicationController.index).withNewSession.flashing("success" -> "You've been logged out")
  }

  private def getUserInfo(accessToken: String): Future[User] = {
    // Fetch user information from Google using the access token
    // For now, return a dummy user
    Future.successful(User("dummyId", "Dummy User", "dummy@example.com"))
  }
}
