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
			<spring:message code="meeting.view.title" /> ${meeting.name} 
		</h2>
	</div>
	<div class="portlet-section-menu">
		<a href="<portlet:renderURL portletMode="view"/>" class="back"><spring:message
			code="button.home" />
		</a> <a
		href="<portlet:renderURL><portlet:param name="action" value="editMeeting"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:renderURL>"
		class="update"><spring:message code="meeting.edit.title" />
		</a> <a
		href="<portlet:actionURL><portlet:param name="action" value="deleteMeeting"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:actionURL>"
		class="delete"
		onclick="return(confirm('<spring:message code="meeting.delete.confirm"/>'));"><spring:message
			code="meeting.delete.title" />
		</a> <a
		href="<portlet:actionURL><portlet:param name="action" value="moderateMeeting"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:actionURL>"
		class="bbb"><spring:message code="meeting.moderate.title" />
		</a>
	</div>
	<div class="portlet-section-body">
		
		<div class="portlet-section-meeting">

			<table border="0" cellpadding="4">
				<tr>
					<th class="portlet-form-field-label"><spring:message
							code="meeting.label.welcome" />
					</th>
					<td>${meeting.welcome}</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message
							code="meeting.label.voiceBridge" />
					</th>
					<td>${meeting.voiceBridge}</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message
							code="meeting.label.record" />
					</th>
					<td><input type="checkbox" disabled="disabled" <c:if test="${meeting.record==true}">checked="checked"</c:if>/></td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message
							code="meeting.label.meetingDate" />
					</th>
					<td><fmt:formatDate value="${meeting.meetingDate}" pattern="dd-MM-yyyy HH:mm" />
					</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message
							code="meeting.label.meetingDuration" />
					</th>
					<fmt:parseDate value="${meeting.meetingDuration}"
						var="parsedduration" pattern="HH:mm" type="time" />
					<td><fmt:formatDate value="${parsedduration}" pattern="HH:mm" />
					</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message
							code="meeting.label.creationDate" />
					</th>
					<td><fmt:formatDate value="${meeting.creationDate}"
							type="both" dateStyle="medium" timeStyle="short" />
					</td>
				</tr>

			</table>
		</div>
		<div class="portlet-section-data">
			<h3>
				<spring:message code="invitation.view.title" />
			</h3>
			<table border="0" cellpadding="4">

				<tr>
					<th><spring:message code="invitation.label.displayname" />
					</th>
					<th><spring:message code="invitation.label.emailadress" />
					</th>
					<th><spring:message code="invitation.label.creationdate" />
					</th>
					<th><spring:message code="invitation.action.title" />
					</th>
				</tr>

				<c:forEach items="${invitations}" var="invitation">
					<tr>
						<td>${invitation.displayName}</td>
						<td>${invitation.emailAdress}</td>
						<td><fmt:formatDate value="${invitation.creationDate}" pattern="dd-MM-yyyy HH:mm" /></td>
						<td><a
							href="<portlet:actionURL><portlet:param name="action" value="sendInvitation"/><portlet:param name="invitation" value="${invitation.id}"/></portlet:actionURL>"
							class="mail"><spring:message code="invitation.email.sendagain" />
						</a> <a
							href="<portlet:actionURL><portlet:param name="action" value="deleteInvitation"/><portlet:param name="invitation" value="${invitation.id}"/></portlet:actionURL>"
							class="delete"
							onclick="return(confirm('<spring:message code="invitation.delete.confirm"/>'));"><spring:message
									code="invitation.delete.title" />
						</a></td>
					</tr>
				</c:forEach>
				
				<portlet:actionURL var="actionUrl">
					<portlet:param name="action" value="addInvitation" />
				</portlet:actionURL>
				
				<form:form modelAttribute="invitation" name="invitation" method="post" action="${actionUrl}">
				<input name="meeting" type="hidden" value="${meeting.id}"/>
				<tr>
					<td><form:input cssClass="portlet-form-input-field"
							path="displayName" size="30" maxlength="100" />
						
					</td>
					<td><form:input cssClass="portlet-form-input-field"
							path="emailAdress" size="30" maxlength="100" />
					</td>
					<td>
					</td>
					<td><input type="submit" name="_finish" value="<spring:message code="button.sendinvitation"/>" />
					</td>
				</tr>

				<tr>
					<td><form:errors cssClass="portlet-msg-error" path="displayName" /></td>
					<td colspan="2"><form:errors cssClass="portlet-msg-error" path="emailAdress" /></td>
				</tr>
				</form:form>
				<tr>
					<td><a href="<portlet:renderURL><portlet:param name="action" value="ldapSearch"/>
							<portlet:param name="meeting" value="${meeting.id}"/></portlet:renderURL>" class="ldap">
							<spring:message code="ldapsearch.shorttitle" />
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="portlet-section-footer">
		
	</div>
</div>