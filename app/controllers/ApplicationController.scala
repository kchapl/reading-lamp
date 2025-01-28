package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesProvider}
import models.Book
import scala.concurrent.ExecutionContext

@Singleton
class ApplicationController @Inject() (
    cc: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc)
    with I18nSupport {

  val bookForm: Form[BookForm] = Form(
    mapping(
      "isbn"      -> nonEmptyText,
      "startDate" -> localDate,
      "userId"    -> nonEmptyText
    )(BookForm.apply)(BookForm.unapply)
  )

  def index = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.index(bookForm, Seq.empty[Book]))
  }

  def submit = Action { implicit request: MessagesRequest[AnyContent] =>
    bookForm
      .bindFromRequest()
      .fold(
        formWithErrors => {
          BadRequest(views.html.index(formWithErrors, Seq.empty[Book]))
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
