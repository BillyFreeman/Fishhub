
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fishhub</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/css/auth.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="panel main-panel">
                    <div class="panel-mid-block">
                        <div class="logo-block">
                            <img class="logo" src="resources/images/fh_paper_logo.png">
                        </div>
                        <div class="manual-auth-block">
                            <form:form method="post" action="register" modelAttribute="regDTO">
                                <div class="add-on">
                                    <i class="fa fa-user"></i>
                                    <form:input path="username" type="text" class="input-lg" placeholder="User name" />
                                    <form:errors path="username" element="div" class="fh-error" />
                                </div>
                                <div class="add-on">
                                    <i class="fa fa-envelope-o"></i>
                                    <form:input path="email" type="email" class="input-lg" placeholder="Email" />
                                    <form:errors path="email" element="div" class="fh-error"/>
                                </div>
                                <div class="add-on">
                                    <i class="fa fa-lock"></i>
                                    <form:input path="password" type="password" class="input-lg" placeholder="User password" />
                                    <form:errors path="password" element="div" class="fh-error"/>
                                </div>
                                <div class="add-on">
                                    <i class="fa fa-lock"></i>
                                    <form:input path="confirmPassword" type="password" class="input-lg" placeholder="Confirm password" />
                                    <form:errors path="confirmPassword" element="div" class="fh-error"/>
                                </div>
                                <input type="submit" class="btn btn-lg login-button" value="SIGN IN">
                            </form:form>
                        </div>
                    </div>
                    <div class="panel-footer">
                        Already registered? <a href="signin"><span class="bold">SIGN IN</span></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
