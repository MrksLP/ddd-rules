package de.markus.ddd.domain.shared;

import de.markus.ddd.domain.shared.regeln.Mitteilbar;
import de.markus.ddd.domain.shared.regeln.Mitteilung;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ValidierungsFehler extends Exception {
    private Set<Mitteilbar> gebrocheneValidierungsRegeln = new HashSet<>();

    public ValidierungsFehler(Mitteilbar regel) {
        this.gebrocheneValidierungsRegeln = new HashSet<>();
        this.gebrocheneValidierungsRegeln.add(regel);
    }

    public ValidierungsFehler addRegel(Optional<ValidierungsFehler> fehler) {
        if (fehler.isPresent()) {
            this.gebrocheneValidierungsRegeln.addAll(fehler.get().gebrocheneValidierungsRegeln);
        }
        return this;
    }

    public void werfenWennGebrocheneValdierungsregelnVorhanden() throws ValidierungsFehler {
        if (!this.gebrocheneValidierungsRegeln.isEmpty()) {
            throw this;
        }
    }

    public Set<Mitteilung> getMitteilungenGebrochenerRegeln() {
        return gebrocheneValidierungsRegeln.stream()
                .map(mitteilbar -> mitteilbar.getMitteilung())
                .collect(Collectors.toSet());
    }
}
