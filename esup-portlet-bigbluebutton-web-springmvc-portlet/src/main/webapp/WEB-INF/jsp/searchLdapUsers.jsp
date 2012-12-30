<%--

    Copyright (C) 2012 Esup Portail http://www.esup-portail.org
    Copyright (C) 2012 CUFR Jean François Champollion http://www.univ-jfc.fr
    @Author (C) 2012 Franck Bordinat <franck.bordinat@univ-jfc.fr>
   
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>

<%@ include file="/WEB-INF/jsp/include.jsp"%>


<link rel="stylesheet" href="<html:cssPath/>main.css" type="text/css" />
<portlet:renderURL var="renderRefreshUrl" />

<div class="esup-portlet-bigbluebutton">

	<div class="portlet-section-header">
		<h2>
			<spring:message code="ldapsearch.title" /> 
		</h2>
	</div>
	<div class="portlet-section-menu">
		<a href="<portlet:renderURL><portlet:param name="action" value="viewMeeting"/><portlet:param name="meeting" value="${meeting}"/></portlet:renderURL>"
			class="back"><spring:message code="invitation.view.title" />
		</a>
	</div>
	<div class="portlet-section-body">

		<div class="portlet-section-searchform">
			<table>
				<portlet:actionURL var="actionUrl">
				<portlet:param name="action" value="ldapSearch" />
				</portlet:actionURL>
				
				<form:form modelAttribute="ldapsearch" name="ldapsearch" method="post" action="${actionUrl}">
				<tr>
					<td><form:input cssClass="portlet-form-input-field"
							path="displayName" size="30" maxlength="100" />
						
					</td>
					<td><input type="submit" name="_finish" value="<spring:message code="button.search"/>" />
					<input name="meeting" type="hidden" value="${meeting}"/>
					</td>
				</tr>
				<tr>
					<td><form:errors cssClass="portlet-msg-error" path="displayName" />${message}</td>
				</tr>
				</form:form>
				
			</table>
		</div>
		<div class="portlet-section-data">
			<h3>
				<spring:message code="ldapsearch.result.title" />
			</h3>
			<table border="0" cellpadding="4">

				<tr>
					<th><spring:message code="invitation.label.displayname" />
					</th>
					<th><spring:message code="invitation.label.emailadress" />
					</th>
					<th><spring:message code="invitation.action.title" />
					</th>
				</tr>

				<c:forEach items="${ldapusers}" var="user">
					<tr>
						<td>${user.displayName}</td>
						<td>${user.emailAdress}</td>
						<td><a
							href="<portlet:actionURL><portlet:param name="action" value="addInvitationFromLdap"/><portlet:param name="meeting" value="${meeting}"/>
							<portlet:param name="displayName" value="${user.displayName}"/>
							<portlet:param name="emailAdress" value="${user.emailAdress}"/></portlet:actionURL>"
							class="mail"><spring:message code="button.sendinvitation" />
						</a></td>
					</tr>
				</c:forEach>
				
			</table>
		</div>
	</div>
	<div class="portlet-section-footer">
		
	</div>
</div>