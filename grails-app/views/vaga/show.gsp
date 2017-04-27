
<%@ page import="exemplodaauladetestes.Vaga" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'vaga.label', default: 'Vaga')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-vaga" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-vaga" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list vaga">
			
				<g:if test="${vagaInstance?.descricao}">
				<li class="fieldcontain">
					<span id="descricao-label" class="property-label"><g:message code="vaga.descricao.label" default="Descricao" /></span>
					
						<span class="property-value" aria-labelledby="descricao-label"><g:fieldValue bean="${vagaInstance}" field="descricao"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${vagaInstance?.ocupada}">
				<li class="fieldcontain">
					<span id="ocupada-label" class="property-label"><g:message code="vaga.ocupada.label" default="Ocupada" /></span>
					
						<span class="property-value" aria-labelledby="ocupada-label"><g:formatBoolean boolean="${vagaInstance?.ocupada}" /></span>
					
				</li>
				</g:if>

				<g:if test="${vagaInstance?.historicoDeReservas}">
					<li class="fieldcontain">
						<span id="historico-label" class="property-label"><g:message code="vaga.historico.label" default="Histórico" /></span>

						<g:each in="${vagaInstance?.historicoDeReservas}" status="i" var="reserva">
							<span class="property-value" aria-labelledby="historico-label">${reserva.entrada}, ${reserva.saida}</span>
						</g:each>
					</li>
				</g:if>

				<g:if test="${vagaInstance?.historicoDeReservas}">
					<li class="fieldcontain">
						<span id="tempo-label" class="property-label"><g:message code="vaga.tempo.label" default="Tempo de ocupação" /></span>

						<span class="property-value" aria-labelledby="tempo-label">${vagaInstance.tempoOcupacaoDHMS().days} dias, ${vagaInstance.tempoOcupacaoDHMS().hours} horas, ${vagaInstance.tempoOcupacaoDHMS().minutes} minutos, ${vagaInstance.tempoOcupacaoDHMS().seconds} segundos</span>
					</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${vagaInstance?.id}" />
					<g:link class="edit" action="edit" id="${vagaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
