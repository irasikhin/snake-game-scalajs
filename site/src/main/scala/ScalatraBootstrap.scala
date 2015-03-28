import javax.servlet.ServletContext

import org.scalatra.LifeCycle
import ru.ir.controllers.IndexController

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {
    context.mount(new IndexController, "/")
  }

}
