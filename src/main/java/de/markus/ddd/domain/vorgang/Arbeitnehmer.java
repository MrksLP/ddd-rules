package de.markus.ddd.domain.vorgang;

import de.markus.ddd.domain.shared.Nachname;
import de.markus.ddd.domain.shared.ValidierungsFehler;
import de.markus.ddd.domain.shared.Vorname;
import de.markus.ddd.domain.shared.regeln.FilterRegel;
import de.markus.ddd.domain.shared.regeln.PflichtfeldRegel;
import de.markus.ddd.domain.shared.regeln.TransformationsRegel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
public class Arbeitnehmer {

    private static final SimpleDateFormat GEBURTSTAG_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    public static final FilterRegel<Date> GEBURTSTAG_GEBOREN_VOR_HEUTE = new FilterRegel<>(
            pruefObjekt -> new Date().after(pruefObjekt),
            "Das Geburtstag muss vor dem heutigen Tag sein");


    public static final TransformationsRegel<String, Date> GEBURTSTAG_FORMAT_FEHLER = new TransformationsRegel<>(
            pruefObjekt -> GEBURTSTAG_FORMATTER.parse(pruefObjekt),
            "Der Geburtstag folgt nicht dem gültigen Pattern dd-MM-yyyy");

    public static final PflichtfeldRegel<Vorname> VORNAME_PFLICHTFELD = new PflichtfeldRegel<>("Vorname ist ein Pflichtfeld");
    public static final PflichtfeldRegel<Nachname> NACHNAME_PFLICHTFELD = new PflichtfeldRegel<>("Nachname ist ein Pflichtfeld");
    public static final PflichtfeldRegel<String> GEBURTSTAG_PFLICHTFELD = new PflichtfeldRegel<>("Geburtstag ist ein Pflichtfeld");

    @Getter
    private Vorname vorname;
    @Getter
    private Nachname nachname;
    private Date geburtstag;

    public Arbeitnehmer setzeVorname(Vorname vorname) throws ValidierungsFehler {
        this.vorname = VORNAME_PFLICHTFELD.pruefeWerfend(vorname);
        return this;
    }

    public Arbeitnehmer setzeNachname(Nachname nachname) throws ValidierungsFehler {
        this.nachname = NACHNAME_PFLICHTFELD.pruefeWerfend(nachname);
        return this;
    }

    public Arbeitnehmer setzeGeburtstag(String value) throws ValidierungsFehler {

        GEBURTSTAG_PFLICHTFELD.pruefeWerfend(value);
        Date geburtstagsAnwerber = GEBURTSTAG_FORMAT_FEHLER.erfasse(value);
        GEBURTSTAG_GEBOREN_VOR_HEUTE.pruefeWerfend(geburtstagsAnwerber);

        this.geburtstag = geburtstagsAnwerber;
        return this;
    }

    public String getGeburtstag() {
        return GEBURTSTAG_FORMATTER.format(this.geburtstag);
    }
}
