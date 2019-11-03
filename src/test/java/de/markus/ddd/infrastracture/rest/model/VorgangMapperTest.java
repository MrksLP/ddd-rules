package de.markus.ddd.infrastracture.rest.model;

import de.markus.ddd.domain.shared.Identifier;
import de.markus.ddd.domain.shared.Nachname;
import de.markus.ddd.domain.shared.Vorname;
import de.markus.ddd.domain.vorgang.Arbeitnehmer;
import de.markus.ddd.domain.vorgang.Vorgang;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VorgangMapperTest {

    @Test
    public void given_ValidRestVorgang_when_map_then_ValidVorgangNoErrors() {

        VorgangResource vorgangResource = VorgangResource.builder()
                .identifier(UUID.randomUUID().toString())
                .vorname("Max")
                .nachname("Mustermann")
                .geburtstag("05-03-1989")
                .build();

        VorgangMapper vorgangMapper = new VorgangMapper();

        vorgangMapper.map(vorgangResource);
        Vorgang result = vorgangMapper.getErgebnis();

        assertEquals(vorgangResource.getIdentifier(), result.getIdentifier().getValue());
        assertEquals(vorgangResource.getVorname(), result.getArbeitnehmer().getVorname().getValue());
        assertEquals(vorgangResource.getNachname(), result.getArbeitnehmer().getNachname().getValue());
        assertEquals(vorgangResource.getGeburtstag(), result.getArbeitnehmer().getGeburtstag());
    }

    @Test
    public void given_AllInvalidRestVorgang_when_map_then_AlleValidierungsFehler() {

        VorgangResource vorgangResource = VorgangResource.builder()
                .identifier("a")
                .vorname("a")
                .nachname("a")
                .geburtstag("a")
                .build();

        VorgangMapper vorgangMapper = new VorgangMapper();

        vorgangMapper.map(vorgangResource);
        Vorgang result = vorgangMapper.getErgebnis();
        //TODO result is what?

        VorgangValidierungsErgebnis validierungsErgebnis = vorgangMapper.getValidierungsErgebnis();

        assertEquals(1, validierungsErgebnis.getIdentifier().size());
        assertTrue(validierungsErgebnis.getIdentifier().contains(Identifier.FORMAT_FEHLER.getMitteilung()));

        assertEquals(1, validierungsErgebnis.getVorname().size());
        assertTrue(validierungsErgebnis.getVorname().contains(Vorname.ERSTER_BUCHSTABE_GROSS_REST_KLEIN.getMitteilung()));

        assertEquals(2, validierungsErgebnis.getNachname().size());
        assertTrue(validierungsErgebnis.getNachname().contains(Nachname.MINDESTENS_ZWEI_ZEICHEN.getMitteilung()));
        assertTrue(validierungsErgebnis.getNachname().contains(Nachname.ERSTER_BUCHSTABE_GROSS_REST_KLEIN.getMitteilung()));

        assertEquals(1, validierungsErgebnis.getGeburtstag().size());
        assertTrue(validierungsErgebnis.getGeburtstag().contains(Arbeitnehmer.GEBURTSTAG_FORMAT_FEHLER.getMitteilung()));
    }


    @Test
    public void given_ValiderRestVorgangAberNichtVollstaendig_when_map_then_NichtVollstaendigerVorgang() {

        VorgangResource vorgangResource = VorgangResource.builder()
                .identifier(UUID.randomUUID().toString())
                .vorname("Max")
                // Fehlender nachname
                .geburtstag("05-03-1989")
                .build();

        VorgangMapper vorgangMapper = new VorgangMapper();

        vorgangMapper.map(vorgangResource);
        Vorgang result = vorgangMapper.getErgebnis();
        VorgangValidierungsErgebnis validierungsErgebnis = vorgangMapper.getValidierungsErgebnis();


        assertEquals(vorgangResource.getIdentifier(), result.getIdentifier().getValue());
        assertEquals(vorgangResource.getVorname(), result.getArbeitnehmer().getVorname().getValue());
        assertEquals(vorgangResource.getGeburtstag(), result.getArbeitnehmer().getGeburtstag());
    }


    @Test
    public void given_SomeInvalidRestVorgangUndNichtVollstaendig_when_map_then_NichtVollstaendigerVorgang() {

        VorgangResource vorgangResource = VorgangResource.builder()
                .identifier(UUID.randomUUID().toString())
                .vorname("?!")
                // Fehlender nachname
                .geburtstag("05-03-1989")
                .build();

        VorgangMapper vorgangMapper = new VorgangMapper();

        vorgangMapper.map(vorgangResource);
        Vorgang result = vorgangMapper.getErgebnis();
        VorgangValidierungsErgebnis validierungsErgebnis = vorgangMapper.getValidierungsErgebnis();


        assertEquals(vorgangResource.getIdentifier(), result.getIdentifier().getValue());
        assertEquals(vorgangResource.getGeburtstag(), result.getArbeitnehmer().getGeburtstag());
        assertNull(result.getArbeitnehmer().getVorname());
        assertNull(result.getArbeitnehmer().getNachname());
    }
}
