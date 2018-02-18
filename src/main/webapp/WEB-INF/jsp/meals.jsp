<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealsDatatables.js" defer></script>
<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>
        <div class="row">
            <div class="col-sm-7">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form class="form-horizontal" id="filter">
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="startDate"><spring:message code="meal.startDate"/>:</label>
                                <div class="col-sm-3">
                                    <input class="form-control" type="date" id="startDate" name="startDate" value="${param.startDate}">
                                </div>
                                <label class="control-label col-sm-4" for="startTime"><spring:message code="meal.startTime"/>:</label>
                                <div class="col-sm-2">
                                    <input class="form-control" type="time" id="startTime" name="startTime" value="${param.startTime}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="endDate"><spring:message code="meal.endDate"/>:</label>
                                <div class="col-sm-3">
                                    <input class="form-control" type="date" id="endDate" name="endDate" value="${param.endDate}">
                                </div>
                                <label class="control-label col-sm-4" for="endTime"><spring:message code="meal.endTime"/>:</label>
                                <div class="col-sm-2">
                                    <input class="form-control" type="time" id="endTime" name="endTime" value="${param.endTime}">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer text-right">
                        <a class="btn btn-danger" type="button" onclick="cleaneFilter()">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                        <a class="btn btn-primary" type="button" onclick="filter()">
                            <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <a class="btn btn-primary" onclick="add()">
            <span class="glyphicon glyphicon-plus"></span>
            <spring:message code="meal.add"/>
        </a>
        <hr>
        <div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
            <div class="row"></div>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped display dataTable no-footer" id="datatable" role="grid" aria-describedby="datatable_info" style="width: 1140px">
                        <thead>
                        <tr role="row">
                            <th><spring:message code="meal.dateTime"/></th>
                            <th><spring:message code="meal.description"/></th>
                            <th><spring:message code="meal.calories"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <c:forEach items="${meals}" var="meal">
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}" role="row">
                                <td>
                                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                        ${fn:formatDateTime(meal.dateTime)}
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a href="meals/update?id=${meal.id}"><span class="glyphicon glyphicon-pencil"></span></a></td>
                                <td><a class="delete" onclick="deleteRow(${meal.id})" ><span class="glyphicon glyphicon-remove"></span></a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="row"></div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal"  id="detailsForm">
                    <input type="hidden" name="id" value="${meal.id}">
                    <div class="form-group">
                        <label class="control-label col-xs-3" for="dateTime"><spring:message code="meal.dateTime"/>:</label>
                        <div class="col-xs-9"><input class="form-control" id="dateTime" type="datetime-local" value="${meal.dateTime}" name="dateTime" placeholder="<spring:message code="meal.dateTime"/>"></div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-xs-3" for="description"><spring:message code="meal.description"/>:</label>
                        <div class="col-xs-9"><input class="form-control" id="description" type="text" value="${meal.description}" size=40 name="description" placeholder="<spring:message code="meal.description"/>"></div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-xs-3" for="calories"><spring:message code="meal.calories"/>:</label>
                        <div class="col-xs-9"><input class="form-control" id="calories" type="number" value="${meal.calories}" name="calories" placeholder="1000"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="save()" >
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                    <%--<button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>--%>
                </form>
            </div>
        </div>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>