package com.github.dropwitch.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 255)
    private String name;

    @Column(name = "created_at")
    @Generated(value = GenerationTime.INSERT)
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime createdAt;

    @Column(name = "updated_at")
    @Generated(value = GenerationTime.ALWAYS)
    @JsonSerialize(using = DateTimeSerializer.class)
    private DateTime updatedAt;

}
