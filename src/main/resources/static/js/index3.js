$(document).ready(function()
{
	var myCreatedElement = document.createElement("input");
	var myContainer = document.getElementById("parameters");
	
	//setAttribute() is used to create attributes or change it:
	myCreatedElement.setAttribute("id","myId");
	
	//here you add the element you created using appendChild()
	myContainer.appendChild(myCreatedElement);
});  	