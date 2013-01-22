<%--

    Copyright (C) 2012 Esup Portail http://www.esup-portail.org
    Copyright (C) 2012 CUFR Jean Fran�ois Champollion http://www.univ-jfc.fr
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
<script type="text/javascript" src="<html:jsPath/>calendar.js"></script>

<div class="esup-portlet-bigbluebutton">

	<div class="portlet-section-header">
		<h2>
			<spring:message code="meeting.edit.title" />
		</h2>
	</div>
	<div class="portlet-section-menu">
		<a href="<portlet:renderURL portletMode="view"/>" class="back"><spring:message code="button.home" />
		<a href="<portlet:renderURL><portlet:param name="action" value="viewMeeting"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:renderURL>"
			class="mail"><spring:message code="invitation.view.title" />
		</a>
	</div>
	<div class="portlet-section-body">
	

		<div class="portlet-section-form">
			<portlet:actionURL var="actionUrl">
				<portlet:param name="action" value="editMeeting" />
				<portlet:param name="meeting" value="${meeting.id}" />
			</portlet:actionURL>
	
			<form:form modelAttribute="meeting" method="post"
				action="${actionUrl}">
	
				<table border="0" cellpadding="4">
					<tr>
						<th class="portlet-form-field-label"><spring:message
								code="meeting.label.name" />
						</th>
						<td><form:input cssClass="portlet-form-input-field"
								path="name" size="30" maxlength="80" />
						</td>
						<td><form:errors cssClass="portlet-msg-error" path="name" />
						</td>
					</tr>
					<tr>
						<th class="portlet-form-field-label"><spring:message
								code="meeting.label.welcome" />
						</th>
						<td><form:textarea cssClass="portlet-form-input-field"
								path="welcome" rows="5" cols="30" maxlength="255" />
						</td>
						<td><form:errors cssClass="portlet-msg-error" path="welcome" />
						</td>
					</tr>
					<!--  <tr>
								<th class="portlet-form-field-label"><spring:message code="meeting.label.attendeePW"/></th>
								<td><form:input cssClass="portlet-form-input-field" path="attendeePW" size="30" maxlength="80" /></td>
								<td><form:errors cssClass="portlet-msg-error" path="attendeePW" /></td>
					</tr>
					<tr>
								<th class="portlet-form-field-label"><spring:message code="meeting.label.moderatorPW"/></th>
								<td><form:input cssClass="portlet-form-input-field" path="moderatorPW" size="30" maxlength="80" /></td>
								<td><form:errors cssClass="portlet-msg-error" path="moderatorPW" /></td>
					</tr>-->
					<tr>
						<th class="portlet-form-field-label"><spring:message
								code="meeting.label.meetingDate" />
						</th>
						<td><form:input cssClass="portlet-form-input-field"
								path="meetingDate" size="30" maxlength="80" readonly="true"
								onclick="javascript:NewCssCal ('meetingDate','ddMMyyyy','dropdown',true,'24');" />
							<a href="javascript:void(0);"
							onclick="javascript:NewCssCal ('meetingDate','ddMMyyyy','dropdown',true,'24');"><img
								src="<html:imagesPath/>cal.gif" alt="calendar" border="0">
						</a>
						</td>
						<td><form:errors cssClass="portlet-msg-error"
								path="meetingDate" />
						</td>
					</tr>
					<tr>
						<th class="portlet-form-field-label"><spring:message
								code="meeting.label.meetingDuration" />
						</th>
						<td><form:input cssClass="portlet-form-input-field"
								path="meetingDuration" size="30" maxlength="80" />
						</td>
						<td><form:errors cssClass="portlet-msg-error"
								path="meetingDuration" />
						</td>
					</tr>
					<tr>
						<th class="portlet-form-field-label"><spring:message
								code="meeting.label.record" />
						</th>
						<td><form:checkbox cssClass="portlet-form-input-field"
								path="record"/>
						</td>
					</tr>
					<tr>
						<th colspan="3"><input type="submit" name="_finish"
							value="<spring:message code="button.save"/>" /> <input
							type="submit" name="_cancel"
							value="<spring:message code="button.cancel"/>" /></th>
					</tr>
				</table>
	
			</form:form>
		</div>
	</div>

	<div class="portlet-section-footer">
		</a>
	</div>
</div>