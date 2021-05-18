package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.sql.DataSource

@Repository
// 생성자가 하나면 @AutoWired 생략 가능
class JdbcTemplateMemberRepository @Autowired constructor(dataSource: DataSource) : MemberRepository {
  private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

  override fun save(member: Member): Member {
    // 자동으로 Query 를 작성해줌
    val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
    jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id")
    val parameters = HashMap<String, Any>()
    parameters["name"] = member.name

    val key = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters))
    member.id = key.toLong()
    return member
  }

  override fun findById(id: Long): Member? {
    val result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id)
    return result.find { it.id == id }
  }

  override fun findByName(name: String): Member? {
    val result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name)
    return result.find { it.name == name }
  }

  override fun findAll(): List<Member> {
    return jdbcTemplate.query("select * from member", memberRowMapper())
  }

  // Callback Lambda
  private fun memberRowMapper(): RowMapper<Member> {
    return RowMapper { rs: ResultSet, rowNum: Int ->
      val member = Member(rs.getString("name"))
      member.id = rs.getLong("id")
      member
    }
  }
}