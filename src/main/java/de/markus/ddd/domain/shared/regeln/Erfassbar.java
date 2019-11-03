package de.markus.ddd.domain.shared.regeln;

import de.markus.ddd.domain.shared.ValidierungsFehler;

import java.util.Optional;

public interface Erfassbar<IN, OUT> {
    OUT erfasse(IN value) throws ValidierungsFehler;
}
