
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fishhub</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="<c:url value="resources/css/auth.css" />">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="panel main-panel">
                    <div class="panel-mid-block">
                        <div class="logo-block">
                            <img class="logo" src="<c:url value="resources/images/fh_paper_logo.png" />">
                        </div>
                        <div class="manual-auth-block">
                            <form method="post" action="home">
                                <div class="add-on">
                                    <i class="fa fa-user"></i>
                                    <input type="text" class="input-lg" placeholder="User name">
                                </div>
                                <div class="add-on">
                                    <i class="fa fa-lock"></i>
                                    <input type="password" class="input-lg" placeholder="User password">
                                </div>
                                <input type="submit" class="btn btn-lg login-button" value="LOG IN">
                                <input type="checkbox" name="logged" value="false"><span class="checkbox-label">Keep me logged in</span>
                            </form>
                        </div>
                        <hr>
                        <div class="social-auth-block">
                            <button class="btn btn-lg fb-button">
                                <i class="fa fa-facebook-official"></i>Login with <span
                                    class="bold">facebook</span>
                            </button>
                            <div class="dev-or-block">
                                <span class="dev-or">or</span>
                            </div>
                            <button class="btn btn-lg ggl-button">
                                <i class="fa fa-google-plus"></i>Login with <span
                                    class="bold">google+</span>
                            </button>
                        </div>
                    </div>
                    <div class="panel-footer">
                        Don't have account? <a href="<c:url value="signup" />"><span class="bold">SIGN UP</span></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
