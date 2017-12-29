<%-- 
    Document   : Login
    Created on : 21/06/2017, 07:56:45 PM
    Author     : Jose Segovia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="w3.css">
        <!--Estos Scripts se encargan de redireccionar a MovilServlet si se ingresa con un dispositivo movil-->
        <script type="text/javascript"> 
            var device = navigator.userAgent;
            if (device.match(/Iphone/i)|| device.match(/Ipod/i)|| device.match(/Android/i)|| device.match(/J2ME/i)|| device.match(/BlackBerry/i)|| device.match(/iPhone|iPad|iPod/i)|| device.match(/Opera Mini/i)|| device.match(/IEMobile/i)|| device.match(/Mobile/i)|| device.match(/Windows Phone/i)|| device.match(/windows mobile/i)|| device.match(/windows ce/i)|| device.match(/webOS/i)|| device.match(/palm/i)|| device.match(/bada/i)|| device.match(/series60/i)|| device.match(/nokia/i)|| device.match(/symbian/i)|| device.match(/HTC/i)){
                window.location = "MovilServlet";
            }
            else{
            } 
        </script>
        <script type="text/javascript">
            if(screen.width<800){
                window.location="MovilServlet";
            }
        </script> 
            
    </head>
    <body>
        <%@include file="Navegacionjsp.jspf" %>
        <div class="w3-container w3-center w3-blue-gray body-height">
                <h1>Debe Loguearse</h1>
                <!--Se hace un Form para pedir Nombre de Usuario y Contraseña y se envia a LoginServlet para la verificacion-->
                <form action="LoginServlet" class="w3-container" style="width: 50%;margin-top: 3% ;margin-left: auto; margin-right: auto">
                    <label class="w3-text-purple w3-left"><b>Nombre</b></label>
                    <input type="text" class="w3-input w3-border w3-khaki" name="nombre" value="" required autofocus="">
                    <label class="w3-text-purple w3-left"><b>Contraseña</b></label>
                    <input type="password" name="pass" value="" class="w3-input w3-border w3-khaki" required />
                        <input type="submit" class="w3-btn w3-blue w3-margin-top" value="Loguearse" /> 
                        <input type="hidden" name="vaccion" value="ingresar"> 
                </form>
        </div>
    </body>
</html>
