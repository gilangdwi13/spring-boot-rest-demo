package com.gilang.demo.repository;

import com.gilang.demo.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT NOW()", nativeQuery = true)
    Optional<Timestamp> sysDate();

    //pakai jpql
    @Query("select a from Attendance a join a.user u where a.user.username = :username AND a.isDelete = 0 ")
    Optional<List<Attendance>> findByUsername(@Param("username") String username);

    //pakai native query
    @Query(value = "select * from attendance a join `user` u on a.user_id = u.user_id " +
            "where u.username = :username and :month = month(check_in)", nativeQuery = true)
    Optional<List<Attendance>> findByUsernameAndMonth(@Param("username") String username, @Param("month") String month);

    @Query(value = "select * from attendance a join `user` u on a.user_id = u.user_id " +
            "where u.username = :username and :month = month(check_in)", nativeQuery = true)
    Page<Attendance> findByUsernameAndMonthPagination(@Param("username") String username, @Param("month") String month, Pageable paging);

    @Query(value = "select * from attendance a where DATE_FORMAT(a.check_in, '%Y-%m-%d') = curdate() and user_id = :userId", nativeQuery = true)
    Optional<Attendance> findAttendanceByTodayCheckIn(@Param("userId") Long userId);
}
