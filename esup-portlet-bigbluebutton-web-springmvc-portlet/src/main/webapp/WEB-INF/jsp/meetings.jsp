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
			<spring:message code="meetings.title" />
		</h2>
	</div>

	<div class="portlet-section-body">
		<div class="portlet-section-data">
			<table border="0" cellpadding="4">

				<tr>
					<th><spring:message code="meeting.label.name" />
					</th>
					<th><spring:message code="meeting.label.meetingDate" />
					</th>
					<th><spring:message code="meeting.label.meetingDuration" />
					</th>
					<th><spring:message code="meeting.label.status" />
					</th>
					<th><spring:message code="meeting.action.title" />
					</th>
				</tr>

				<c:forEach items="${meetings}" var="meeting">
					<fmt:parseDate value="${meeting.meetingDuration}"
						var="parsedduration" pattern="HH:mm" type="time" />
					<tr>
						<td>${meeting.name}</td>
						<td><fmt:formatDate value="${meeting.meetingDate}"
								pattern="dd-MM-yyyy HH:mm" />
						</td>
						<td><fmt:formatDate value="${parsedduration}" pattern="HH:mm" />
						</td>
						<td><c:choose>
								<c:when test="${meeting.isrunning==true}">
									<spring:message code="meeting.status.running" />
								</c:when>
								<c:when test="${meeting.isrunning==false}">
									<spring:message code="meeting.status.notrunning" />
								</c:when>
							</c:choose></td>
						<td><a
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
						</a> <a
							href="<portlet:renderURL><portlet:param name="action" value="viewMeeting"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:renderURL>"
							class="mail"><spring:message code="invitation.view.title" />
						</a> <a
							href="<portlet:renderURL><portlet:param name="action" value="viewRecordings"/><portlet:param name="meeting" value="${meeting.id}"/></portlet:renderURL>"
							class="play"><spring:message code="recording.view.title" />
						</a> </td>
					</tr>
				</c:forEach>
				<c:if test="${empty meetings}">
					<tr>
						<td nowrap><spring:message code="meetings.noentries" /></td>
					</tr>				
				</c:if>
				<tr>
					<td colspan="4"><a
						href="<portlet:renderURL><portlet:param name="action" value="addMeeting"/></portlet:renderURL>"
						class="add"><spring:message code="meeting.add.title" />
					</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="portlet-section-footer"></div>
</div>