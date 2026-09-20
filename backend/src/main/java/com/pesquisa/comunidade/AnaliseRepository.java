package com.pesquisa.comunidade;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class AnaliseRepository {

    private final JdbcTemplate jdbc;

    public AnaliseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Jogadores mais ativos por número de sessões registradas.
     */
    public List<Map<String, Object>> jogadoresMaisAtivos(int limite) {
        String sql = """
            SELECT j.nickname_atual AS jogador,
                   COUNT(s.id) AS total_sessoes
            FROM jogadores j
            JOIN sessoes s ON s.jogador_id = j.id
            GROUP BY j.id, j.nickname_atual
            ORDER BY total_sessoes DESC
            LIMIT ?
            """;
        return jdbc.queryForList(sql, limite);
    }
    public List<Map<String, Object>> tempoMedioSessaoPorJogador() {
        String sql = """
            SELECT j.nickname_atual AS jogador,
                   ROUND(AVG(TIMESTAMPDIFF(SECOND, s.entrada, s.saida)) / 60.0, 1) AS media_minutos,
                   COUNT(s.id) AS sessoes_consideradas
            FROM jogadores j
            JOIN sessoes s ON s.jogador_id = j.id
            WHERE s.saida IS NOT NULL
            GROUP BY j.id, j.nickname_atual
            ORDER BY media_minutos DESC
            """;
        return jdbc.queryForList(sql);
    }
    public List<Map<String, Object>> violacoesPorJogador() {
        String sql = """
            SELECT j.nickname_atual AS jogador,
                   COUNT(v.id) AS total_violacoes,
                   ROUND(AVG(v.nivel), 2) AS nivel_medio,
                   ROUND(MAX(v.nivel), 2) AS nivel_maximo
            FROM jogadores j
            JOIN violacoes v ON v.jogador_id = j.id
            GROUP BY j.id, j.nickname_atual
            ORDER BY total_violacoes DESC
            """;
        return jdbc.queryForList(sql);
    }
    public List<Map<String, Object>> violacoesPorTipo(int limite) {
        String sql = """
            SELECT tv.display AS tipo_verificacao,
                   tv.descricao AS descricao,
                   COUNT(v.id) AS total_ocorrencias
            FROM violacoes v
            JOIN tipos_verificacao tv ON tv.check_id = v.check_id
            GROUP BY tv.check_id, tv.display, tv.descricao
            ORDER BY total_ocorrencias DESC
            LIMIT ?
            """;
        return jdbc.queryForList(sql, limite);
    }
    public List<Map<String, Object>> linhaDoTempoViolacoes(String nickname) {
        String sql = """
            SELECT v.ocorrido_em, tv.display AS tipo_verificacao, v.nivel, v.sessao_id
            FROM violacoes v
            JOIN jogadores j ON j.id = v.jogador_id
            JOIN tipos_verificacao tv ON tv.check_id = v.check_id
            WHERE j.nickname_atual = ?
            ORDER BY v.ocorrido_em ASC
            """;
        return jdbc.queryForList(sql, nickname);
    }
    public List<Map<String, Object>> loginsPorDia() {
        String sql = """
                SELECT DATE(entrada) AS dia, COUNT(*) AS total
                FROM sessoes
                GROUP BY DATE(entrada)
                ORDER BY dia
            """;
        return jdbc.queryForList(sql);
    }
    public List<Map<String, Object>> sessoesPorHora() {
        String sql = """
            SELECT DAYOFWEEK(entrada) AS dia_semana,
                   HOUR(entrada)      AS hora,
                   COUNT(*)           AS total
            FROM sessoes
            GROUP BY DAYOFWEEK(entrada), HOUR(entrada)
            ORDER BY dia_semana, hora
            """;
        return jdbc.queryForList(sql);
    }
    public List<Map<String, Object>> violacoesPorHora() {
        String sql = """
            SELECT DAYOFWEEK(ocorrido_em) AS dia_semana,
                   HOUR(ocorrido_em)      AS hora,
                   COUNT(*)               AS total
            FROM violacoes
            GROUP BY DAYOFWEEK(ocorrido_em), HOUR(ocorrido_em)
            ORDER BY dia_semana, hora
            """;
        return jdbc.queryForList(sql);
    }
        public List<Map<String, Object>> violacoesVsTempo() {
        String sql = """
            SELECT j.nickname_atual AS jogador,
                   j.plataforma     AS plataforma,
                   COALESCE(s.minutos, 0) AS minutos_jogados,
                   COALESCE(v.total, 0)   AS total_violacoes
            FROM jogadores j
            LEFT JOIN (
                SELECT jogador_id,
                       ROUND(SUM(TIMESTAMPDIFF(SECOND, entrada, saida)) / 60.0, 1) AS minutos
                FROM sessoes
                WHERE saida IS NOT NULL
                GROUP BY jogador_id
            ) s ON s.jogador_id = j.id
            LEFT JOIN (
                SELECT jogador_id, COUNT(*) AS total
                FROM violacoes
                GROUP BY jogador_id
            ) v ON v.jogador_id = j.id
            ORDER BY total_violacoes DESC
            """;
        return jdbc.queryForList(sql);
    }
        public List<Map<String, Object>> resumoPorPlataforma() {
        String sql = """
            SELECT j.plataforma AS plataforma,
                   COUNT(*)                   AS contas,
                   COALESCE(SUM(s.qtd), 0)    AS sessoes,
                   COALESCE(SUM(v.qtd), 0)    AS violacoes
            FROM jogadores j
            LEFT JOIN (SELECT jogador_id, COUNT(*) AS qtd FROM sessoes    GROUP BY jogador_id) s ON s.jogador_id = j.id
            LEFT JOIN (SELECT jogador_id, COUNT(*) AS qtd FROM violacoes  GROUP BY jogador_id) v ON v.jogador_id = j.id
            GROUP BY j.plataforma
            """;
        return jdbc.queryForList(sql);
    }
}
