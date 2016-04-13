package com.github.dropwitch.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "master_common")
public class MasterCommon {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "key", nullable = false, length = 255)
    private String key;

    @Basic
    @Column(name = "value", nullable = true, length = -1)
    private String value;

    @Basic
    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using=DateTimeSerializer.class)
    private DateTime createdAt;

    @Basic
    @Column(name = "updated_at", nullable = false)
    @JsonSerialize(using=DateTimeSerializer.class)
    private DateTime updatedAt;
}
