package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{Book, User}
import scala.concurrent.ExecutionContext

@Singleton
class ApplicationController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val bookForm: Form[BookForm] = Form(
    mapping(
      "isbn" -> nonEmptyText,
      "startDate" -> localDate,
      "userId" -> nonEmptyText
    )(BookForm.apply)(BookForm.unapply)
  )

  def index = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(bookForm))
  }

  def submit = Action { implicit request: Request[AnyContent] =>
    bookForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      bookData => {
        // Save the book data to the database
        // For now, just print it to the console
        println(s"Book data: $bookData")
        Redirect(routes.ApplicationController.index).flashing("success" -> "Book added!")
      }
    )
  }
}

case class BookForm(isbn: String, startDate: java.time.LocalDate, userId: String)
