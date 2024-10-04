package br.com.matteusmoreno.uol_backend_challenge.request;

import br.com.matteusmoreno.uol_backend_challenge.constant.HeroesGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePlayerRequest(
        @NotBlank(message = "Name is required")
        String name,
        String email,
        String phone,
        @NotNull(message = "Heroes Group is required")
        HeroesGroup heroesGroup) {
}
