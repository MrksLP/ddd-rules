package de.markus.ddd.domain.shared.regeln;

import de.markus.ddd.domain.shared.ValidierungsFehler;
import lombok.Getter;

import java.util.Optional;

public class FilterRegel<T> implements Mitteilbar, Pruefbar<T> {

    @FunctionalInterface
    public interface RegelTest<T> {
        boolean filter(T pruefObjekt);
    }
    private RegelTest<T> regelTest;

    @Getter
    private Mitteilung mitteilung;
    public FilterRegel(RegelTest<T> regelTest, String mitteilung) {
        this.regelTest = regelTest;
        this.mitteilung = new Mitteilung(mitteilung);
    }

    @Override
    public Optional<ValidierungsFehler> pruefe(T value) {
        if (!this.regelTest.filter(value)){
            return Optional.of(new ValidierungsFehler(this));
        }
        return Optional.empty();
    }
}
