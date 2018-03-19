package io.huta.application.hi;

import lombok.*;
import lombok.experimental.Wither;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Hi {
    @Wither
    private Integer id;
    @Wither
    private String name;

}
