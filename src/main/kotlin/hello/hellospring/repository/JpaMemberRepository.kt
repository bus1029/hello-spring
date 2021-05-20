package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

//@Repository
class JpaMemberRepository(private val em: EntityManager): MemberRepository {

  override fun save(member: Member): Member {
    em.persist(member)
    return member
  }

  override fun findById(id: Long): Member? {
    return em.find(Member::class.java, id)
  }

  override fun findByName(name: String): Member? {
    return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
      .setParameter("name", name)
      .resultList
      .firstOrNull()
  }

  override fun findAll(): List<Member> {
    return em.createQuery("select m from Member m", Member::class.java)
      .resultList
  }
}