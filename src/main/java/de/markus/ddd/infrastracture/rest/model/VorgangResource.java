package de.markus.ddd.infrastracture.rest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VorgangResource {
    private String identifier;
    private String vorname;
    private String nachname;
    private String geburtstag;
}
