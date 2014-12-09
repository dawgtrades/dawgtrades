<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AuctionItem</title>
</head>
<body>
<h1>DawgTrades</h1>

<h3>You are logged in as ${username}

<form action="AuctionItem_Result" method="post">
<p> Name of item: <input name="name"  type="text" size="40"> </p>
<p> 
	<div data-role="fieldcontain">
	    <label for="select-1">Select Category</label>
	    <select name="category" id="select-1" data-mini="true">
		<#list categories as category>
			<option value="${category}">${category}</option>		
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