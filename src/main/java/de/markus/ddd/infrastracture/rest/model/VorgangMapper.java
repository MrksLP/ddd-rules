package de.markus.ddd.infrastracture.rest.model;

import de.markus.ddd.domain.shared.Identifier;
import de.markus.ddd.domain.shared.Nachname;
import de.markus.ddd.domain.shared.Vorname;
import de.markus.ddd.domain.vorgang.Arbeitnehmer;
import de.markus.ddd.domain.vorgang.Vorgang;
import lombok.Getter;

public class VorgangMapper extends Mapper {

    @Getter
    private Vorgang ergebnis;
    @Getter
    private VorgangValidierungsErgebnis validierungsErgebnis;

    public void map(VorgangResource quelle) {
        this.ergebnis = null;
        this.validierungsErgebnis = new VorgangValidierungsErgebnis();

        Identifier identifier = bearbeite(() -> Identifier.erstelle(quelle.getIdentifier()),
                validierungsMitteilungen -> this.validierungsErgebnis.addIdentifier(validierungsMitteilungen));

        Arbeitnehmer arbeitnehmer = mapArbeitnehmer(quelle);

        this.ergebnis = new Vorgang(identifier, arbeitnehmer);
    }

    private Arbeitnehmer mapArbeitnehmer(VorgangResource quelle) {
        Arbeitnehmer arbeitnehmer = new Arbeitnehmer();

        Vorname vorname = bearbeite(() -> Vorname.erstelle(quelle.getVorname()),
                validierungsMitteilungen -> this.validierungsErgebnis.addVorname(validierungsMitteilungen));

        Nachname nachname = bearbeite(() -> Nachname.erstelle(quelle.getNachname()),
                validierungsMitteilungen -> this.validierungsErgebnis.addNachname(validierungsMitteilungen));

        bearbeite(() -> arbeitnehmer.setzeVorname(vorname),
                mitteilungen -> {
                    if (this.validierungsErgebnis.getVorname().isEmpty()) {
                        this.validierungsErgebnis.addVorname(mitteilungen);
                    }
                });
        bearbeite(() -> arbeitnehmer.setzeNachname(nachname),
                mitteilungen -> {
                    if (this.validierungsErgebnis.getNachname().isEmpty()) {
                        this.validierungsErgebnis.addNachname(mitteilungen);
                    }
                });
        bearbeite(() -> arbeitnehmer.setzeGeburtstag(quelle.getGeburtstag()),
                mitteilungen -> this.validierungsErgebnis.addGeburtstag(mitteilungen));

        return arbeitnehmer;
    }


}
