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

        <link rel="stylesheet" href="styles/kendo.dimetis.css" />
        <link rel="stylesheet" href="styles/kendo.common.css" />

        <script src="js/jquery.js"></script>
        <script src="js/kendo.all.js"></script>

        <title>View Database</title>
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
        <div id="heading"
            style="font-size: 36px; text-align: center; color: white; margin-top: 50px; text-shadow: 2px 2px 4px #000000;">
        </div>
        <p></p>
        <div id="grid" style="width: 70%; margin: 0 auto; text-align: center"></div>
        <p></p>
        <form action="downloadExcel" method="get" style="   margin: 0 auto; text-align: center">
            <button type="submit" id="download-btn"
                class="k-button k-button-solid-primary k-button-solid k-button-md k-rounded-md">Download Database as
                Excel</button>
        </form>




        <script>
            $(document).ready(function () {

                $("#navbar").kendoMenu();

                $("#heading").text("Database entries :");

                $.ajax({
                    url: 'viewDB',
                    dataType: 'json',
                    success: function (data) {
                        $('#grid').kendoGrid({
                            dataSource: {
                                data: data,
                                schema: {
                                    model: {
                                        id: 'Id',
                                        fields: {
                                            Id: { type: 'string' },
                                            name: { type: 'string' },
                                            address: { type: 'string' },
                                            email: { type: 'string' },
                                            phNo: { type: 'number' },
                                            state: { type: 'string' },
                                            place: { type: 'string' }
                                        }
                                    }
                                },
                                pageSize: 10
                            },
                            height: 550,
                            groupable: true,
                            sortable: true,
                            pageable: {
                                refresh: false,
                                pageSizes: true,
                                buttonCount: 3
                            },
                            columns: [
                                {
                                    field: "id",
                                    title: "ID",
                                    // width: 120
                                },
                                {
                                    field: "name",
                                    title: "Name",
                                    // width: 200
                                },
                                {
                                    field: "address",
                                    title: "Address",
                                    // width: 250
                                },
                                {
                                    field: "email",
                                    title: "Email",
                                    // width: 250
                                },
                                {
                                    field: "phNo",
                                    title: "Phone Number",
                                    // width: 200
                                },
                                {
                                    field: "state",
                                    title: "State",
                                    // width: 150
                                },
                                {
                                    field: "place",
                                    title: "Place",
                                    // width: 150
                                }
                            ]
                        });
                    }
                });


                // Auto-fit columns on window resize
                $(window).resize(function () {
                    var grid = $("#grid").data("kendoGrid");
                    grid.autoFitColumn(0);
                    grid.autoFitColumn(1);
                    grid.autoFitColumn(2);
                    grid.autoFitColumn(3);
                    grid.autoFitColumn(4);
                    grid.autoFitColumn(5);
                    grid.autoFitColumn(6);
                });

            });
        </script>
    </body>

    </html>