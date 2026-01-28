package com.project.managesales.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.managesales.enums.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn
    private Long id;

    @Pattern(
            regexp = "^[A-Za-z]{6}$",
            message = "Must contain exactly 6 letters"
    )
    private String customerCode;

    @Size(min = 3, max = 50,
        message = "Name must be at least 3 letters long and maximum 50 letters"
    )
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}
