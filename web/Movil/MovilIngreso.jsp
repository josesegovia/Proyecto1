<%-- 
    Document   : MovilIngreso
    Created on : 23/12/2017, 06:41:04 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
    </head>
    <body>
        <header class="w3-container w3-red w3-card w3-center w3-text-black">
            <h1>Hotel 4Quedas</h1>
        </header>
        <div class="w3-center">
            <h3 class="w3-blue w3-text-black w3-card">Debe Loguearse</h3>
            <!--Un Form hacia MovilServlet para Iniciar Sesion-->
            <form action="MovilServlet" class="w3-container form-width w3-green w3-padding-16 centro" method="POST">
                <!--Pide el Nº de Membrecia-->
                <label class="w3-text-purple w3-left"><b>N° de Membrecia</b></label>
                <input type="text" class="w3-input w3-border w3-khaki" name="numero" autofocus="" >
                <!--Pide la Cedula-->
                <label class="w3-text-purple w3-left"><b>Cedula</b></label>
                <input type="text" name="cedula" class="w3-input w3-border w3-khaki" />
                <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Loguearse" /> 
                <!--El input vaccion indica la accion a realizarse-->
                <input type="hidden" name="vaccion" value="ingresar"> 
            </form>
        </div>
    </body>
</html>