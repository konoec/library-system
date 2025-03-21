package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.repository.CredentialRepository;

import java.util.List;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificador de contraseñas

    public Credential authenticateUser(String username, String password) {
        Credential credential = credentialRepository.findByUsername(username);
        if (credential != null && passwordEncoder.matches(password, credential.getPassword())) {
            return credential;
        }
        return null;
    }

    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public void saveCredential(Credential credential) {
        String encodedPassword = encodePassword(credential.getPassword());
        credential.setPassword(encodedPassword);
        credentialRepository.save(credential);
    }

    public List<Credential> listCredentials() {
        return credentialRepository.findAll();
    }

    public void updateCredential(Long id, Credential credential) {
        Credential credentialToUpdate = credentialRepository.findById(id).orElse(null);
        if (credentialToUpdate != null) {
            credentialToUpdate.setUsername(credential.getUsername());

            String encodedPassword = encodePassword(credential.getPassword());
            credential.setPassword(encodedPassword);

            credentialToUpdate.setPassword(credential.getPassword());


            credentialToUpdate.setEmployee(credential.getEmployee());
            credentialToUpdate.setRole(credential.getRole());
            credentialRepository.save(credentialToUpdate);
        }
    }

    public Credential findById(Long id) {
        return credentialRepository.findById(id).get();
    }

    public void deleteCredential(Long id) {
        credentialRepository.deleteById(id);
    }

    public List<Credential> searchCredentials(String username) {
        return credentialRepository.findByUsernameContainingIgnoreCase(username);
    }
}
