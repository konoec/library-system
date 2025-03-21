package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.librarysystem.entities.Client;
import pe.edu.utp.librarysystem.entities.Employee;
import pe.edu.utp.librarysystem.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> listClients() {
        return clientRepository.findByActiveTrue();
    }

    public void updateClient(Long id, Client client) {
        Client clientToUpdate = clientRepository.findById(id).orElse(null);
        if (clientToUpdate != null) {
            clientToUpdate.setFullName(client.getFullName());
            clientToUpdate.setPhone(client.getPhone());
            clientToUpdate.setEmail(client.getEmail());
            clientToUpdate.setAddress(client.getAddress());
            clientRepository.save(clientToUpdate);
        }
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).get();
    }

    public void deleteClient(Long id) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
        client.setActive(false);
        clientRepository.save(client);
    }

    public List<Client> searchClients(String fullName) {
        return clientRepository.findByFullNameContainingIgnoreCaseAndActiveTrue(fullName);
    }
}
