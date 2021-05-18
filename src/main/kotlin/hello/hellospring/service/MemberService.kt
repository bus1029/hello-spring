package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemberRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
class MemberService (private val memberRepository: MemberRepository) {

  /**
   * 회원 가입
   * 같은 이름이 있는 중복 회원은 안됨
   */
  fun join(member: Member): Long {
    validateDuplication(member)

    memberRepository.save(member)
    return member.id
  }

  private fun validateDuplication(member: Member) {
    memberRepository.findByName(member.name)?.also {
      throw IllegalStateException("이미 존재하는 회원입니다.")
    }
  }

  /**
   * 전체 회원 조회
   */
  fun findMembers(): List<Member> {
    return memberRepository.findAll()
  }

  fun findOne(memberId: Long): Member? {
    return memberRepository.findById(memberId)
  }
}