package controllers

import java.time.LocalDate

case class BookForm(isbn: String, startDate: LocalDate, userId: String)

object BookForm {
  def apply(isbn: String, startDate: LocalDate, userId: String): BookForm =
    new BookForm(isbn, startDate, userId)

  def unapply(form: BookForm): Option[(String, LocalDate, String)] =
    Some((form.isbn, form.startDate, form.userId))
}
