package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.JdbcMemberRepository
import hello.hellospring.repository.MemberRepository
import hello.hellospring.repository.MemoryMemberRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import java.lang.IllegalStateException

@SpringBootTest
// Test 케이스에 Transactional 을 달아두면 Test 가 끝났을 때 Rollback 을 해줌
@Transactional
class MemberServiceIntegrationTest {
  @Autowired private lateinit var memberService: MemberService
  @Autowired private lateinit var memberRepository: MemberRepository

  @Test
  // Test 는 반복할 수 있어야 함
  fun join() {
    // given
    val member = Member("hello")

    // when
    val saveId = memberService.join(member)

    // then
    memberService.findOne(saveId)?.let {
      assertThat(member.name).isEqualTo(it.name)
    }
  }

  @Test
  fun 중복_회원_예외() {
    val member = Member("spring")
    memberService.join(member)

    val member1 = Member("spring")
    val throwMessage = assertThrows(IllegalStateException::class.java) {
      memberService.join(member1)
    }

    assertThat(throwMessage.message).isEqualTo("이미 존재하는 회원입니다.")
  }
}