<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>


        <style>
            html {
                font-size: 14px;
                font-family: Arial, Helvetica, sans-serif;
            }

            #navbar {
                background-color: #333;
                overflow: hidden;
            }

            #navbar ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            #navbar li {
                float: left;
            }

            #navbar li a {
                display: block;
                color: white;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
            }

            #navbar li a:hover {
                background-color: #111;
            }
        </style>

        <title>WebApp by Rishik Chandan</title>
        <link rel="stylesheet" href="styles/kendo.dimetis.css" />
        <link rel="stylesheet" href="styles/kendo.common.css" />

        <script src="js/jquery.js"></script>
        <script src="js/kendo.all.js"></script>
    </head>

    <body text="white" style="background-color: #545454;">
        <nav id="navbar" style="width: 60%; margin: 0 auto; text-align: center">
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="upload.jsp">Upload</a></li>
                <li><a href="form.jsp">Form</a></li>
                <li><a href="viewDB.jsp">View Database</a></li>
            </ul>
        </nav>
        <div id="greeting"
            style="font-size: 36px; text-align: center; color: white; margin-top: 50px; text-shadow: 2px 2px 4px #000000;">
        </div>
        <div id="author"
            style="font-size: 24px; text-align: center; color: white; margin-top: 50px; text-shadow: 2px 2px 4px #000000;">
        </div>
        <script>
            $(document).ready(function () {
                $("#navbar").kendoMenu();
                $("#greeting").text("Excel Uploader and Form to SQL Webapp!");
                $("#author").text("Made by Rishik Chandan");
            });
        </script>
    </body>

    </html>