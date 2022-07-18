package com.gilang.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "leaves")
public class Leaves implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "from_date")
    @JsonProperty("from_date")
    private Date fromDate;

    @Column(name = "to_date")
    @JsonProperty("to_date")
    private Date toDate;

    @Column(name = "reason")
    @JsonProperty("reason")
    private String reason;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "is_delete")
    @JsonIgnore
    private Boolean isDelete;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    @JsonProperty("current_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "backup_user_id")
    @JsonProperty("backup_user")
    private User userBackup;
}
