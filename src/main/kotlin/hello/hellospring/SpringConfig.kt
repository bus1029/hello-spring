package hello.hellospring

import hello.hellospring.repository.*
import hello.hellospring.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.sql.DataSource

@Configuration
class SpringConfig @Autowired constructor(private val em: EntityManager) {

  @Bean
  fun memberService(): MemberService {
    return MemberService(memberRepository())
  }

  @Bean
  fun memberRepository(): MemberRepository {
    return JpaMemberRepository(em)
  }
}