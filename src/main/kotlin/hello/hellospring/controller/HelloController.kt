package hello.hellospring.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {
  @GetMapping("hello")
  fun hello(model: Model): String {
    model.addAttribute("data", "hello!!")
    return "hello"
  }

  @GetMapping("hello-mvc")
  fun helloMvc(@RequestParam("name") name: String, model: Model): String {
    model.addAttribute("name", name)
    return "hello-template"
  }

  @GetMapping("hello-string")
  // HTTP 통신 프로토콜의 Body에 해당 데이터를 직접 넣어주겠다는 의미
  @ResponseBody
  fun helloString(@RequestParam("name") name: String): String {
    return "hello " + name
  }

  @GetMapping("hello-api")
  @ResponseBody
  fun helloApi(@RequestParam("name") name: String): Hello {
    val hello = Hello()
    hello.name = name
    return hello
  }

  // Kotlin은 묵시적으로 static class로 생성됨
  class Hello {
    var name: String = ""
  }
}