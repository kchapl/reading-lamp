package models

import java.time.LocalDate

case class Book(
  isbn: String,
  startDate: LocalDate,
  userId: String
)
