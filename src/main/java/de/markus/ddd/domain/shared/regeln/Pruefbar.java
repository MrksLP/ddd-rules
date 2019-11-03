package de.markus.ddd.domain.shared.regeln;

import de.markus.ddd.domain.shared.ValidierungsFehler;

import java.util.Optional;

public interface Pruefbar<T> {
    Optional<ValidierungsFehler> pruefe(T value);

    default T pruefeWerfend(T value) throws ValidierungsFehler {
        Optional<ValidierungsFehler> pruefErgebnis = pruefe(value);
        if (pruefErgebnis.isPresent()) {
            throw pruefErgebnis.get();
        }
        return value;
    }
}
