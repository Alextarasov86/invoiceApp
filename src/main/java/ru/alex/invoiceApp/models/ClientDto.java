package ru.alex.invoiceApp.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ClientDto {
    @NotEmpty(message = "The First name id required")
    private String firstName;
    @NotEmpty(message = "The Last name id required")
    private String lastName;
    @NotEmpty(message = "The Email id required")
    @Email
    private String email;
    private String phone;
    private String address;
    @NotEmpty(message = "The Status id required")
    private String status;
}
