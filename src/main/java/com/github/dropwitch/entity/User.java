package com.github.dropwitch.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    @NotNull
    @Size(max = 255)
    private String name;

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
