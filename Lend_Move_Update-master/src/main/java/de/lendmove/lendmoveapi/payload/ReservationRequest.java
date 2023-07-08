package de.lendmove.lendmoveapi.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest
{
    public Long id;

    // wenn ein Mitarbeiter die Reservierung einen User machen möchtet.
    public String username;


}
