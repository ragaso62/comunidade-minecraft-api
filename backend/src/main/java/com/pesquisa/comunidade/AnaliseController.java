package com.pesquisa.comunidade;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // libera acesso do front local; restrinja depois se for expor de verdade
public class AnaliseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @GetMapping("/login/por-dia")
    public List<Map<String, Object>> loginsPorDia() {
        return repositorio.loginsPorDia();
    }

    @GetMapping("/violacoes/por-dia")
    public List<Map<String, Object>> violacoesPorDia() {
    String sql = "SELECT DATE(ocorrido_em) as dia, COUNT(*) as total " +
                 "FROM violacoes GROUP BY DATE(ocorrido_em) ORDER BY dia";
    return jdbcTemplate.queryForList(sql);
    }
    
    @GetMapping("/sessoes/por-hora")
    public List<Map<String, Object>> sessoesPorHora() {
        return repositorio.sessoesPorHora();
    }
 
    @GetMapping("/violacoes/por-hora")
    public List<Map<String, Object>> violacoesPorHora() {
        return repositorio.violacoesPorHora();
    }
 
    @GetMapping("/jogadores/violacoes-vs-tempo")
    public List<Map<String, Object>> violacoesVsTempo() {
        return repositorio.violacoesVsTempo();
    }
 
    @GetMapping("/jogadores/por-plataforma")
    public List<Map<String, Object>> resumoPorPlataforma() {
        return repositorio.resumoPorPlataforma();
    }
}
