package de.markus.ddd.domain.shared;

import de.markus.ddd.domain.shared.regeln.FilterRegel;
import lombok.Getter;

public class Vorname {

    public static Vorname erstelle(String value) throws ValidierungsFehler {
        if (value == null) {
            return null;
        }
        return new Vorname(value);
    }

    public static final FilterRegel<String> ERSTER_BUCHSTABE_GROSS_REST_KLEIN = new FilterRegel<>(
            pruefObjekt -> pruefObjekt.matches("^[A-Z][a-z]+$"),
            "Der erste Buchstabe muss ein Großbuchstabe sein.");

    @Getter
    private String value;

    private Vorname(String value) throws ValidierungsFehler {
        ValidierungsFehler validierungsFehler = new ValidierungsFehler();
        validierungsFehler.addRegel(ERSTER_BUCHSTABE_GROSS_REST_KLEIN.pruefe(value));
        validierungsFehler.werfenWennGebrocheneValdierungsregelnVorhanden();

        this.value = value;
    }
}
