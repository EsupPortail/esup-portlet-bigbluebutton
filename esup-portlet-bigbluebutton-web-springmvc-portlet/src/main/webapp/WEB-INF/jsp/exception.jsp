<%@ include file="/WEB-INF/jsp/include.jsp"%>

<div class="portlet-title">
	<h2>
		<spring:message code="exception.title" />
	</h2>
</div>

<div class="portlet-section">

	<span class="exceptionMessage"> <spring:message
			code="${exceptionMessage}" /> </span> <a href="#"
		id="exception-details-link"> <spring:message
			code="exception.details" /> </a>
	<div class="exception-details">
		<pre>${exceptionStackTrace}</pre>
	</div>

</div>

