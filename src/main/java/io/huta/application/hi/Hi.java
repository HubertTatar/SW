package io.huta.application.hi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Hi {
    @Wither
    private Integer id;
    private String name;

}
