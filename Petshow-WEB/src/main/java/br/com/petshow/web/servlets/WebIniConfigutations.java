package br.com.petshow.web.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.petshow.role.AcessoRole;
import br.com.petshow.util.WriteConsoleUtil;
@Deprecated
//@WebServlet(value="/webIniConfigutations", loadOnStartup=WebServletStartsConstants.START_INI_CONFIGURATIONS)
public class WebIniConfigutations extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6522392989960698269L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		WriteConsoleUtil.write("Iniciando o Servlet para verificar as configurações iniciais básicas do Servidor");
		super.init(config);
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		AcessoRole acesso = context.getBean(AcessoRole.class);
		acesso.load();
		WriteConsoleUtil.write("Acessos carregados...........OK");
	}
	
}
