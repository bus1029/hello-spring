package hello.hellospring.repository

import hello.hellospring.domain.Member

class MemoryMemberRepository : MemberRepository {
  companion object {
    val store: MutableMap<Long, Member> = HashMap()
    var sequence: Long = 0L
  }

  override fun save(member: Member): Member {
    member.id = ++sequence
    store[member.id] = member
    return member
  }

  override fun findById(id: Long): Member? {
    return store[id]
  }

  override fun findByName(name: String): Member? {
    // Get first element that satisfies predicate in find
    return store.values.find { member -> member.name == name }
  }

  override fun findAll(): List<Member> {
    return store.values.toList()
  }

  fun clearStore() {
    store.clear()
  }
}