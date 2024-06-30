package com.be.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    @JsonProperty("uuid")
    private String uuid;

    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Timestamp createdAt;

    @Column(name = "modified_at", nullable = false)
    @JsonProperty("modified_at")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Timestamp modifiedAt;

}
