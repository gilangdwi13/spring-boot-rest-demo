package com.gilang.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "check_in")
    @JsonProperty("check_in")
    private Timestamp checkIn;

    @Column(name = "check_out")
    @JsonProperty("check_out")
    private Timestamp checkOut;

    @Column(name = "approved_by")
    @Size(max = 200)
    @JsonProperty("approved_by")
    private String approvedBy;

    @Column(name = "approved_at")
    @JsonProperty("approved_at")
    private Timestamp approvedAt;

    @Column(name = "status")
    @Size(max = 100)
    @JsonProperty("status")
    private String status;

    @Column(name = "is_delete")
    @JsonIgnore
    private Boolean isDelete;

    //kalau mau ada kolom user id, kolom join harus apply insertable = false, updatable = false
    /*@Column(name = "user_id")
    @JsonIgnore
    private Long userId;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
