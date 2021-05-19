package hello.hellospring.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class TimeTraceAop {
  // 어디에다가 적용할거야?
  // hello.hellospring 패키지 하위에 다 적용
  @Around("execution(* hello.hellospring..*(..))")
  fun execute(joinPoint: ProceedingJoinPoint): Any? {
    val start = System.currentTimeMillis()
    println("START: $joinPoint")
    try {
      return joinPoint.proceed()
    } finally {
      val finish = System.currentTimeMillis()
      val timeMs = finish - start
      println("END: $joinPoint ${timeMs}ms")
    }
  }
}