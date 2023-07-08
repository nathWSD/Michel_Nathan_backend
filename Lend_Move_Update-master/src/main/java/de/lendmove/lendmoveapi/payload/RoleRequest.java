package de.lendmove.lendmoveapi.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleRequest
{
    private Set<String> role;
}
