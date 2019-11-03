package de.markus.ddd.infrastracture.rest.model;

import de.markus.ddd.domain.shared.ValidierungsFehler;
import de.markus.ddd.domain.shared.regeln.Mitteilbar;
import de.markus.ddd.domain.shared.regeln.Mitteilung;

import java.util.Set;

public abstract class Mapper {

    protected <T> T bearbeite(Versuch<T> versuch, MitteilungZurueckfuehren mitteilungZurueckfuehren) {
        try {
            return versuch.versuchen();
        } catch (Exception e) {
            if (e instanceof ValidierungsFehler) {
                ValidierungsFehler fehler = (ValidierungsFehler) e;
                mitteilungZurueckfuehren.anhaengen(fehler.getMitteilungenGebrochenerRegeln());
            } else {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @FunctionalInterface
    protected interface MitteilungZurueckfuehren {
        void anhaengen(Set<Mitteilung> mitteilung);
    }

    @FunctionalInterface
    protected interface Versuch<T> {
        T versuchen() throws ValidierungsFehler;
    }
}
