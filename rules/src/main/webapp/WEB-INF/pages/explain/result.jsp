<%@ include file="/common/taglibs.jsp"%>


<table>
	<tr>
		<td>
			<appfuse:label styleClass="desc" key="patient.patientName"/>
			<c:out value="${patient.patientName}"/>
		</td>
		<td>
			<appfuse:label styleClass="desc" key="patient.patientSex"/>
			<c:out value="${patient.sex}"/>
		</td>
		<td>
			<appfuse:label styleClass="desc" key="patient.age"/>
			<c:out value="${patient.age}"/>
		</td>
		<td>
			<appfuse:label styleClass="desc" key="patient.diagnostic"/>
			<c:out value="${patient.diagnostic}"/>
		</td>
	</tr>
	<tr>
		<td>
			<appfuse:label styleClass="desc" key="patient.patientId"/>
			<c:out value="${patient.patientId}"/>
		</td>
		<td>
			<appfuse:label styleClass="desc" key="patient.section"/>
			<c:out value="${patient.section}"/>
		</td>
		<td>
			<appfuse:label styleClass="desc" key="patient.age"/>
			<c:out value="${patient.age}"/>
		</td>
	</tr>
</table>

<h2><c:out value="${patient.examinaim}"/></h2>
