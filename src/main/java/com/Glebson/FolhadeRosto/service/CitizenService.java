package com.Glebson.FolhadeRosto.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Glebson.FolhadeRosto.dto.AllergyDto;
import com.Glebson.FolhadeRosto.dto.CitizenDto;
import com.Glebson.FolhadeRosto.dto.ConditionDto;
import com.Glebson.FolhadeRosto.dto.DrugDto;

@Service
public class CitizenService {
    private final JdbcTemplate jdbcTemplate;

    public CitizenService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Async
    public CompletableFuture<Long> getCitizenCodeByMedicalRecordAsync(Long medicalRecordId) {
        String sql = """
            SELECT co_prontuario from tb_atend ta where co_seq_atend  = ?;
        """;
        var result = jdbcTemplate.queryForMap(sql, medicalRecordId);

        return CompletableFuture.completedFuture((Long) result.get("co_prontuario"));
    }

    @Async
    public CompletableFuture<CitizenDto> getCitizenInfoAsync(Long citizenCod) {
        String sql = """
            SELECT nu_cpf, nu_cns, no_cidadao, no_social, dt_nascimento, no_sexo, no_pai, no_mae FROM tb_cidadao tc 
            WHERE co_seq_cidadao = ?
        """;
        return CompletableFuture.completedFuture(jdbcTemplate.queryForObject(sql,  (rs, column) -> new CitizenDto(
            rs.getString("no_cidadao"),
            rs.getString("no_social"),
            rs.getString("nu_cpf"),
            rs.getString("nu_cns"),
            rs.getDate("dt_nascimento").toLocalDate(),
            rs.getString("no_sexo"),
            rs.getString("no_mae"),
            rs.getString("no_pai")
        ), citizenCod));
    }

    @Async
    public CompletableFuture<List<ConditionDto>> getConditionsAsync(Long citizenCode) {
        String sql = """
            SELECT 
                ciap.co_ciap, 
                ciap.ds_ciap 
            FROM 
                tb_problema p
            JOIN 
                tb_prontuario pr ON pr.co_seq_prontuario = p.co_prontuario
            JOIN 
                tb_ciap ciap ON ciap.co_seq_ciap = p.co_ciap
            WHERE 
                pr.co_cidadao = ?
                AND p.co_ciap IS NOT NULL;
        """;
        
        return CompletableFuture.completedFuture(jdbcTemplate.query(sql, (rs, rowNum) -> new ConditionDto(
            rs.getString("co_ciap"),
            rs.getString("ds_ciap")
        ), citizenCode));
    }

    @Async
    public CompletableFuture<List<DrugDto>> getActiveDrugsAsync(Long citizenCode) {
        String sql = """
            SELECT DISTINCT
                m.no_principio_ativo,
                rm.no_posologia,
                rm.dt_inicio_tratamento,
                rm.dt_fim_tratamento
            FROM
                tb_receita_medicamento rm
            JOIN
                tb_medicamento m ON m.co_seq_medicamento = rm.co_medicamento
            JOIN
                tb_atend a ON a.co_atend_prof = rm.co_atend_prof
            JOIN
                tb_prontuario p ON p.co_seq_prontuario = a.co_prontuario
            WHERE
                p.co_cidadao = ?
                AND (rm.dt_fim_tratamento IS NULL OR rm.dt_fim_tratamento > CURRENT_DATE)
        """;

        return CompletableFuture.completedFuture(jdbcTemplate.query(sql, (rs, rowNum) -> new DrugDto(
            rs.getString("no_principio_ativo"),
            rs.getString("no_posologia"),
            rs.getDate("dt_inicio_tratamento").toLocalDate(),
            rs.getDate("dt_fim_tratamento").toLocalDate()
        ), citizenCode));
    }

    @Async
    public CompletableFuture<List<AllergyDto>> getAllergiesAsync(Long citizenCode) {
        String sql = """
            	SELECT DISTINCT
                    mc.no_medicamento_filtro AS nome,
                    'Medicamento' AS categoria_alergia,
                    ca.no_criticidade_alergia AS grau_criticidade,
                    tra.no_tipo_reacao_alergia AS tipo_reacao,
                    gca.no_grau_certeza_alergia AS grau_certeza
                FROM
                    tb_alergia_evolucao ae
                JOIN tb_atend a ON a.co_atend_prof = ae.co_atend_prof
                JOIN tb_prontuario p ON p.co_seq_prontuario = a.co_prontuario
                JOIN tb_substancia_espec_alergia sea ON sea.co_seq_substanc_espec_alergia = ae.co_unico_alergia
                JOIN tb_medicamento_catmat mc ON mc.co_medicamento = sea.co_medicamento_catmat
                LEFT JOIN tb_criticidade_alergia ca ON ca.co_criticidade_alergia = ae.co_criticidade_alergia
                LEFT JOIN tb_tipo_reacao_alergia tra ON tra.co_seq_tipo_reacao_alergia = ae.co_tipo_reacao_alergia
                LEFT JOIN tb_grau_certeza_alergia gca ON gca.co_seq_grau_certeza_alergia = ae.co_grau_certeza_alergia
                WHERE p.co_cidadao = ?
                
                UNION ALL
                
                SELECT DISTINCT
                    sc.no_substancia_alergia AS nome,
                    c_sc.no_categ_substancia_alergia AS categoria_alergia,
                    ca.no_criticidade_alergia AS grau_criticidade,
                    tra.no_tipo_reacao_alergia AS tipo_reacao,
                    gca.no_grau_certeza_alergia AS grau_certeza
                FROM
                    tb_alergia_evolucao ae
                JOIN tb_atend a ON a.co_atend_prof = ae.co_atend_prof
                JOIN tb_prontuario p ON p.co_seq_prontuario = a.co_prontuario
                JOIN tb_substancia_espec_alergia sea ON sea.co_seq_substanc_espec_alergia = ae.co_unico_alergia
                JOIN tb_substancia_cbara sc ON sc.co_seq_substancia_alergia = sea.co_substancia
                JOIN tb_categ_substancia_alergia c_sc ON c_sc.co_seq_cat_substancia_alergia = sc.co_cat_substancia_alergia
                LEFT JOIN tb_criticidade_alergia ca ON ca.co_criticidade_alergia = ae.co_criticidade_alergia
                LEFT JOIN tb_tipo_reacao_alergia tra ON tra.co_seq_tipo_reacao_alergia = ae.co_tipo_reacao_alergia
                LEFT JOIN tb_grau_certeza_alergia gca ON gca.co_seq_grau_certeza_alergia = ae.co_grau_certeza_alergia
                WHERE p.co_cidadao = ?
                     
                UNION ALL
                
                SELECT DISTINCT
                    t.no_imunobiologico AS nome,
                    'Biológico' AS categoria_alergia,
                    ca.no_criticidade_alergia AS grau_criticidade,
                    tra.no_tipo_reacao_alergia AS tipo_reacao,
                    gca.no_grau_certeza_alergia AS grau_certeza
                FROM
                    tb_alergia_evolucao ae
                JOIN tb_atend a ON a.co_atend_prof = ae.co_atend_prof
                JOIN tb_prontuario p ON p.co_seq_prontuario = a.co_prontuario
                JOIN tb_substancia_espec_alergia sea ON sea.co_seq_substanc_espec_alergia = ae.co_unico_alergia
                JOIN tb_imunobiologico t ON t.co_imunobiologico = sea.co_imunobiologico 
                LEFT JOIN tb_criticidade_alergia ca ON ca.co_criticidade_alergia = ae.co_criticidade_alergia
                LEFT JOIN tb_tipo_reacao_alergia tra ON tra.co_seq_tipo_reacao_alergia = ae.co_tipo_reacao_alergia
                LEFT JOIN tb_grau_certeza_alergia gca ON gca.co_seq_grau_certeza_alergia = ae.co_grau_certeza_alergia
                WHERE p.co_cidadao = ?;

        """;

        return CompletableFuture.completedFuture(jdbcTemplate.query(sql, (rs, rowNum) -> new AllergyDto(
            rs.getString("nome"),
            rs.getString("categoria_alergia"),
            rs.getString("grau_criticidade"),
            rs.getString("tipo_reacao"),
            rs.getString("grau_certeza")
        ) , citizenCode, citizenCode, citizenCode));
    }
}