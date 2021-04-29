package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class MemoryMemberRepositoryTest {
  private val repository = MemoryMemberRepository()

  // 매 테스트가 끝날때 마다 호출됨
  @AfterEach
  fun afterEach() {
    repository.clearStore()
  }

  @Test
  fun save() {
    val member = Member("spring")
    repository.save(member)
    val result = repository.findById(member.id)
    assertThat(member).isEqualTo(result)
  }

  @Test
  fun findByName() {
    val member = Member("spring1")
    repository.save(member)

    val member2 = Member("spring1")
    repository.save(member2)

    val result = repository.findByName("spring1")
    assertThat(result).isEqualTo(member)
  }

  @Test
  fun findAll() {
    val member = Member("spring1")
    repository.save(member)

    val member2 = Member("spring1")
    repository.save(member2)

    val result = repository.findAll()
    assertThat(result.size).isEqualTo(2)
  }
}