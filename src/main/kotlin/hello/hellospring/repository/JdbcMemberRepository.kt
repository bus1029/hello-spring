package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.stereotype.Repository
import java.sql.*
import javax.sql.DataSource

//@Repository
class JdbcMemberRepository(private val dataSource: DataSource): MemberRepository {

  override fun save(member: Member): Member {
    val sql = "insert into member(name) values(?)"
    val con: Connection? = getConnection()
    val pstmt = con!!.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    var rs: ResultSet? = null
    try {
      pstmt.setString(1, member.name)
      pstmt.executeUpdate()
      rs = pstmt.generatedKeys

      if (rs.next()) {
        member.id = rs.getLong(1)
      } else {
        throw SQLException("id 조회 실패")
      }

      return member
    } catch (e: SQLException) {
      throw IllegalStateException(e)
    } finally {
      close(con, pstmt, rs)
    }

  }

  override fun findById(id: Long): Member? {
    val sql = "select * from member where id = ?"
    var conn: Connection? = null
    var pstmt: PreparedStatement? = null
    var rs: ResultSet? = null
    try {
      conn = getConnection()
      pstmt = conn!!.prepareStatement(sql)
      pstmt.setLong(1, id)
      rs = pstmt.executeQuery()
      return if (rs.next()) {
        val member = Member(rs.getString("name"))
        member.id = rs.getLong("id")
        member
      } else {
        null
      }
    } catch (e: Exception) {
      throw IllegalStateException(e)
    } finally {
      close(conn, pstmt, rs)
    }
  }

  override fun findByName(name: String): Member? {
    val sql = "select * from member where name = ?"
    var conn: Connection? = null
    var pstmt: PreparedStatement? = null
    var rs: ResultSet? = null

    return try {
      conn = getConnection()
      pstmt = conn!!.prepareStatement(sql)
      pstmt.setString(1, name)
      rs = pstmt.executeQuery()
      if (rs.next()) {
        val member = Member(rs.getString("name"))
        member.id = rs.getLong("id")
        return member
      }
      null
    } catch (e: java.lang.Exception) {
      throw IllegalStateException(e)
    } finally {
      close(conn, pstmt, rs)
    }
  }

  override fun findAll(): List<Member> {
    val sql = "select * from member"
    var conn: Connection? = null
    var pstmt: PreparedStatement? = null
    var rs: ResultSet? = null

    return try {
      conn = getConnection()
      pstmt = conn!!.prepareStatement(sql)
      rs = pstmt.executeQuery()
      val members: MutableList<Member> = ArrayList()
      while (rs.next()) {
        val member = Member(rs.getString("name"))
        member.id = rs.getLong("id")
        members.add(member)
      }
      members
    } catch (e: java.lang.Exception) {
      throw IllegalStateException(e)
    } finally {
      close(conn, pstmt, rs)
    }
  }

  private fun getConnection(): Connection? {
    return DataSourceUtils.getConnection(dataSource)
  }

  private fun close(conn: Connection?, pstmt: PreparedStatement?, rs: ResultSet?) {
    try {
      rs?.let {
        rs.close()
      }
    } catch (e: SQLException) {
      e.printStackTrace()
    }
    try {
      pstmt?.let {
        pstmt.close()
      }
    } catch (e: SQLException) {
      e.printStackTrace()
    }
    try {
      conn?.let {
        close(conn)
      }
    } catch (e: SQLException) {
      e.printStackTrace()
    }
  }

  @Throws(SQLException::class)
  private fun close(conn: Connection) {
    DataSourceUtils.releaseConnection(conn, dataSource)
  }
}