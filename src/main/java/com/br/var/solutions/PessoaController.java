package com.br.var.solutions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/pessoa")
@Slf4j
public class PessoaController {
    @CrossOrigin(origins = "*")
    @GetMapping

    //EndPoint
//      1                       2                   3              4
//publico/Privado   // tipo de retorno      // nome do metodo // parametros
    public ResponseEntity<Object> get() {
        PessoaRequest pessoaRequest1 = new PessoaRequest();
        pessoaRequest1.setNome("pedro");
        pessoaRequest1.setSobrenome("Xavier");

        return ResponseEntity.ok(pessoaRequest1);

    }

    @GetMapping("/resumo")
    public ResponseEntity<Object> getPessoa(@RequestBody PessoaRequest pessoinha) {
        InformacaoIMC imc = new InformacaoIMC();
        int anoNascimento = 0;
        String impostoRenda = null;
        String conversao = null;


        if (!pessoinha.getNome().isEmpty()) {


            log.info("iniciando processo de resumo da pessoa: ", pessoinha);

            if (Objects.nonNull(pessoinha.getAltura()) && Objects.nonNull(pessoinha.getPeso())) {
                log.info("iniciando calculo do imc");
                imc = calcularimc(pessoinha.getAltura(), pessoinha.getPeso());
            }

            if (Objects.nonNull(pessoinha.getIdade())) {
                log.info("iniciando calculo de data de nascimento");
                anoNascimento = calcularAnoNascimento(pessoinha.getIdade());
            }

            if (Objects.nonNull(pessoinha.getSalario())) {
                log.info("Iniciando calculo de imposto de renda");
                impostoRenda = CalcularFaixaImporto(pessoinha.getSalario());
            }

            if (Objects.nonNull(pessoinha.getSaldo())) {
                log.info("converter real em dolar");
                conversao = ConversaoDolart(pessoinha.getSaldo());
            }

            log.info("Montando Objeto de retorno para o Front-end");
            PessoaResponse resumo = montarRespostaFrontEnd(pessoinha, imc, anoNascimento, impostoRenda, conversao);


            return ResponseEntity.ok(resumo);

        }

        return ResponseEntity.noContent().build();

    }


    private PessoaResponse montarRespostaFrontEnd(PessoaRequest pessoa, InformacaoIMC imc, int anoNascimento, String impostoRenda, String saldoEmDolar) {
        PessoaResponse response = new PessoaResponse();

        response.setNome(pessoa.getNome());
        response.setSalario(impostoRenda);
        response.setAnoNascimento(anoNascimento);
        response.setSaldoEmDolar(saldoEmDolar);
        response.setImc(imc.getImc());
        response.setClassificaçãoIMC(imc.getClassificação());
        response.setAnoNascimento(anoNascimento);
        response.setIdade(pessoa.idade);
        response.setSobrenome(pessoa.getSobrenome());
        response.setPeso(pessoa.peso);
        response.setAltura(pessoa.altura);
        response.setEndereco(pessoa.getEndereco());
        response.setSaldo(pessoa.saldo);



        return response;
    }


    private String ConversaoDolart(double saldo) {
        return String.valueOf(saldo / 5.11);

    }


    // Regra : salario x liquota - dedução
    private String CalcularFaixaImporto(double salario) {
        log.info("iniciando calcuclo de imposto de renda");
        String novoSalarioCalculado;

        if (salario < 2112.0) {
            return "isento";

        } else if (salario > 2112.0 && salario <= 2826.65) {
            double calculoIRRF = ((salario * 0.075) / 100) - 158.40;
            double novoSalario = salario - calculoIRRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;

        } else if (salario > 2826.66 && salario <= 3751.05) {
            double calculoIRRF = ((salario * 0.15) / 100) - 370.40;
            double novoSalario = salario - calculoIRRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;

        } else if (salario > 3751.06 && salario <= 4664.68) {
            double calculoIRRF = ((salario * 0.225) / 100) - 651.73;
            double novoSalario = salario - calculoIRRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;
        } else {
            double calculoIRRF = ((salario * 0.275) / 100) - 884.96;
            double novoSalario = salario - calculoIRRF;
            novoSalarioCalculado = String.valueOf(novoSalario);
            return novoSalarioCalculado;
        }


    }

    private int calcularAnoNascimento(int idade) {
        LocalDate datalocal = LocalDate.now();
        int anoAtual = datalocal.getYear();
        return anoAtual - idade;
    }


    private InformacaoIMC calcularimc(double altura, double peso) {
        double imc = peso / (altura * altura);

        InformacaoIMC imccalculado = new InformacaoIMC();

        if (imc < 18.5) {
            imccalculado.setImc(String.valueOf(imc));
            imccalculado.setClassificação(" e você está abaixo do peso.");
            return imccalculado;
        } else if (imc >= 18.5 && imc <= 24.9) {
            imccalculado.setImc(String.valueOf(imc));
            imccalculado.setClassificação("e voce está no peso ideal");
            return imccalculado;
        } else if (imc > 24.9 && imc <= 29.9) {
            imccalculado.setImc(String.valueOf(imc));
            imccalculado.setClassificação("e voce está acima do peso.");
            return imccalculado;
        } else if (imc > 29.9 && imc <= 34.9) {
            imccalculado.setImc(String.valueOf(imc));
            imccalculado.setClassificação(" e voce está obesidade classe 1. ");
            return imccalculado;
        } else if (imc > 34.9 && imc <= 39.9) {
            imccalculado.setImc(String.valueOf(imc));
            imccalculado.setClassificação(" e voce está obesidade classe 2 .");
            return imccalculado;
        } else {
            imccalculado.setImc(String.valueOf(imc));
            imccalculado.setClassificação("e voce está obesidade classe 3");
            return imccalculado;
        }

    }


}
