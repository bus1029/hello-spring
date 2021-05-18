package hello.hellospring.domain

import javax.persistence.*

@Entity
class Member(var name: String = "") {
  @Id
  // DB 가 알아서 생성해주려면 => IDENTITY
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0
}