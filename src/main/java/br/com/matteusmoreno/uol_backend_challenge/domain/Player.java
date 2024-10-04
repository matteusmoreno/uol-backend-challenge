package br.com.matteusmoreno.uol_backend_challenge.domain;

import br.com.matteusmoreno.uol_backend_challenge.constant.HeroesGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private HeroesGroup heroesGroup;
}
