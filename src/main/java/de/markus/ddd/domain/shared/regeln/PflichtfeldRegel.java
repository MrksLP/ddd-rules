package de.markus.ddd.domain.shared.regeln;

public class PflichtfeldRegel<T> extends FilterRegel<T> {

    public PflichtfeldRegel(String mitteilung) {
        super(pruefObjekt -> pruefObjekt != null, mitteilung);
    }
}
