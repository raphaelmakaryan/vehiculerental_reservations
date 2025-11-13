package fr.vehiclerental.reservations.entity;


public class MaintenanceDTO {
    private Integer id;
    private int idVehicule;
    private int idUnavailability;

    public MaintenanceDTO(Integer id, int idVehicule, int idUnavailability) {
        this.id = id;
        this.idVehicule = idVehicule;
        this.idUnavailability = idUnavailability;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public int getIdUnavailability() {
        return idUnavailability;
    }

    public void setIdUnavailability(int idUnavailability) {
        this.idUnavailability = idUnavailability;
    }

    @Override
    public String toString() {
        return "MaintenanceDTO{" +
                "id=" + id +
                ", idVehicule=" + idVehicule +
                ", idUnavailability=" + idUnavailability +
                '}';
    }
}
