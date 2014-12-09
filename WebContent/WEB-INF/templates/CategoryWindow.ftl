<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>CategoryWindow</title>
</head>

<body>
<h1>DawgTrades</h1>
<h3>You are logged in as ${username}</h3>

<p>
Please select a category!
<div class="boxed">
  <form action="findSubcategoriesOfCategory" method="Get">
    <p>
      <div>
	<label for="catlist"/>
	<select name="catlist" id="catlist">
	  <c:forEach items="${root}" var="cat">
	    <option value="${root.get(cat)}">${root.get(cat).getName()}</option>
	</select>
      </div>
    </p>
  </form>
</div>
</p>

</body>
</html>