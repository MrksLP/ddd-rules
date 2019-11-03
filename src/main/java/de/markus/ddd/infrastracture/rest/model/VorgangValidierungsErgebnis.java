package de.markus.ddd.infrastracture.rest.model;

import de.markus.ddd.domain.shared.regeln.Mitteilung;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class VorgangValidierungsErgebnis {
    private Set<Mitteilung> identifier = new HashSet<>();
    private Set<Mitteilung> vorname = new HashSet<>();
    private Set<Mitteilung> nachname = new HashSet<>();
    private Set<Mitteilung> geburtstag = new HashSet<>();

    public void addIdentifier(Set<Mitteilung> mitteilung) {
        this.identifier.addAll(mitteilung);
    }

    public void addVorname(Set<Mitteilung> mitteilung) {
        this.vorname.addAll(mitteilung);
    }

    public void addNachname(Set<Mitteilung> mitteilung) {
        this.nachname.addAll(mitteilung);
    }

    public void addGeburtstag(Set<Mitteilung> mitteilung) {
        this.geburtstag.addAll(mitteilung);
    }
}
