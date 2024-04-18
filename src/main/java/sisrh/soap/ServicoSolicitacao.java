package sisrh.soap;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.WebServiceContext;

import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;
import sisrh.dto.Solicitacoes;
import sisrh.seguranca.Autenticador;

@WebService
@SOAPBinding(style = Style.RPC)
public class ServicoSolicitacao {

    @Resource
    WebServiceContext context;

    @WebMethod(action = "solicitar")
    public Solicitacoes solicitar() throws Exception {
        // Obter o nome do usuário autenticado
        String usuario = Autenticador.getUsuario(context);

        Solicitacoes solicitacoes = new Solicitacoes();

        // Verificar se o usuário está autenticado
        if (usuario != null && !usuario.isEmpty()) {
            // Obter solicitações associadas ao usuário autenticado
            List<Solicitacao> lista = Banco.listarSolicitacoes(usuario);
            for (Solicitacao sol : lista) {
                solicitacoes.getSolicitacoes().add(sol);
            }
        } else {
            // Se o usuário não estiver autenticado, retornar uma lista vazia de solicitações
            System.out.println("Usuário não autenticado.");
        }

        return solicitacoes;
    }
}
