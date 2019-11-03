package de.markus.ddd.domain.shared.regeln;

import lombok.Getter;

public class Mitteilung {

    @Getter
    private String inhalt;

    public Mitteilung(String mitteilung) {
        this.inhalt = mitteilung;
    }
}
