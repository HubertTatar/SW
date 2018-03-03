package io.huta.application.hi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHiCommand {
    private String name;

    public Hi toHi() {
        return new Hi(null, name);
    }
}
