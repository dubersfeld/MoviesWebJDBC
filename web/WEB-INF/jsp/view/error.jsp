<spring:message code="title.generalError" var="title" />
<template:main htmlTitle="${title}" bodyTitle="${title}">

 <jsp:attribute name="localeContent"><br/>
 
	Language:<br/>
	<a href="?locale=fr_FR">Français</a><br/>
	<a href="?locale=en_US">English</a>
	<br/><br/>
 </jsp:attribute>


 <jsp:attribute name="navigationContent"><br/>
   
 	<a href="<c:url value="/actorQueries" />"><spring:message code="index.basicActorQueries" /></a><br />
 	<a href="<c:url value="/directorQueries" />"><spring:message code="index.basicDirectorQueries" /></a><br />
 	<a href="<c:url value="/movieQueries" />"><spring:message code="index.basicMovieQueries" /></a><br />
 	<a href="<c:url value="/advancedQueries" />"><spring:message code="index.advancedQueries" /></a><br />

  </jsp:attribute>

</template:main>



