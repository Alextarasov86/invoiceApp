package ru.alex.invoiceApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.invoiceApp.models.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    public Client findByEmail(String email);
}
