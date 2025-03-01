package com.portalsocial.portalsocial.api.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaIdInput {
    @NotNull
    private Long id;
}
