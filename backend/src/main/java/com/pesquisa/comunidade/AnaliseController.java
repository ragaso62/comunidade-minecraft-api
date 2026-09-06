package com.pesquisa.comunidade;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // libera acesso do front local; restrinja depois se for expor de verdade
public class AnaliseController {

    private final AnaliseRepository repositorio;

    public AnaliseController(AnaliseRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping("/jogadores/mais-ativos")
    public List<Map<String, Object>> jogadoresMaisAtivos(
            @RequestParam(defaultValue = "10") int limite) {
        return repositorio.jogadoresMaisAtivos(limite);
    }

    @GetMapping("/jogadores/tempo-medio-sessao")
    public List<Map<String, Object>> tempoMedioSessao() {
        return repositorio.tempoMedioSessaoPorJogador();
    }

    @GetMapping("/violacoes/por-jogador")
    public List<Map<String, Object>> violacoesPorJogador() {
        return repositorio.violacoesPorJogador();
    }

    @GetMapping("/violacoes/por-tipo")
    public List<Map<String, Object>> violacoesPorTipo(
            @RequestParam(defaultValue = "10") int limite) {
        return repositorio.violacoesPorTipo(limite);
    }

    @GetMapping("/violacoes/linha-do-tempo/{nickname}")
    public List<Map<String, Object>> linhaDoTempo(@PathVariable String nickname) {
        return repositorio.linhaDoTempoViolacoes(nickname);
    }
}
