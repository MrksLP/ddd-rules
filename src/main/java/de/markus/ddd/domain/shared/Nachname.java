package de.markus.ddd.domain.shared;

import de.markus.ddd.domain.shared.regeln.FilterRegel;
import lombok.Getter;

public class Nachname {

    public static Nachname erstelle(String value) throws ValidierungsFehler {
        if (value == null) {
            return null;
        }
        return new Nachname(value);
    }

    public static final FilterRegel<String> ERSTER_BUCHSTABE_GROSS_REST_KLEIN = new FilterRegel<>(
            pruefObjekt -> pruefObjekt.matches("^[A-Z][a-z]+$"),
            "Der erste Buchstabe muss ein Großbuchstabe sein.");
    public static final FilterRegel<String> MINDESTENS_ZWEI_ZEICHEN = new FilterRegel<>(
            pruefObjekt -> pruefObjekt.matches("^\\w{2,}$"),
            "Es müssen mindestens zwei Buchstaben vorhanden sein");

    @Getter
    private String value;

    private Nachname(String value) throws ValidierungsFehler {
        ValidierungsFehler validierungsFehler = new ValidierungsFehler();
        validierungsFehler.addRegel(ERSTER_BUCHSTABE_GROSS_REST_KLEIN.pruefe(value));
        validierungsFehler.addRegel(MINDESTENS_ZWEI_ZEICHEN.pruefe(value));
        validierungsFehler.werfenWennGebrocheneValdierungsregelnVorhanden();

        this.value = value;
    }
}
