package com.gilang.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;

    @Column(name = "username")
    @Size(max = 200)
    @JsonProperty("username")
    private String username;

    @Column(name = "password")
    @Size(max = 200)
    @JsonIgnore
    private String password;

    @Column(name = "role")
    @Size(max = 200)
    @JsonProperty("role")
    private String role;

    @Column(name = "name")
    @Size(max = 100)
    @JsonProperty("name")
    private String name;

    @Column(name = "is_delete")
    @JsonProperty("is_delete")
    private Boolean isDelete;
}
