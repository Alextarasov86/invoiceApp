package ru.alex.invoiceApp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.alex.invoiceApp.models.Client;
import ru.alex.invoiceApp.repositories.ClientRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    // получаем список продуктов
//    public List<Client> listClients(String email) {
//        if(email != null) return clientRepository.findByEmail(email);
//        return clientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//    }

}
