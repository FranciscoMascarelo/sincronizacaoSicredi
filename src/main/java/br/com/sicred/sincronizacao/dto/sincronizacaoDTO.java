package br.com.sicred.sincronizacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class sincronizacaoDTO {
	private String agencia;
	private String conta;
	private Double saldo;
	private String status;
	private Boolean statusAtualizacao;
}
