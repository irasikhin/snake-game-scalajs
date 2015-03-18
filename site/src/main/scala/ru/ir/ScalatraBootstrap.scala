package ru.ir

import org.scalatra.LifeCycle
import javax.servlet.ServletContext

import ru.ir.controllers.IndexController

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {
    context.mount(new IndexController, "/")
  }

}
