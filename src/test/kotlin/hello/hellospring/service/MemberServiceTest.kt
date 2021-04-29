package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemoryMemberRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.lang.IllegalStateException

class MemberServiceTest {
  private lateinit var memberService: MemberService
  private lateinit var memberRepository: MemoryMemberRepository

  @BeforeEach
  fun init() {
    memberRepository = MemoryMemberRepository()
    // Dependency Injection
    memberService = MemberService(memberRepository)
  }

  @AfterEach
  fun cleanStore() {
    memberRepository.clearStore()
  }

  @Test
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

  @Test
  fun findMembers() {
  }

  @Test
  fun findOne() {
  }
}