package ru.ir.controllers

import org.scalatra.ScalatraFilter
import scalatags.Text.all
import scalatags.Text.all._

class IndexController extends ScalatraFilter {

  get("/") {
    contentType = "text/html"

    html(
      all.head(
        script(
          "alert('Hello World')"
        )
      ),
      body(
        div(
          h1(id := "title", "This is a title"),
          p("This is a big paragraph of text")
        )
      )
    )
  }

}
