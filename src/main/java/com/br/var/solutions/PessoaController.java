package com.br.var.solutions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

//      1                       2                   3              4
//publico/Privado   // tipo de retorno      // nome do metodo // parametros
    //EndPoint
    @CrossOrigin(origins = "*")
    @GetMapping
    public           ResponseEntity<Object>     get(){
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("pedro");
        pessoa1.setSobrenome("Xavier");

 return ResponseEntity.ok(pessoa1);

    }


}
