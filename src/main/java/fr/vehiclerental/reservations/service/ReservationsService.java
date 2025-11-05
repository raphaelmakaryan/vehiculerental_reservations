package fr.vehiclerental.reservations.service;

import fr.vehiclerental.reservations.entity.Reservations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
//import org.json.JSONArray;
//import org.json.JSONObject;
import java.time.*;

import java.time.LocalDate;

@Service

public class ReservationsService {

    public Object requestClient(int idUser) {
        RestTemplate restTemplate = new RestTemplate();
        String userRequest = "http://localhost:8081/clients/" + idUser;
        Object response = restTemplate.getForObject(userRequest, Object.class);
        if (response != null) {
            return response;
        } else {
            return new Object();
        }
    }

    public Object translateDate(Object data) {
        //JSONArray jsonArray = new JSONArray(data.toString());
        //JSONObject jsonObject = jsonArray.getJSONObject(0);
        //return jsonObject;
    }

    public boolean canReserve(LocalDate birthdayUser, String licenseNumber) {
        if ((LocalDate.now().getYear() - birthdayUser.getYear()) >= 18 && !licenseNumber.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public Object requestVehicle(int idVehicle) {
        RestTemplate restTemplate = new RestTemplate();
        String userRequest = "http://localhost:8082/vehicles/" + idVehicle;
        Object response = restTemplate.getForObject(userRequest, Object.class);
        if (response != null) {
            return response;
        } else {
            return new Object();
        }
    }


    public boolean requestYearClient(LocalDate yearClient, int horsePower) {
        return true;
        /*
        RestTemplate restTemplate = new RestTemplate();
        String userRequest = "http://localhost:8082/vehicles/" + idVehicle;
        Object response = restTemplate.getForObject(userRequest, Object.class);
        if (response != null) {
            return response;
        } else {
            return new Object();
        }
         */
    }



    /*
    public void createClient(String codeAlpha, ClientDao clientDao) {
        Client newClient = new Client();
        newClient.setFirstName("Sarah");
        newClient.setLastName("rrere");
        newClient.setNumber_license(codeAlpha);
        newClient.setObtaining_license(2004);
        newClient.setBirthday(LocalDate.of(2025, 12, 2));
        clientDao.save(newClient);
    }

     */
}