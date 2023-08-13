package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private Long id;
    private LocalDate registrationDate;
    private String username;
    private String firstName;
    private String lastName;
    private Bank currentBank;
    private List<Currency> currency;
    private Integer decimalPlaces;
    private Integer notificationTime;
}
