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

        <title>Upload Form to Database</title>
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


        <!-- Generate the KendoUI form using the script tag -->
        <form method="post" action="formSubmit" class="k-form k-content"
            style="width: 40%; margin: 0 auto; text-align: center">
            <div id="form"></div>
        </form>
        <script>
            $("#navbar").kendoMenu();

            $("#form").kendoForm(
                {
                    orientation: "vertical",
                    items: [{
                        type: "group",
                        label: "Data insertion Form",
                        items: [
                            {
                                field: "ID",
                                label: "ID:",
                                validation: {
                                    required: true,
                                    pattern: {
                                        value: "[0-9]+",
                                        message: "Only numbers are allowed"
                                    }
                                },
                                attributes: {
                                    type: "text",
                                    name: "ID",
                                    placeholder: "Enter your ID",
                                    pattern: "[0-9]*"
                                },
                            },
                            {
                                field: "Name",
                                label: "Name:",
                                validation: {
                                    required: true
                                },
                                attributes: {
                                    type: "text",
                                    name: "Name",
                                    placeholder: "Enter your name",
                                    pattern: "[A-Za-z]*"
                                },
                                pattern: {
                                    value: "[A-Za-z]+",
                                    message: "Only alphabets are allowed"
                                }
                            },
                            {
                                field: "Address",
                                label: "Address:",
                                validation: {
                                    required: true
                                },
                                attributes: {
                                    type: "text",
                                    name: "Address",
                                    placeholder: "Enter your Address"
                                },
                            },
                            {
                                field: "Email",
                                label: "Email:",
                                validation: {
                                    required: true,
                                    email: true
                                },
                                attributes: {
                                    type: "text",
                                    name: "Email",
                                    placeholder: "Enter your Email Address"
                                }
                            },
                            {
                                field: "Phone",
                                label: "Phone No:",
                                validation: {
                                    required: true
                                },
                                attributes: {
                                    type: "text",
                                    name: "Phone",
                                    maxlength: "10",
                                    placeholder: "Enter your 10-Digit Phone number",
                                    pattern: "[0-9]*"
                                },
                                pattern: {
                                    value: "^\\d{10}$",
                                    message: "Enter a valid phone number"
                                }
                            },

                            {
                                field: "State",
                                label: "State:",
                                validation: {
                                    required: true,
                                    pattern: {
                                        value: "[A-Za-z ]+",
                                        message: "Only alphabets and spaces are allowed"
                                    }
                                },
                                attributes: {
                                    type: "text",
                                    name: "State",
                                    placeholder: "Enter your State",
                                    pattern: "[A-Za-z]*"
                                },
                            },
                            {
                                field: "Place",
                                label: "Place:",
                                validation: {
                                    required: true,
                                    pattern: {
                                        value: "[A-Za-z ]+",
                                        message: "Only alphabets and spaces are allowed"
                                    }
                                },
                                attributes: {
                                    type: "text",
                                    name: "Place",
                                    placeholder: "Enter your Place",
                                    pattern: "[A-Za-z]*"
                                },
                            }
                        ]
                    }],
                    validation: {
                        messages: {
                            required: "This field is required"
                        }
                    }
                });

        </script>
    </body>
    </html>