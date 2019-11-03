package de.markus.ddd.domain.shared.regeln;

import de.markus.ddd.domain.shared.ValidierungsFehler;
import lombok.Getter;

import java.util.Optional;

public class TransformationsRegel<IN, OUT> implements Mitteilbar, Erfassbar<IN, OUT>, Pruefbar<IN> {

    @FunctionalInterface
    public interface RegelTest<IN, OUT> {
        OUT filter(IN pruefObjekt) throws Exception;
    }

    private RegelTest<IN, OUT> regelTest;
    @Getter
    private Mitteilung mitteilung;

    public TransformationsRegel(RegelTest<IN, OUT> regelTest, String mitteilung) {
        this.regelTest = regelTest;
        this.mitteilung = new Mitteilung(mitteilung);
    }

    @Override
    public OUT erfasse(IN pruefObjekt) throws ValidierungsFehler {
        try {
            return regelTest.filter(pruefObjekt);
        } catch (Exception e) {
            throw new ValidierungsFehler(this);
        }
    }

    @Override
    public Optional<ValidierungsFehler> pruefe(IN pruefObjekt) {
        ValidierungsFehler fehler = new ValidierungsFehler(this);
        try {
            regelTest.filter(pruefObjekt);
        } catch (Exception e) {
            return Optional.of(fehler);
        }
        return Optional.empty();
    }
}
