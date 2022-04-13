package br.com.sicred.sincronizacao;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import br.com.sicred.sincronizacao.dto.sincronizacaoDTO;
import br.com.sicred.sincronizacao.service.ReceitaService;

@SpringBootApplication
public class SincronizacaoApplication {

	public static void main(String[] args) throws IOException, RuntimeException, InterruptedException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		Reader reader = Files.newBufferedReader(Paths.get(args[0].toString()));

		CSVReader csvReader = new CSVReaderBuilder(reader)
				.withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build();
		
		List<String[]> contas = csvReader.readAll();
		
		List<sincronizacaoDTO> contasDTOs = convertToDTO(contas);
		
		List<sincronizacaoDTO> contasAtualizadoDTOs = atualizaContas(contasDTOs);
		
		salvaArquivo(args, contasAtualizadoDTOs);
	}

	private static void salvaArquivo(String[] args, List<sincronizacaoDTO> contasAtualizadoDTOs)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		Writer writer = Files.newBufferedWriter(Paths.get(args[0].toString().replace(".csv", "_atualizado_" + LocalDate.now() +".csv")));
        StatefulBeanToCsv<sincronizacaoDTO> beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

        beanToCsv.write(contasAtualizadoDTOs);
		
        writer.flush();
        writer.close();
	}

	private static List<sincronizacaoDTO> atualizaContas(List<sincronizacaoDTO> contasDTOs)
			throws InterruptedException {
		List<sincronizacaoDTO> contasAtualizadoDTOs = new ArrayList<sincronizacaoDTO>();
		for (sincronizacaoDTO conta : contasDTOs) {
			ReceitaService receitaService = new ReceitaService();
			conta.setStatusAtualizacao(receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), conta.getSaldo(),
					conta.getStatus()));
			contasAtualizadoDTOs.add(conta);
		}
		return contasAtualizadoDTOs;
	}

	private static List<sincronizacaoDTO> convertToDTO(List<String[]> contas) {
		List<sincronizacaoDTO> contasDTOs = new ArrayList<sincronizacaoDTO>();
		for (String[] conta : contas) {
			sincronizacaoDTO contasDTO = new sincronizacaoDTO();
			contasDTO.setAgencia(conta[0]);
			contasDTO.setConta(conta[1].replace("-", ""));
			contasDTO.setSaldo(Double.valueOf(conta[2].replace(",", ".")));
			contasDTO.setStatus(conta[3]);

			contasDTOs.add(contasDTO);
		}
		return contasDTOs;
	}
}
