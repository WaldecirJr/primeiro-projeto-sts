package com.example.pontointeligente.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.*;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import com.example.pontointeligente.api.entities.Empresa;
import com.example.pontointeligente.api.entities.Funcionario;
import com.example.pontointeligente.api.entities.Lancamento;
import com.example.pontointeligente.api.enums.PerfilEnum;
import com.example.pontointeligente.api.enums.TipoEnum;
import com.example.pontointeligente.api.utils.PasswordUtils;
import org.springframework.test.context.junit.jupiter.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @BeforeAll
    public void setUp() throws Exception {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
    }

    @AfterAll
    public void tearDown() throws Exception {
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioId() {
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);

        assertEquals(2, lancamentos.size());
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado() {
       Pageable page = new Pageable() {
           @Override
           public int getPageNumber() {
               return 0;
           }

           @Override
           public int getPageSize() {
               return 10;
           }

           @Override
           public long getOffset() {
               return 0;
           }

           @Override
           public Sort getSort() {
               return null;
           }

           @Override
           public Pageable next() {
               return null;
           }

           @Override
           public Pageable previousOrFirst() {
               return null;
           }

           @Override
           public Pageable first() {
               return null;
           }

           @Override
           public Pageable withPage(int pageNumber) {
               return null;
           }

           @Override
           public boolean hasPrevious() {
               return false;
           }
       };
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);

        assertEquals(2, lancamentos.getTotalElements());
    }

    private Lancamento obterDadosLancamentos(Funcionario funcionario) {
        Lancamento lancameto = new Lancamento();
        lancameto.setData(new Date());
        lancameto.setTipo(TipoEnum.INICIO_ALMOCO);
        lancameto.setFuncionario(funcionario);
        return lancameto;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setCpf("24291173474");
        funcionario.setEmail("email@email.com");
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de exemplo");
        empresa.setCnpj("51463645000100");
        return empresa;
    }

}
