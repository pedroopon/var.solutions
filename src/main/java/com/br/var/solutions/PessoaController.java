package com.br.var.solutions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String imc = null;
        int anoNascimento = 0;
        String impostoRenda = null;
        double conversao = 0 ;


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
            Object resumo = montarRespostaFrontEnd(imc, anoNascimento, impostoRenda);


            return ResponseEntity.ok(resumo);

        }

        return ResponseEntity.noContent().build();

    }

    private double ConversaoDolart(double saldo) {
        return 0;
    }

    private Object montarRespostaFrontEnd(String imc, int anoNascimento, String impostoRenda) {
        return null;
    }


    private String CalcularFaixaImporto(double salario) {
        return null;
    }

    private int calcularAnoNascimento(int idade) {
        return 0;

    }

    private String calcularimc(double altura, double peso) {
        double imc = peso / (altura * altura);

        if (imc <= 18.5){
            return "O IMC calculado é: " + imc + " e você está abaixo do peso.  ";
        } else if (imc > 18.5 && imc <= 24.9) {
            return "o seu IMC calculado é: " + imc + " e voce está no peso ideal. ";
        } else if (imc > 24.9 && imc <= 29.9) {
            return "o seu IMC calculado é: " + imc + " e voce está acima do peso. ";
        }

    }


}
