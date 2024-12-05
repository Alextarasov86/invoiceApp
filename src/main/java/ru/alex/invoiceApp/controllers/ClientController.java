package ru.alex.invoiceApp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alex.invoiceApp.models.Client;
import ru.alex.invoiceApp.models.ClientDto;
import ru.alex.invoiceApp.repositories.ClientRepository;
import ru.alex.invoiceApp.services.ClientService;

import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping({"", "/"})
    public String getClients(Model model) {
        var clients = clientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/create")
    public String createClient(Model model) {
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "create";
    }

    @PostMapping("/create")
    public String createClient(@Valid @ModelAttribute ClientDto clientDto, BindingResult bindingResult) {
        if(clientRepository.findByEmail(clientDto.getEmail()) != null) {
            bindingResult.addError(new FieldError("clientDto", "email", clientDto.getEmail(),
                    false, null, null, "Email already used"));
        }
        if(bindingResult.hasErrors()) {
            return "create";
        }

        Client client = new Client();
        client.setEmail(clientDto.getEmail());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());
        client.setStatus(clientDto.getStatus());
        client.setCreatedAt(new Date());
        clientRepository.save(client);

        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String editClient(Model model, @RequestParam int id) {
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null) {
            return "redirect:/clients";
        }

        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setAddress(client.getAddress());
        clientDto.setPhone(client.getPhone());
        clientDto.setStatus(client.getStatus());
        clientDto.setEmail(client.getEmail());

        model.addAttribute("clientDto", clientDto);
        model.addAttribute("client", client);
        return "edit";
    }

    @PostMapping("/edit")
    public String editClient(Model model, @Valid @ModelAttribute ClientDto clientDto,
                             BindingResult bindingResult, @RequestParam int id) {
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client);

        if(bindingResult.hasErrors()) {
            return "edit";
        }

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());
        client.setStatus(clientDto.getStatus());
        client.setEmail(clientDto.getEmail());

        try{
            clientRepository.save(client);
        }
        catch(Exception e) {
            bindingResult.addError(
                    new FieldError("clientDto", "email", clientDto.getEmail(),
                            false, null, null, "Email already used")
            );
            return "redirect:/clients";
        }

        return "redirect:/clients";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam int id) {
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null) {
            return "redirect:/clients";
        }
        clientRepository.delete(client);
        return "redirect:/clients";
    }
}
