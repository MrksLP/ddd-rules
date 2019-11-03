package de.markus.ddd.domain.shared;

import de.markus.ddd.domain.shared.regeln.TransformationsRegel;
import lombok.Getter;

import java.util.UUID;

public class Identifier {

    public static Identifier erstelle(String value) throws ValidierungsFehler {
        if (value == null) {
            return null;
        }
        return new Identifier(value);
    }

    public static final TransformationsRegel<String, UUID> FORMAT_FEHLER = new TransformationsRegel<>(
            pruefObjekt -> UUID.fromString(pruefObjekt),
            "Der Wert folgt nicht dem gültigen Pattern einer UUID");

    @Getter
    private String value;

    private Identifier(String value) throws ValidierungsFehler {
        FORMAT_FEHLER.erfasse(value);
        this.value = value;
    }
}
