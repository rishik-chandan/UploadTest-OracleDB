<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction Failure</title>
<style>
    .flip-card{
        background-color: transparent;
        width: 190px;
        height: 254px;
        -webkit-perspective: 1000px;
                perspective: 1000px;
        font-family: sans-serif;
    }
    
    .title{
        font-size: 1.5em;
        font-weight: 900;
        text-align: center;
        margin: 0;
    }
    
    .flip-card-inner{
        position: relative;
        width: 100%;
        height: 100%;
        text-align: center;
        -webkit-transition: -webkit-transform 0.8s;
        transition: -webkit-transform 0.8s;
        transition: transform 0.8s;
        transition: transform 0.8s, -webkit-transform 0.8s;
        -webkit-transform-style: preserve-3d;
                transform-style: preserve-3d;
    }
    
    .flip-card:hover .flip-card-inner{
        -webkit-transform: rotateY(180deg);
                transform: rotateY(180deg);
    }
    
    .flip-card-front, .flip-card-back{
        -webkit-box-shadow: 0 8px 14px 0 rgba(0,0,0,0.2);
                box-shadow: 0 8px 14px 0 rgba(0,0,0,0.2);
        position: absolute;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
            -ms-flex-direction: column;
                flex-direction: column;
        -webkit-box-pack: center;
            -ms-flex-pack: center;
                justify-content: center;
        width: 100%;
        height: 100%;
        -webkit-backface-visibility: hidden;
        backface-visibility: hidden;
        border: 1px solid rgb(255, 80, 80);
        border-radius: 1rem;
    }
    
    .flip-card-front{
        background: linear-gradient(120deg, bisque 60%, rgb(255, 231, 222) 88%,
         rgb(255, 211, 195) 40%, rgba(255, 127, 80, 0.603) 48%);
        color: rgb(255, 80, 80);
    }
    
    .flip-card-back{
        background: linear-gradient(120deg, rgb(255, 174, 145) 30%, coral 88%,
         bisque 40%, rgb(255, 185, 160) 78%);
        color: white;
        -webkit-transform: rotateY(180deg);
                transform: rotateY(180deg);
    }
    a:link, a:visited {
      color: white;
      padding: 15px 25px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
    }
    
    a:hover, a:active {
      background-color: red;
    }
    </style>
</head>
<body text="white" style="background-color: #545454;">
    <div class="flip-card" style="margin: 0 auto; text-align: center">
        <div class="flip-card-inner" style="margin: 0 auto; text-align: center">
            <div class="flip-card-front">
                <p class="title">Unknown Error</p>
                <p class="title">Check logs</p>
                <p>Hover Me</p>
            </div>
            <div class="flip-card-back" style="margin: 0 auto; text-align: center">
                <p class="title"><a href="javascript:history.back()">Go Back</a></p>
                <p class="title"><a href ="index.jsp">Home</a></p>
                <p>WebApp made by Rishik Chandan</p>
            </div>
        </div>
    </div>
</body>
</html>