<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>

		<style>
			html {
				font-size: 14px;
				font-family: Arial, Helvetica, sans-serif;
			}
		</style>

		<title>Upload XLSX to Database</title>

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
		<div style="margin-top: 1em;"></div>
		<div id="example" style="width: 60%; margin: 0 auto; text-align: center">
			<form method="post" action="uploadFile" enctype="multipart/form-data">
				<div class="upload-section">
					<input name="file" id="file" type="file" aria-label="file" />

					<p></p>
					<div class="demo-hint">
						You can only upload Microsoft Excel <strong>.XLSX</strong> and <strong>.XLS</strong>
						files.
					</div>
					<div class="demo-hint">
						Maximum allowed file size is <strong>10MB</strong>.
					</div>
					<p></p>
					<p>
						<button type="submit" id="submit-btn"
							class="k-button k-button-solid-primary k-button-solid k-button-md k-rounded-md"
							disabled>Submit to Database</button>
					</p>
				</div>
			</form>
			<form action="downloadExcel" method="get">
				<button type="submit" id="download-btn"
					class="k-button k-button-solid-primary k-button-solid k-button-md k-rounded-md">Download Database as
					Excel</button>
			</form>
		</div>

		<script>
			$(document)
				.ready(
					function () {

						$("#navbar").kendoMenu();

						$("#file").kendoUpload({
							validation: {
								maxFileSize: 10000000,
								allowedExtensions: [".xls", ".xlsx"]
							},
							multiple: false,
							select: function (e) {
								if (e.files[0].extension.toLowerCase() === ".xls" || e.files[0].extension.toLowerCase() === ".xlsx") {
									$("#submit-btn").prop("disabled", false);
								} else {
									$("#submit-btn").prop("disabled", true);
								}
							}
						});



					});
		</script>

	</body>
	<style>
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

		#file {
			margin-top: 30px;
		}
	</style>

	</html>