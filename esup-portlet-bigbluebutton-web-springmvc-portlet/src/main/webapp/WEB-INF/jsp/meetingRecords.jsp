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
		href="<portlet:renderURL><portlet:param name="action" value="viewMeeting"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:renderURL>"
		class="mail"><spring:message code="invitation.view.title" />
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
				<spring:message code="recording.view.title" />
			</h3>
			<p><spring:message code="recording.label.warning" />
			</p>
			<table border="0" cellpadding="4">

				<tr>
					<th><spring:message code="recording.label.recordID" />
					</th>
					<th><spring:message code="recording.label.name" />
					</th>
					<th><spring:message code="recording.label.length" />
					</th>
					<th><spring:message code="recording.label.starttime" />
					</th>
					<th><spring:message code="recording.action.title" />
					</th>
				</tr>

				<c:forEach items="${recordings}" var="recording">
					<tr>
						<td>${recording.recordID}</td>
						<td>${recording.name}</td>
						<td>${recording.length}</td>
						<td>${recording.startTime}</td>
						<td><a
							href="<portlet:actionURL><portlet:param name="action" value="playRecording"/><portlet:param name="playback" value="${recording.playback}"/></portlet:actionURL>"
							class="play"><spring:message code="recording.playback.view" />
						</a> <a
							href="<portlet:actionURL><portlet:param name="action" value="deleteRecording"/><portlet:param name="recordID" value="${recording.recordID}"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:actionURL>"
							class="delete"
							onclick="return(confirm('<spring:message code="recording.delete.confirm"/>'));"><spring:message
									code="recording.delete.title" />
						</a></td>
					</tr>
				</c:forEach>
				<c:if test="${empty recordings}">
					<tr>
						<td nowrap><spring:message code="recording.playback.noentries" /></td>
					</tr>				
				</c:if>
				
				</table>
		</div>
	</div>
	<div class="portlet-section-footer">
		
	</div>
</div>