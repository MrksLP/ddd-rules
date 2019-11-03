package de.markus.ddd.domain.vorgang;

import de.markus.ddd.domain.shared.Identifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Vorgang {

    private Identifier identifier;

    private Arbeitnehmer arbeitnehmer;
}
