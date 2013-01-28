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
<link rel="stylesheet" type="text/css" href="<html:cssPath/>jquery-ui-1.8.23.css" />
<script type="text/javascript" src="<html:jsPath/>jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<html:jsPath/>jquery-ui-1.8.23.min.js"></script>
<script  type="text/javascript" src="<html:jsPath/>jquery-ui-timepicker-addon.js"></script>

<div class="esup-portlet-bigbluebutton">

	<div class="portlet-section-header">
		<h2>
			<spring:message code="meeting.add.title" />
		</h2>
	</div>
	<div class="portlet-section-menu">
		<a href="<portlet:renderURL portletMode="view"/>" class="back"><spring:message code="button.home" />
		</a>
	</div>
	<div class="portlet-section-body">

		<portlet:actionURL var="actionUrl">
			<portlet:param name="action" value="addMeeting" />
		</portlet:actionURL>

		<form:form modelAttribute="meeting" name="meeting" method="post"
			action="${actionUrl}">

			<table border="0" cellpadding="4">
				<tr>
					<th class="portlet-form-field-label"><spring:message code="meeting.label.name" />
						<a href="#" id="help"><img src="<html:imagesPath/>help.png" alt="<spring:message code="meeting.help.alt" />"></a>
						<span class="help"><spring:message code="meeting.help.name" /></span>
					</th>
					<td><form:input cssClass="portlet-form-input-field"	path="name" size="30" maxlength="80" />
					</td>
					<td><form:errors cssClass="portlet-msg-error" path="name" />
					</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message code="meeting.label.welcome" />
						<a href="#" id="help"><img src="<html:imagesPath/>help.png" alt="<spring:message code="meeting.help.alt" />"></a>
						<span class="help"><spring:message code="meeting.help.welcome" /></span>
					</th>
					<td><form:textarea cssClass="portlet-form-input-field"	path="welcome" rows="10" cols="30" maxlength="255" />
					</td>
					<td><form:errors cssClass="portlet-msg-error" path="welcome" />
					</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message code="meeting.label.meetingDate" />
						<a href="#" id="help"><img src="<html:imagesPath/>help.png" alt="<spring:message code="meeting.help.alt" />"></a>
						<span class="help"><spring:message code="meeting.help.meetingDate" /></span>
					</th>
					<td><form:input cssClass="portlet-form-input-field"
							path="meetingDate" size="30" maxlength="80" readonly="true"
							id="date_picker" />
					</td>
					<td><form:errors cssClass="portlet-msg-error"
							path="meetingDate" />
					</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message code="meeting.label.meetingDuration" />
						<a href="#" id="help"><img src="<html:imagesPath/>help.png" alt="<spring:message code="meeting.help.alt" />"></a>
						<span class="help"><spring:message code="meeting.help.meetingDuration" /></span>
					</th>
					<td><form:input cssClass="portlet-form-input-field"
							path="meetingDuration" size="30" maxlength="80" />
					</td>
					<td><form:errors cssClass="portlet-msg-error"
							path="meetingDuration" />
					</td>
				</tr>
				<tr>
					<th class="portlet-form-field-label"><spring:message code="meeting.label.record" />
						<a href="#" id="help"><img src="<html:imagesPath/>help.png" alt="<spring:message code="meeting.help.alt" />"></a>
						<span class="help"><spring:message code="meeting.help.record" /></span>
					</th>
					<td><form:checkbox cssClass="portlet-form-input-field"
							path="record"/>
					</td>
				</tr>
				<tr>
					<th colspan="3"><input type="submit" name="_finish"
						value="<spring:message code="button.finish"/>" /> <input
						type="submit" name="_cancel"
						value="<spring:message code="button.cancel"/>" /></th>
				</tr>
			</table>

		</form:form>

	</div>

	<div class="portlet-section-footer">
	</div>
	
	<script  type="text/javascript" src="<html:jsPath/>meeting.js"></script>
</div>