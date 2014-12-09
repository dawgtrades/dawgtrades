<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>
<title>AuctionItem/title>
</head>
<body>
<h1>DawgTrades</h1>

<h3>You are logged in as ${username}

<form action="Login" method="post">
<p> Name of item: <input name="username"  type="text" size="40"> </p>
<p> 
	<div data-role="fieldcontain">
	    <label for="select-1">Select Category</label>
	    <select name="select-1" id="select-1" data-mini="true">
		<#list categories as category>
			<option value="category">${category}</option>		
		</#list>
		</select>
	</div> 
 </p>
<p> Product ID: <input name="identifier"  type="text" size="20">
<p> Description: <input name="description" type="text" size="500">
<p> Starting price: <input name="minprice" type="number" size="10">
<p> 
	<div data-role="controlgroup" data-type="horizontal" data-mini="true">
		<input type=submit data-role="button" value="Submit">
		<input type=reset data-role="button" value="Reset">
	</div>
</p>
</form>
</body>
</html>