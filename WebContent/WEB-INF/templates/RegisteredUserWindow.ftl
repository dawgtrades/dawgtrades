<html>
<head>
<title>RegisteredUserWindow</title>
</head>
<body>

<h1>DawgTrades</h1>

<h3>You are logged in as ${username}</h3>

<p>

<#if users??>

	Please leave feedback on your recent transactions!
	<#list users as user>
		<div class="boxed">
		<form action="CreateExperienceReport" method="Post">
		    <p>You are reviewing: ${user.name}</p>
		    <p> 
		    	<div data-role="fieldcontain">
				<label for="select-1">Select Rating</label>
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
			<p> <input type=submit> <input type=reset> </>
		</form>
		</div>
	</#list>
</#if>
<ol>
<li>
<a href="BrowseItems">Browse Items</a>
</li>
<li>
<a href="AuctionItem">Auction an Item</a>
</li>
<li>
<a href="Profile">View Profile</a>
</li>
<li>
<a href="UserAuctions">View Your Auctions</a>
</li>
<li>
<a href="Login">Unregister</a>
</li>
<li>
<a href="Logout"> Logout</a>
</li>
</ol>

</body>
</html>
