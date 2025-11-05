package fr.vehiclerental.reservations.service;

import fr.vehiclerental.reservations.entity.Client;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class ClientService {
    public String createCodeAlphanumeric() {
        Random rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz123567810";
        String result = "";
        int longueur = alphabet.length();
        for (int i = 0; i < 9; i++) {
            int k = rand.nextInt(longueur);
            result += alphabet.charAt(k);
        }
        return result;
    }

    public void createClient(String codeAlpha, ClientDao clientDao) {
        Client newClient = new Client();
        newClient.setFirstName("Sarah");
        newClient.setLastName("rrere");
        newClient.setNumber_license(codeAlpha);
        newClient.setObtaining_license(2004);
        newClient.setBirthday(LocalDate.of(2025, 12, 2));
        clientDao.save(newClient);
    }
}
