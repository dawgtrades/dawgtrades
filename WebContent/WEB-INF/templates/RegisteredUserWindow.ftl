<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>
<title>RegisteredUserWindow</title>
</head>
<body>

<h1>DawgTrades</h1>

<h3>You are logged in as ${username}

<p>
<ol>
<li>
<#if experienceReports??>
	Please leave feedback on your recent transactions!
	<#list experienceReports as report>
		<form action="CreateExperienceReport" method="Post">
		    <p>You are reviewing: ${report.reviewed}</p>
		    <p> 
		    	<div data-role="fieldcontain">
				<label for="select-1">Select</label>
				<select name="select-1" id="select-1" data-mini="true">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				</select>
				</div> 
			</p>
			<p> Comments: <input name="report"  type="text" size="20"> </p>
			<p> <input type=submit> <input type=reset> </p>
		</form>
	</#list>
</#if>
</li>
<li>
<a href="BrowseItems" data-role="button">Browse Items</a>
</li>
<li>
<a href="AuctionItem" data-role="button">Auction an Item</a>
</li>
<li>
<a href="Profile" data-role="button">View Profile</a>
</li>
<li>
<a href="UserAuctions" data-role="button">View Your Auctions</a>
</li>
<li>
<a href="Login" data-role="button">Unregister</a>
</li>
<li>
<a href="Logout" data-role="button"> Logout</a>
</li>
</ol>

</body>
</html>
