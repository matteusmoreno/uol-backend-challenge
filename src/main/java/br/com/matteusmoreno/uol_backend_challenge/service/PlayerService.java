package br.com.matteusmoreno.uol_backend_challenge.service;

import br.com.matteusmoreno.uol_backend_challenge.constant.HeroesGroup;
import br.com.matteusmoreno.uol_backend_challenge.domain.Avenger;
import br.com.matteusmoreno.uol_backend_challenge.domain.JusticeLeague;
import br.com.matteusmoreno.uol_backend_challenge.domain.Nickname;
import br.com.matteusmoreno.uol_backend_challenge.domain.Player;
import br.com.matteusmoreno.uol_backend_challenge.exception.GroupMaxPlayersReachedException;
import br.com.matteusmoreno.uol_backend_challenge.repository.PlayerRepository;
import br.com.matteusmoreno.uol_backend_challenge.request.CreatePlayerRequest;
import br.com.matteusmoreno.uol_backend_challenge.response.PlayerDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;
    private final Random random;
    private final ResourceLoader resourceLoader;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, ObjectMapper objectMapper, XmlMapper xmlMapper, Random random, ResourceLoader resourceLoader) {
        this.playerRepository = playerRepository;
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
        this.random = random;
        this.resourceLoader = resourceLoader;
    }

    @Value("${files.avengers-json}")
    private String avengersFile;

    @Value("${files.justice-league-xml}")
    private String justiceLeagueFile;

    @Transactional
    public Player createPlayer(CreatePlayerRequest request) throws IOException {
        Player player = new Player();
        BeanUtils.copyProperties(request, player);

        String nickname;
        int avengersCount = countByHeroesGroup(HeroesGroup.AVENGERS);
        int justiceLeagueCount = countByHeroesGroup(HeroesGroup.JUSTICE_LEAGUE);

        if (player.getHeroesGroup() == HeroesGroup.AVENGERS) {
            if (playerRepository.countByHeroesGroup(HeroesGroup.AVENGERS) == avengersCount) {
                throw new GroupMaxPlayersReachedException();
            }
            nickname = chooseRandomNickname(HeroesGroup.AVENGERS);
        } else {
            if (playerRepository.countByHeroesGroup(HeroesGroup.JUSTICE_LEAGUE) == justiceLeagueCount) {
                throw new GroupMaxPlayersReachedException();
            }
            nickname = chooseRandomNickname(HeroesGroup.JUSTICE_LEAGUE);
        }

        player.setNickname(nickname);
        return playerRepository.save(player);
    }

    private String chooseRandomNickname(HeroesGroup group) throws IOException {
        List<String> nicknames;

        if (group == HeroesGroup.AVENGERS) {
            nicknames = getAvengersNicknames();
        } else {
            nicknames = getJusticeLeagueNicknames();
        }

        return nicknames.get(random.nextInt(nicknames.size()));
    }

    private List<String> getAvengersNicknames() throws IOException {
        Resource resource = resourceLoader.getResource(avengersFile);
        Avenger avenger = objectMapper.readValue(resource.getInputStream(), Avenger.class);        List<String> avengersList = new ArrayList<>(avenger.getVingadores().stream()
                .map(Nickname::getCodinome)
                .toList());

        for (Player player : playerRepository.findAll()) {
            avengersList.remove(player.getNickname());
        }

        return avengersList;
    }

    private List<String> getJusticeLeagueNicknames() throws IOException {
        Resource resource = resourceLoader.getResource(justiceLeagueFile);
        JusticeLeague justiceLeague = xmlMapper.readValue(resource.getInputStream(), JusticeLeague.class);        List<String> justiceLeagueList = justiceLeague.getCodinomes();

        for (Player player : playerRepository.findAll()) {
            justiceLeagueList.remove(player.getNickname());
        }

        return justiceLeagueList;
    }

    private int countByHeroesGroup(HeroesGroup heroesGroup) throws IOException {
        if (heroesGroup == HeroesGroup.AVENGERS) {
            Avenger avenger = objectMapper.readValue(new File(avengersFile), Avenger.class);
            List<String> avengersList = new ArrayList<>(avenger.getVingadores().stream()
                    .map(Nickname::getCodinome)
                    .toList());
            return avengersList.size();
        } else  {
            JusticeLeague justiceLeague = xmlMapper.readValue(new File(justiceLeagueFile), JusticeLeague.class);
            return justiceLeague.getCodinomes().size();
        }
    }

    public Page<PlayerDetailsResponse> findAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable).map(PlayerDetailsResponse::new);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
