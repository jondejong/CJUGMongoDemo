<%@ page import="mongo.demo.Family" %>



<div class="fieldcontain ${hasErrors(bean: familyInstance, field: 'familyName', 'error')} ">
	<label for="familyName">
		<g:message code="family.familyName.label" default="Family Name" />
		
	</label>
	<g:textField name="familyName" value="${familyInstance?.familyName}" />

</div>

<div class="fieldcontain ${hasErrors(bean: familyInstance, field: 'people', 'error')} ">
	<label for="people">
		<g:message code="family.people.label" default="People" />
		
	</label>
	<g:select name="people" from="${mongo.demo.Person.list()}" multiple="multiple" optionKey="id" size="5" required="" value="${familyInstance?.people*.id}" class="many-to-many"/>

</div>

