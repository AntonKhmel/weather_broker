<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" src="resources/javascript/query.js"></script>

<page:template>
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Weather broker</h1>
                    <ol class="breadcrumb">
                        <li class="active">Execute the query</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="list-group">
                        Name city: <input type="text" id="city">
                        <br><br>
                        Select the type of request: <select id="request">
                            <option value="yahoo">Get weather yahoo</option>
                            <option value="db">Get weather database</option>
                        </select>
                        <br><br>
                        <button type="button" onclick="Clear()">Clear</button>
                        <button type="button" onclick="GetWeather()">Get</button>
                    </div>
                </div>
                <div class="col-md-9">
                    <pre id = 'result'></pre>
                </div>
            </div>
            <hr>
        </div>
    </jsp:body>
</page:template>

