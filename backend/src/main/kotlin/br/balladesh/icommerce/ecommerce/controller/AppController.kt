package br.balladesh.icommerce.ecommerce.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
  //  @RequestMapping(value = "/", produces = "text/html")
  //  public String home() {
  //    return "index";
  //  }
  @RequestMapping(value = ["/**/{path:[^.]*}"])
  fun redirect(): String {
    return "forward:/"
  }
}
