package br.com.matteusmoreno.uol_backend_challenge.repository;

import br.com.matteusmoreno.uol_backend_challenge.constant.HeroesGroup;
import br.com.matteusmoreno.uol_backend_challenge.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    int countByHeroesGroup(HeroesGroup heroesGroup);
}
