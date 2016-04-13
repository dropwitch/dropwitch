package com.github.dropwitch.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "master_common")
public class MasterCommon {
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "key")
    @NotNull
    @Size(max = 255)
    private String key;

    @Basic
    @Column(name = "value")
    @Null
    private String value;

    @Basic
    @Column(name = "created_at")
    @NotNull
    @JsonSerialize(using=DateTimeSerializer.class)
    private DateTime createdAt;

    @Basic
    @Column(name = "updated_at")
    @NotNull
    @JsonSerialize(using=DateTimeSerializer.class)
    private DateTime updatedAt;
}
