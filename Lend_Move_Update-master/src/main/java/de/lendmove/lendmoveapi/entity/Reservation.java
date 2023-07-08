package de.lendmove.lendmoveapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "reservations")
@DynamicUpdate
@Getter
@Setter
@ToString
public class Reservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationDateStart;

    private String reservationDateEnd;

    private String reservationTimeStart;

    private String reservationTimeEnd;

    private String status;

    private Long carId;

    private String username;

    private String stationStart;

    private String stationEnd;

    private String price;

    private String tarif;

    private boolean  available = true;
    private String image;

    public Reservation()
    {

    }

    public Reservation(String reservationDateStart, String reservationDateEnd, String reservationTimeStart, String reservationTimeEnd,
                       String reservationStat, Long carId, String username, String stationStart,
                       String stationEnd, String tarif, String price, String image) {
        this.reservationDateStart = reservationDateStart;
        this.reservationDateEnd = reservationDateEnd;
        this.reservationTimeStart = reservationTimeStart;
        this.reservationTimeEnd = reservationTimeEnd;
        this.status = reservationStat;
        this.carId = carId;
        this.username = username;
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.tarif = tarif;
        this.price = price;
        this.image = image;
    }




    public Reservation(String reservationDateStart, String reservationDateEnd, String reservationTimeStart, String reservationTimeEnd,
                       String reservationStat, Long carId, String username, String stationStart,
                       String stationEnd, boolean available, String tarif, String price, String image) {
        this.reservationDateStart = reservationDateStart;
        this.reservationDateEnd = reservationDateEnd;
        this.reservationTimeStart = reservationTimeStart;
        this.reservationTimeEnd = reservationTimeEnd;
        this.status = reservationStat;
        this.carId = carId;
        this.username = username;
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.available = available;
        this.tarif = tarif;
        this.price = price;
        this.image = image;
    }
}
