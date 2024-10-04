package br.com.matteusmoreno.uol_backend_challenge.response;

import br.com.matteusmoreno.uol_backend_challenge.constant.HeroesGroup;
import br.com.matteusmoreno.uol_backend_challenge.domain.Player;

public record PlayerDetailsResponse(
        Long id,
        String name,
        String email,
        String phone,
        String nickname,
        HeroesGroup heroesGroup) {

    public PlayerDetailsResponse(Player player) {
        this(
                player.getId(),
                player.getName(),
                player.getEmail(),
                player.getPhone(),
                player.getNickname(),
                player.getHeroesGroup());
    }
}
